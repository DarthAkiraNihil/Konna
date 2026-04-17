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
 * <p>
 *     Interface for the most important utility class in Konna which purpose
 *     is to create new objects with dependency injection support.
 * </p>
 * <p>
 *     Unlike standard instantiating objects with {@code new}, this class can do this
 *     with automatic dependency resolution, injecting dependencies by handling
 *     {@link io.github.darthakiranihil.konna.core.di.KInject} annotation. However,
 *     it's possible to just create object with this class.
 * </p>
 * <p>
 *     An implementation of this class should put created objects in
 *     {@link KObjectRegistry}, at least if created class is instance of {@link KObject}, but this
 *     is not actually required and might be tuned according to your needs.
 * </p>
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KActivator {

    /**
     * <p>
     *     Tries to get instance of specified class <i>or</i> creates an object of that class.
     * </p>
     * <p>
     *     An implementation common implementation of this class should check if passed class
     *     is neither abstract nor an interface, because it defines the way the activator should
     *     work.
     * </p>
     * <p>
     *     However, it's important to notice that some objects are not in containers but still
     *     need to be singleton (for example,
     *     {@link io.github.darthakiranihil.konna.core.app.KApplicationFeatures}). This behavior
     *     might be specified by {@link io.github.darthakiranihil.konna.core.di.KSingleton}. In
     *     this case activator should firstly look if it has been previously created.
     * </p>
     * <p>
     *     In other cases an object, firstly, must be retrieved from a container, if it's abstract
     *     or an interface. Else it should be created as usual, with constructor, field and method
     *     injection.
     * </p>
     * 
     * @param clazz Class to instantiate or to get
     * @return Instance of specified class
     * @param <T> Type of instantiated object
     *
     * @since 0.6.0
     */
    <T> T createObject(Class<? extends T> clazz);

    /**
     * <p>
     *     Creates an object of specified class.
     * </p>
     * <p>
     *     Unlike {@link KActivator#createObject(Class)}, this method's implementation must
     *     forbid instantiation if class is abstract or an interface (by throwing
     *     {@link io.github.darthakiranihil.konna.core.object.except.KInstantiationException}).
     *     However, it must still support dependency injection, but then it have to handle
     *     ordering of {@link KArgs} and injected constructor args (it depends on implementation).
     * </p>
     * @param clazz Class to instantiate
     * @param explicitArgs Explicit instantiation args
     * @return Instance of specified class
     * @param <T> Type of instantiated object
     *
     * @since 0.6.0
     */
    <T> T createObject(Class<? extends T> clazz, KArgs explicitArgs);

}
