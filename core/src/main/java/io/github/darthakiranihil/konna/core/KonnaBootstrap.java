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

package io.github.darthakiranihil.konna.core;

import io.github.darthakiranihil.konna.core.app.KArgumentParser;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonDeserializer;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.except.KBootstrapException;

final class KonnaBootstrap {

    private static final String ARG_PARSER_CLASS_KEY = "arg_parser";
    private static final String HYPERVISOR_ROOT_KEY = "hypervisor";
    private static final String HYPERVISOR_CLASS_KEY = "class";
    private static final String HYPERVISOR_CONFIG_KEY = "config";

    private static class Schema implements KJsonValidator {

        private final KJsonValidator validator;

        public Schema() {
            var builder = new KJsonPropertyValidationInfo.Builder();

            this.validator = new KJsonObjectValidator(
                builder
                    .withName(ARG_PARSER_CLASS_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(KJsonValueIsClassValidator.INSTANCE)
                    .build(),
                builder
                    .withName(HYPERVISOR_ROOT_KEY)
                    .withExpectedType(KJsonValueType.OBJECT)
                    .withValidator(
                        new KJsonObjectValidator(
                            builder
                                .createSeparated()
                                .withName(HYPERVISOR_CLASS_KEY)
                                .withExpectedType(KJsonValueType.STRING)
                                .withValidator(KJsonValueIsClassValidator.INSTANCE)
                                .build(),
                            builder
                                .createSeparated()
                                .withName(HYPERVISOR_CONFIG_KEY)
                                .withExpectedType(KJsonValueType.OBJECT)
                                .withValidator(KEngineHypervisorConfig.SCHEMA)
                                .build()
                        )
                    )
                    .build()
            );
        }

        @Override
        public void validate(KJsonValue value) {
            this.validator.validate(value);
        }
    }

    public static final KJsonValidator SCHEMA = new Schema();

    private final KJsonValue config;

    public KonnaBootstrap(KJsonValue validatedConfig) {
        this.config = validatedConfig;
    }

    @SuppressWarnings("unchecked")
    public KArgumentParser getArgumentParser() {

        try {
            var argumentParserClass = (Class<? extends KArgumentParser>)
                Class.forName(this.config.getProperty(ARG_PARSER_CLASS_KEY).getString());

            return argumentParserClass
                .getConstructor()
                .newInstance();

        } catch (Throwable e) {
            throw new KBootstrapException("Could not create engine hypervisor", e);
        }

    }
    @SuppressWarnings("unchecked")
    public KEngineHypervisor createHypervisor() {

        KJsonDeserializer deserializer = new KStandardJsonDeserializer();

        try {

            var hypervisorData = this.config.getProperty(HYPERVISOR_ROOT_KEY);

            var engineHypervisorClass = (Class<? extends KEngineHypervisor>)
                Class.forName(hypervisorData.getProperty(HYPERVISOR_CLASS_KEY).getString());

            var engineHypervisorConfig = hypervisorData.getProperty(HYPERVISOR_CONFIG_KEY);
            KEngineHypervisorConfig deserializedConfig = deserializer.deserialize(
                engineHypervisorConfig,
                KEngineHypervisorConfig.class
            );

            return engineHypervisorClass
                .getConstructor(KEngineHypervisorConfig.class)
                .newInstance(deserializedConfig);

        } catch (Throwable e) {
            throw new KBootstrapException("Could not create engine hypervisor", e);
        }
    }

}
