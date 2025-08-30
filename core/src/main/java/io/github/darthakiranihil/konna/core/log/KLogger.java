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

import io.github.darthakiranihil.konna.core.log.except.KLoggingException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard Logger class.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KLogger {

    private KLogger() {

    }

    private static boolean isInitialized;

    private static KLogLevel logLevel;
    private static KLogFormatter defaultLogFormatter;
    private static List<KLogHandler> logHandlers;

    /**
     * Initialize the logger. Can be called only once, after that subsequent calls
     * will produce {@link KLoggingException}
     *
     * @param minLogLevel Minimal log level messages of that should be handled
     * @param defaultFormatter Default log formatter used, when {@link KLogHandler}
     *                         does not provide formatter inside its implementation
     */
    public static void init(final KLogLevel minLogLevel, final KLogFormatter defaultFormatter) {

        if (KLogger.isInitialized) {
            throw new KLoggingException("Cannot initialize logger: already initialized");
        }

        KLogger.logLevel = minLogLevel;
        KLogger.defaultLogFormatter = defaultFormatter;
        KLogger.logHandlers = new ArrayList<>();

        KLogger.isInitialized = true;

    }

    /**
     * Adds a new log handler to process.
     * @param handler New log handler.
     */
    public static void addLogHandler(final KLogHandler handler) {
        KLogger.logHandlers.add(handler);
    }

    private static void log(final KLogLevel level, final String message, final Object... args) {

        if (!KLogger.isInitialized) {
            throw new KLoggingException("Cannot log message: logger is not initialized");
        }

        if (level.ordinal() > KLogger.logLevel.ordinal()) {
            return;
        }

        for (var handler: KLogger.logHandlers) {
            if (handler.hasFormatter()) {
                handler.handleLog(level, message, args);
            } else {
                handler.handleLog(
                    level,
                    KLogger.defaultLogFormatter.format(message, args)
                );
            }
        }

    }

    private static String prepareThrowable(final Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Logs a message with {@link KLogLevel}.FATAL level.
     * @param message Message template
     * @param args Format args
     */
    public static void fatal(final String message, final Object... args) {
        KLogger.log(KLogLevel.FATAL, message, args);
    }

    /**
     * Logs a throwable with {@link KLogLevel}.FATAL level.
     * @param throwable Throwable to log
     */
    public static void fatal(final Throwable throwable) {
        KLogger.log(KLogLevel.FATAL, KLogger.prepareThrowable(throwable));
    }

    /**
     * Logs a message with {@link KLogLevel}.ERROR level.
     * @param message Message template
     * @param args Format args
     */
    public static void error(final String message, final Object... args) {
        KLogger.log(KLogLevel.ERROR, message, args);
    }

    /**
     * Logs a throwable with {@link KLogLevel}.ERROR level.
     * @param throwable Throwable to log
     */
    public static void error(final Throwable throwable) {
        KLogger.log(KLogLevel.ERROR, KLogger.prepareThrowable(throwable));
    }

    /**
     * Logs a message with {@link KLogLevel}.WARNING level.
     * @param message Message template
     * @param args Format args
     */
    public static void warning(final String message, final Object... args) {
        KLogger.log(KLogLevel.WARNING, message, args);
    }

    /**
     * Logs a throwable with {@link KLogLevel}.WARNING level.
     * @param throwable Throwable to log
     */
    public static void warning(final Throwable throwable) {
        KLogger.log(KLogLevel.WARNING, KLogger.prepareThrowable(throwable));
    }

    /**
     * Logs a message with {@link KLogLevel}.INFO level.
     * @param message Message template
     * @param args Format args
     */
    public static void info(final String message, final Object... args) {
        KLogger.log(KLogLevel.INFO, message, args);
    }

    /**
     * Logs a message with {@link KLogLevel}.DEBUG level.
     * @param message Message template
     * @param args Format args
     */
    public static void debug(final String message, final Object... args) {
        KLogger.log(KLogLevel.DEBUG, message, args);
    }

    /**
     * Logs a message with {@link KLogLevel}.TRACE level.
     * @param message Message template
     * @param args Format args
     */
    public static void trace(final String message, final Object... args) {
        KLogger.log(KLogLevel.TRACE, message, args);
    }

}
