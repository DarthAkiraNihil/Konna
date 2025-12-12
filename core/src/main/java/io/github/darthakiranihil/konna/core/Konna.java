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

import io.github.darthakiranihil.konna.core.app.KApplicationArgument;
import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.except.KBootstrapException;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Konna extends KObject {

    private static final String BOOTSTRAP_CONFIG = "bootstrap.json";

    private static List<KApplicationArgument> defaultAndCustom(List<KApplicationArgument> customArgs) {
        List<KApplicationArgument> concatenated = new ArrayList<>(KApplicationArgument.DEFAULT_ARGS);
        concatenated.addAll(customArgs);
        return Collections.unmodifiableList(concatenated);
    }

    public Konna(final String[] args) {
        this(args, KApplicationArgument.DEFAULT_ARGS);

        try (InputStream bootstrapConfig = ClassLoader
            .getSystemClassLoader()
            .getResourceAsStream(BOOTSTRAP_CONFIG)
        ) {

            if (bootstrapConfig == null) {
                throw new KBootstrapException(
                    String.format(
                        "Cannot read bootstrap config file %s from resources",
                        BOOTSTRAP_CONFIG
                    )
                );
            }

            KJsonParser parser = new KStandardJsonParser(new KStandardJsonTokenizer());
            KJsonValue config = parser.parse(bootstrapConfig);
            KonnaBootstrap.SCHEMA.validate(config);
            KonnaBootstrap bootstrap = new KonnaBootstrap(config);

            KApplicationFeatures features = bootstrap.argumentParser().parse(args, KApplicationArgument.DEFAULT_ARGS);


        } catch (IOException e) {

        } catch (KJsonValidationError e) {
            KSystemLogger.fatal(e);
        }

    }

    public Konna(final String[] args, List<KApplicationArgument> customArgs) {
        //this.
    }

}
