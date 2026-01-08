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

package io.github.darthakiranihil.konna.graphics.image;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

/**
 * Enumeration representing different modes of texture filtering.
 * Their applying depends on
 * {@link io.github.darthakiranihil.konna.graphics.render.KRenderFrontend}
 * implementation.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public enum KTextureFiltering {

    /**
     * Fetch four nearest texels that best maps to the pixel on screen.
     */
    NEAREST,
    /**
     * Linearly interpolate color of pixel using colors of neighbors.
     */
    LINEAR,
    /**
     * Use mipmap. Usually should be used only for min filtering.
     */
    MIPMAP,
    /**
     * Fetch the best fitting image from the mip map chain based on the pixel/texel ratio
     * and then sample the texels with a nearest filter.
     */
    MIPMAP_NEAREST_NEAREST,
    /**
     * Fetch the best fitting image from the mip map chain based on the pixel/texel ratio
     * and then sample the texels with a linear filter.
     */
    MIPMAP_LINEAR_NEAREST,
    /**
     * Fetch the two best fitting images from the mip map chain and then sample the nearest
     * texel from each of the two images, combining them to the final output pixel.
     */
    MIPMAP_NEAREST_LINEAR,
    /**
     * Fetch the two best fitting images from the mip map chain and then sample
     * the four nearest texels from each of the two
     * images, combining them to the final output pixel.
     */
    MIPMAP_LINEAR_LINEAR;

    /**
     * Returns whether this texture filtering mode is mipmap or not.
     * @return Whether this texture filtering mode is mipmap or not
     */
    public boolean isMipmap() {
        return this != NEAREST && this != LINEAR;
    }

    /**
     * Json validator for this type.
     */
    public static final KJsonValidator VALIDATOR = (v) -> {
        String s = v.getString();

        try {
            KTextureFiltering.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new KJsonValidationError(e.getMessage());
        }

    };
}
