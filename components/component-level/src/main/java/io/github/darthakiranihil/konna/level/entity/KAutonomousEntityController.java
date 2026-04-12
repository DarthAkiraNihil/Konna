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

import io.github.darthakiranihil.konna.core.except.KIllegalStateException;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.object.KArgs;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelComponentTags;
import org.jspecify.annotations.Nullable;

import java.util.Collections;

/**
 * Provides for assigned autonomous entity next move directions in order
 * for them to move. The way it returns a new direction depends on implementation.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public abstract class KAutonomousEntityController extends KObject {

    /**
     * Constructs {@link KArgs} object for this class.
     * @param qualifier Controller qualifier
     * @param params Controller params
     * @return Packed args
     */
    public static KArgs args(final String qualifier, final KAssetDefinition params) {
        return () -> new Object[] {qualifier, params};
    }

    private @Nullable KLevelEntity assignedEntity;
    private @Nullable KLevel level;

    /**
     * <p>
     *     Standard constructor.
     * </p>
     * <p>
     *     Please note that if this controller needs validation for passed parameters,
     *     its constructor should be annotated with {@link KAutonomousEntityControllerParam}
     *     or {@link KAutonomousEntityControllerParams} annotations.
     * </p>
     * @param qualifier Controller qualifier
     * @param params Controller parameters
     */
    public KAutonomousEntityController(
        final String qualifier,
        final KAssetDefinition params
    ) {
        super(
            String.format(
                "controller_%s", qualifier
            ),
            Collections.singleton(KLevelComponentTags.CONTROLLER)
        );

        this.applyParams(params);
    }

    /**
     * Applies params to this controller.
     * @param params Params to apply
     */
    protected void applyParams(final KAssetDefinition params) {

    }

    /**
     * @return Next move direction for assigned entity
     */
    public abstract KVector2i getNextMoveDirection();

    /**
     * Sets a level for this controller.
     * @param level Level for this controller
     */
    public final void setLevel(final KLevel level) {
        this.level = level;
    }

    /**
     * @return Level assigned to this controller
     */
    protected final KLevel getLevel() {
        if (this.level == null) {
            throw new KIllegalStateException(
                "Autonomous entity controller must be assigned to a level, which is not presented"
            );
        }

        return this.level;
    }

    /**
     * Sets an assigned entity to this controller.
     * @param entity Assigned entity for this controller
     */
    public final void setAssignedEntity(final KLevelEntity entity) {
        this.assignedEntity = entity;
    }

    /**
     * @return Entity assigned to this controller
     */
    protected final KLevelEntity getAssignedEntity() {
        if (this.assignedEntity == null) {
            throw new KIllegalStateException(
                "Autonomous entity controller must be assigned to an entity, which is not presented"
            );
        }

        return this.assignedEntity;
    }
}
