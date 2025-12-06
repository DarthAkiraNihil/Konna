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

/**
 * Represents a json validator that check if the json value
 * is correct according to checks, that are implementation-defined.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KJsonValidator {

    /**
     * Validates a json value.
     * @throws  io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError
     *          if json value does not pass validation checks
     * @param value Json value to validate
     */
    void validate(KJsonValue value);

}
