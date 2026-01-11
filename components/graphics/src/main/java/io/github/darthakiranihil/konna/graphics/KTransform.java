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
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Representation of position, rotation and scaling of an object.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("UnusedReturnValue")
public final class KTransform {

    private static @Nullable KTransformMatrixCalculator transformMatrixCalculator;

    public static void setTransformMatrixCalculator(final KTransformMatrixCalculator calculator) {
        KTransform.transformMatrixCalculator = calculator;
    }

    private double rotation;
    private KVector2i translation;
    private KVector2d scaling;

    private @Nullable KTransform parent;
    private final List<KTransform> children;

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

        this.children = new LinkedList<>();

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

    public KVector2i getCenter() {
        return this.center;
    }

    /**
     * Rotates the object.
     * @param theta Rotation angle
     * @return This object (for method chaining)
     */
    public KTransform rotate(double theta) {
        this.rotation += theta;
        this.invalidateCache();
        return this;
    }

    /**
     * Scales the object with multiplying its current scaling on passed factor.
     * @param factor Scale factor
     * @return This object (for method chaining)
     */
    public KTransform scale(final KVector2d factor) {
        this.scaling = new KVector2d(this.scaling.x() * factor.x(), this.scaling.y() * factor.y());
        this.invalidateCache();
        return this;
    }

    /**
     * Translates the object.
     * @param value Translation vector
     * @return This object (for method chaining)
     */
    public KTransform translate(final KVector2i value) {
        this.translation = this.translation.add(value);
        this.invalidateCache();
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

    /**
     * Returns scaling of the object.
     * @return Scaling of this object
     */
    public KVector2d getScaling() {
        return this.scaling;
    }

    /**
     * Sets scaling for the object.
     * @param scale New object scaling.
     * @return This object (for method chaining)
     */
    public KTransform setScaling(final KVector2d scale) {
        this.scaling = scale;
        this.invalidateCache();
        return this;
    }

    public @Nullable KTransform getParent() {
        return this.parent;
    }

    public void setParent(KTransform parentTransform) {
        parentTransform.addChild(this);
        this.parent = parentTransform;
    }

    public List<KTransform> getChildren() {
        return this.children;
    }

    public void addChild(final KTransform child) {
        this.children.add(child);
        child.invalidateCache();
        this.invalidateCache();
    }

    public void removeChild(final KTransform child) {
        this.children.remove(child);

        child.parent = null;
        child.invalidateCache();

        this.invalidateCache();
    }

    private void invalidateCache() {
        this.cached = false;
        this.children.forEach(KTransform::invalidateCache);
    }

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
