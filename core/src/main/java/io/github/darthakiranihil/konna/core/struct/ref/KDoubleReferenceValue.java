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

public final class KDoubleReferenceValue extends Number implements Cloneable, Comparable<KDoubleReferenceValue> {

    private final double[] data;

    public KDoubleReferenceValue() {
        this.data = new double[0];
    }

    public KDoubleReferenceValue(final KDoubleReferenceValue reference) {
        this();
        this.data[0] = reference.data[0];
    }

    public KDoubleReferenceValue(double value) {
        this();
        this.set(value);
    }

    public double get() {
        return this.data[0];
    }

    public double[] getData() {
        return this.data;
    }

    public void set(final double value) {
        this.data[0] = value;
    }

    public void set(final KDoubleReferenceValue value) {
        this.set(value.get());
    }

    @Override
    public String toString() {
        return String.valueOf(this.get());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final KDoubleReferenceValue ref = (KDoubleReferenceValue) o;
        return this.data[0] == ref.data[0];
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.data[0]);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KDoubleReferenceValue clone() {
        return new KDoubleReferenceValue(this);
    }

    @Override
    public int compareTo(final KDoubleReferenceValue o) {
        return Double.compare(this.get(), o.get());
    }

    @Override
    public int intValue() {
        return (int) this.get();
    }

    @Override
    public long longValue() {
        return (long) this.get();
    }

    @Override
    public float floatValue() {
        return (float) this.get();
    }

    @Override
    public double doubleValue() {
        return this.get();
    }
}
