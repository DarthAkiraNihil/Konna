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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public class KTransform implements KTransformable {

    private double rotation;
    private KVector2i translation;
    private KVector2d scaling;

    private final float[][] matrix;
    private boolean cached;

    public KTransform(
        double rotation,
        final KVector2i translation,
        final KVector2d scaling
    ) {
        this.rotation = rotation;
        this.translation = translation;
        this.scaling = scaling;
        this.matrix = new float[3][3];
        this.cached = false;
    }

    public KTransform() {
        this(0.0, KVector2i.ZERO, KVector2d.ONE);
    }

    @Override
    public void rotate(double theta) {
        this.rotation += theta;
        this.cached = false;
    }

    @Override
    public void rotate(double theta, KVector2i pivot) {
        this.rotation += theta;
        this.translation = this.translation.add(pivot);
        this.cached = false;
    }

    @Override
    public void scale(KVector2d factor) {
        this.scaling = new KVector2d(this.scaling.x() * factor.x(), this.scaling.y() * factor.y());
        this.cached = false;
    }

    @Override
    public void translate(KVector2i value) {
        this.translation = this.translation.add(value);
        this.cached = false;
    }

    public double getRotation() {
        return this.rotation;
    }

    public KVector2i getTranslation() {
        return this.translation;
    }

    @Override
    public KVector2d getScaling() {
        return this.scaling;
    }

    @Override
    public void setScaling(KVector2d scale) {
        this.scaling = scale;
    }

    public float[][] matrix() {

        if (cached) {
            return this.matrix;
        }

        this.matrix[0][0] = (float) (Math.cos(this.rotation) * this.scaling.x());
        this.matrix[1][0] = (float) Math.sin(this.rotation);
        // this.matrix[2][0] = 0.0f;
        this.matrix[0][1] = (float) -Math.sin(this.rotation);
        this.matrix[1][1] = (float) (Math.cos(this.rotation) * this.scaling.y());
        // this.matrix[2][1] = 0.0f;
        this.matrix[0][2] = (float) this.translation.x();
        this.matrix[1][2] = (float) this.translation.y();
        this.matrix[2][2] = 1.0f;

        this.cached = true;
        return this.matrix;

    }

}
