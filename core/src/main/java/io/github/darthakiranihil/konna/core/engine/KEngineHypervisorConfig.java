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

import io.github.darthakiranihil.konna.core.app.KFrameLoader;
import io.github.darthakiranihil.konna.core.app.KFrameSpawnOptions;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;

import java.util.List;

/**
 * Record class of initial Konna hypervisor configuration.
 * @param contextLoader Class of engine context loader
 * @param componentLoader Class of engine component loader
 * @param serviceLoader Class of component services loader
 * @param messageRoutesConfigurers List of engine message routes configurers
 * @param eventRegisterers List of engine event registerers
 * @param components List of engine components classes to load
 * @param frameLoader Class of application's frame loader
 * @param frameSpawnOptions Initial spawn options of application's frame
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KEngineHypervisorConfig(
    @KJsonSerialized @KJsonCustomName(name = ENGINE_CONTEXT_LOADER_KEY)
    Class<? extends KEngineContextLoader> contextLoader,

    @KJsonSerialized @KJsonCustomName(name = COMPONENT_LOADER_KEY)
    Class<? extends KComponentLoader> componentLoader,

    @KJsonSerialized @KJsonCustomName(name = SERVICE_LOADER_KEY)
    Class<? extends KServiceLoader> serviceLoader,

    @KJsonSerialized @KJsonCustomName(name = MESSAGE_ROUTE_CONFIGURERS_KEY)
    @KJsonArray(elementType = Class.class)
    List<Class<? extends KMessageRoutesConfigurer>> messageRoutesConfigurers,

    @KJsonSerialized @KJsonCustomName(name = EVENT_REGISTERERS_KEY)
    @KJsonArray(elementType = Class.class)
    List<Class<? extends KEventRegisterer>> eventRegisterers,

    @KJsonSerialized @KJsonCustomName(name = COMPONENTS_KEY)
    @KJsonArray(elementType = Class.class)
    Class<? extends KComponent>[] components,

    @KJsonSerialized @KJsonCustomName(name = FRAME_LOADER_KEY)
    Class<? extends KFrameLoader> frameLoader,

    @KJsonSerialized @KJsonCustomName(name = FRAME_OPTIONS_KEY)
    KFrameSpawnOptions frameSpawnOptions
) {

    private static final String ENGINE_CONTEXT_LOADER_KEY = "context_loader";
    private static final String COMPONENT_LOADER_KEY = "component_loader";
    private static final String SERVICE_LOADER_KEY = "service_loader";
    private static final String COMPONENTS_KEY = "components";
    private static final String MESSAGE_ROUTE_CONFIGURERS_KEY = "route_configurers";
    private static final String EVENT_REGISTERERS_KEY = "event_registerers";
    private static final String FRAME_LOADER_KEY = "frame_loader";
    private static final String FRAME_OPTIONS_KEY = "frame_options";

    /**
     * JSON schema of config, that should be used
     * for validation of loaded json file.
     */
    public static final KJsonValidator SCHEMA = KJsonObjectValidatorBuilder
        .create()
        .withField(ENGINE_CONTEXT_LOADER_KEY, KJsonValueType.STRING)
        .withValidator(KJsonValueIsClassValidator.INSTANCE)
        .finishField()
        .withField(COMPONENT_LOADER_KEY, KJsonValueType.STRING)
        .withValidator(KJsonValueIsClassValidator.INSTANCE)
        .finishField()
        .withField(SERVICE_LOADER_KEY, KJsonValueType.STRING)
        .withValidator(KJsonValueIsClassValidator.INSTANCE)
        .finishField()
        .withField(MESSAGE_ROUTE_CONFIGURERS_KEY, KJsonValueType.ARRAY)
        .withValidator(
            KJsonArrayValidatorBuilder
                .create(KJsonValueType.STRING)
                .withValidator(KJsonValueIsClassValidator.INSTANCE)
                .build()
        )
        .finishField()
        .withField(EVENT_REGISTERERS_KEY, KJsonValueType.ARRAY)
        .withValidator(
            KJsonArrayValidatorBuilder
                .create(KJsonValueType.STRING)
                .withValidator(KJsonValueIsClassValidator.INSTANCE)
                .build()
        )
        .finishField()
        .withField(COMPONENTS_KEY, KJsonValueType.ARRAY)
        .withValidator(
            KJsonArrayValidatorBuilder
                .create(KJsonValueType.STRING)
                .withValidator(KJsonValueIsClassValidator.INSTANCE)
                .build()
        )
        .finishField()
        .withField(FRAME_LOADER_KEY, KJsonValueType.STRING)
        .withValidator(KJsonValueIsClassValidator.INSTANCE)
        .finishField()
        .withField(FRAME_OPTIONS_KEY, KJsonValueType.OBJECT)
        .withValidator(KFrameSpawnOptions.SCHEMA)
        .finishField()
        .build();

}
