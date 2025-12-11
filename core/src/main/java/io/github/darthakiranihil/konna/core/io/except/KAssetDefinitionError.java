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

package io.github.darthakiranihil.konna.core.io.except;

import io.github.darthakiranihil.konna.core.except.KRuntimeException;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception thrown when an asset definition contained errors so
 * it cannot be loaded.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KAssetDefinitionError extends KRuntimeException {

    public static KAssetDefinitionError propertyNotFound(final String property) {
        return new KAssetDefinitionError(
            String.format(
                "Property with name %s is not found in asset definition",
                property
            )
        );
    }

    public KAssetDefinitionError(final String message) {
        super(message);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }
}
