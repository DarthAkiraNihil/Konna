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

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KObjectPool<T extends KObject> extends KObject {

    private final class PoolRecord {
        private final T object;
        private boolean inUse;

        public T getObject() {
            return this.object;
        }

        public boolean isInUse() {
            return this.inUse;
        }

        private void setInUse(boolean flag) {
            this.inUse = flag;
        }

        public PoolRecord(final T object) {
            this.object = object;
            this.inUse = false;
        }
    }

    private final Map<T, PoolRecord> objects;
    private final Queue<T> unusedObjects;
    private final KPoolableObjectPolicy<T> objectPolicy;

    public KObjectPool(int initialSize, final KPoolableObjectPolicy<T> objectPolicy) {
        this.objects = new ConcurrentHashMap<>();
        this.unusedObjects = new ConcurrentLinkedQueue<>();
        this.objectPolicy = objectPolicy;

        for (int i = 0; i < initialSize; i++) {
            var object = this.objectPolicy.createInstance();

            this.objects.put(object, new PoolRecord(object));
            this.unusedObjects.add(object);
        }
    }

    public T obtain(final Object... args) {

        var obtained = this.unusedObjects.poll();

        PoolRecord poolRecord = this.objects.get(obtained);
        poolRecord.setInUse(true);
        this.objects.put(obtained, poolRecord);

        this.objectPolicy.onObtain(obtained, args);
        return obtained;

    }

    public void release(final T object) {

        this.objectPolicy.onRelease(object);

        PoolRecord poolRecord = this.objects.get(object);
        poolRecord.setInUse(false);
        this.objects.put(object, poolRecord);

        this.unusedObjects.add(object);

    }


}
