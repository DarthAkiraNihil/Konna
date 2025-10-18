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

package io.github.darthakiranihil.konna.core.log.std;

import io.github.darthakiranihil.konna.core.log.KLogFormatter;
import io.github.darthakiranihil.konna.core.log.KLogLevel;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link KLogFormatter} that formats given message
 * and adds timestamp to it.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KTimestampLogFormatter extends KObject implements KLogFormatter {

    public KTimestampLogFormatter() {
        super(KTimestampLogFormatter.class.getSimpleName(), new HashSet<>(List.of(KTag.DefaultTags.STD)));
    }

    @Override
    public String format(final KLogLevel level, final String message, final Object... args) {
        return String.format(
            "[%s] [%s]: %s\n",
            Instant.now(),
            level,
            String.format(message, args)
        );
    }

}
