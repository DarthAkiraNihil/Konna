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

import io.github.darthakiranihil.konna.core.except.KUnsupportedOperationException;

/**
 * Represents an immutable container - it can be used only for dependency resolution
 * and its class mapping cannot be modified.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KImmutableContainer extends KContainer {

    /**
     * Creates an immutable container from another container (it should be mutable, since
     * creating an immutable container from another immutable does not make sense).
     * @param wrapped Container to make it immutable
     */
    public KImmutableContainer(final KContainer wrapped) {
        super(wrapped, String.format("locked_%s", wrapped.name()));
    }

    @Override
    public <IN> KContainer add(
        final Class<IN> fromInterface,
        final Class<? extends IN> toImplementation
    ) {
        throw new KUnsupportedOperationException(
            "cannot add class mapping to an immutable container"
        );
    }

    @Override
    public KContainer add(final Class<?> clazz) {
        throw new KUnsupportedOperationException(
            "cannot add class mapping to an immutable container"
        );
    }

    @Override
    public <T> Class<T> resolve(final Class<T> clazz) {
        return this.parent.resolve(clazz);
    }
}
