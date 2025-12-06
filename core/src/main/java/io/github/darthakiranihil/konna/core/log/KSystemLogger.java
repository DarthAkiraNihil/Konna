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

import io.github.darthakiranihil.konna.core.log.std.*;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

import java.util.LinkedList;
import java.util.List;

/**
 * Convenience class that is basically a static proxy for {@link KStandardLogger}.
 * Provides opportunity to log from everywhere. The instance of proxied logger cannot
 * be changed, so you only can add new log handlers to it. If it is disabled, no log
 * message will be actually logged.
 *
 * @since 0.2.0
 * @author Darth Akria Nihil
 */
public final class KSystemLogger extends KUninstantiable {

    private static final KLogger PROXIED_LOGGER = new KStandardLogger(
        "system_logger",
        KLogLevel.TRACE,
        new KTimestampLogFormatter(),
        new LinkedList<>(
            List.of(
                new KTerminalLogHandler(new KLogcatLikeLogFormatter(false)),
                new KFileLogHandler(
                    "konna.system.log",
                    new KLogcatLikeLogFormatter(true)
                )
            )
        )
    );

    private static boolean enabled = true;

    /**
     * Enabled the system logger.
     */
    private static void enable() {
        KSystemLogger.enabled = true;
    }

    /**
     * Disables the system logger.
     */
    private static void disable() {
        KSystemLogger.enabled = false;
    }

    /**
     * Sets a minimum log level of message to be logged.
     * @param logLevel New minimum log level
     */
    public static void setLogLevel(final KLogLevel logLevel) {
        PROXIED_LOGGER.setLogLevel(logLevel);
    }

    /**
     * Adds a new log handler to process.
     * @param handler New log handler.
     */
    public static void addLogHandler(final KLogHandler handler) {
        PROXIED_LOGGER.addLogHandler(handler);
    }

    /**
     * Logs a message with {@link KLogLevel}.FATAL level.
     * @param message Message template
     * @param args Format args
     */
    public static void fatal(final String message, final Object... args) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.fatal(message, args);
    }

    /**
     * Logs a throwable with {@link KLogLevel}.FATAL level.
     * @param throwable Throwable to log
     */
    public static void fatal(final Throwable throwable) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.fatal(throwable);
    }

    /**
     * Logs a message with {@link KLogLevel}.ERROR level.
     * @param message Message template
     * @param args Format args
     */
    public static void error(final String message, final Object... args) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.error(message, args);
    }

    /**
     * Logs a throwable with {@link KLogLevel}.ERROR level.
     * @param throwable Throwable to log
     */
    public static void error(final Throwable throwable) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.error(throwable);
    }

    /**
     * Logs a message with {@link KLogLevel}.WARNING level.
     * @param message Message template
     * @param args Format args
     */
    public static void warning(final String message, final Object... args) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.warning(message, args);
    }

    /**
     * Logs a throwable with {@link KLogLevel}.WARNING level.
     * @param throwable Throwable to log
     */
    public static void warning(final Throwable throwable) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.warning(throwable);
    }

    /**
     * Logs a message with {@link KLogLevel}.INFO level.
     * @param message Message template
     * @param args Format args
     */
    public static void info(final String message, final Object... args) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.info(message, args);
    }

    /**
     * Logs a message with {@link KLogLevel}.DEBUG level.
     * @param message Message template
     * @param args Format args
     */
    public static void debug(final String message, final Object... args) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.debug(message, args);
    }

    /**
     * Logs a message with {@link KLogLevel}.TRACE level.
     * @param message Message template
     * @param args Format args
     */
    public static void trace(final String message, final Object... args) {
        if (!KSystemLogger.enabled) {
            return;
        }
        PROXIED_LOGGER.trace(message, args);
    }

}
