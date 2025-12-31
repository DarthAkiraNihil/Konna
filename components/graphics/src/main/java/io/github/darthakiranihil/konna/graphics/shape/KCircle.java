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

package io.github.darthakiranihil.konna.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;

/**
 * Representation of a circle that is oval with squared size.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KCircle extends KOval {

    /**
     * Standard constructor.
     * @param coordinates Coordinates of circle's center
     * @param r Circle's radius
     * @param outlineColor Circle's outline color
     * @param fillColor Circle's fill color
     */
    public KCircle(final KVector2i coordinates, int r, final KColor outlineColor, final KColor fillColor) {
        super(coordinates, KSize.squared(2 * r), outlineColor, fillColor);
    }

    /**
     * Creates a circle with transparent fill color.
     * @param coordinates Coordinates of circle's center
     * @param r Circle's radius
     * @param outlineColor Circle's outline color
     */
    public KCircle(final KVector2i coordinates, int r, final KColor outlineColor) {
        super(coordinates, KSize.squared(2 * r), outlineColor);
    }

    /**
     * Creates a circle with transparent outline and fill color.
     * @param coordinates Coordinates of circle's center
     * @param r Circle's radius
     */
    public KCircle(final KVector2i coordinates, int r) {
        super(coordinates, KSize.squared(2 * r));
    }

    /**
     * Standard constructor, but circle's center coordinates
     * are defined by separated int parameters. Also creates a
     * circle with transparent outline and fill color.
     * @param x X coordinate of circle's center
     * @param y Y coordinate of circle's center
     * @param r Circle's radius
     */
    public KCircle(int x, int y, int r) {
        super(x, y, 2 * r, 2 * r);
    }

    /**
     * Standard constructor, but circle's center coordinates
     * are defined by separated int parameters. Also creates a circle
     * with transparent fill color.
     * @param x X coordinate of circle's center
     * @param y Y coordinate of circle's center
     * @param r Circle's radius
     * @param outlineColor Circle's outline color
     */
    public KCircle(int x, int y, int r, final KColor outlineColor) {
        super(x, y, 2 * r, 2 * r, outlineColor);
    }

    /**
     * Standard constructor, but circle's center coordinates
     * are defined by separated int parameters.
     * @param x X coordinate of circle's center
     * @param y Y coordinate of circle's center
     * @param r Circle's radius
     * @param outlineColor Circle's outline color
     * @param fillColor Circle's fill color
     */
    public KCircle(int x, int y, int r, final KColor outlineColor, final KColor fillColor) {
        super(x, y, 2 * r, 2 * r, outlineColor, fillColor);
    }

}
