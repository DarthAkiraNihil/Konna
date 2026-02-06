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

package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

/**
 * Standard implementation of {@link KJsonValidator} that is used
 * to validate if json value is an array and its elements are valid.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KJsonArrayValidator implements KJsonValidator {

    private final KJsonValueType expectedElementType;
    private final KJsonValidator[] validators;

    /**
     * Standard constructor.
     *
     * @param expectedElementType Expected type of array elements
     * @param validators Validators for array elements
     */
    public KJsonArrayValidator(
        final KJsonValueType expectedElementType,
        final KJsonValidator... validators
    ) {
        this.expectedElementType = expectedElementType;
        this.validators = validators;
    }

    /**
     * Validates a json value - validates element type and runs it through
     * validators set on {@link KJsonArrayValidator} instantiation.
     * Also checks if the passed value is {@link KJsonValueType#ARRAY}
     * and throws {@link KJsonValidationError} if it is not.
     * @param value Json value to validate.
     */
    @Override
    public void validate(final KJsonValue value) {

        if (value.getType() != KJsonValueType.ARRAY) {
            throw KJsonValidationError.incorrectType(KJsonValueType.ARRAY, value.getType());
        }

        int i = 0;
        for (KJsonValue element: value) {
            if (element.getType() != this.expectedElementType) {
                throw KJsonValidationError.incorrectType(
                    this.expectedElementType,
                    element.getType()
                );
            }


            for (KJsonValidator validator: this.validators) {
                try {
                    validator.validate(element);
                } catch (KJsonValidationError e) {
                    throw KJsonValidationError.wrappedError(Integer.toString(i), e);
                }

            }
            i++;
        }

    }
}
