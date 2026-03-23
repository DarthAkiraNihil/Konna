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

package io.github.darthakiranihil.konna.level.layer;

import io.github.darthakiranihil.konna.core.struct.KVector2i;

/**
 * Level transition data record.
 * @param levelDescriptor Transitioned level descriptor.
 *                        If level is {@link KTransitionedLevelType#PREDEFINED},
 *                        it represents asset id of destination level.
 *                        If level is {@link KTransitionedLevelType#GENERATED},
 *                        it represents asset id of generator to use to generate
 *                        destination level
 * @param type Type of destination level
 * @param destinationSector Destination sector name
 * @param destinationPosition Destination position on sector
 */
public record KLevelTransitionData(
    String levelDescriptor,
    KTransitionedLevelType type,
    String destinationSector,
    KVector2i destinationPosition
) {
}
