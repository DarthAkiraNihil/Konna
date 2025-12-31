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
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KTransformPositiveTests extends KStandardTestClass {

    @Test
    public void testRotation() {

        KTransform transform = new KTransform();

        double angle = Math.PI / 6;
        transform.rotate(angle);

        var rotation = transform.getRotation();
        Assertions.assertEquals(angle, rotation);

        float[][] matrix = transform.matrix();

        Assertions.assertEquals((float) (Math.sqrt(3) / 2), matrix[0][0]);
        Assertions.assertEquals(0.5f, matrix[1][0]);
        Assertions.assertEquals(-0.5f, matrix[0][1]);
        Assertions.assertEquals((float) (Math.sqrt(3) / 2), matrix[1][1]);

    }

    @Test
    public void testTranslation() {

        KTransform transform = new KTransform();

        KVector2i vector = new KVector2i(10, 11);
        transform.translate(vector);

        KVector2i translation = transform.getTranslation();
        Assertions.assertEquals(vector, translation);

        float[][] matrix = transform.matrix();

        Assertions.assertEquals((float) vector.x(), matrix[0][2]);
        Assertions.assertEquals((float) vector.y(), matrix[1][2]);

    }

    @Test
    public void testScaling() {

        KTransform transform = new KTransform();

        KVector2d scale = new KVector2d(2.0, 2.0);
        transform.scale(scale);

        KVector2d scaling = transform.getScaling();
        Assertions.assertEquals(scale, scaling);

        float[][] matrix = transform.matrix();

        Assertions.assertEquals(2.0f, matrix[0][0]);
        Assertions.assertEquals(2.0f, matrix[1][1]);

    }

    @Test
    public void testPivotedRotation() {

        KTransform transform = new KTransform();

        double angle = Math.PI / 6;
        KVector2i pivot = new KVector2i(10, 10);
        transform.rotate(angle, pivot);

        var rotation = transform.getRotation();
        Assertions.assertEquals(angle, rotation);

        float[][] matrix = transform.matrix();

        Assertions.assertEquals((float) (Math.sqrt(3) / 2), matrix[0][0]);
        Assertions.assertEquals(0.5f, matrix[1][0]);
        Assertions.assertEquals(-0.5f, matrix[0][1]);
        Assertions.assertEquals((float) (Math.sqrt(3) / 2), matrix[1][1]);
        Assertions.assertEquals((float) pivot.x(), matrix[0][2]);
        Assertions.assertEquals((float) pivot.y(), matrix[1][2]);

    }

    @Test
    public void testSetScaling() {

        KTransform transform = new KTransform();

        KVector2d scale = new KVector2d(2.0, 2.0);
        transform.setScaling(scale);

        KVector2d scaling = transform.getScaling();
        Assertions.assertEquals(scale, scaling);

        float[][] matrix = transform.matrix();

        Assertions.assertEquals(2.0f, matrix[0][0]);
        Assertions.assertEquals(2.0f, matrix[1][1]);

    }

    @Test
    public void testChainedTransform() {

        KTransform transform = new KTransform();

        double angle = Math.PI / 6;
        KVector2i vector = new KVector2i(10, 11);
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

        float[][] matrix = transform.matrix();
        transform.matrix(); // ONLY FOR COVERAGE!

        Assertions.assertEquals((float) Math.sqrt(3), matrix[0][0]);
        Assertions.assertEquals(0.5f, matrix[1][0]);
        Assertions.assertEquals(-0.5f, matrix[0][1]);
        Assertions.assertEquals((float) Math.sqrt(3), matrix[1][1]);
        Assertions.assertEquals((float) vector.x(), matrix[0][2]);
        Assertions.assertEquals((float) vector.y(), matrix[1][2]);

    }

}
