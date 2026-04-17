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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentLoader;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KArgs;
import io.github.darthakiranihil.konna.level.service.KLevelEntityManagementService;
import io.github.darthakiranihil.konna.level.service.KLevelService;

import java.io.InputStream;

/**
 * Default loader for {@link KLevelComponent}.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public class KLevelComponentLoader implements KComponentLoader {

    private final KJsonParser parser;
    private final KJsonDeserializer deserializer;

    /**
     * Standard constructor.
     * @param parser Json parser to parse config
     * @param deserializer Json deserializer to deserialize config
     */
    @KInject
    public KLevelComponentLoader(
        final KJsonParser parser,
        final KJsonDeserializer deserializer
    ) {
        this.parser = parser;
        this.deserializer = deserializer;
    }

    @Override
    public KComponent load(
        final KEngineModule engineModule,
        final KApplicationFeatures features,
        final KSystemFeatures systemConfig
    ) {
        KResourceLoader resourceLoader = engineModule.resourceLoader();
        KJsonValue parsedConfig;
        try (KResource config = resourceLoader.loadResource("classpath:config/level.json")) {
            InputStream configStream = config.stream();
            if (!config.exists() || configStream == null) {
                throw new KComponentLoadingException(
                    String.format(
                        "Component config file %s not found",
                        "classpath:config/level.json"
                    )
                );
            }
            parsedConfig = this.parser.parse(configStream);
        }

        KLevelComponentConfig.getSchema().validate(parsedConfig);
        KLevelComponentConfig deserializedConfig = deserializer.deserialize(
            parsedConfig,
            KLevelComponentConfig.class
        );
        if (deserializedConfig == null) {
            throw new KComponentLoadingException("Could not read component config");
        }

        KActivator activator = engineModule.activator();
        KMessenger messenger = activator.createObject(KMessenger.class, KMessenger.args("Level"));
        KArgs messengerArgs = KService.argsOfServiceWithMessenger(messenger);
        return new KLevelComponent(
            engineModule,
            activator.createObject(KLevelService.class, messengerArgs),
            activator.createObject(KLevelEntityManagementService.class, messengerArgs),
            deserializedConfig
        );
    }
}
