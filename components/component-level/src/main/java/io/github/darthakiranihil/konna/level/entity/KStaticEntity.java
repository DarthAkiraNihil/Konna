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

package io.github.darthakiranihil.konna.level.entity;

import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KLevelSector;

/**
 * Representation of a static entity that is not moved during its existence. It can be moved
 * manually though.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KStaticEntity extends KMapEntity {

    public KStaticEntity(
        final KEventSystem eventSystem,
        final String name,
        final String descriptor,
        final KVector2i position,
        final KLevelSector currentSector
    ) {
        super(eventSystem, name, descriptor, position, currentSector);
    }

    /**
     * @return Always {@link KVector2i#ZERO}
     */
    @Override
    protected KVector2i getNextMoveDirection() {
        return KVector2i.ZERO;
    }
}
