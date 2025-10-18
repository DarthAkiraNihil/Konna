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

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;

import java.util.HashSet;
import java.util.List;

/**
 * Base for logger class.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract class KLogger extends KObject {

    /**
     * Minimum level of logged messages.
     */
    protected final KLogLevel logLevel;
    /**
     * Default log formatter. It is used when a log handler does not provide its own.
     */
    protected final KLogFormatter defaultLogFormatter;
    /**
     * List of all registered log handlers.
     */
    protected final List<KLogHandler> logHandlers;

    public KLogger(
        final String name,
        final KLogLevel logLevel,
        final KLogFormatter defaultLogFormatter,
        final List<KLogHandler> logHandlers
    ) {
        super(name, new HashSet<>(List.of(KTag.DefaultTags.SYSTEM)));
        this.logLevel = logLevel;
        this.defaultLogFormatter = defaultLogFormatter;
        this.logHandlers = logHandlers;
    }

    /**
     * Adds a new log handler to process.
     * @param handler New log handler.
     */
    public void addLogHandler(final KLogHandler handler) {
        this.logHandlers.add(handler);
    }

    /**
     * Logs a message with {@link KLogLevel}.FATAL level.
     * @param message Message template
     * @param args Format args
     */
    public abstract void fatal(String message, Object... args);

    /**
     * Logs a throwable with {@link KLogLevel}.FATAL level.
     * @param throwable Throwable to log
     */
    public abstract void fatal(Throwable throwable);

    /**
     * Logs a message with {@link KLogLevel}.ERROR level.
     * @param message Message template
     * @param args Format args
     */
    public abstract void error(String message, Object... args);

    /**
     * Logs a throwable with {@link KLogLevel}.ERROR level.
     * @param throwable Throwable to log
     */
    public abstract void error(Throwable throwable);

    /**
     * Logs a message with {@link KLogLevel}.WARNING level.
     * @param message Message template
     * @param args Format args
     */
    public abstract void warning(String message, Object... args);

    /**
     * Logs a throwable with {@link KLogLevel}.WARNING level.
     * @param throwable Throwable to log
     */
    public abstract void warning(Throwable throwable);

    /**
     * Logs a message with {@link KLogLevel}.INFO level.
     * @param message Message template
     * @param args Format args
     */
    public abstract void info(String message, Object... args);

    /**
     * Logs a message with {@link KLogLevel}.DEBUG level.
     * @param message Message template
     * @param args Format args
     */
    public abstract void debug(String message, Object... args);

    /**
     * Logs a message with {@link KLogLevel}.TRACE level.
     * @param message Message template
     * @param args Format args
     */
    public abstract void trace(String message, Object... args);

}
