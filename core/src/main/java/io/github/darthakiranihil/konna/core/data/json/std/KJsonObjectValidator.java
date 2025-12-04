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

public class KJsonObjectValidator implements KJsonValidator {

    private final KJsonPropertyValidationInfo[] propertyInfo;

    public KJsonObjectValidator(final KJsonPropertyValidationInfo... propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

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
