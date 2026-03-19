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
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTextureSource;

/**
 * <p>
 *     Asset type definition for renderable textures.
 * </p>
 * <p>
 *      Its asset schema is
 *      <ul>
 *          <li>{@code source} - a valid {@link KRenderableTextureSource}</li>
 *          <li>
 *              {@code slice_set} - string that may be null if {@code source} is
 *              {@link KRenderableTextureSource#WHOLE_TEXTURE}, else must be an
 *              asset id of a
 *              {@link io.github.darthakiranihil.konna.graphics.image.KTextureSliceSet}
 *          </li>
 *      </ul>
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KRenderableTextureTypedef implements KAssetTypedef {

    /**
     * Constant for renderable texture set type inside Graphics component.
     */
    public static final String RENDERABLE_TEXTURE_ASSET_TYPE = "Graphics.renderableTexture";

    @Override
    public String getName() {
        return RENDERABLE_TEXTURE_ASSET_TYPE;
    }

    @Override
    public KAssetDefinitionRule getRule() {
        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withEnum("source", KRenderableTextureSource.class)
            .withString("slice_set")
            .withRule(d -> {
                String textureSetId = d.getString("slice_set");
                KRenderableTextureSource source = d.getEnum(
                    "source",
                    KRenderableTextureSource.class
                );
                if (source == KRenderableTextureSource.SLICE_SET && textureSetId == null) {
                    throw new KAssetDefinitionError(
                        "Texture set id cannot be null if a renderable texture is taken from there"
                    );
                }
            })
            .build();
    }

}
