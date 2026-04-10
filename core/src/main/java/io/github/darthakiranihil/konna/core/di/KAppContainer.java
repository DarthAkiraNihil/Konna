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
import org.jspecify.annotations.Nullable;

/**
 * Abstract class for app container that is supposed to be used
 * across the whole application.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public abstract class KAppContainer implements KContainer {

    @SuppressWarnings("unchecked")
    public static Class<? extends KAppContainer> useGenerated() {
        return (Class<? extends KAppContainer>) KClassUtils
            .getGeneratedForName("core.modules.kGeneratedAppContainer");
    }

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
    public final Object getInstance(Class<?> clazz) {
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

    @SuppressWarnings("unchecked")
    public final <T> T getInstanceInferred(Class<?> clazz) {
        return (T) this.getInstance(clazz);
    }

    protected abstract Object getRawInstance(Class<?> clazz);

    final void setEngineModule(final KEngineModule module) {
        this.engineModule = module;
        this.setModuleCalled = true;
    }

}
