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
 * <p>
 *     Asset type definition for textures.
 * </p>
 * <p>
 *      Its asset schema is
 *      <ul>
 *          <li>
 *              {@code image} - path to a valid image to use in texture (non-null string)
 *          </li>
 *          <li>
 *              {@code shader} - asset id of a valid
 *              {@link io.github.darthakiranihil.konna.graphics.shader.KShaderProgram} to
 *              link to the texture (non-null string)
 *          </li>
 *          <li>
 *              {@code wrapping} - texture wrapping (subdefinition)
 *              <ul>
 *                  <li>{@code u} - a valid {@link KTextureWrapping}</li>
 *                  <li>{@code v} - a valid {@link KTextureWrapping}</li>
 *              </ul>
 *          </li>
 *          <li>
 *              {@code filtering} - texture filtering (subdefinition)
 *              <ul>
 *                  <li>{@code min} - a valid {@link KTextureFiltering}</li>
 *                  <li>{@code mag} - a valid {@link KTextureFiltering}</li>
 *              </ul>
 *          </li>
 *      </ul>
 * </p>
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
            .withNotNullString("shader")
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
