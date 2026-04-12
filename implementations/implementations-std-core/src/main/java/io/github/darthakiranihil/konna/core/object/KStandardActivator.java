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

import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Standard implementation of {@link KActivator}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("unchecked")
public class KStandardActivator extends KObject implements KActivator {

    private final KAppContainer appContainer;
    private final KObjectRegistry objectRegistry;
    private final Map<Class<?>, Object> singletons;

    /**
     * Standard constructor.
     * @param container Application container to resolve dependencies from
     * @param objectRegistry Object registry to put created objects
     */
    public KStandardActivator(
        final KAppContainer container,
        final KObjectRegistry objectRegistry
    ) {
        super(
            "std_activator",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );

        this.appContainer = container;
        this.objectRegistry = objectRegistry;
        this.singletons = new HashMap<>();
    }

    /**
     * <p>
     *     Tries to get instance of specified class <i>or</i> creates an object of that class.
     * </p>
     * <p>
     *     Firstly, it checks if passed class is neither abstract nor an interface.
     * </p>
     * <p>
     *     Secondly, it reads {@link KSingleton} annotation to check if an object with that class
     *     has been previously created.
     *     If it is true, then returns the created instance.
     * </p>
     * <p>
     *     Else, if passed class is an interface or an abstract class, it gets its instance from
     *     {@link KAppContainer}. If it is not, then, creates an object with constructor,
     *     field, and method injection.
     * </p>
     *
     * @param clazz Class to instantiate or to get
     * @return Instance of specified class
     * @param <T> Type of instantiated object
     *
     */
    @Override
    public <T> T createObject(final Class<? extends T> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            return (T) this.appContainer.getInstance(clazz);
        }
        boolean isSingleton = clazz.isAnnotationPresent(KSingleton.class);
        if (isSingleton) {
            Object object = this.singletons.get(clazz);
            if (object != null) {
                return (T) object;
            }
        }

        Object instantiated = this.injectConstructorAndCreate(clazz);
        this.injectFields(instantiated, clazz);
        this.injectMethods(instantiated, clazz);
        this.pushToRegistry(instantiated);
        if (isSingleton) {
            this.singletons.put(clazz, instantiated);
        }
        return (T) instantiated;

    }

    /**
     * <p>
     *     Creates an object of specified class.
     * </p>
     * <p>
     *     Instantiated object's constructor args must be in that order in which
     *     all explicit args come first, then all injected. Gaps are not allowed!
     * </p>
     * @param clazz Class to instantiate
     * @param explicitArgs Explicit instantiation args
     * @return Instance of specified class
     * @param <T> Type of instantiated object
     * @throws KInvalidArgumentException if clazz is an interface or abstract
     *
     * @since 0.6.0
     */
    @Override
    public <T> T createObject(final Class<? extends T> clazz, final KArgs explicitArgs) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            throw new KInvalidArgumentException(
                "Cannot create object: class is and interface or abstract"
            );
        }

        Object instantiated = this.injectConstructorAndCreate(clazz, explicitArgs);
        this.injectFields(instantiated, clazz);
        this.injectMethods(instantiated, clazz);
        this.pushToRegistry(instantiated);
        return (T) instantiated;
    }

    private Object injectConstructorAndCreate(final Class<?> clazz) {
        Constructor<?> injectedConstructor = this.getConstructor(clazz);
        var parameterTypes = injectedConstructor.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = this.createObject(parameterTypes[i]);
        }

        return KReflectionUtils.newInstance(injectedConstructor, args);
    }

    private Object injectConstructorAndCreate(final Class<?> clazz, final KArgs explicitArgs) {
        Constructor<?> injectedConstructor = this.getConstructor(clazz);
        var parameterTypes = injectedConstructor.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];
        Object[] explicit = explicitArgs.unpack();

        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = (i < explicit.length) ? explicit[i] : this.createObject(parameterTypes[i]);
        }

        return KReflectionUtils.newInstance(injectedConstructor, args);
    }

    private Constructor<?> getConstructor(final Class<?> clazz) {
        var constructors = clazz.getConstructors();

        var injectedConstructors = Arrays.stream(constructors)
            .filter(x -> x.isAnnotationPresent(KInject.class))
            .toList();

        int injectedConstructorsCount = injectedConstructors.size();
        Constructor<?> injectedConstructor;
        if (injectedConstructorsCount > 1) {
            throw new KInstantiationException(
                clazz,
                "An injected class must contain at most one injected constructor"
            );
        } else if (injectedConstructorsCount == 1) {
            injectedConstructor = injectedConstructors.getFirst();
        } else {
            if (constructors.length > 1) {
                throw new KInstantiationException(
                    clazz,
                        "Cannot choose a constructor to inject since"
                    +   "there is more than one constructor"
                );
            }
            injectedConstructor = clazz.getConstructors()[0];
        }

        injectedConstructor.setAccessible(true);
        return injectedConstructor;
    }

    private void injectFields(final Object object, final Class<?> clazz) {

        Arrays.stream(clazz.getDeclaredFields())
            .filter(x -> x.isAnnotationPresent(KInject.class))
            .peek(x -> x.setAccessible(true))
            .forEach(x -> KReflectionUtils.setFieldValue(
                x,
                object,
                this.createObject(x.getType())
            ));

    }

    private void injectMethods(final Object object, final Class<?> clazz) {

        var injectedMethods = Arrays.stream(clazz.getDeclaredMethods())
            .filter(x -> x.isAnnotationPresent(KInject.class))
            .peek(x -> x.setAccessible(true))
            .toList();

        for (var injectedMethod: injectedMethods) {
            var methodParameterTypes = injectedMethod.getParameterTypes();
            Object[] args = new Object[methodParameterTypes.length];
            for (int i = 0; i < methodParameterTypes.length; i++) {
                args[i] = this.createObject(methodParameterTypes[i]);
            }
            KReflectionUtils.invokeMethod(injectedMethod, object, args);
        }

    }

    private void pushToRegistry(final Object object) {
        if (!(object instanceof KObject)) {
            return;
        }

        this.objectRegistry.pushObjectToRegistry(
            (KObject) object,
            KObjectInstantiationType.SINGLETON
        );
    }
}
