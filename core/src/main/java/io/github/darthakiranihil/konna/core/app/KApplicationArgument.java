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

import org.jspecify.annotations.Nullable;

import java.util.List;

public record KApplicationArgument(
    String shortKey,
    String longKey,
    boolean kQualified,
    @Nullable String defaultValue,
    KArgumentValidator validator
) {

    public static final List<KApplicationArgument> DEFAULT_ARGS = List.of();

    public KApplicationArgument(
        final String shortKey,
        final String longKey,
        boolean kQualified
    ) {
        this(shortKey, longKey, kQualified, null, KArgumentValidator.STRING);
    }

    public KApplicationArgument(
        final String shortKey,
        final String longKey
    ) {
        this(shortKey, longKey, true);
    }

    public KApplicationArgument(
        final String shortKey,
        final String longKey,
        final String defaultValue
    ) {
        this(shortKey, longKey, true, defaultValue, KArgumentValidator.STRING);
    }

    public KApplicationArgument(
        final String shortKey,
        final String longKey,
        boolean kQualified,
        final KArgumentValidator validator
    ) {
        this(shortKey, longKey, kQualified, null, validator);
    }

    public KApplicationArgument(
        final String shortKey,
        final String longKey,
        final KArgumentValidator validator
    ) {
        this(shortKey, longKey, true, validator);
    }

    public KApplicationArgument(
        final String shortKey,
        final String longKey,
        final String defaultValue,
        final KArgumentValidator validator
    ) {
        this(shortKey, longKey, true, defaultValue, validator);
    }


}
