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

package io.github.darthakiranihil.konna.core.io.control;

import org.jspecify.annotations.Nullable;

/**
 * Interface for processing raw input events, that actually depends on implementation.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public interface KInputProcessor extends KKeyListener {

    /**
     * Adds a control scheme to this processor.
     * @param scheme Input scheme to be added
     */
    void addControlScheme(KInputControlScheme scheme);

    /**
     * Returns a control scheme with passed name.
     * @param name Name of the scheme
     * @return The actual scheme or {@code null} if it's not found
     */
    @Nullable KInputControlScheme getControlScheme(String name);

    /**
     * Disables this processor. Now it won't process input events.
     */
    void disable();

    /**
     * Enables this processor. Now it will process input events.
     */
    void enable();

}
