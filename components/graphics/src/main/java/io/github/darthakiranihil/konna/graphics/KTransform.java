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

/**
 * Representation of position, rotation and scaling of an object.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public final class KTransform implements KTransformable {

    private double rotation;
    private KVector2i translation;
    private KVector2d scaling;
    private KVector2i center;

    private final float[] matrix;
    private boolean cached;

    /**
     * Standard constructor.
     * @param rotation Rotation
     * @param translation Translation
     * @param scaling Scaling
     */
    public KTransform(
        double rotation,
        final KVector2i translation,
        final KVector2d scaling,
        final KVector2i center
    ) {
        this.center = center;
        this.rotation = rotation;
        this.translation = translation;
        this.scaling = scaling;
        this.matrix = new float[16];
        this.cached = false;
    }

    /**
     * Creates transform with zero rotation, zero translation
     * and 1.0 scaling for X and Y coordinates.
     */
    public KTransform() {
        this(0.0, KVector2i.ZERO, KVector2d.ONE, KVector2i.ZERO);
    }

    public KTransform(final KVector2i center) {
        this(0.0, KVector2i.ZERO, KVector2d.ONE, center);
    }

    @Override
    public KTransformable rotate(double theta) {
        this.rotation += theta;
        this.cached = false;
        return this;
    }

    @Override
    public KTransformable rotate(double theta, final KVector2i pivot) {
        this.rotation += theta;
        this.translation = this.translation.add(pivot);
        this.cached = false;
        return this;
    }

    @Override
    public KTransformable scale(final KVector2d factor) {
        this.scaling = new KVector2d(this.scaling.x() * factor.x(), this.scaling.y() * factor.y());
        this.cached = false;
        return this;
    }

    @Override
    public KTransformable translate(final KVector2i value) {
        this.translation = this.translation.add(value);
        this.cached = false;
        return this;
    }

    /**
     * Returns rotation of this transform.
     * @return Rotation of this transform.
     */
    public double getRotation() {
        return this.rotation;
    }

    /**
     * Returns translation of this transform.
     * @return Translation of this transform
     */
    public KVector2i getTranslation() {
        return this.translation;
    }

    @Override
    public KVector2d getScaling() {
        return this.scaling;
    }

    @Override
    public KTransformable setScaling(final KVector2d scale) {
        this.scaling = scale;
        return this;
    }

    /**
     * Returns transformation matrix representation
     * of this transform.
     * @return Transformation matrix of this transform
     */
    public float[] matrix() {

        if (cached) {
            return this.matrix;
        }

        this.matrix[0] = (float) (Math.cos(this.rotation) * this.scaling.x()); // 0, 0
        this.matrix[4] = (float) Math.sin(this.rotation); // 1, 0
        // this.matrix[2][0] = 0.0f;
        this.matrix[1] = (float) -Math.sin(this.rotation); // 0, 1
        this.matrix[5] = (float) (Math.cos(this.rotation) * this.scaling.y()); // 1, 1
        // this.matrix[2][1] = 0.0f;
        this.matrix[3] = (float) this.translation.x(); // 0, 3
        this.matrix[7] = (float) this.translation.y(); // 1, 3
        this.matrix[10] = 1.0f; // 2, 2
        this.matrix[15] = 1.0f; // 3, 3

        this.cached = true;
        return this.matrix;

    }

}
