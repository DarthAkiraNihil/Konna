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

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KCompositeAssetDefinitionRuleBuilder;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;

public final class KTextureSliceSetTypedef implements KAssetTypedef {

    public static final String TEXTURE_SLICE_SET_ASSET_TYPE = "Graphics.textureSliceSet";

    @Override
    public String getName() {
        return TEXTURE_SLICE_SET_ASSET_TYPE;
    }

    @Override
    public KAssetDefinitionRule getRule() {
        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withNotNullString("base_texture")
            .withValidatedSubdefinitionArray(
                "slices",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withNotNullString("name")
                    .withValidatedSubdefinitionArray(
                        "uv",
                        KCompositeAssetDefinitionRuleBuilder
                            .create()
                            .withFloat("u")
                            .withFloat("v")
                            .build()
                    )
                    .withValidatedSubdefinitionArray(
                        "xy",
                        KCompositeAssetDefinitionRuleBuilder
                            .create()
                            .withInt("x")
                            .withInt("y")
                            .build()
                    )
                    .withValidatedSubdefinitionArray(
                        "colors",
                        KCompositeAssetDefinitionRuleBuilder
                            .create()
                            .withInt("r")
                            .withInt("g")
                            .withInt("b")
                            .build()
                    )
                    .withRule(
                        d -> {
                            KAssetDefinition[] uv = d.getSubdefinitionArray("uv");
                            KAssetDefinition[] xy = d.getSubdefinitionArray("xy");
                            KAssetDefinition[] colors = d.getSubdefinitionArray("colors");

                            //if (uv.length != xy.length || xy.length != colors.length || uv.length != colors.length) {
                            if (uv.length != xy.length || xy.length != colors.length || uv.length != colors.length) {
                                throw new KAssetDefinitionError("Amount of uv, xy and colors components must be the same");
                            }
                        }
                    )
                    .build()
            )
            .build();
    }
}
