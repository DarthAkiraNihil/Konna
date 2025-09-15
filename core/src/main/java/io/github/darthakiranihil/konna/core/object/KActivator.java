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
import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class KActivator {

    private KActivator() {

    }

    private enum ObjectInstantiationType {
        IMMORTAL,
        SINGLETON,
        WEAK_SINGLETON,
        POOLABLE,
        WEAK_POOLABLE,
        TRANSIENT,
        TEMPORAL
    }

    private static final
    Map<Class<?>, ObjectInstantiationType> OBJECT_INSTANTIATION_TYPES = new HashMap<>();

    private static final
    Map<Class<?>, KObject> SINGLETONS = new HashMap<>();
    private static final
    Map<Class<?>, WeakReference<KObject>> WEAK_SINGLETONS = new HashMap<>();
    private static final
    Map<Class<?>, KObjectPool<?>> POOLS = new HashMap<>();


    private static final
    Map<Class<?>, SoftReference<Class<?>>> CACHED_DEPENDENCIES = new HashMap<>();

    static {

        List<Class<?>> poolableClasses;
        List<String> poolableCandidates = Arrays.stream(Package.getPackages())
            .map(Package::getName)
            .filter((name) -> !(name.startsWith("java") || name.startsWith("jdk") || name.startsWith("sun")))
            .toList();

        try {
            poolableClasses = KAnnotationUtils.findAnnotatedClasses(KPoolable.class, poolableCandidates);
        } catch (IOException | ClassNotFoundException e) {
            throw new KInstantiationException(KActivator.class, e);
        }

        for (var poolableClass: poolableClasses) {
            KPoolable poolableMeta = poolableClass.getAnnotation(KPoolable.class);
            int initialSize = poolableMeta.initialPoolSize();

            KObjectPool<?> pool = new KObjectPool<>(
                (Class<? extends KObject>) poolableClass,
                initialSize
            );

            KActivator.POOLS.put(poolableClass, pool);

        }

    }

    public static <T> T create(final Class<? extends T> clazz, final KContainer container) {

        var klass = KActivator.getClassImplementation(clazz, container);

        ObjectInstantiationType instantiationType = KActivator.getOrResolveInstantiationType(klass);

        return switch (instantiationType) {
            case SINGLETON -> KActivator.createSingleton(klass,  container, false);
            case WEAK_SINGLETON -> KActivator.createSingleton(klass, container, true);
            case POOLABLE -> KActivator.createPoolable(klass, container);
            default -> throw new RuntimeException("fuck fuck");
        };
    }

    public static <T> void delete(final T object) {
        var klass = object.getClass();

        ObjectInstantiationType instantiationType = KActivator.getOrResolveInstantiationType(klass);


        switch (instantiationType) {
            case SINGLETON -> KActivator.deleteSingleton(object, (Class<T>) klass, false);
            case WEAK_SINGLETON -> KActivator.deleteSingleton(object, (Class<T>) klass, true);
            case POOLABLE -> KActivator.deletePoolable(object, (Class<T>) klass);
            default -> throw new RuntimeException("fuck fuck");
        };
    }

    private static <T> Class<T> getClassImplementation(final Class<T> clazz, final KContainer container) {
        if (KActivator.CACHED_DEPENDENCIES.containsKey(clazz)) {
            var ref = KActivator.CACHED_DEPENDENCIES.get(clazz);
            var klass = ref.get();
            if (klass != null) {
                return (Class<T>) klass;
            }
        }

        try {
            var klass = container.resolve(clazz);
            KActivator.CACHED_DEPENDENCIES.put(clazz, new SoftReference<>(klass));
            return klass;
        } catch (KDependencyResolveException e) {
            throw new KInstantiationException(clazz, e);
        }
    }

    private static <T> ObjectInstantiationType getOrResolveInstantiationType(final Class<T> klass) {
        if (KActivator.OBJECT_INSTANTIATION_TYPES.containsKey(klass)) {
            return KActivator.OBJECT_INSTANTIATION_TYPES.get(klass);
        } else {
            var instantiationType = KActivator.getInstantiationType(klass);
            KActivator.OBJECT_INSTANTIATION_TYPES.put(klass, instantiationType);
            return instantiationType;
        }
    }

    private static <T> ObjectInstantiationType getInstantiationType(final Class<T> clazz) {
        if (clazz.isAnnotationPresent(KSingleton.class)) {
            KSingleton meta = clazz.getAnnotation(KSingleton.class);
            return meta.weak()
                ? ObjectInstantiationType.WEAK_SINGLETON
                : ObjectInstantiationType.SINGLETON;
        }

        if (clazz.isAnnotationPresent(KPoolable.class)) {
            return ObjectInstantiationType.POOLABLE;
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

    private static <T> T createPoolable(final Class<T> clazz, final KContainer container) {
        KObjectPool<?> pool = KActivator.POOLS.get(clazz);
        return (T) pool.obtain(container);
    }

    private static <T> void deleteSingleton(final T object, final Class<T> clazz, boolean weak) {
        if (weak) {
            if (!KActivator.WEAK_SINGLETONS.containsKey(clazz)) {
                throw new KDeletionException(
                    object,
                    "cannot delete a non-instantiated weak singleton"
                );
            }

            var ref = KActivator.WEAK_SINGLETONS.get(clazz);
            KObject deleted = ref.get();
            if (deleted != null) {
                KActivator.WEAK_SINGLETONS.remove(clazz);
            }
        }

        if (!KActivator.SINGLETONS.containsKey(clazz)) {
            throw new KDeletionException(
                object,
                "cannot delete a non-instantiated singleton"
            );
        }

        KActivator.SINGLETONS.remove(clazz);
    }

    private static <T> void deletePoolable(final T object, final Class<T> clazz) {
        KObjectPool<T> pool = (KObjectPool<T>) KActivator.POOLS.get(clazz);
        pool.release(object);
    }
}
