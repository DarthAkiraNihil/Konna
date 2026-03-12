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

import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.level.entity.state.KMapEntityState;
import io.github.darthakiranihil.konna.level.entity.state.KMapEntityStateTransition;

import java.util.List;
import java.util.Objects;

public final class KMapEntityStateMachineMetadataTypedef implements KAssetTypedef {

    public static final String STATE_MACHINE_METADATA_TYPEDEF = "Level.stateMachineMetadata";

    @Override
    public String getName() {
        return STATE_MACHINE_METADATA_TYPEDEF;
    }

    @Override
    public KAssetDefinitionRule getRule() {
        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withValidatedSubdefinition(
                "states",
                (d) -> {
                    List<String> props = d.getProperties();
                    for (var prop: props) {
                        if (d.hasClassObject(prop, KMapEntityState.class)) {
                            continue;
                        }

                        throw KAssetDefinitionError.propertyNotFound(prop);
                    }
                }
            )
            .withValidatedSubdefinitionArray(
                "transitions",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withNotNullString("from")
                    .withNotNullString("to")
                    .withClassObject("class", KMapEntityStateTransition.class)
                    .build()
            )
            .withRule(d -> {
                KAssetDefinition states = d.getSubdefinition("states");
                KAssetDefinition[] transitions = d.getSubdefinitionArray("transitions");

                for (int i = 0; i < transitions.length; i++) {
                    KAssetDefinition transition = transitions[i];
                    String from = Objects.requireNonNull(transition.getString("from"));
                    String to = Objects.requireNonNull(transition.getString("to"));

                    if (!states.hasClassObject(from)) {
                        throw new KAssetDefinitionError(
                            String.format(
                                "Transition %d starts from unknown state node: %s",
                                i,
                                from
                            )
                        );
                    }

                    if (!states.hasClassObject(to)) {
                        throw new KAssetDefinitionError(
                            String.format(
                                "Transition %d points to unknown state node: %s",
                                i,
                                to
                            )
                        );
                    }

                }
            })
            .build();
    }
}
