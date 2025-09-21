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
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.object.registry.KObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The most important utility class in Konna which purpose is to create new objects.
 * Unlike standard instantiating objects with {@code new}, this class can do this
 * with automatic dependency resolution, dispatching different instantiation types
 * and so on.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("unchecked")
public final class KActivator {

    private KActivator() {

    }

    private static final
    Map<Class<?>, KObjectInstantiationType> OBJECT_INSTANTIATION_TYPES = new HashMap<>();

    private static final
    Map<Class<?>, KObject> SINGLETONS = new HashMap<>();
    private static final
    Map<Class<?>, WeakReference<KObject>> WEAK_SINGLETONS = new HashMap<>();
    private static final
    Map<Class<?>, KObjectPool<?>> POOLS = new HashMap<>();
    private static final
    Map<Class<?>, KWeakObjectPool<?>> WEAK_POOLS = new HashMap<>();


    private static final
    Map<Class<?>, SoftReference<Class<?>>> CACHED_DEPENDENCIES = new HashMap<>();

    static {

        List<Class<?>> poolableClasses;
        List<String> poolableCandidates = Arrays.stream(Package.getPackages())
            .map(Package::getName)
            .filter(
                (name) -> !(
                        name.startsWith("java")
                    ||  name.startsWith("jdk")
                    ||  name.startsWith("sun")
                )
            )
            .toList();

        try {
            poolableClasses = KAnnotationUtils.findAnnotatedClasses(
                KPoolable.class,
                poolableCandidates
            );

        } catch (IOException | ClassNotFoundException e) {
            throw new KInstantiationException(KActivator.class, e);
        }

        for (var poolableClass: poolableClasses) {
            KPoolable poolableMeta = poolableClass.getAnnotation(KPoolable.class);
            int initialSize = poolableMeta.initialPoolSize();

            if (poolableMeta.weak()) {
                KWeakObjectPool<?> pool = new KWeakObjectPool<>(
                    (Class<? extends KObject>) poolableClass,
                    initialSize
                );
                KActivator.WEAK_POOLS.put(poolableClass, pool);
            } else {
                KObjectPool<?> pool = new KObjectPool<>(
                    (Class<? extends KObject>) poolableClass,
                    initialSize
                );
                KActivator.POOLS.put(poolableClass, pool);
            }
        }
    }

    /**
     * Creates a new object or returns it if it already instantiated (depends on created class).
     * Also puts it to {@link KObjectRegistry} if the object created is not temporal.
     * @param clazz Class to instantiate
     * @param container Container (used for dependency resolution)
     * @return Created object
     * @param <T> Type of object to instantiate
     *
     * @see KContainer
     * @see KObjectInstantiationType
     * @see KObjectRegistry
     */
    public static <T> T create(final Class<? extends T> clazz, final KContainer container) {

        var klass = KActivator.getClassImplementation(clazz, container);

        KObjectInstantiationType
        instantiationType = KActivator.getOrResolveInstantiationType(klass);

        T created = switch (instantiationType) {
            case SINGLETON, IMMORTAL -> KActivator.createSingleton(klass,  container, false);
            case WEAK_SINGLETON -> KActivator.createSingleton(klass, container, true);
            case POOLABLE -> KActivator.createPoolable(klass, container, false);
            case WEAK_POOLABLE -> KActivator.createPoolable(klass, container, true);
            case TRANSIENT, TEMPORAL -> KActivator.createNewObject(klass, container);
        };

        if (instantiationType != KObjectInstantiationType.TEMPORAL && created instanceof KObject) {
            KObjectRegistry.push((KObject) created, instantiationType);
        }

        return created;
    }

    /**
     * "deletes" a created object.
     * Actually, there is no such action as "deleting an object", however, this method's
     * purpose exists - it removes all possible references of the object, making it unreachable
     * by none, so tha garbage collected will delete the object at last. However, poolable objects
     * are just returned to their pool, not wiped.
     * Additionally, it removes record from {@link KObjectRegistry} of the deleted object.
     *
     * @param object Object to delete
     * @param <T> Type of object to delete
     */
    public static <T> void delete(final T object) {
        var klass = object.getClass();

        KObjectInstantiationType
        instantiationType = KActivator.getOrResolveInstantiationType(klass);


        switch (instantiationType) {
            case IMMORTAL -> throw new KDeletionException(object, "object is immortal");
            case SINGLETON -> KActivator.deleteSingleton(object, (Class<T>) klass, false);
            case WEAK_SINGLETON -> KActivator.deleteSingleton(object, (Class<T>) klass, true);
            case POOLABLE -> KActivator.deletePoolable(object, (Class<T>) klass, false);
            case WEAK_POOLABLE -> KActivator.deletePoolable(object, (Class<T>) klass, true);
        }

        if (instantiationType != KObjectInstantiationType.TEMPORAL && object instanceof KObject) {
            KObjectRegistry.remove(((KObject) object).id());
        }
    }

    private static <T> Class<T> getClassImplementation(
        final Class<T> clazz,
        final KContainer container
    ) {
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

    private static <T> KObjectInstantiationType getOrResolveInstantiationType(
        final Class<T> klass
    ) {
        if (KActivator.OBJECT_INSTANTIATION_TYPES.containsKey(klass)) {
            return KActivator.OBJECT_INSTANTIATION_TYPES.get(klass);
        } else {
            var instantiationType = KActivator.getInstantiationType(klass);
            KActivator.OBJECT_INSTANTIATION_TYPES.put(klass, instantiationType);
            return instantiationType;
        }
    }

    private static <T> KObjectInstantiationType getInstantiationType(final Class<T> clazz) {
        if (clazz.isAnnotationPresent(KSingleton.class)) {
            KSingleton meta = clazz.getAnnotation(KSingleton.class);
            if (meta.immortal()) {
                return KObjectInstantiationType.IMMORTAL;
            }

            return meta.weak()
                ? KObjectInstantiationType.WEAK_SINGLETON
                : KObjectInstantiationType.SINGLETON;
        }

        if (clazz.isAnnotationPresent(KPoolable.class)) {
            KPoolable meta = clazz.getAnnotation(KPoolable.class);
            return meta.weak()
                ? KObjectInstantiationType.WEAK_POOLABLE
                : KObjectInstantiationType.POOLABLE;
        }

        if (clazz.isAnnotationPresent(KTransient.class)) {
            KTransient meta = clazz.getAnnotation(KTransient.class);
            return meta.temporal()
                ? KObjectInstantiationType.TEMPORAL
                : KObjectInstantiationType.TRANSIENT;
        }

        return KObjectInstantiationType.TRANSIENT;
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

    private static <T> T createSingleton(
        final Class<T> clazz,
        final KContainer container,
        boolean weak
    ) {
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

    private static <T> T createPoolable(
        final Class<T> clazz,
        final KContainer container,
        boolean weak
    ) {
        KAbstractObjectPool<?> pool = weak
            ? KActivator.WEAK_POOLS.get(clazz)
            : KActivator.POOLS.get(clazz);

        try {
            return (T) pool.obtain(container);
        } catch (KEmptyObjectPoolException e) {
            throw new KInstantiationException(clazz, e);
        }
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

    private static <T> void deletePoolable(final T object, final Class<T> clazz, boolean weak) {
        KAbstractObjectPool<T> pool = (KAbstractObjectPool<T>) (
            weak
            ? KActivator.WEAK_POOLS.get(clazz)
            : KActivator.POOLS.get(clazz)
        );
        pool.release(object);
    }
}
