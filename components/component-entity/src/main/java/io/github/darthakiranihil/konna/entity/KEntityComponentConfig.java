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

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;

/**
 * Container for Entity engine component configuration.
 * @param entityFactoryClass Class of entity factory (to create new entities)
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public record KEntityComponentConfig(
    @KJsonSerialized @KJsonCustomName(name = ENTITY_FACTORY_CLASS_KEY)
    Class<? extends KEntityFactory> entityFactoryClass
) {

    private static final String ENTITY_FACTORY_CLASS_KEY = "entity_factory";

    /**
     * @return JSON schema of config, that should be used
     *         for validation of loaded JSON  file.
     */
    public static KJsonValidator getSchema() {
        return KJsonObjectValidatorBuilder
            .create()
            .withField(ENTITY_FACTORY_CLASS_KEY, KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .build();
    }

}
