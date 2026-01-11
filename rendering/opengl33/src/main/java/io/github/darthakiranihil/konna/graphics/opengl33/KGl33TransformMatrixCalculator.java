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

package io.github.darthakiranihil.konna.graphics.opengl33;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.graphics.KTransform;
import io.github.darthakiranihil.konna.graphics.KTransformMatrixCalculator;
import org.joml.Matrix4f;

/**
 * Transform matrix calculator implementation using JOML and fixes
 * according to OpenGL NDC's.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KGl33TransformMatrixCalculator
    extends KObject
    implements KTransformMatrixCalculator {

    private KSize viewportSize;

    public KGl33TransformMatrixCalculator() {
        this.viewportSize = KSize.squared(KGl33RenderFrontend.DEFAULT_VIEWPORT_SIZE_SIDE);
    }

    /**
     * Sets viewport size in which to calculate the matrix.
     * @param size Size of the viewport
     */
    public void setViewportSize(final KSize size) {
        this.viewportSize = size;
    }

    @Override
    public void calculateMatrix(final KTransform transform, final float[] dst) {
        KVector2f center = KGeometryUtils.plainToGl(transform.getCenter(), this.viewportSize);

        Matrix4f matrix = new Matrix4f();
        KTransform parent = transform.getParent();
        if (parent != null) {
            matrix.set(parent.getMatrix());
        } else {
            matrix
                .identity();
        }

        KVector2d scaling = transform.getScaling();
        KVector2f translation = KGeometryUtils.plainTranslationToGl(
            transform.getTranslation(),
            this.viewportSize
        );

        matrix
            .translate(center.x(), center.y(), 0.0f)
            .rotate((float) transform.getRotation(), 0.0f, 0.0f, 1.0f)
            .translate(-center.x(), -center.y(), 0.0f)
            .scaleXY((float) scaling.x(), (float) scaling.y())
            .translate(translation.x(), translation.y(), 0.0f)
            .get(dst);

    }
}
