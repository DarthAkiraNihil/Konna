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

package io.github.darthakiranihil.konna.compiler.core.util;

import org.jspecify.annotations.Nullable;

import javax.lang.model.type.TypeMirror;
import java.util.LinkedList;
import java.util.List;

// todo: require strict constructor parameter ordering
public record KModuleMetadata(
    TypeMirror type,
    String className,
    boolean hasApplicationFeatures,
    boolean hasSystemFeatures,
    List<ModuleDependency> moduleDependencies,
    List<ProviderDescription> providers
) {

    public record ModuleDependency(
        TypeMirror module,
        TypeMirror requiredType,
        String qualifier
    ) {

    }

    public record ProviderDescription(
        String methodName,
        List<TypeMirror> providedClasses,
        boolean isSingleton,
        @Nullable String qualifier
    ) {

    }

    static final class Builder {

        private final TypeMirror type;
        private final String className;

        private boolean hasApplicationFeatures;
        private boolean hasSystemFeatures;
        private final List<ModuleDependency> moduleDependencies;
        private final List<ProviderDescription> providers;

        public Builder(final TypeMirror type, final String className) {
            this.type = type;
            this.className = className;
            this.moduleDependencies = new LinkedList<>();
            this.providers = new LinkedList<>();
        }

        public void setThatHasApplicationFeatures() {
            this.hasApplicationFeatures = true;
        }

        public void setThatHasSystemFeatures() {
            this.hasSystemFeatures = true;
        }

        public void addModuleDependency(
            final TypeMirror dep,
            final TypeMirror requiredClass,
            final String qualifier
        ) {
            this.moduleDependencies.add(new ModuleDependency(dep, requiredClass, qualifier));
        }

        public void addProvider(
            final String methodName,
            final List<TypeMirror> providedClasses,
            boolean isSingleton,
            final String qualifier
        ) {
            this.providers.add(
                new ProviderDescription(
                    methodName,
                    providedClasses,
                    isSingleton,
                    qualifier
                )
            );
        }

        public KModuleMetadata build() {
            return new KModuleMetadata(
                this.type,
                this.className,
                this.hasApplicationFeatures,
                this.hasSystemFeatures,
                this.moduleDependencies,
                this.providers
            );
        }

    }

}
