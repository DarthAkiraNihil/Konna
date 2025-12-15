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

import org.jspecify.annotations.Nullable;

/**
 * Interface for a simple container that contain
 * different values that may influence application functionality.
 * The container is read-only.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KApplicationFeatures {

    /**
     * Gets value for specific feature.
     * @param key Feature key
     * @return Value of this feature.
     */
    @Nullable String getFeature(String key);

}
