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

/**
 * <p>
 *     Asset type definition for texture slice sets.
 * </p>
 * <p>
 *      Its asset schema is
 *      <ul>
 *          <li>
 *              {@code base_texture} - asset id of a valid
 *              {@link io.github.darthakiranihil.konna.graphics.image.KTexture} (non-null string)
 *          </li>
 *          <li>
 *              {@code slices} - list of slices (array of subdefinitions)
 *              <ul>
 *                  <li>{@code name} - name of a slice (non-null string)</li>
 *                  <li>
 *                      {@code uv} - list of slice uvs (array of subdefinitions)
 *                      <ul>
 *                          <li>{@code u} - float</li>
 *                          <li>{@code v} - float</li>
 *                      </ul>
 *                  </li>
 *                  <li>
 *                      {@code xy} - list of slice xys (array of subdefinitions)
 *                      <ul>
 *                          <li>{@code x} - float</li>
 *                          <li>{@code y} - float</li>
 *                      </ul>
 *                  </li>
 *                  <li>
 *                      {@code colors} - list of slice colors (array of subdefinitions)
 *                      <ul>
 *                          <li>{@code r} - int</li>
 *                          <li>{@code g} - int</li>
 *                          <li>{@code b} - int</li>
 *                      </ul>
 *                  </li>
 *                  <li>
 *                      number of elements in {@code uv}, {@code xy} and {@code colors}
 *                      must be the same!
 *                  </li>
 *              </ul>
 *          </li>
 *      </ul>
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KTextureSliceSetTypedef implements KAssetTypedef {

    /**
     * Constant for texture slice set type inside Graphics component.
     */
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

                            if (uv.length != xy.length || xy.length != colors.length) {
                                throw new KAssetDefinitionError(
                                    "Amount of uv, xy and colors components must be the same"
                                );
                            }
                        }
                    )
                    .build()
            )
            .build();
    }
}
