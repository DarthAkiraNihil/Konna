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
    boolean hasApplicationFeatures,
    boolean hasSystemFeatures,
    List<ModuleDependency> moduleDependencies
) {

    public record ModuleDependency(
        TypeMirror module,
        TypeMirror requiredType
    ) {

    }

    static final class Builder {

        private boolean hasApplicationFeatures;
        private boolean hasSystemFeatures;
        private final List<ModuleDependency> moduleDependencies;

        public Builder() {
            this.moduleDependencies = new LinkedList<>();
        }

        public void setThatHasApplicationFeatures() {
            this.hasApplicationFeatures = true;
        }

        public void setThatHasSystemFeatures() {
            this.hasSystemFeatures = true;
        }

        public void addModuleDependency(final TypeMirror dep, final TypeMirror requiredClass) {
            this.moduleDependencies.add(new ModuleDependency(dep, requiredClass));
        }

        public KModuleMetadata build() {
            return new KModuleMetadata(
                this.hasApplicationFeatures,
                this.hasSystemFeatures,
                this.moduleDependencies
            );
        }

    }

}
