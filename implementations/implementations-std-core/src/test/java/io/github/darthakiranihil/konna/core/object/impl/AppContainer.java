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

package io.github.darthakiranihil.konna.core.object.impl;

import io.github.darthakiranihil.konna.core.app.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@NullMarked
public class AppContainer extends KAppContainer {

    private @Nullable TestSingleton singleton;

    public AppContainer() {
        super(new KStandardApplicationFeatures(Map.of()), new KSystemFeatures());
    }

    @Override
    protected Object getRawInstance(Class<?> clazz) {
        if (clazz == TestDependencyInterface.class) {
            return new TestDependencyImplementation();
        }

        if (clazz == TestSingletonInterface.class) {
            if (singleton == null) {
                this.singleton = new TestSingleton();
            }

            return this.singleton;
        }

        if (clazz == TestInterfaceToResolve.class) {
            return new TestResolvedImplementation(this.getInstanceInferred(TestDependencyInterface.class));
        }

        throw new KDependencyResolveException(clazz);
    }
}
