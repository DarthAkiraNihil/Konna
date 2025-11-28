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

package io.github.darthakiranihil.konna.core.log;

import io.github.darthakiranihil.konna.core.log.std.KLogcatLikeLogFormatter;
import io.github.darthakiranihil.konna.core.log.std.KStandardLogger;
import io.github.darthakiranihil.konna.core.log.std.KTerminalLogHandler;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

import java.util.List;

// TODO
public final class KMainLogger extends KUninstantiable {

    private static final KLogger proxied = new KStandardLogger(
        "1ab",
        KLogLevel.TRACE,
        new KLogcatLikeLogFormatter(false), List.of(new KTerminalLogHandler(new KLogcatLikeLogFormatter(false)))
    );

    /**
     * Logs a message with {@link KLogLevel}.FATAL level.
     * @param message Message template
     * @param args Format args
     */
    public static void fatal(String message, Object... args) {

    }

    /**
     * Logs a throwable with {@link KLogLevel}.FATAL level.
     * @param throwable Throwable to log
     */
    public static void fatal(Throwable throwable) {

    }

    /**
     * Logs a message with {@link KLogLevel}.ERROR level.
     * @param message Message template
     * @param args Format args
     */
    public static void error(String message, Object... args) {

    }

    /**
     * Logs a throwable with {@link KLogLevel}.ERROR level.
     * @param throwable Throwable to log
     */
    public static void error(Throwable throwable) {

    }

    /**
     * Logs a message with {@link KLogLevel}.WARNING level.
     * @param message Message template
     * @param args Format args
     */
    public static void warning(String message, Object... args) {

    }

    /**
     * Logs a throwable with {@link KLogLevel}.WARNING level.
     * @param throwable Throwable to log
     */
    public static void warning(Throwable throwable) {

    }

    /**
     * Logs a message with {@link KLogLevel}.INFO level.
     * @param message Message template
     * @param args Format args
     */
    public static void info(String message, Object... args) {

    }

    /**
     * Logs a message with {@link KLogLevel}.DEBUG level.
     * @param message Message template
     * @param args Format args
     */
    public static void debug(String message, Object... args) {

    }

    /**
     * Logs a message with {@link KLogLevel}.TRACE level.
     * @param message Message template
     * @param args Format args
     */
    public static void trace(String message, Object... args) {

    }

}
