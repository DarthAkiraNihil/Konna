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
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;

import java.util.Objects;

public class KArc extends KAbstractShape {

    private final KVector2i center;
    private final KSize size;
    private final int startAngle;
    private final int arcAngle;
    private KColor outlineColor;
    private KColor fillColor;

    public KArc(final KVector2i center, final KSize size, int startAngle, int arcAngle, final KColor outlineColor, final KColor fillColor) {
        this.center = center;
        this.size = size;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    public KArc(final KVector2i coordinates, final KSize size, int startAngle, int arcAngle) {
        this(coordinates, size, startAngle, arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KArc(final KVector2i coordinates, final KSize size, int startAngle, int arcAngle, final KColor outlineColor) {
        this(coordinates, size, startAngle, arcAngle, outlineColor, KColor.TRANSPARENT);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this(new KVector2i(x, y), new KSize(width, height), startAngle, arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle, final KColor outlineColor) {
        this(new KVector2i(x, y), new KSize(width, height), startAngle, arcAngle, outlineColor, KColor.TRANSPARENT);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle, final KColor outlineColor, final KColor fillColor) {
        this(new KVector2i(x, y), new KSize(width, height), startAngle, arcAngle, outlineColor, fillColor);
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    public KVector2i center() {
        return this.center;
    }

    public KSize size() {
        return this.size;
    }

    public int startAngle() {
        return this.startAngle;
    }

    public int arcAngle() {
        return this.arcAngle;
    }

    public KColor getOutlineColor() {
        return outlineColor;
    }

    public KColor getFillColor() {
        return fillColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KArc kArc = (KArc) o;
        return
            this.startAngle == kArc.startAngle
                &&  this.arcAngle == kArc.arcAngle
                &&  Objects.equals(this.center, kArc.center)
                &&  Objects.equals(this.size, kArc.size)
                &&  Objects.equals(this.outlineColor, kArc.outlineColor)
                &&  Objects.equals(this.fillColor, kArc.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.center,
            this.size,
            this.startAngle,
            this.arcAngle,
            this.outlineColor,
            this.fillColor
        );
    }

}
