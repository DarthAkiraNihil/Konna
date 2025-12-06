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
import io.github.darthakiranihil.konna.core.data.json.KJsonPropertyValidationInfo;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

/**
 * Standard implementation of {@link KJsonValidator} that is used
 * to validate if json value is an object and its properties are valid.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KJsonObjectValidator implements KJsonValidator {

    private final KJsonPropertyValidationInfo[] propertyInfo;

    /**
     * Standard constructor.
     * @param propertyInfo Object properties' validation info
     *
     * @see KJsonPropertyValidationInfo
     */
    public KJsonObjectValidator(final KJsonPropertyValidationInfo... propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    /**
     * Validates a json value - validates object's property according
     * to its {@link KJsonPropertyValidationInfo} and runs property's
     * value through validators set on {@link KJsonArrayValidator} instantiation.
     * Also checks if the passed value is {@link KJsonValueType#OBJECT}
     * and throws {@link KJsonValidationError} if it is not.
     * @param value Json value to validate
     * @see KJsonPropertyValidationInfo
     */
    @Override
    public void validate(final KJsonValue value) {
        if (value.getType() != KJsonValueType.OBJECT) {
            throw KJsonValidationError.incorrectType(KJsonValueType.OBJECT, value.getType());
        }

        for (var property: this.propertyInfo) {
            String propertyName = property.name();
            if (!value.hasProperty(propertyName)) {
                if (property.required()) {
                    throw KJsonValidationError.generalError(
                        propertyName,
                        "the property is required"
                    );
                } else {
                    value.setProperty(
                        propertyName,
                        new KJsonValue(
                            property.expectedType(),
                            property.defaultValue()
                        )
                    );
                }
            }

            KJsonValue propertyValue = value.getProperty(propertyName);
            if (propertyValue.isNull() && !property.nullable()) {
                throw KJsonValidationError.generalError(
                    propertyName,
                    "the property is not nullable but null got"
                );
            }

            for (KJsonValidator validator: property.validators()) {
                try {
                    validator.validate(propertyValue);
                } catch (KJsonValidationError e) {
                    throw KJsonValidationError.wrappedError(propertyName, e);
                }
            }
        }
    }
}
