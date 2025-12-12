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

package io.github.darthakiranihil.konna.core;

import io.github.darthakiranihil.konna.core.data.json.KJsonPropertyValidationInfo;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;

final class KonnaBootstrap {

    private static final String ARG_PARSER_KEY = "arg_parser";

    private static class Schema implements KJsonValidator {

        private final KJsonValidator validator;

        public Schema() {
            var builder = new KJsonPropertyValidationInfo.Builder();

            this.validator = new KJsonObjectValidator(
                builder
                    .withName(ARG_PARSER_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(KJsonValueIsClassValidator.INSTANCE)
                    .build()
            );
        }

        @Override
        public void validate(KJsonValue value) {
            this.validator.validate(value);
        }
    }





}
