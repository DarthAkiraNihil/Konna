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
 * Representation of position, rotation and scaling of an object,
 * supporting parent transforms.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("UnusedReturnValue")
public final class KTransform {

    private static @Nullable KTransformMatrixCalculator transformMatrixCalculator;

    /**
     * Sets a global transform matrix calculator.
     * @param calculator New global matrix calculator
     */
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
     * @param center Center of this transform
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
     * Creates transform with zero rotation, zero translation,
     * 1.0 scaling for X and Y coordinates and zero as center coordinate.
     */
    public KTransform() {
        this(0.0, KVector2i.ZERO, KVector2d.ONE, KVector2i.ZERO);
    }

    /**
     * Creates transform with zero rotation, zero translation,
     * 1.0 scaling for X and Y coordinates and specific center coordinate.
     * Center should depend on the renderable object shape configuration.
     * @param center Center of this transform
     */
    public KTransform(final KVector2i center) {
        this(0.0, KVector2i.ZERO, KVector2d.ONE, center);
    }

    /**
     * Returns center of this transform.
     * @return Center of this transform
     */
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

    /**
     * Returns parent transform for this transform.
     * @return Parent transform, if presented, else {@code null}
     */
    public @Nullable KTransform getParent() {
        return this.parent;
    }

    /**
     * Sets a parent for this transform.
     * @param parentTransform New parent transform
     */
    public void setParent(final KTransform parentTransform) {
        parentTransform.addChild(this);
        this.parent = parentTransform;
    }

    /**
     * Returns list of children of this transform.
     * @return Children of this transform
     */
    public List<KTransform> getChildren() {
        return this.children;
    }

    /**
     * Adds a child to this transform.
     * @param child Child transform to add
     * @return This transform
     */
    public KTransform addChild(final KTransform child) {
        this.children.add(child);
        child.parent = this;

        child.invalidateCache();
        this.invalidateCache();
        return this;
    }

    /**
     * Removes a child from this transform.
     * @param child Child transform to remove
     * @return This transform
     */
    public KTransform removeChild(final KTransform child) {
        this.children.remove(child);

        child.parent = null;
        child.invalidateCache();

        this.invalidateCache();
        return this;
    }

    private void invalidateCache() {
        this.cached = false;
        this.children.forEach(KTransform::invalidateCache);
    }

    /**
     * Gets plain matrix representation of this transform.
     * It relies on {@link KTransform#transformMatrixCalculator} field, that should be
     * set by {@link KGraphicsComponent} on applying its config. If it is not, then
     * {@link KInvalidGraphicsStateException} will be thrown that is fatal.
     * If transform parameters or parent transform is not changed,
     * then cached matrix will be used. If a transform is added to this as a child,
     * then added matrix cache will be invalidated.
     * Implementation of {@link KTransformMatrixCalculator} should reflect used render frontend
     * as it will prevent errors caused by different coordinate systems used by the final transform
     * and the render window.
     *
     * @return Plain matrix representation of this transform.
     */
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
