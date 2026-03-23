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

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KCompositeAssetDefinitionRuleBuilder;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;

/**
 * <p>
 *     Asset type definition for levels.
 * </p>
 * <p>
 *      Its asset schema is
 *      <ul>
 *          <li>
 *              {@code sectors} - level sectors (subdefinition)
 *              <ul>
 *                  <li>key is a non-null string, representing name of the sector/li>
 *                  <li>
 *                      value is a subdefinition, containing information about sector
 *                      <ul>
 *                          <li>
 *                              {@code size} - sector's size (subdefinition)
 *                              <ul>
 *                                  <li>{@code width} - int</li>
 *                                  <li>{@code height} - int</li>
 *                              </ul>
 *                          </li>
 *                          <li>
 *                              {@code tiles} - sector tiles
 *                              (array of width * height non-null strings)
 *                          </li>
 *                          <li>
 *                              {@code heights} - sector heightmap
 *                              (array of width * height ints)
 *                          </li>
 *                          <li>
 *                              {@code sector_links} - sector links (subdefinition array)
 *                              <ul>
 *                                  <li>
 *                                      {@code sector} - name of linked sector (non-null string)
 *                                  </li>
 *                                  <li>
 *                                      {@code position} - position of the link (subdefinition)
 *                                      <ul>
 *                                          <li>{@code x} - int</li>
 *                                          <li>{@code y} - int</li>
 *                                      </ul>
 *                                  </li>
 *                                  <li>
 *                                      {@code destination} - destination on the linked sector
 *                                      (subdefinition)
 *                                      <ul>
 *                                          <li>{@code x} - int</li>
 *                                          <li>{@code y} - int</li>
 *                                      </ul>
 *                                  </li>
 *                              </ul>
 *                          </li>
 *                          <li>
 *                              {@code entities} - sector entities (subdefinition array)
 *                              <ul>
 *                                  <li>
 *                                      {@code position} - position to place entities on
 *                                  </li>
 *                                  <li>
 *                                      {@code controllable} - list of controllable entities
 *                                      (subdefinition array)
 *                                      <ul>
 *                                          <li>
 *                                              {@code name} - entity name (non-null string)
 *                                          </li>
 *                                          <li>
 *                                              {@code descriptor} - entity descriptor
 *                                              (non-null string)
 *                                          </li>
 *                                      </ul>
 *                                  </li>
 *                                  <li>
 *                                      {@code static} - list of static entities
 *                                      (subdefinition array)
 *                                      <ul>
 *                                          <li>
 *                                              {@code name} - entity name (non-null string)
 *                                          </li>
 *                                          <li>
 *                                              {@code descriptor} - entity descriptor
 *                                              (non-null string)
 *                                          </li>
 *                                      </ul>
 *                                  </li>
 *                                  <li>
 *                                      {@code autonomous} - list of static entities
 *                                      (subdefinition array)
 *                                      <ul>
 *                                          <li>
 *                                              {@code name} - entity name (non-null string)
 *                                          </li>
 *                                          <li>
 *                                              {@code descriptor} - entity descriptor
 *                                              (non-null string)
 *                                          </li>
 *                                          <li>
 *                                              {@code controller} - entity controller
 *                                              (reference to a class extending
 *                                              {@link KAutonomousEntityController}
 *                                          </li>
 *                                          <li>
 *                                              {@code params} - controller params (subdefinition)
 *                                          </li>
 *                                      </ul>
 *                                  </li>
 *                              </ul>
 *                          </li>
 *                      </ul>
 *                  </li>
 *              </ul>
 *          </li>
 *
 *      </ul>
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLevelTypedef implements KAssetTypedef {

    /**
     * Constant for level type inside Level component.
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

        KAssetDefinitionRule sectorRule = KCompositeAssetDefinitionRuleBuilder
            .create()
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
            .withValidatedSubdefinitionArray(
                "level_transitions",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withValidatedSubdefinition(
                        "position",
                        positionRule
                    )
                    .withNotNullString("level_descriptor")
                    .withEnum("level_type", KTransitionedLevelType.class)
                    .withNotNullString("destination_sector")
                    .withValidatedSubdefinition(
                        "destination_position",
                        positionRule
                    )
                    .build()
            )
            .build();

        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withValidatedSubdefinition(
                "sectors",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withRule(sectors -> {
                        for (var sector: sectors.getProperties()) {
                            if (!sectors.hasSubdefinition(sector)) {
                                throw KAssetDefinitionError.propertyNotFound(sector);
                            }

                            KAssetDefinition sectorDefinition = sectors.getSubdefinition(sector);
                            sectorRule.validate(sectorDefinition);
                        }
                    })
                    .build()
            )
            .build();
    }
}
