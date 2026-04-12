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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.except.KIllegalStateException;
import io.github.darthakiranihil.konna.core.except.KUnsupportedOperationException;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Abstract class for app container that is supposed to be used
 * across the whole application.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public abstract class KAppContainer implements KContainer {

    /**
     * Use this method's value in
     * {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig}
     * if you want to use generated application container.
     * @return {@link Class} of a generated container
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends KAppContainer> useGenerated() {
        return (Class<? extends KAppContainer>) KClassUtils
            .getGeneratedForName("core.modules.kGeneratedAppContainer");
    }

    /**
     * Mock container that is used to bypass some nullability checks.
     * Must not be used in production. Any attempt to invoke
     * {@link KAppContainer#getInstance(Class)} will cause {@link KUnsupportedOperationException}.
     *
     * @since 0.6.0
     * @author Darth Akira Nihil
     */
    @ApiStatus.Internal
    public static final class Mock extends KAppContainer {

        public Mock() {
            super(new KApplicationFeatures.Mock(), new KSystemFeatures());
        }

        @Override
        public Object getRawInstance(final Class<?> clazz) {
            throw new KUnsupportedOperationException("Cannot use mock app container!");
        }
    }

    /**
     * Application features assigned to this container.
     */
    protected final KApplicationFeatures applicationFeatures;
    /**
     * System features assigned to this container.
     */
    protected final KSystemFeatures systemFeatures;
    /**
     * Engine module assigned to this container.
     */
    protected @Nullable KEngineModule engineModule;
    private boolean setModuleCalled;

    /**
     * Constructs this container.
     * @param applicationFeatures Application features assigned to this container
     * @param systemFeatures System features assigned to this container
     */
    protected KAppContainer(
        final KApplicationFeatures applicationFeatures,
        final KSystemFeatures systemFeatures
    ) {
        this.applicationFeatures = applicationFeatures;
        this.systemFeatures = systemFeatures;
    }

    @Override
    public final Object getInstance(final Class<?> clazz) {
        if (!this.setModuleCalled) {
            return this.getRawInstance(clazz);
        }

        if (this.engineModule == null) {
            throw new KIllegalStateException("Engine module is not initialized");
        }

        Object moduleClassObject = this.engineModule.getInstance(clazz);
        return moduleClassObject == null
            ? this.getRawInstance(clazz)
            : moduleClassObject;
    }

    /**
     * Same as {@link KAppContainer#getInstance(Class)}, but with automatic
     * return type inferring.
     * @param clazz Class of object to get
     * @param <T> Type of retrieved object (inferred)
     * @return Instance of object with specified class or {@code null}
     *         if corresponding class is not found
     */
    @SuppressWarnings("unchecked")
    public final <T> T getInstanceInferred(final Class<?> clazz) {
        return (T) this.getInstance(clazz);
    }

    /**
     * Returns instance of object of specified class, if it's not found in {@link KEngineModule},
     * or has not been set yet.
     * @param clazz Class of object to get
     * @return Instance of object with specified class
     * @throws io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException if
     *         specified object is not found
     */
    protected abstract Object getRawInstance(Class<?> clazz);

    final void setEngineModule(final KEngineModule module) {
        this.engineModule = module;
        this.setModuleCalled = true;
    }

}
