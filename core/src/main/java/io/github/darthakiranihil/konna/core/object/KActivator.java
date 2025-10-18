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
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.util.KIndex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Interface for the most important utility class in Konna which purpose is to create new objects.
 * Unlike standard instantiating objects with {@code new}, this class can do this
 * with automatic dependency resolution, dispatching different instantiation types
 * and so on. At least it implies here.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract class KActivator extends KObject {

    protected final KContainerResolver containerResolver;
    protected final KObjectRegistry objectRegistry;
    protected final KIndex index;

    public KActivator(
        final KContainerResolver containerResolver,
        final KObjectRegistry objectRegistry,
        final KIndex index
    ) {
        super("activator", new HashSet<>(List.of(KTag.DefaultTags.SYSTEM)));

        this.containerResolver = containerResolver;
        this.objectRegistry = objectRegistry;
        this.index = index;
    }
    /**
     * Creates a new container and returns it. Container may be empty or not, it depends
     * on the implementation.
     * @return A new container
     */
    public abstract KContainer newContainer();

    /**
     * Creates a new object or returns it if it already instantiated (depends on created class).
     * @param clazz Class to instantiate
     * @param container Container (used for dependency resolution)
     * @param <T> Type of object to instantiate
     * @param nonInjectedArgs Arguments that are not injected (passed explicitly)
     *                        when constructing an object
     * @return Created object
     * @see KContainer
     * @see KObjectInstantiationType
     */
    public abstract <T> T create(
        Class<? extends T> clazz,
        KContainer container,
        Object... nonInjectedArgs
    );

    /**
     * Creates a new object or returns it if it already instantiated (depends on created class).
     * Implementation of this method may not require a source of container, but it recommended.
     * @param clazz Class to instantiate
     * @param nonInjectedArgs Arguments that are not injected (passed explicitly)
     *                        when constructing an object
     * @return Created object
     * @param <T> Type of object to instantiate
     *
     * @see KContainer
     * @see KObjectInstantiationType
     */
    public abstract <T> T create(
        Class<? extends T> clazz,
        Object... nonInjectedArgs
    );

    /**
     * "deletes" a created object.
     * Actually, there is no such action as "deleting an object", however, this method's
     * purpose exists - it removes all possible references of the object, making it unreachable
     * by none, so tha garbage collected will delete the object at last. However, poolable objects
     * are just returned to their pool, not wiped.
     *
     * @param object Object to delete
     * @param <T> Type of object to delete
     */
    public abstract <T> void delete(T object);
}
