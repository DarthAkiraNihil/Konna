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
import io.github.darthakiranihil.konna.graphics.except.KInvalidGraphicsStateException;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

/**
 * Representation of position, rotation and scaling of an object.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public final class KTransform implements KTransformable {

    private static @Nullable KTransformMatrixCalculator transformMatrixCalculator;

    public static void setTransformMatrixCalculator(final KTransformMatrixCalculator calculator) {
        KTransform.transformMatrixCalculator = calculator;
    }

    private double rotation;
    private KVector2i translation;
    private KVector2d scaling;
    private @Nullable KTransformable parent;
    private final KVector2i center;

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
        this.rotation = rotation;
        this.scaling = scaling;
        this.translation = translation;
        this.center = center;

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

    public KVector2i getCenter() {
        return this.center;
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
        this.cached = false;
        return this;
    }

    @Override
    public @Nullable KTransformable getParent() {
        return this.parent;
    }

    @Override
    public void setParent(KTransformable parentTransform) {
        this.parent = parentTransform;
    }

    @Override
    public float[] getMatrix() {

        if (this.cached) {
            return this.matrix;
        }

        if (KTransform.transformMatrixCalculator == null) {
            throw new KInvalidGraphicsStateException(
                    "Cannot calculate transform matrix: matrix calculator is not set. "
                +   "Make sure it is defined in graphics component's config "
                +   "by transform_matrix_calculator key"
            );
        }

        KTransform.transformMatrixCalculator.calculateMatrix(this, this.matrix);
        this.cached = true;
        return this.matrix;

    }
}
