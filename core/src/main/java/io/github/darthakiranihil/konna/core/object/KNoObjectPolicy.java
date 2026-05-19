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

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

enum KNoObjectPolicy {

    THROW_EXCEPTION {
        @Override
        public <T extends KPoolable> Optional<T> obtainRawObject(
            Queue<T> objectQueue,
            Class<?> clazz
        ) {
            if (objectQueue.isEmpty()) {
                throw new KEmptyObjectPoolException(clazz);
            }

            return Optional.of(objectQueue.poll());
        }
    },
    RETURN_EMPTY {
        @Override
        public <T extends KPoolable> Optional<T> obtainRawObject(
            Queue<T> objectQueue,
            Class<?> clazz
        ) {
            return objectQueue.isEmpty()
                ? Optional.empty()
                : Optional.of(objectQueue.poll());
        }
    },
    WAIT {

        private final ReentrantLock lock = new ReentrantLock();
        private final Condition waiting = this.lock.newCondition();
        private volatile boolean flag;

        @Override
        public <T extends KPoolable> Optional<T> obtainRawObject(Queue<T> objectQueue, Class<?> clazz) {

            this.lock.lock();
            try {
                while (objectQueue.isEmpty()) {
                    this.flag = true;
                    this.waiting.await();
                }

                return Optional.of(objectQueue.poll());
            } catch (InterruptedException e) {
                throw new KException(e.getMessage());
            } finally {
                this.lock.unlock();
            }

        }

        @Override
        public void objectReturnedCallback() {
            this.lock.lock();
            try {
                if (this.flag) {
                    this.flag = false;
                    this.waiting.signal();
                }
            } finally {
                this.lock.unlock();
            }

        }
    };

    public static KNoObjectPolicy fromAnnotationMetadata(
        final KAllocatePool.NoObjectPolicy policy
    ) {
        return KNoObjectPolicy.valueOf(policy.name());
    }

    public static String getTypeQualifier(KNoObjectPolicy policy) {
        return switch (policy) {
            case RETURN_EMPTY -> "Forgiving";
            case THROW_EXCEPTION -> "Strict";
            case WAIT -> "Waiting";
        };
    }

    public abstract <T extends KPoolable> Optional<T> obtainRawObject(Queue<T> objectQueue, Class<?> clazz);
    public void objectReturnedCallback() {

    }

}
