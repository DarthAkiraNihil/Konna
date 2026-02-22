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

package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Maximum abstract object, representing container for entity properties
 * and only that. Also provides some convenience methods, used to get
 * that properties.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public class KEntity extends KObject {

    /**
     * Default tag, used to mark all created entity objects.
     */
    public static final KTag TAG = new KTag("entity");

    private final List<KEntityDataComponent> dataComponents;
    private final List<KEntityBehaviour> behaviours;

    private final String type;

    /**
     * Standard constructor.
     * @param name Name of the entity
     * @param type Name of entity type
     * @param dataComponents List of data components, used by this entity
     */
    public KEntity(
        final String name,
        final String type,
        final List<KEntityDataComponent> dataComponents
    ) {
        super(name, KStructUtils.setOfTags(TAG));

        this.type = type;
        this.dataComponents = dataComponents;
        this.behaviours = new LinkedList<>();
    }

    /**
     * Tries to get a data components with specific class from this entity.
     * @param clazz Class of the data component to get
     * @return The actual data component instance or {@code null} if it is not found
     * @param <T> Type parameter for retrieved component
     */
    @SuppressWarnings("unchecked")
    public final <T extends KEntityDataComponent> @Nullable T getComponent(
        final Class<T> clazz
    ) {

        var result = this
            .dataComponents
            .stream()
            .filter(c -> c.getClass() == clazz)
            .findFirst()
            .orElse(null);

        return (T) result;

    }

    /**
     * @return Type of this entity
     */
    public final String type() {
        return this.type;
    }

    public void addBehaviour(final KEntityBehaviour behaviour) {
        this.behaviours.add(behaviour);
    }

    public List<KEntityBehaviour> getBehaviours() {
        return this.behaviours;
    }

}
