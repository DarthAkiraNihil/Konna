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

package io.github.darthakiranihil.konna.core.object.std;

import io.github.classgraph.ClassGraph;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KInjectedConstructor;
import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.core.util.KIndex;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Standard implementation of {@link KActivator}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("unchecked")
@KSingleton(immortal = true)
public final class KStandardActivator extends KActivator {

    private final Map<Class<?>, KObject> singletons;
    private final Map<Class<?>, WeakReference<KObject>> weakSingletons;
    private final Map<Class<?>, KObjectPool<?>> pools;
    private final Map<Class<?>, KWeakObjectPool<?>> weakPools;

    private final Map<Class<?>, KObjectInstantiationType> objectInstantiationTypes;
    private final Map<Class<?>, SoftReference<Class<?>>> cachedDependencies;

    /**
     * Standard constructor.
     * On initialization, it looks for all poolable classes and creates corresponding
     * object pools for them, other types of instantiation are ignored at this moment.
     * @param containerResolver Container resolver
     * @param objectRegistry Object registry
     * @param index System index
     */
    public KStandardActivator(
        final KContainerResolver containerResolver,
        final KObjectRegistry objectRegistry,
        final KIndex index
    ) {
        super(containerResolver, objectRegistry, index);
        this.addTag(KTag.DefaultTags.STD);
        ClassGraph.CIRCUMVENT_ENCAPSULATION = ClassGraph.CircumventEncapsulationMethod.JVM_DRIVER;

        this.singletons = new HashMap<>();
        this.weakSingletons = new HashMap<>();
        this.pools = new HashMap<>();
        this.weakPools = new HashMap<>();
        this.objectInstantiationTypes = new HashMap<>();
        this.cachedDependencies = new HashMap<>();

        List<Class<?>> poolableClasses;

        poolableClasses = KClassUtils.getAnnotatedClasses(
            this.index,
            KPoolable.class
        );

        for (var poolableClass: poolableClasses) {
            KPoolable poolableMeta = poolableClass.getAnnotation(KPoolable.class);
            int initialSize = poolableMeta.initialPoolSize();

            if (poolableMeta.weak()) {
                KWeakObjectPool<?> pool = new KWeakObjectPool<>(
                    (Class<? extends KObject>) poolableClass,
                    initialSize,
                    this,
                    this.objectRegistry
                );
                this.weakPools.put(poolableClass, pool);
            } else {
                KObjectPool<?> pool = new KObjectPool<>(
                    (Class<? extends KObject>) poolableClass,
                    initialSize,
                    this,
                    this.objectRegistry
                );
                this.pools.put(poolableClass, pool);
            }
        }
    }

    /**
     * Returns a new container with master container of caller class as its parent.
     * @return A new container, which parent is the master container of the caller class
     */
    @Override
    public KContainer newContainer() {
        return new KContainer(this.containerResolver.resolve());
    }

    /**
     * Creates a new object or returns it if it already instantiated (depends on created class).
     * Also puts it to {@link KObjectRegistry} if the object created is not temporal.
     * @param clazz Class to instantiate
     * @param container Container (used for dependency resolution)
     * @param <T> Type of object to instantiate
     * @param nonInjectedArgs Arguments that are not injected (passed explicitly)
     *                        when constructing an object
     * @return Created object
     * @see KContainer
     * @see KObjectInstantiationType
     * @see KObjectRegistry
     * @see KInject
     */
    @Override
    public <T> T create(
        final Class<? extends T> clazz,
        final KContainer container,
        final Object... nonInjectedArgs
    ) {

        var klass = this.getClassImplementation(clazz, container);

        KObjectInstantiationType
            instantiationType = this.getOrResolveInstantiationType(klass);

        T created = switch (instantiationType) {
            case SINGLETON, IMMORTAL -> this.createSingleton(
                klass,
                container,
                false,
                nonInjectedArgs
            );
            case WEAK_SINGLETON -> this.createSingleton(
                klass,
                container,
                true,
                nonInjectedArgs
            );
            case POOLABLE -> this.createPoolable(
                klass,
                container,
                false,
                nonInjectedArgs
            );
            case WEAK_POOLABLE -> this.createPoolable(
                klass,
                container,
                true,
                nonInjectedArgs
            );
            case TRANSIENT, TEMPORAL -> this.createNewObject(
                klass,
                container,
                nonInjectedArgs
            );
        };

        if (instantiationType != KObjectInstantiationType.TEMPORAL && created instanceof KObject) {
            this.objectRegistry.push((KObject) created, instantiationType);
            this.addTagsToCreatedObject((KObject) created, instantiationType);
        }

        return created;
    }

    /**
     * Creates a new object or returns it if it already instantiated (depends on created class).
     * Also puts it to {@link KObjectRegistry} if the object created is not temporal.
     * This method uses default container, that must be set before calling it
     * @param clazz Class to instantiate
     * @param nonInjectedArgs Arguments that are not injected (passed explicitly)
     *                        when constructing an object
     * @return Created object
     * @param <T> Type of object to instantiate
     *
     * @see KContainer
     * @see KObjectInstantiationType
     * @see KObjectRegistry
     */
    @Override
    public <T> T create(
        final Class<? extends T> clazz,
        final Object... nonInjectedArgs
    ) {
        return this.create(
            clazz,
            this.containerResolver.resolve(),
            nonInjectedArgs
        );
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
    @Override
    public <T> void delete(final T object) {
        var klass = object.getClass();

        KObjectInstantiationType
            instantiationType = this.getOrResolveInstantiationType(klass);


        switch (instantiationType) {
            case IMMORTAL -> throw new KDeletionException(object, "object is immortal");
            case SINGLETON -> this.deleteSingleton(object, (Class<T>) klass, false);
            case WEAK_SINGLETON -> this.deleteSingleton(object, (Class<T>) klass, true);
            case POOLABLE -> this.deletePoolable(object, (Class<T>) klass, false);
            case WEAK_POOLABLE -> this.deletePoolable(object, (Class<T>) klass, true);
        }

        if (
            instantiationType != KObjectInstantiationType.TEMPORAL
                &&  instantiationType != KObjectInstantiationType.POOLABLE
                &&  instantiationType != KObjectInstantiationType.WEAK_POOLABLE
                &&  object instanceof KObject) {
            this.objectRegistry.remove(((KObject) object).id());
        }
    }

    private <T> Class<T> getClassImplementation(
        final Class<T> clazz,
        final KContainer container
    ) {
        if (this.cachedDependencies.containsKey(clazz)) {
            var ref = this.cachedDependencies.get(clazz);
            var klass = ref.get();
            if (klass != null) {
                return (Class<T>) klass;
            }
        }

        try {
            var klass = container.resolve(clazz);
            this.cachedDependencies.put(clazz, new SoftReference<>(klass));
            return klass;
        } catch (KDependencyResolveException e) {
            throw new KInstantiationException(clazz, e);
        }
    }

    private <T> KObjectInstantiationType getOrResolveInstantiationType(
        final Class<T> klass
    ) {
        if (this.objectInstantiationTypes.containsKey(klass)) {
            return this.objectInstantiationTypes.get(klass);
        } else {
            var instantiationType = this.getInstantiationType(klass);
            this.objectInstantiationTypes.put(klass, instantiationType);
            return instantiationType;
        }
    }

    private <T> KObjectInstantiationType getInstantiationType(final Class<T> clazz) {
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

    private <T> Constructor<?> getConstructor(final Class<T> clazz) {
        var constructors = clazz.getDeclaredConstructors();
        Constructor<?> foundConstructor = null;
        for (var constructor: constructors) {
            if (constructor.isAnnotationPresent(KInjectedConstructor.class)) {
                foundConstructor = constructor;
                break;
            }
        }

        if (foundConstructor == null) {
            foundConstructor = constructors[0];
        }
        foundConstructor.setAccessible(true);
        return foundConstructor;
    }

    private <T> T createNewObject(
        final Class<T> clazz,
        final KContainer container,
        final Object... nonResolvedArgs
    ) {

        var constructor = this.getConstructor(clazz);
        constructor.setAccessible(true);

        var parameterTypes = constructor.getParameterTypes();
        var parameterAnnotations = constructor.getParameterAnnotations();

        Object[] parameters = new Object[parameterTypes.length];
        int nonInjectedArgsProcessed = 0;

        for (int i = 0; i < parameterTypes.length; i++) {
            boolean isNonInjected = true;
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                if (parameterAnnotations[i][j] instanceof KInject) {
                    isNonInjected = false;
                    break;
                }
            }

            if (isNonInjected) {
                parameters[i] = nonResolvedArgs[nonInjectedArgsProcessed];
                nonInjectedArgsProcessed++;
            } else {
                parameters[i] = this.create(parameterTypes[i], container);
            }
        }

        try {
            return (T) constructor.newInstance(parameters);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new KInstantiationException(clazz, e);
        }
    }

    private <T> T createSingleton(
        final Class<T> clazz,
        final KContainer container,
        boolean weak,
        final Object... nonResolvedArgs
    ) {
        if (weak) {
            if (this.weakSingletons.containsKey(clazz)) {
                var ref = this.weakSingletons.get(clazz);
                KObject object = ref.get();
                if (object != null) {
                    return (T) object;
                }
            }

            var object = this.createNewObject(clazz, container, nonResolvedArgs);
            this.weakSingletons.put(clazz, new WeakReference<>((KObject) object));
            return object;
        }

        if (this.singletons.containsKey(clazz)) {
            return (T) this.singletons.get(clazz);
        }

        var object = this.createNewObject(clazz, container, nonResolvedArgs);
        this.singletons.put(clazz, (KObject) object);
        return object;
    }

    private <T> T createPoolable(
        final Class<T> clazz,
        final KContainer container,
        boolean weak,
        final Object... nonResolvedArgs
    ) {
        KAbstractObjectPool<?> pool = weak
            ? this.weakPools.get(clazz)
            : this.pools.get(clazz);

        try {
            return (T) pool.obtain(container, nonResolvedArgs);
        } catch (KEmptyObjectPoolException e) {
            throw new KInstantiationException(clazz, e);
        }
    }

    private void addTagsToCreatedObject(
        final KObject object,
        final KObjectInstantiationType instantiationType) {
        switch (instantiationType) {
            case IMMORTAL -> object.addTag(KTag.DefaultTags.IMMORTAL);
            case SINGLETON -> object.addTag(KTag.DefaultTags.SINGLETON);
            case WEAK_SINGLETON -> object.addTags(
                KTag.DefaultTags.SINGLETON,
                KTag.DefaultTags.WEAK
            );
            case POOLABLE -> object.addTag(KTag.DefaultTags.POOLABLE);
            case WEAK_POOLABLE -> object.addTags(
                KTag.DefaultTags.POOLABLE,
                KTag.DefaultTags.WEAK
            );
            case TRANSIENT -> object.addTag(KTag.DefaultTags.TRANSIENT);
        }
    }

    private <T> void deleteSingleton(final T object, final Class<T> clazz, boolean weak) {
        if (weak) {
            if (!this.weakSingletons.containsKey(clazz)) {
                throw new KDeletionException(
                    object,
                    "cannot delete a non-instantiated weak singleton"
                );
            }

            var ref = this.weakSingletons.get(clazz);
            KObject deleted = ref.get();
            if (deleted != null) {
                this.weakSingletons.remove(clazz);
            }

            return;
        }

        if (!this.singletons.containsKey(clazz)) {
            throw new KDeletionException(
                object,
                "cannot delete a non-instantiated singleton"
            );
        }

        this.singletons.remove(clazz);
    }

    private <T> void deletePoolable(final T object, final Class<T> clazz, boolean weak) {
        KAbstractObjectPool<T> pool = (KAbstractObjectPool<T>) (
            weak
                ? this.weakPools.get(clazz)
                : this.pools.get(clazz)
        );
        pool.release(object);
    }
}
