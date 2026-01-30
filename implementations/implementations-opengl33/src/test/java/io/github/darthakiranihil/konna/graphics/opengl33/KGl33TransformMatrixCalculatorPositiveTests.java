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

import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KTransform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KGl33TransformMatrixCalculatorPositiveTests {

    public KGl33TransformMatrixCalculatorPositiveTests() {
        KTransform.setTransformMatrixCalculator(new KGl33TransformMatrixCalculator());
    }

    @Test
    public void testRotation() {

        KTransform transform = new KTransform();

        double angle = Math.PI / 6;
        transform.rotate(angle);

        var rotation = transform.getRotation();
        Assertions.assertEquals(angle, rotation);

        float[] matrix = transform.getMatrix();

        Assertions.assertEquals((float) (Math.sqrt(3) / 2), matrix[0]);
        Assertions.assertEquals(0.5f, matrix[1]);
        Assertions.assertEquals(-0.5f, matrix[4]);
        Assertions.assertEquals((float) (Math.sqrt(3) / 2), matrix[5]);

    }

    @Test
    public void testTranslation() {

        KTransform transform = new KTransform();

        KVector2i vector = new KVector2i(320, 320);
        transform.translate(vector);

        KVector2i translation = transform.getTranslation();
        Assertions.assertEquals(vector, translation);

        float[] matrix = transform.getMatrix();

        Assertions.assertEquals(1.0f, matrix[12]);
        Assertions.assertEquals(-1.0f, matrix[13]);

    }

    @Test
    public void testScaling() {

        KTransform transform = new KTransform();

        KVector2d scale = new KVector2d(2.0, 2.0);
        transform.scale(scale);

        KVector2d scaling = transform.getScaling();
        Assertions.assertEquals(scale, scaling);

        float[] matrix = transform.getMatrix();

        Assertions.assertEquals(2.0f, matrix[0]);
        Assertions.assertEquals(2.0f, matrix[5]);

    }

    @Test
    public void testSetScaling() {

        KTransform transform = new KTransform();

        KVector2d scale = new KVector2d(2.0, 2.0);
        transform.setScaling(scale);

        KVector2d scaling = transform.getScaling();
        Assertions.assertEquals(scale, scaling);

        float[] matrix = transform.getMatrix();

        Assertions.assertEquals(2.0f, matrix[0]);
        Assertions.assertEquals(2.0f, matrix[5]);

    }

    @Test
    public void testChainedTransform() {

        KTransform transform = new KTransform();

        double angle = Math.PI / 6;
        KVector2i vector = new KVector2i(320, 320);
        KVector2d scale = new KVector2d(2.0, 2.0);

        transform
            .rotate(angle)
            .translate(vector)
            .scale(scale);

        var rotation = transform.getRotation();
        KVector2d scaling = transform.getScaling();
        KVector2i translation = transform.getTranslation();

        Assertions.assertEquals(angle, rotation);
        Assertions.assertEquals(vector, translation);
        Assertions.assertEquals(scale, scaling);

        float[] matrix = transform.getMatrix();
        transform.getMatrix(); // ONLY FOR COVERAGE!

        Assertions.assertEquals((float) Math.sqrt(3), matrix[0]);
        Assertions.assertEquals(1.0f, matrix[1]);
        Assertions.assertEquals(-1.0f, matrix[4]);
        Assertions.assertEquals((float) Math.sqrt(3), matrix[5]);
        // Assertions.assertEquals(1.0f, matrix[12]);
        // Assertions.assertEquals(-1.0f, matrix[13]);

    }

    @Test
    public void testTransformWithParent() {

        KTransform parent = new KTransform();
        KTransform child = new KTransform();

        child.setParent(parent);
        Assertions.assertEquals(parent, child.getParent());
        Assertions.assertEquals(1, parent.getChildren().size());

        double angle = Math.PI / 4;
        parent.rotate(angle);
        child.rotate(angle);

        Assertions.assertEquals(angle, parent.getRotation());
        Assertions.assertEquals(angle, child.getRotation());

        float[] matrix = child.getMatrix();

        Assertions.assertEquals(0.0f, matrix[0]);
        Assertions.assertTrue(Math.abs(1f - matrix[1]) < 0.001f);
        Assertions.assertTrue(Math.abs(-1f - matrix[4]) < 0.001f);
        Assertions.assertEquals(0.0f, matrix[5]);

    }

    @Test
    public void testTransformWithChild() {

        KTransform parent = new KTransform();
        KTransform child = new KTransform();

        parent.addChild(child);
        Assertions.assertEquals(parent, child.getParent());
        Assertions.assertEquals(1, parent.getChildren().size());

        double angle = Math.PI / 4;
        parent.rotate(angle);
        child.rotate(angle);

        Assertions.assertEquals(angle, parent.getRotation());
        Assertions.assertEquals(angle, child.getRotation());

        float[] matrix = child.getMatrix();

        Assertions.assertEquals(0.0f, matrix[0]);
        Assertions.assertTrue(Math.abs(1f - matrix[1]) < 0.001f);
        Assertions.assertTrue(Math.abs(-1f - matrix[4]) < 0.001f);
        Assertions.assertEquals(0.0f, matrix[5]);

        parent.removeChild(child);
        Assertions.assertNull(child.getParent());
        Assertions.assertEquals(0, parent.getChildren().size());

    }
}
