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

package io.github.darthakiranihil.konna.core.struct.ref;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KIntReferencePositiveTests extends KStandardTestClass {

    private final int initialValue;
    private final int newValue;

    public KIntReferencePositiveTests() {
        this.initialValue = 1;
        this.newValue = 2;
    }

    @Test
    public void testProcessReference() {
        KIntReference ref = new KIntReference(this.initialValue);
        this.process(ref);
        Assertions.assertEquals(this.newValue, ref.get());
    }

    @Test
    public void testCreateFromAnother() {
        KIntReference ref = new KIntReference(new KIntReference(this.initialValue));
        Assertions.assertEquals(this.initialValue, ref.get());
    }

    @Test
    public void testSetFromAnother() {
        KIntReference ref = new KIntReference(this.initialValue);
        ref.set(new KIntReference(this.newValue));
        Assertions.assertEquals(this.newValue, ref.get());
    }

    @Test
    public void testClone() {
        KIntReference ref = new KIntReference(this.initialValue);
        KIntReference clone = ref.clone();
        clone.set(this.newValue);
        Assertions.assertNotEquals(ref.get(), clone.get());
    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KIntReference primal = new KIntReference(this.initialValue);
        KIntReference diff = new KIntReference(this.newValue);
        KIntReference equal = new KIntReference(primal);

        Assertions.assertFalse(primal.equals(null));
        Assertions.assertFalse(primal.equals(1));
        Assertions.assertFalse(primal.equals(diff));

        Assertions.assertTrue(primal.equals(equal));
        Assertions.assertEquals(primal, primal);
        Assertions.assertEquals(primal.hashCode(), equal.hashCode());

    }

    @Test
    public void testCompareTo() {

        KIntReference a = new KIntReference(this.initialValue);
        KIntReference b = new KIntReference(this.newValue);

        Assertions.assertEquals(
            Integer.compare(this.initialValue, this.newValue),
            a.compareTo(b)
        );

    }

    @Test
    public void testValues() {

        KIntReference ref = new KIntReference(this.initialValue);

        Assertions.assertEquals((float) this.initialValue, ref.floatValue());
        Assertions.assertEquals(this.initialValue, ref.doubleValue());
        Assertions.assertEquals(this.initialValue, ref.intValue());
        Assertions.assertEquals(this.initialValue, ref.longValue());

    }

    @Test
    public void testToString() {
        KIntReference ref = new KIntReference(this.initialValue);
        Assertions.assertEquals(String.valueOf(this.initialValue), ref.toString());
    }

    private void process(final KIntReference ref) {
        ref.set(this.newValue);
    }
}
