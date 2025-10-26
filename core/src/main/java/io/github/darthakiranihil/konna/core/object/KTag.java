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

/**
 * Convenience wrapper for immutable string tag (typically used for {@link KObject}).
 * @param name Name of the tag
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KTag(String name) {
    /**
     * Static class of all Konna default tags.
     */
    public static class DefaultTags {
        /**
         * Marks that the object is a standard implementation
         * of an internal abstract class or interface.
         */
        public static final KTag STD = new KTag("std");
        /**
         * Marks that the object is related to engine system.
         */
        public static final KTag SYSTEM = new KTag("system");
        /**
         * Marks that the object is used or created in test context.
         */
        public static final KTag TEST = new KTag("test");
        /**
         * Marks that the object is immortal.
         * @see KObjectInstantiationType
         * @see KSingleton
         */
        public static final KTag IMMORTAL = new KTag("immortal");
        /**
         * Marks that the object is a singleton.
         * @see KObjectInstantiationType
         * @see KSingleton
         */
        public static final KTag SINGLETON = new KTag("singleton");
        /**
         * Marks that the object is poolable.
         * @see KObjectInstantiationType
         * @see KPoolable
         */
        public static final KTag POOLABLE = new KTag("poolable");
        /**
         * Marks that the object is transient.
         * @see KObjectInstantiationType
         * @see KTransient
         */
        public static final KTag TRANSIENT = new KTag("transient");
        /**
         * Marks that the object is hold by a weak reference.
         * @see KObjectInstantiationType
         * @see KSingleton
         * @see KPoolable
         */
        public static final KTag WEAK = new KTag("weak");
        /**
         * Marks that the object is an object pool.
         * @see KObjectPool
         */
        public static final KTag POOL = new KTag("pool");
        /**
         * Marks that the object is a weak object pool.
         * @see KWeakObjectPool
         */
        public static final KTag WEAK_POOL = new KTag("weak_pool");
        /**
         * Marks that the object is an event.
         * @see io.github.darthakiranihil.konna.core.message.KEvent
         * @see io.github.darthakiranihil.konna.core.message.KSimpleEvent
         */
        public static final KTag EVENT = new KTag("event");
    }
}
