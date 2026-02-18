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

import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KCompositeAssetDefinitionRuleBuilder;
import io.github.darthakiranihil.konna.graphics.image.KTextureFiltering;
import io.github.darthakiranihil.konna.graphics.image.KTextureWrapping;

/**
 * Asset type definition for textures.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public final class KTextureTypedef implements KAssetTypedef {

    /**
     * Constant for texture asset type inside Graphics component.
     */
    public static final String TEXTURE_ASSET_TYPE = "Graphics.texture";

    @Override
    public String getName() {
        return TEXTURE_ASSET_TYPE;
    }

    @Override
    public KAssetDefinitionRule getRule() {
        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withNotNullString("image")
            .withValidatedSubdefinition(
                "wrapping",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withEnum("u", KTextureWrapping.class)
                    .withEnum("v", KTextureWrapping.class)
                    .build()
            )
            .withValidatedSubdefinition(
                "filtering",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withEnum("min", KTextureFiltering.class)
                    .withEnum("mag", KTextureFiltering.class)
                    .build()
            )
            .build();
    }
}
