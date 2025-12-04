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

package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

public class KJsonArrayValidator implements KJsonValidator {

    private final KJsonValueType expectedElementType;
    private final KJsonValidator[] validators;

    public KJsonArrayValidator(final KJsonValueType expectedElementType, final KJsonValidator... validators) {
        this.expectedElementType = expectedElementType;
        this.validators = validators;
    }

    @Override
    public void validate(final KJsonValue value) {

        if (value.getType() != KJsonValueType.ARRAY) {
            throw KJsonValidationError.incorrectType(KJsonValueType.ARRAY, value.getType());
        }


        for (KJsonValue element: value) {
            if (element.getType() != this.expectedElementType) {
                throw KJsonValidationError.incorrectType(this.expectedElementType, element.getType());
            }

            for (KJsonValidator validator: this.validators) {
                validator.validate(element);
            }
        }

    }
}
