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

package io.github.darthakiranihil.konna.core.di;

import io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException;
import io.github.darthakiranihil.konna.core.object.KObject;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple container for interface-implementation class mapping, that is
 * useful when resolving and injecting dependencies. Supports hierarchical
 * containers that means a parent container can be specified for it.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KContainer extends KObject {

    private final Map<Class<?>, Class<?>> classMap;
    /**
     * Reference to the parent container of this container.
     */
    private final @Nullable KContainer parent;

    /**
     * Zero-arg constructor. Constructs a container without parent one.
     */
    public KContainer() {
        this.classMap = new HashMap<>();
        this.parent = null;
    }

    /**
     * Constructs container with specified parent container.
     * @param parent Parent container
     */
    public KContainer(final KContainer parent) {
        this.classMap = new HashMap<>();
        this.parent = parent;
        this.setParent(parent);
    }

    /**
     * Constructs container with specified container object name.
     * @param name Name of created container object
     */
    public KContainer(final String name) {
        super(name);
        this.classMap = new HashMap<>();
        this.parent = null;
    }

    /**
     * Constructs container with specified parent container and container object name.
     * @param parent Parent container
     * @param name Name of created container object
     */
    public KContainer(final KContainer parent, final String name) {
        super(name, parent);
        this.classMap = new HashMap<>();
        this.parent = parent;
    }

    /**
     * Adds a binding from a given interface to specified implementation of it.
     * @param fromInterface Interface to be bind
     * @param toImplementation Implementation to the interface
     * @return This container
     * @param <IN> Type of the interface
     */
    public <IN> KContainer add(
        final Class<IN> fromInterface,
        final Class<? extends IN> toImplementation
    ) {
        this.classMap.put(fromInterface, toImplementation);
        return this;
    }

    /**
     * Adds a binding from a given class to itself. Useful when class
     * does not have any other implementations, or it is final.
     * @param clazz Class to bind
     * @return This container
     */
    public KContainer add(
        final Class<?> clazz
    ) {
        this.classMap.put(clazz, clazz);
        return this;
    }



    /**
     * Resolves an implementation (or just maps one to another) for given class.
     * The common example is getting class of the implementation of an interface.
     * When resolving, container's classmap has higher priority than its parent container,
     * so if one interface has different mappings in this and parent container, this
     * container's mapping will be returned first. If it fails to find a mapping,
     * {@link KDependencyResolveException} will be thrown.
     * @param clazz Class to resolve
     * @return Mapped resolved class
     * @param <T> Type of resolved class
     */
    @SuppressWarnings("unchecked")
    public <T> Class<T> resolve(final Class<T> clazz) {
        if (this.classMap.containsKey(clazz)) {
            return (Class<T>) this.classMap.get(clazz);
        }

        if (this.parent != null) {
            return this.parent.resolve(clazz);
        }

        throw new KDependencyResolveException(clazz);
    }

}
