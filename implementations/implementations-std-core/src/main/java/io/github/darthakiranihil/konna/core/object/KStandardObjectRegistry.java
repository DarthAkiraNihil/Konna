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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;
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

    private static sealed abstract class RegistryRecord implements KObjectRegistryRecord {

        private final UUID recordId;
        private final UUID objectId;
        private final boolean isSynthetic;
        private final Class<?> objectClass;
        private final Set<String> objectTags;

        public RegistryRecord(Object object) {
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
    }

    private static final class CommonObjectRecord extends RegistryRecord {

        private final WeakReference<?> ref;

        public CommonObjectRecord(final Object object, final WeakReference<?> ref) {
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

        public ImmortalObjectRecord(final Object object) {
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

    public static final KFrameTaskDescription
        REMOVE_DEAD_RECORDS_TASK = KFrameTaskDescription.ofAsyncDelayedPersistent(
        "ObjectsRegistry.removeDeadRecords",
        KFrameEvent.FRAME_FINISHED,
        Integer.MAX_VALUE,
        7200
    );

    private final Map<UUID, KObjectRegistryRecord> records;
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

        this.records = new HashMap<>(INITIAL_CAPACITY);
        this.referenceQueue = new ReferenceQueue<>();

        frameTaskScheduler.scheduleTask(REMOVE_DEAD_RECORDS_TASK, this::removeDeadObjects);
    }

    @Override
    public KObjectRegistryRecord pushObject(final Object object) {

        WeakReference<?> ref = new WeakReference<>(object, this.referenceQueue);
        KObjectRegistryRecord objectRecord = new CommonObjectRecord(object, ref);
        this.records.put(objectRecord.objectId(), objectRecord);
        return objectRecord;

    }

    @Override
    public KObjectRegistryRecord pushImmortalObject(final Object object) {

        KObjectRegistryRecord objectRecord = new ImmortalObjectRecord(object);
        this.records.put(objectRecord.objectId(), objectRecord);
        return objectRecord;

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
            .filter(x -> x.isPresent() && x.getObjectTags().contains(tag))
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<KObjectRegistryRecord> getObjectsOfType(final Class<?> clazz) {
        return this.records
            .values()
            .stream()
            .filter(x -> x.isPresent() && x.getObjectClass().equals(clazz))
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<KObjectRegistryRecord> getObjects() {
        return Set.copyOf(this.records.values());
    }

    private void removeDeadObjects() {
        this.records
            .entrySet()
            .removeIf(x -> !x.getValue().isPresent());

        Object deleted = this.referenceQueue.poll();
        while (deleted != null) {
            // todo: add check if object is managed, then delete
            deleted = this.referenceQueue.poll();
        }
    }

}
