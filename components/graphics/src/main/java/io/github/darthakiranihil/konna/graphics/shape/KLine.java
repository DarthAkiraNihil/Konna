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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;

import java.util.Objects;

/**
 * Representation of a line.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KLine extends KAbstractShape {

    private final KVector2i start;
    private final KVector2i end;
    private KColor color;

    /**
     * Standard constructor.
     * @param start Start coordinates of the line
     * @param end End coordinates of the line
     * @param color Line's color
     */
    public KLine(final KVector2i start, final KVector2i end, final KColor color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    /**
     * Creates a line with transparent color.
     * @param start Start coordinates of the line
     * @param end End coordinates of the line
     */
    public KLine(final KVector2i start, final KVector2i end) {
        this(start, end, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but start and end coordinates are defined
     * by separated int parameters.
     * @param x1 X coordinate of start of the line
     * @param x2 X coordinate of end of the line
     * @param y1 Y coordinate of start of the line
     * @param y2 Y coordinate of end of the line
     * @param color Line's color
     */
    public KLine(int x1, int x2, int y1, int y2, final KColor color) {
        this(new KVector2i(x1, y1), new KVector2i(x2, y2), color);
    }

    /**
     * Standard constructor, but start and end coordinates are defined
     * by separated int parameters. Also creates a line with transparent color.
     * @param x1 X coordinate of start of the line
     * @param x2 X coordinate of end of the line
     * @param y1 Y coordinate of start of the line
     * @param y2 Y coordinate of end of the line
     */
    public KLine(int x1, int x2, int y1, int y2) {
        this(new KVector2i(x1, y1), new KVector2i(x2, y2), KColor.TRANSPARENT);
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    public KColor getColor() {
        return color;
    }

    public KVector2i start() {
        return this.start;
    }

    public KVector2i end() {
        return this.end;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KLine kLine = (KLine) o;
        return
            Objects.equals(this.start, kLine.start)
                &&  Objects.equals(this.end, kLine.end)
                &&  Objects.equals(this.color, kLine.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end, this.color);
    }

}
