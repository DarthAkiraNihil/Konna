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

package io.github.darthakiranihil.konna.graphics.type;

import io.github.darthakiranihil.konna.core.io.KAssetDefinitionValidator;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KRuleBasedAssetDefinitionValidatorBuilder;
import io.github.darthakiranihil.konna.graphics.image.KTextureFiltering;
import io.github.darthakiranihil.konna.graphics.image.KTextureWrapping;

public final class KTextureTypeDefinition implements KAssetTypedef {

    @Override
    public String getName() {
        return "Graphics.texture";
    }

    @Override
    public KAssetDefinitionValidator getValidator() {
        return KRuleBasedAssetDefinitionValidatorBuilder
            .create()
            .withNotNullString("image")
            .withValidatedSubdefinition(
                "wrapping",
                KRuleBasedAssetDefinitionValidatorBuilder
                    .create()
                    .withEnum("u", KTextureWrapping.class)
                    .withEnum("v", KTextureWrapping.class)
                    .build()
            )
            .withValidatedSubdefinition(
                "filtering",
                KRuleBasedAssetDefinitionValidatorBuilder
                    .create()
                    .withEnum("min", KTextureFiltering.class)
                    .withEnum("mag", KTextureFiltering.class)
                    .build()
            )
            .build();
    }
}
