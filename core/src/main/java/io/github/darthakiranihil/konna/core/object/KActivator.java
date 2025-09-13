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
import io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class KActivator {

    private enum ObjectInstantiationType {
        IMMORTAL,
        SINGLETON,
        WEAK_SINGLETON,
        POOLABLE,
        WEAK_POOLABLE,
        TRANSIENT,
        TEMPORAL
    }

    private static final Map<Class<?>, ObjectInstantiationType> objectInstantiationTypes = new HashMap<>();

    private static final Map<Class<?>, KObject> SINGLETONS = new HashMap<>();
    private static final Map<Class<?>, WeakReference<KObject>> WEAK_SINGLETONS = new HashMap<>();
    private static final Map<Class<?>, SoftReference<Class<?>>> cachedDependencies = new HashMap<>();

    public static <T> T create(final Class<? extends T> clazz, final KContainer container) {

        var klass = KActivator.getClassImplementation(clazz, container);

        ObjectInstantiationType instantiationType;
        if (KActivator.objectInstantiationTypes.containsKey(klass)) {
            instantiationType = KActivator.objectInstantiationTypes.get(klass);
        } else {
            instantiationType = KActivator.getInstantiationType(klass);
            KActivator.objectInstantiationTypes.put(klass, instantiationType);
        }

        return switch (instantiationType) {
            case SINGLETON -> KActivator.createSingleton(klass,  container, false);
            case WEAK_SINGLETON -> KActivator.createSingleton(klass, container, true);
            default -> throw new RuntimeException("fuck fuck");
        };
    }

    public static void delete() {

    }

    private static <T> Class<T> getClassImplementation(final Class<T> clazz, final KContainer container) {
        if (KActivator.cachedDependencies.containsKey(clazz)) {
            var ref = KActivator.cachedDependencies.get(clazz);
            var klass = ref.get();
            if (klass != null) {
                return (Class<T>) klass;
            }
        }

        try {
            var klass = container.resolve(clazz);
            KActivator.cachedDependencies.put(clazz, new SoftReference<>(klass));
            return klass;
        } catch (KDependencyResolveException e) {
            throw new KInstantiationException(clazz, e);
        }
    }

    private static <T> ObjectInstantiationType getInstantiationType(final Class<T> clazz) {
        if (clazz.isAnnotationPresent(KSingleton.class)) {
            KSingleton meta = clazz.getAnnotation(KSingleton.class);
            return meta.weak()
                ? ObjectInstantiationType.WEAK_SINGLETON
                : ObjectInstantiationType.SINGLETON;
        }

        throw new RuntimeException("fuck");
    }

    private static <T> T createNewObject(final Class<T> clazz, final KContainer container) {
        //todo: iterative and autowired analog

        var constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        var parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = KActivator.create(parameterTypes[i], container);
        }

        try {
            return (T) constructor.newInstance(parameters);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new KInstantiationException(clazz, e);
        }
    }

    private static <T> T createSingleton(final Class<T> clazz, final KContainer container, boolean weak) {
        if (weak) {
            if (KActivator.WEAK_SINGLETONS.containsKey(clazz)) {
                var ref = KActivator.WEAK_SINGLETONS.get(clazz);
                KObject object = ref.get();
                if (object != null) {
                    return (T) object;
                }
            }

            var object = KActivator.createNewObject(clazz, container);
            KActivator.WEAK_SINGLETONS.put(clazz, new WeakReference<>((KObject) object));
            return object;
        }

        if (KActivator.SINGLETONS.containsKey(clazz)) {
            return (T) KActivator.SINGLETONS.get(clazz);
        }

        var object = KActivator.createNewObject(clazz, container);
        KActivator.SINGLETONS.put(clazz, (KObject) object);
        return object;
    }
}
