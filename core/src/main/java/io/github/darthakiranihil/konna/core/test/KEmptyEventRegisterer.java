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

import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import org.jetbrains.annotations.TestOnly;

/**
 * Implementation of {@link KEventRegisterer} to be used only in tests.
 * Does nothing.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@TestOnly
public final class KEmptyEventRegisterer implements KEventRegisterer {

    @Override
    public void registerEvents(final KEventSystem eventSystem) {

    }

}
