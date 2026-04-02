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

/**
 * Noise layer tool interface, providing operations for manipulating noise values
 * located on this layer.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KNoiseLayerTool extends KLayerTool {

    /**
     * @param x X coordinate of set noise value
     * @param y Y coordinate of set noise value
     * @return Noise value contained by specified place
     */
    float getNoiseValue(int x, int y);

    /**
     * Sets a noise value on specific place.
     * @param x X coordinate to set noise value on
     * @param y Y coordinate to set noise value on
     * @param value Noise value to set
     */
    void setNoiseValue(int x, int y, float value);

}
