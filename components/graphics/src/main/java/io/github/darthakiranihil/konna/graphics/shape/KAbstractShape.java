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

import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.KTransform;
import io.github.darthakiranihil.konna.graphics.KTransformable;

/**
 * Abstract base for all standard shapes.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
// since transformable methods are wrappers to KTransform methods
@KExcludeFromGeneratedCoverageReport
public abstract class KAbstractShape implements KShape {

    private final KTransform transform;

    /**
     * Creates shape with initial transform.
     */
    public KAbstractShape() {
        this.transform = new KTransform();
    }

    @Override
    public KTransform getTransform() {
        return this.transform;
    }

    @Override
    public KTransformable rotate(double theta) {
        this.transform.rotate(theta);
        return this;
    }

    @Override
    public KTransformable rotate(double theta, final KVector2i pivot) {
        this.transform.rotate(theta, pivot);
        return this;
    }

    @Override
    public KTransformable scale(final KVector2d factor) {
        this.transform.scale(factor);
        return this;
    }

    @Override
    public KTransformable translate(final KVector2i value) {
        this.transform.translate(value);
        return this;
    }

    @Override
    public KVector2d getScaling() {
        return this.transform.getScaling();
    }

    @Override
    public KTransformable setScaling(final KVector2d scale) {
        this.transform.setScaling(scale);
        return this;
    }

}
