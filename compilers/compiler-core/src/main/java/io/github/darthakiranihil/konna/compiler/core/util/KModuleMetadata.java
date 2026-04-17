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

import javax.lang.model.type.TypeMirror;
import java.util.LinkedList;
import java.util.List;

public record KModuleMetadata(
    TypeMirror type,
    String className,
    List<ProviderDescription> providers
) {

    public record ProviderDescription(
        String methodName,
        TypeMirror mainProvidedClass,
        List<TypeMirror> providedClasses,
        boolean isSingleton
    ) {

    }

    static final class Builder {

        private final TypeMirror type;
        private final String className;

        private final List<ProviderDescription> providers;

        Builder(final TypeMirror type, final String className) {
            this.type = type;
            this.className = className;
            this.providers = new LinkedList<>();
        }

        public void addProvider(
            final String methodName,
            final TypeMirror mainProvidedClass,
            final List<TypeMirror> providedClasses,
            boolean isSingleton
        ) {
            this.providers.add(
                new ProviderDescription(
                    methodName,
                    mainProvidedClass,
                    providedClasses,
                    isSingleton
                )
            );
        }

        public KModuleMetadata build() {
            return new KModuleMetadata(
                this.type,
                this.className,
                this.providers
            );
        }

    }

}
