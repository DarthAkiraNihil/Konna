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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;

import java.util.List;

/**
 * Record class of initial Konna hypervisor configuration.
 * @param messageRoutesConfigurers List of engine message routes configurers
 * @param eventRegisterers List of engine event registerers
 * @param componentLoaders List of engine components classes to loadz
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KEngineHypervisorConfig(
    @KJsonSerialized @KJsonCustomName(name = APP_CONTAINER_KEY)
    Class<? extends KAppContainer> applicationContainer,

    @KJsonSerialized @KJsonCustomName(name = MESSAGE_ROUTE_CONFIGURERS_KEY)
    @KJsonArray(elementType = Class.class)
    List<Class<? extends KMessageRoutesConfigurer>> messageRoutesConfigurers,

    @KJsonSerialized @KJsonCustomName(name = EVENT_REGISTERERS_KEY)
    @KJsonArray(elementType = Class.class)
    List<Class<? extends KEventRegisterer>> eventRegisterers,

    @KJsonSerialized @KJsonCustomName(name = COMPONENTS_LOADERS_KEY)
    @KJsonArray(elementType = Class.class)
    List<Class<? extends KComponentLoader>> componentLoaders
) {

    private static final String APP_CONTAINER_KEY = "application_container";
    private static final String COMPONENTS_LOADERS_KEY = "component_loaders";
    private static final String MESSAGE_ROUTE_CONFIGURERS_KEY = "route_configurers";
    private static final String EVENT_REGISTERERS_KEY = "event_registerers";

    /**
     * @return JSON schema of config, that should be used
     *         for validation of loaded JSON file.
     */
    public static KJsonValidator getSchema() {
        return KJsonObjectValidatorBuilder
            .create()
            .withField(APP_CONTAINER_KEY, KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .withField(MESSAGE_ROUTE_CONFIGURERS_KEY, KJsonValueType.ARRAY)
            .withValidator(
                KJsonArrayValidatorBuilder
                    .create(KJsonValueType.STRING)
                    .withValidator(new KJsonValueIsClassValidator())
                    .build()
            )
            .finishField()
            .withField(EVENT_REGISTERERS_KEY, KJsonValueType.ARRAY)
            .withValidator(
                KJsonArrayValidatorBuilder
                    .create(KJsonValueType.STRING)
                    .withValidator(new KJsonValueIsClassValidator())
                    .build()
            )
            .finishField()
            .withField(COMPONENTS_LOADERS_KEY, KJsonValueType.ARRAY)
            .withValidator(
                KJsonArrayValidatorBuilder
                    .create(KJsonValueType.STRING)
                    .withValidator(new KJsonValueIsClassValidator())
                    .build()
            )
            .finishField()
            .build();
    }

}
