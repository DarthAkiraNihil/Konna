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

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KBooleanReferencePositiveTests extends KStandardTestClass {

    private final boolean initialValue;
    private final boolean newValue;

    public KBooleanReferencePositiveTests() {
        this.initialValue = true;
        this.newValue = false;
    }

    @Test
    public void testProcessReference() {
        KBooleanReference ref = new KBooleanReference(this.initialValue);
        this.process(ref);
        Assertions.assertEquals(this.newValue, ref.get());
    }

    @Test
    public void testCreateFromAnother() {
        KBooleanReference ref = new KBooleanReference(new KBooleanReference(this.initialValue));
        Assertions.assertEquals(this.initialValue, ref.get());
    }

    @Test
    public void testSetFromAnother() {
        KBooleanReference ref = new KBooleanReference(this.initialValue);
        ref.set(new KBooleanReference(this.newValue));
        Assertions.assertEquals(this.newValue, ref.get());
    }

    @Test
    public void testClone() {
        KBooleanReference ref = new KBooleanReference(this.initialValue);
        KBooleanReference clone = ref.clone();
        clone.set(this.newValue);
        Assertions.assertNotEquals(ref.get(), clone.get());
    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KBooleanReference primal = new KBooleanReference(this.initialValue);
        KBooleanReference diff = new KBooleanReference(this.newValue);
        KBooleanReference equal = new KBooleanReference(primal);

        Assertions.assertFalse(primal.equals(null));
        Assertions.assertFalse(primal.equals(1));
        Assertions.assertFalse(primal.equals(diff));

        Assertions.assertTrue(primal.equals(equal));
        Assertions.assertEquals(primal, primal);
        Assertions.assertEquals(primal.hashCode(), equal.hashCode());

    }

    @Test
    public void testCompareTo() {

        KBooleanReference a = new KBooleanReference(this.initialValue);
        KBooleanReference b = new KBooleanReference(this.newValue);

        Assertions.assertEquals(
            Boolean.compare(this.initialValue, this.newValue),
            a.compareTo(b)
        );

    }

    @Test
    public void testToString() {
        KBooleanReference ref = new KBooleanReference(this.initialValue);
        Assertions.assertEquals(String.valueOf(this.initialValue), ref.toString());
    }

    private void process(final KBooleanReference ref) {
        ref.set(this.newValue);
    }
    
}
