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

/**
 * Json validator that checks that value represents any existing Java class.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KJsonValueIsClassValidator implements KJsonValidator {

    /**
     * Convenience static instance of the validator.
     */
    public static final KJsonValidator INSTANCE = new KJsonValueIsClassValidator();

    /**
     * Checks if passed value is string and its value is a name of any
     * existing Java class (inside application). Throws {@link KJsonValidationError}
     * if one of two conditions are not met.
     *
     * @param value Json value to validate
     */
    @Override
    public void validate(final KJsonValue value) {

        if (value.getType() != KJsonValueType.STRING) {
            throw KJsonValidationError.incorrectType(KJsonValueType.STRING, value.getType());
        }

        String className = value.getString();
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new KJsonValidationError("specified class not found");
        }


    }
}
