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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.time.Instant;
import java.util.Arrays;

/**
 * Implementation of {@link KLogFormatter} that formats given message
 * like it was written by LogCat from Android. It does not look absolutely the
 * same as the real LogCat message but it is similar
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KLogcatLikeLogFormatter extends KObject implements KLogFormatter {

    private final boolean doNotColorize;

    /**
     * Standard constructor.
     * @param doNotColorize If {@code true} the message will not contain any colors,
     *                      else message will be colorized
     */
    public KLogcatLikeLogFormatter(boolean doNotColorize) {
        super(
            KColorfulTerminalLogFormatter.class.getSimpleName(),
            KStructUtils.setOfTags(KTag.DefaultTags.STD)
        );
        this.doNotColorize = doNotColorize;
    }

    private static String levelToStatusBackgroundColor(final KLogLevel level) {
        return switch (level) {
            case TRACE -> "\033[48;2;207;208;211m";
            case DEBUG -> "\033[48;2;57;98;126m";
            case INFO -> "\033[48;2;104;131;92m";
            case WARNING -> "\033[48;2;183;177;55m";
            case ERROR, FATAL -> "\033[48;2;202;98;97m";
        };
    }

    private static String levelStatusToForegroundColor(final KLogLevel level) {
        return switch (level) {
            case DEBUG -> "\033[37m";
            case INFO -> "\033[38;2;233;245;230m";
            case TRACE, WARNING, ERROR, FATAL -> "\033[30m";
        };
    }

    private static String levelToMessageBackgroundColor(final KLogLevel level) {
        return switch (level) {
            case TRACE -> "\033[38;2;207;208;211m";
            case DEBUG -> "\033[38;2;57;98;126m";
            case INFO -> "\033[38;2;104;131;92m";
            case WARNING -> "\033[38;2;183;177;55m";
            case ERROR, FATAL -> "\033[38;2;202;98;97m";
        };
    }

    private static char levelToChar(final KLogLevel level) {
        return switch (level) {
            case TRACE -> 'T';
            case DEBUG -> 'D';
            case INFO -> 'I';
            case WARNING -> 'W';
            case ERROR -> 'E';
            case FATAL -> 'F';
        };
    }

    private String getCallerPackage() {
        var stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length == 0) {
            return "unknown";
        }

        try {
            int stackTraceIdx = 2;
            Class<?> callerClass = Class.forName(stackTrace[stackTraceIdx++].getClassName());
            var callerClassInterfaces = Arrays.asList(callerClass.getInterfaces());
            while (
                    (
                            callerClass.getSuperclass() == KLogger.class
                        ||  callerClassInterfaces.contains(KLogHandler.class)
                        ||  callerClassInterfaces.contains(KLogFormatter.class)
                    )
                && stackTraceIdx < stackTrace.length
            ) {
                callerClass = Class.forName(stackTrace[stackTraceIdx++].getClassName());
                callerClassInterfaces = Arrays.asList(callerClass.getInterfaces());
            }

            return callerClass.getPackageName();
        } catch (Throwable e) {
            return "unknown";
        }
    }

    @Override
    public String format(final KLogLevel level, final String message, final Object... args) {
        if (this.doNotColorize) {
            return String.format(
                "[%s]\t%s\t%s %s",
                Instant.now(),
                this.getCallerPackage(),
                KLogcatLikeLogFormatter.levelToChar(level),
                String.format(message, args)
            );
        }

        return String.format(
            "[%s]\t%s\t%s%s %s \033[0m %s%s\033[0m",
            Instant.now(),
            this.getCallerPackage(),
            KLogcatLikeLogFormatter.levelToStatusBackgroundColor(level),
            KLogcatLikeLogFormatter.levelStatusToForegroundColor(level),
            KLogcatLikeLogFormatter.levelToChar(level),
            KLogcatLikeLogFormatter.levelToMessageBackgroundColor(level),
            String.format(message, args)
        );
    }

}
