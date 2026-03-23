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

package io.github.darthakiranihil.konna.level.layer.tool;

import io.github.darthakiranihil.konna.core.struct.KSize;

/**
 * Interface for layer tools that provide information about size of assigned layer.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KSizedLayerTool extends KLayerTool {

    /**
     * @return Size of assigned layer
     */
    KSize getSize();

}
