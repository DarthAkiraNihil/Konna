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

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.except.KExceptionSeverity;

public class KAssetValidationError extends KException {

    private final String invalidType;

    public KAssetValidationError(final String invalidType, final String message) {
        super(message);
        this.invalidType = invalidType;
    }

    public String getInvalidType() {
        return this.invalidType;
    }

    @Override
    public KExceptionSeverity getSeverity() {
        return KExceptionSeverity.ERROR;
    }
}
