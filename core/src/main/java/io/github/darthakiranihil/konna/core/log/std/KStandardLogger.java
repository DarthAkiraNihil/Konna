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
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.KTag;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Standard Logger class.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KStandardLogger extends KLogger {

    private static final Object LOG_LOCK = new Object();

    /**
     * Standard constructor.
     * @param name Name of the logger object
     * @param logLevel Minimum level of message to be logged
     * @param defaultLogFormatter Default log formatter
     * @param logHandlers List of pre-registered log handlers
     */
    public KStandardLogger(
        final String name,
        final KLogLevel logLevel,
        final KLogFormatter defaultLogFormatter,
        final List<KLogHandler> logHandlers
    ) {
        super(name, logLevel, defaultLogFormatter, logHandlers);
        this.addTag(KTag.DefaultTags.STD);
    }

    @Override
    public void fatal(final String message, final Object... args) {
        this.log(KLogLevel.FATAL, message, args);
    }

    @Override
    public void fatal(final Throwable throwable) {
        this.log(KLogLevel.FATAL, this.prepareThrowable(throwable));
    }

    @Override
    public void error(final String message, final Object... args) {
        this.log(KLogLevel.ERROR, message, args);
    }

    @Override
    public void error(final Throwable throwable) {
        this.log(KLogLevel.ERROR, this.prepareThrowable(throwable));
    }

    @Override
    public void warning(final String message, final Object... args) {
        this.log(KLogLevel.WARNING, message, args);
    }

    @Override
    public void warning(final Throwable throwable) {
        this.log(KLogLevel.WARNING, this.prepareThrowable(throwable));
    }

    @Override
    public void info(final String message, final Object... args) {
        this.log(KLogLevel.INFO, message, args);
    }

    @Override
    public void debug(final String message, final Object... args) {
        this.log(KLogLevel.DEBUG, message, args);
    }

    @Override
    public void trace(final String message, final Object... args) {
        this.log(KLogLevel.TRACE, message, args);
    }

    private void log(final KLogLevel level, final String message, final Object... args) {

        if (level.ordinal() > this.logLevel.ordinal()) {
            return;
        }

        for (var handler: this.logHandlers) {
            synchronized (KStandardLogger.LOG_LOCK) {
                if (handler.hasFormatter()) {
                    handler.handleLog(level, message, args);
                } else {
                    handler.handleLog(
                        level, this.defaultLogFormatter.format(level, message, args)
                    );
                }
            }
        }

    }

    private String prepareThrowable(final Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

}
