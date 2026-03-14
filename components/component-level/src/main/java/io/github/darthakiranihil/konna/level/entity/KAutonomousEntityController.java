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

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelComponentTags;
import io.github.darthakiranihil.konna.level.map.KLocation;

/**
 * Provides for assigned autonomous entity next move directions in order
 * for them to move. The way it returns a new direction depends on implementation.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public abstract class KAutonomousEntityController extends KObject {

    /**
     * Entity assigned to this controller.
     */
    protected final KMapEntity assignedEntity;
    /**
     * Location the assigned entity belongs to.
     */
    protected final KLocation location;

    /**
     * <p>
     *     Standard constructor.
     * </p>
     * <p>
     *     Please note that if this controller needs validation for passed parameters,
     *     its constructor should be annotated with {@link KAutonomousEntityControllerParam}
     *     or {@link KAutonomousEntityControllerParams} annotations.
     * </p>
     * @param assignedEntity Entity assigned to this controller
     *                       (that should be autonomous entity, also the controller should
     *                       be assigned to that entity)
     * @param location Location the assigned entity belongs to
     * @param params Controller parameters
     */
    public KAutonomousEntityController(
        final KMapEntity assignedEntity,
        final KLocation location,
        final KAssetDefinition params
    ) {
        super(
            String.format(
                "controller_%s:%s",
                assignedEntity.name(),
                assignedEntity.getDescriptor()
            ),
            KStructUtils.setOfTags(KLevelComponentTags.CONTROLLER)
        );
        this.assignedEntity = assignedEntity;
        this.location = location;

        this.applyParams(params);
    }

    /**
     * Applies params to this controller.
     * @param params Params to apply
     */
    protected abstract void applyParams(KAssetDefinition params);

    /**
     * @return Next move direction for assigned entity
     */
    public abstract KVector2i getNextMoveDirection();

}
