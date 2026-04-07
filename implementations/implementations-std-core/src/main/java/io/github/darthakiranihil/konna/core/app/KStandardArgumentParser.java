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

package io.github.darthakiranihil.konna.core.app;

import io.github.darthakiranihil.konna.core.app.except.KArgumentParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Standard implementation of {@link KArgumentParser}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KStandardArgumentParser implements KArgumentParser {

    @Override
    public KApplicationFeatures parse(
        final String[] args,
        final List<KApplicationArgument> options
    ) {

        Map<String, String> features = new HashMap<>();

        for (var option: options) {

            String value = null;
            String featureName = option.longKey();
            for (String arg: args) {
                String shortQualifier = String.format(
                    "-%s%s",
                    option.kQualified() ? "K" : "",
                    option.shortKey()
                );

                String longQualifier = String.format(
                    "--%s%s",
                    option.kQualified() ? "K" : "",
                    featureName
                );

                if (arg.startsWith(shortQualifier)) {
                    value = arg.substring(shortQualifier.length());
                } else if (arg.startsWith(longQualifier)) {
                    var qualifierLength = longQualifier.length();
                    if (arg.length() < qualifierLength + 1) {
                        value = arg.substring(longQualifier.length());
                    } else {
                        value = arg.substring(longQualifier.length() + 1);
                    }
                } else {
                    continue;
                }

                break;

            }

            if (value == null || value.isEmpty()) {
                if (option.defaultValue() == null) {
                    throw KArgumentParseException.fieldIsRequired(featureName);
                } else {
                    value = option.defaultValue();
                }
            }

            if (!option.validator().validate(value)) {
                throw KArgumentParseException.validationFailed(featureName);
            }
            if (features.containsKey(featureName)) {
                throw KArgumentParseException.argumentAlreadyParsed(featureName);
            }
            features.put(featureName, value);

        }

        return new KStandardApplicationFeatures(features);
    }

}
