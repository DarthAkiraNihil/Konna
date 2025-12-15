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

/**
 * Interface for a log formatter that is designed to prepare log messages
 * to be handled by {@link KLogHandler} in specified format.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KLogFormatter {

    /**
     * Formats a log message.
     *
     * @param level Log message level
     * @param message Base message
     * @param args Format arguments
     * @return Formatted log message
     */
    String format(KLogLevel level, String message, Object... args);

}
