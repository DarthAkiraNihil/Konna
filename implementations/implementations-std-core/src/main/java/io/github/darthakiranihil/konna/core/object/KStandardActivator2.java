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
import io.github.darthakiranihil.konna.core.di.KInjectable;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;

import java.lang.reflect.Modifier;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class KStandardActivator2 extends KObject implements KActivator2 {

    private final KAppContainer appContainer;
    private final KObjectRegistry objectRegistry;

    public KStandardActivator2(
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

    }

    @Override
    public <T> T createObject(final Class<? extends T> clazz) {
        // todo: more registry methods
        if (clazz.isAnnotationPresent(KInjectable.class)) {
            var object = this.objectRegistry.listObjects()
                .stream()
                .filter(x -> x.getClass().equals(clazz))
                .findFirst()
                .orElse(null);

            if (object != null) {
                return (T) object;
            }
        }

        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            return (T) this.appContainer.getInstance(clazz);
        }

        Object instantiated = this.injectConstructorAndCreate(clazz);
        this.injectFields(instantiated, clazz);
        this.injectMethods(instantiated, clazz);
        return (T) instantiated;

    }

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
        return (T) instantiated;
    }

    private Object injectConstructorAndCreate(final Class<?> clazz) {
        var injectedConstructor = Arrays.stream(clazz.getConstructors())
            .filter(x -> x.isAnnotationPresent(KInject.class))
            .findFirst()
            .orElse(null);

        if (injectedConstructor == null) {
            injectedConstructor = clazz.getConstructors()[0];
        }

        injectedConstructor.setAccessible(true);
        var parameterTypes = injectedConstructor.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = this.createObject(parameterTypes[i]);
        }

        return KReflectionUtils.newInstance(injectedConstructor, args);
    }

    private Object injectConstructorAndCreate(final Class<?> clazz, final KArgs explicitArgs) {
        var injectedConstructor = Arrays.stream(clazz.getConstructors())
            .filter(x -> x.isAnnotationPresent(KInject.class))
            .findFirst()
            .orElse(null);

        if (injectedConstructor == null) {
            injectedConstructor = clazz.getConstructors()[0];
        }

        injectedConstructor.setAccessible(true);
        var parameterTypes = injectedConstructor.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];
        Object[] explicit = explicitArgs.unpack();

        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = (i < explicit.length) ? explicit[i] : this.createObject(parameterTypes[i]);
        }

        return KReflectionUtils.newInstance(injectedConstructor, args);
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
}
