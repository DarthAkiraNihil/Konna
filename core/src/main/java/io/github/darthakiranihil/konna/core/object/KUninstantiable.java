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

import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;

/**
 * Utility class which purpose it to throw exception when a class is about
 * to be created, when it's forbidden. Generally used in utility classes
 * that have private constructor, so you cannot create it directly, but
 * it is still possible with {@link KActivator}
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KUninstantiable {

    public KUninstantiable() {
        throw new KInstantiationException(
            String.format("Cannot instantiate a uninstantiable class %s", this.getClass())
        );
    }
}
