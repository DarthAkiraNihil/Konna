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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentLoader;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.entity.service.KEntityManagementService;

import java.io.InputStream;

/**
 * Default loader for {@link KEntityComponent}.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public class KEntityComponentLoader implements KComponentLoader {

    private final KJsonParser parser;
    private final KJsonDeserializer deserializer;

    /**
     * Standard constructor.
     * @param parser Json parser to parse config
     * @param deserializer Json deserializer to deserialize config
     */
    @KInject
    public KEntityComponentLoader(
        final KJsonParser parser,
        final KJsonDeserializer deserializer
    ) {
        this.parser = parser;
        this.deserializer = deserializer;
    }


    @Override
    public KComponent load(
        final KEngineContext ctx,
        final KApplicationFeatures features,
        final KSystemFeatures systemConfig
    ) {
        KJsonValue parsedConfig;
        try (KResource config = ctx.loadResource("classpath:config/entity.json")) {
            InputStream configStream = config.stream();
            if (!config.exists() || configStream == null) {
                throw new KComponentLoadingException(
                    String.format(
                        "Component config file %s not found",
                        "classpath:config/entity.json"
                    )
                );
            }
            parsedConfig = this.parser.parse(configStream);
        }

        KEntityComponentConfig.getSchema().validate(parsedConfig);
        KEntityComponentConfig cfg = deserializer.deserialize(
            parsedConfig, KEntityComponentConfig.class
        );
        if (cfg == null) {
            throw new KComponentLoadingException("Could not read component config");
        }

        KContainer container = ctx.getContainer();
        container.add(KEntityFactory.class, cfg.entityFactoryClass());

        KMessenger messenger = ctx.createObject(KMessenger.class);
        return new KEntityComponent(
            ctx,
            ctx.createObject(KEntityManagementService.class, messenger),
            cfg
        );
    }
}
