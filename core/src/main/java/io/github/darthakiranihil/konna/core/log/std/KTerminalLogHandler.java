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
import io.github.darthakiranihil.konna.core.log.KLogHandler;
import io.github.darthakiranihil.konna.core.log.KLogLevel;

/**
 * Implementation of {@link KLogHandler} that writes log
 * messages to stdout (just like terminal). Requires formatter
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KTerminalLogHandler implements KLogHandler {

    private final KLogFormatter logFormatter;

    /**
     * Constructs handler with provided formatter.
     * @param logFormatter Log formatter
     */
    public KTerminalLogHandler(final KLogFormatter logFormatter) {
        this.logFormatter = logFormatter;
    }

    @Override
    public void handleLog(final KLogLevel logLevel, final String message, final Object... args) {

        System.out.println(this.logFormatter.format(logLevel, message, args));

    }

    @Override
    public boolean hasFormatter() {
        return true;
    }
}
