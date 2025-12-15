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
import io.github.darthakiranihil.konna.core.data.json.std.KJsonArrayValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.core.message.KEventQueue;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Record class of initial Konna hypervisor configuration.
 * @param contextLoader Class of engine context loader
 * @param componentLoader Class of engine component loader
 * @param serviceLoader Class of component services loader
 * @param messageRoutesConfigurers List of engine message routes configurers
 * @param eventRegisterers List of engine event registerers
 * @param components List of engine components classes to load
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

    @KJsonSerialized @KJsonCustomName(name = EVENT_QUEUE_KEY)
    Class<? extends KEventQueue> eventQueue
) {

    private static final String ENGINE_CONTEXT_LOADER_KEY = "context_loader";
    private static final String COMPONENT_LOADER_KEY = "component_loader";
    private static final String SERVICE_LOADER_KEY = "service_loader";
    private static final String COMPONENTS_KEY = "components";
    private static final String MESSAGE_ROUTE_CONFIGURERS_KEY = "route_configurers";
    private static final String EVENT_REGISTERERS_KEY = "event_registerers";
    private static final String EVENT_QUEUE_KEY = "event_queue";

    @NullMarked
    private static final class Schema implements KJsonValidator {

        private final KJsonValidator schema;

        Schema() {
            var propInfoBuilder = new KJsonPropertyValidationInfo.Builder();

            this.schema = new KJsonObjectValidator(
                propInfoBuilder
                    .withName(ENGINE_CONTEXT_LOADER_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(
                        KJsonValueIsClassValidator.INSTANCE
                    )
                    .build(),
                propInfoBuilder
                    .withName(COMPONENT_LOADER_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(
                        KJsonValueIsClassValidator.INSTANCE
                    )
                    .build(),
                propInfoBuilder
                    .withName(SERVICE_LOADER_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(
                        KJsonValueIsClassValidator.INSTANCE
                    )
                    .build(),
                propInfoBuilder
                    .withName(MESSAGE_ROUTE_CONFIGURERS_KEY)
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(
                        new KJsonArrayValidator(
                            KJsonValueType.STRING,
                            KJsonValueIsClassValidator.INSTANCE
                        )
                    )
                    .build(),
                propInfoBuilder
                    .withName(EVENT_REGISTERERS_KEY)
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(
                        new KJsonArrayValidator(
                            KJsonValueType.STRING,
                            KJsonValueIsClassValidator.INSTANCE
                        )
                    )
                    .build(),
                propInfoBuilder
                    .withName(COMPONENTS_KEY)
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(
                        new KJsonArrayValidator(
                            KJsonValueType.STRING,
                            KJsonValueIsClassValidator.INSTANCE
                        )
                    )
                    .build(),
                propInfoBuilder
                    .withName(EVENT_QUEUE_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(
                        KJsonValueIsClassValidator.INSTANCE
                    )
                    .build()

            );
        }

        @Override
        public void validate(final KJsonValue value) {
            this.schema.validate(value);
        }
    }

    /**
     * JSON schema of config, that should be used
     * for validation of loaded json file.
     */
    public static final KJsonValidator SCHEMA = new Schema();

}
