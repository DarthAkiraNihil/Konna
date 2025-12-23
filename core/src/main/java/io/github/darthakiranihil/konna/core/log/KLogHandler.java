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
 * Interface for a log handler that writes result log line to
 * a source, provided by its implementation.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KLogHandler {

    /**
     * Handles log in its own way.
     * @param logLevel Log message level
     * @param tag Log message tag
     * @param message Log message itself
     * @param args Format arguments
     */
    void handleLog(KLogLevel logLevel, String tag, String message, Object... args);

    /**
     * Return state of custom {@link KLogFormatter} containment inside implementation
     * of the handler.
     * @return {@code true} if handler contains inside it custom {@link KLogFormatter}
     */
    boolean hasFormatter();

}
