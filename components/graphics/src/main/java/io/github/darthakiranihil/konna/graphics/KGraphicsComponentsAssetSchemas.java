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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.data.json.KJsonPropertyValidationInfo;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

final class KGraphicsComponentsAssetSchemas extends KUninstantiable {

    public KGraphicsComponentsAssetSchemas() {
        super();
    }

    private static final class ShaderAssetSchema implements KJsonValidator {

        private final KJsonValidator schema;

        public ShaderAssetSchema() {

            var builder = new KJsonPropertyValidationInfo.Builder();

            this.schema = new KJsonObjectValidator(
                builder
                    .withName("type")
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator((v) -> {
                        String s = v.getString();
                        if (s == null || (!s.equals("vertex") && !s.equals("fragment"))) {
                            throw new KJsonValidationError("incorrect shader type");
                        }
                    })
                    .build(),
                builder
                    .withName("source")
                    .withExpectedType(KJsonValueType.STRING) // todo: value is path
                    .build()
            );

        }

        @Override
        public void validate(KJsonValue value) {
            this.schema.validate(value);
        }
    }

    public static KJsonValidator SHADER = new ShaderAssetSchema();
}
