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

package io.github.darthakiranihil.konna.core.app;

/**
 * Interface for an application argument validator
 * that is used in argument parsing.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KArgumentValidator {

    /**
     * Default validator that checks if value is a string,
     * so it will always return {@code true}.
     */
    KArgumentValidator STRING = (String arg) -> true;
    /**
     * Default validator that checks if value is an integer.
     */
    KArgumentValidator INTEGER = (String arg) -> {

        try {
            int i = Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    };

    /**
     * Validates passed argument.
     * @param arg Validated argument
     * @return {@code true} if the argument is valid, else {@code false}
     */
    boolean validate(String arg);

}
