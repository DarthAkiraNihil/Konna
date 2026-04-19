/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.core.object;

import io.github.darthakiranihil.konna.core.app.KFrameEvent;
import io.github.darthakiranihil.konna.core.app.KFrameTaskDescription;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.except.KDeletedObjectException;
import org.jspecify.annotations.Nullable;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Utility class that stores records about all objects, created with
 * {@link KActivator} (excluding temporal). Should be
 * used in debugging, though it is possible to use it in other way.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KStandardObjectRegistry extends KObject implements KObjectRegistry {

    private static final int INITIAL_CAPACITY = 64;

    private abstract static sealed class RegistryRecord implements KObjectRegistryRecord {

        private final UUID recordId;
        private final UUID objectId;
        private final boolean isSynthetic;
        private final Class<?> objectClass;
        private final Set<String> objectTags;

        RegistryRecord(final Object object) {
            this.recordId = UUID.randomUUID();

            Class<?> objectClazz = object.getClass();
            if (KObject.class.isAssignableFrom(objectClazz)) {
                this.objectTags = ((KObject) object).tags();
                this.objectId = ((KObject) object).id();
                this.isSynthetic = false;
            } else {
                this.objectTags = Collections.singleton(KObjectRegistry.SYNTHETIC_TAG);
                this.objectId = this.recordId;
                this.isSynthetic = true;
            }

            this.objectClass = objectClazz;
        }

        @Override
        public UUID recordId() {
            return this.recordId;
        }

        @Override
        public UUID objectId() {
            return this.objectId;
        }

        @Override
        public boolean isSynthetic() {
            return this.isSynthetic;
        }

        @Override
        public Class<?> getObjectClass() {
            return this.objectClass;
        }

        @Override
        public Set<String> getObjectTags() {
            return this.objectTags;
        }

        @Override
        public String toString() {
            if (this.isSynthetic()) {
                return String.format(
                    "[%s] -> %s object (class: %s, synthetic record)",
                    this.recordId,
                    this.isImmortal() ? "immortal" : "common",
                    this.objectClass
                );
            } else {
                return String.format(
                    "[%s] -> [%s], %s object (class: %s)",
                    this.recordId,
                    this.objectId,
                    this.isImmortal() ? "immortal" : "common",
                    this.objectClass
                );
            }
        }
    }

    private static final class CommonObjectRecord extends RegistryRecord {

        private final WeakReference<?> ref;

        CommonObjectRecord(
            final Object object,
            final WeakReference<?> ref
        ) {
            super(object);
            this.ref = ref;
        }

        @Override
        public boolean isPresent() {
            return this.ref.get() != null;
        }

        @Override
        public boolean isImmortal() {
            return false;
        }

        @Override
        public Object getObject() {
            Object object = this.ref.get();
            if (object == null) {
                throw new KDeletedObjectException("Object has been deleted");
            }

            return object;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getCastObject() {
            return (T) this.getObject();
        }

    }

    private static final class ImmortalObjectRecord extends RegistryRecord {

        private final Object object;

        ImmortalObjectRecord(final Object object) {
            super(object);
            this.object = object;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public boolean isImmortal() {
            return true;
        }

        @Override
        public Object getObject() {
            return this.object;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getCastObject() {
            return (T) this.getObject();
        }

    }

    /**
     * Constant for frame task description for removing all dead records, that point to nothing.
     */
    public static final KFrameTaskDescription
        REMOVE_DEAD_RECORDS_TASK = KFrameTaskDescription.ofAsyncDelayedPersistent(
        "ObjectsRegistry.removeDeadRecords",
        KFrameEvent.FRAME_FINISHED,
        Integer.MAX_VALUE,
        7200
    );

    private final Map<UUID, KObjectRegistryRecord> records;
    private final Queue<PhantomReference<?>> phantoms;
    private final ReferenceQueue<Object> referenceQueue;

    @KInject
    public KStandardObjectRegistry(
        final KFrameTaskScheduler frameTaskScheduler
    ) {
        super(
            "ObjectRegistry",
            Set.of(
                KDefaultTags.SYSTEM,
                KDefaultTags.STD
            )
        );

        this.records = new ConcurrentHashMap<>(INITIAL_CAPACITY);
        this.phantoms = new ArrayBlockingQueue<>(INITIAL_CAPACITY);
        this.referenceQueue = new ReferenceQueue<>();

        frameTaskScheduler.scheduleTask(REMOVE_DEAD_RECORDS_TASK, this::postMortem);
    }

    @Override
    public KObjectRegistryRecord pushObject(final Object object) {

        WeakReference<?> ref = new WeakReference<>(object);
        KObjectRegistryRecord objectRecord = new CommonObjectRecord(object, ref);
        this.records.put(objectRecord.objectId(), objectRecord);
        this.phantoms.add(new PhantomReference<>(object, this.referenceQueue));
        return objectRecord;

    }

    @Override
    public KObjectRegistryRecord pushImmortalObject(final Object object) {

        KObjectRegistryRecord objectRecord = new ImmortalObjectRecord(object);
        this.records.put(objectRecord.objectId(), objectRecord);
        this.phantoms.add(new PhantomReference<>(object, this.referenceQueue));
        return objectRecord;

    }

    @Override
    public @Nullable KObjectRegistryRecord getObject(final UUID objectId) {
        return this.records.get(objectId);
    }

    @Override
    public void removeObject(final UUID objectId) {
        this.records.remove(objectId);

    }

    @Override
    public Set<KObjectRegistryRecord> getObjectsWithTag(final String tag) {
        return this.records
            .values()
            .stream()
            .filter(x -> x.getObjectTags().contains(tag))
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<KObjectRegistryRecord> getObjectsOfType(final Class<?> clazz) {
        return this.records
            .values()
            .stream()
            .filter(x -> clazz.isAssignableFrom(x.getObjectClass()))
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<KObjectRegistryRecord> getObjects() {
        return Set.copyOf(this.records.values());
    }

    private void postMortem() {
        Set<UUID> eliminated = this.records
            .entrySet()
            .stream()
            .filter(x -> !x.getValue().isPresent())
            .map(Map.Entry::getKey)
            .collect(Collectors.toUnmodifiableSet());

        this.records
            .entrySet()
            .removeIf(x -> eliminated.contains((x.getKey())));

        Object deleted = this.referenceQueue.poll();
        while (deleted != null) {
            Class<?> clazz = deleted.getClass();
            if (KDeletable.class.isAssignableFrom(clazz)) {
                ((KDeletable) deleted).delete();
            }
            deleted = this.referenceQueue.poll();
        }

        while (!this.phantoms.isEmpty()) {
            this.phantoms.poll(); // dummy code
        }
    }

}
