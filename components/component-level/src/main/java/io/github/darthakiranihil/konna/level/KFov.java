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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.level.entity.KLevelEntity;

import java.util.List;

/**
 * Convenience class that represents entity's field of view.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KFov {

    private final List<KLevelSectorSlice> observedSlices;

    /**
     * Constructs FOV with list of observed slices.
     * @param observedSlices List of observed slices
     */
    public KFov(final List<KLevelSectorSlice> observedSlices) {
        this.observedSlices = observedSlices;
    }

    /**
     * @return List of observed slices of this FOV
     */
    public List<KLevelSectorSlice> getObservedSlices() {
        return this.observedSlices;
    }

    /**
     * @param descriptor Descriptor of entities to find
     * @return List of observed entities with specified descriptor inside this FOV
     */
    public List<KLevelEntity> getEntitiesWithDescriptor(final String descriptor) {
        return
            this.observedSlices
                .stream()
                .flatMap(x -> x.entities().stream())
                .filter(x -> x.getDescriptor().equals(descriptor))
                .toList();
    }

}
