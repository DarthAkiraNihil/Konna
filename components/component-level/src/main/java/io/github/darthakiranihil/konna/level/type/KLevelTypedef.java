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

package io.github.darthakiranihil.konna.level.type;

import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KCompositeAssetDefinitionRuleBuilder;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;

/**
 * Asset type definition for levels that are simply levels.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLevelTypedef implements KAssetTypedef {

    /**
     * Constant for level property type inside Level component.
     */
    public static final String LEVEL_ASSET_TYPE = "Level.level";

    @Override
    public String getName() {
        return LEVEL_ASSET_TYPE;
    }

    @Override
    public KAssetDefinitionRule getRule() {

        KAssetDefinitionRule entityRule = KCompositeAssetDefinitionRuleBuilder
            .create()
            .withNotNullString("name")
            .withNotNullString("descriptor")
            .build();

        KAssetDefinitionRule positionRule = KCompositeAssetDefinitionRuleBuilder
            .create()
            .withInt("x")
            .withInt("y")
            .build();

        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withValidatedSubdefinitionArray(
                "sectors",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withNotNullString("name")
                    .withValidatedSubdefinition(
                        "size",
                        KCompositeAssetDefinitionRuleBuilder
                            .create()
                            .withInt("width")
                            .withInt("height")
                            .build()
                    )
                    .withStringArray("tiles")
                    .withIntArray("heights")
                    .withValidatedSubdefinitionArray(
                        "sector_links",
                        KCompositeAssetDefinitionRuleBuilder
                            .create()
                            .withNotNullString("sector")
                            .withValidatedSubdefinition(
                                "position",
                                positionRule
                            )
                            .withValidatedSubdefinition(
                                "destination",
                                positionRule
                            )
                            .build()
                    )
                    .withValidatedSubdefinitionArray(
                        "entities",
                        KCompositeAssetDefinitionRuleBuilder
                            .create()
                            .withValidatedSubdefinition(
                                "position",
                                positionRule
                            )
                            .withValidatedSubdefinitionArray(
                                "controllable",
                                entityRule
                            )
                            .withValidatedSubdefinitionArray(
                                "static",
                                entityRule
                            )
                            .withValidatedSubdefinitionArray(
                                "autonomous",
                                KCompositeAssetDefinitionRuleBuilder
                                    .create()
                                    .withNotNullString("name")
                                    .withNotNullString("descriptor")
                                    .withClassObject(
                                        "controller",
                                        KAutonomousEntityController.class
                                    )
                                    .withSubdefinition("params")
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();
    }
}
