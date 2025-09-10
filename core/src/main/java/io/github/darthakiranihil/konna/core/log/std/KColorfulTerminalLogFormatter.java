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

import java.time.Instant;

/**
 * Implementation of {@link KLogFormatter} that formats given message
 * and adds timestamp to it and colors of the message log level.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KColorfulTerminalLogFormatter extends KObject implements KLogFormatter {

    public KColorfulTerminalLogFormatter() {
        super(KColorfulTerminalLogFormatter.class.getSimpleName());
    }

    private static String levelToColor(final KLogLevel level) {
        return switch (level) {
            case TRACE -> "\033[37m";
            case DEBUG -> "\033[35m";
            case INFO -> "\033[36m";
            case WARNING -> "\033[33m";
            case ERROR, FATAL -> "\033[31m";
        };
    }

    @Override
    public String format(final KLogLevel level, final String message, final Object... args) {
        return String.format(
            "[%s] [%s%s\033[0m]: %s",
            Instant.now(),
            KColorfulTerminalLogFormatter.levelToColor(level),
            level,
            String.format(message, args)
        );
    }
}
