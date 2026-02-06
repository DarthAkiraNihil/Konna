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

package io.github.darthakiranihil.konna.core.test;

import io.github.darthakiranihil.konna.core.io.control.KInputControlScheme;
import io.github.darthakiranihil.konna.core.io.control.KInputProcessor;
import io.github.darthakiranihil.konna.core.io.control.KKeyInputData;
import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.Nullable;

/**
 * Implementation of {@link KInputProcessor} for test purposes.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@TestOnly
public final class KTestInputProcessor implements KInputProcessor {

    @Override
    public void disable() {

    }

    @Override
    public void enable() {

    }

    @Override
    public void keyPressed(final KKeyInputData data) {

    }

    @Override
    public void keyReleased(final KKeyInputData data) {

    }

    @Override
    public void addControlScheme(final KInputControlScheme scheme) {

    }

    @Override
    public @Nullable KInputControlScheme getControlScheme(final String name) {
        return null;
    }
}
