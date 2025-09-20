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
 * Enumeration that represents the way {@link KActivator} creating a new object
 * of given class. Normally, it is used only by {@link KActivator}
 * and {@link io.github.darthakiranihil.konna.core.object.registry.KObjectRegistry}
 * as it does not have any other purpose.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public enum KObjectInstantiationType {
    /**
     * Created object is immortal.
     * Immortal objects are subtype of singletons that cannot be deleted
     * (the only case to delete it is the application termination)
     */
    IMMORTAL,
    /**
     * Created object is a singleton - created in only one instance.
     * If there are multiple attempts of creating a singleton class after its
     * actual creation, {@link KActivator} will return the created instance
     * instead of instantiating a new object.
     *
     * @see KSingleton
     */
    SINGLETON,
    /**
     * Created object is a weak singleton.
     * Just like singleton,
     * but {@link KActivator} references it through a weak reference.
     *
     * @see KSingleton
     */
    WEAK_SINGLETON,
    /**
     * Created object is a poolable object.
     * Poolable objects are created in {@link KObjectPool}s.
     * When {@link KActivator} tries to create a poolable object,
     * it does not actually create it but retrieves from assigned pool.
     * Similar for deleting - {@link KActivator} just returns poolable object
     * to its place.
     *
     * @see KPoolable
     * @see KObjectPool
     */
    POOLABLE,
    /**
     * Created object is a weak poolable object.
     * Just like poolable, but {@link KWeakObjectPool} are used as pools
     *
     * @see KPoolable
     * @see KWeakObjectPool
     */
    WEAK_POOLABLE,
    /**
     * Created object is a transient object.
     * When {@link KActivator} creates a transient object, it will create
     * a new instance for each call.
     *
     * @see KTransient
     */
    TRANSIENT,
    /**
     * Created object is a temporal object.
     * Just like transient object, but {@link KActivator} does not push
     * it to registry when creating it, that means the only "owner" of the object
     * is one who invoked instantiation. So when it leaves its scope, no one will
     * reference it, and possibly the garbage collection will delete it finally.
     */
    TEMPORAL
}
