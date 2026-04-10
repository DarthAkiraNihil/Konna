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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentLoader;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.service.KRenderService;

import java.io.InputStream;

/**
 * Default loader for {@link KGraphicsComponent}.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public class KGraphicsComponentLoader implements KComponentLoader {

    private final KJsonParser parser;
    private final KJsonDeserializer deserializer;

    /**
     * Standard constructor.
     * @param parser Json parser to parse config
     * @param deserializer Json deserializer to deserialize config
     */
    @KInject
    public KGraphicsComponentLoader(
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
        try (KResource config = ctx.loadResource("classpath:config/graphics.json")) {
            InputStream configStream = config.stream();
            if (!config.exists() || configStream == null) {
                throw new KComponentLoadingException(
                    String.format(
                        "Component config file %s not found",
                        "classpath:config/graphics.json"
                    )
                );
            }
            parsedConfig = this.parser.parse(configStream);
        }

        KGraphicsComponentConfig.getSchema().validate(parsedConfig);
        KGraphicsComponentConfig deserializedConfig = this.deserializer.deserialize(
            parsedConfig,
            KGraphicsComponentConfig.class
        );

        if (deserializedConfig == null) {
            throw new KComponentLoadingException("Could not read component config");
        }

        KTransformMatrixCalculator calculator = ctx
            .createObject(KTransformMatrixCalculator.class);
        KTransform.setTransformMatrixCalculator(calculator);

        // A test that transform matrix calculator is really set
        (
            new KTransform(
                0.0,
                new KVector2i(1, 1),
                new KVector2d(0.0, 0.0),
                KVector2i.ZERO
            )
        ).getMatrix();


        return new KGraphicsComponent(
            ctx,
            ctx.createObject(KRenderService.class),
            deserializedConfig
        );
    }
}
