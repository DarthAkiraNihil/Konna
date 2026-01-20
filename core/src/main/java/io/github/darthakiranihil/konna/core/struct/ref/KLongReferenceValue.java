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

public final class KLongReferenceValue extends Number implements Cloneable, Comparable<KLongReferenceValue> {

    private final long[] data;

    public KLongReferenceValue() {
        this.data = new long[0];
    }

    public KLongReferenceValue(final KLongReferenceValue reference) {
        this();
        this.data[0] = reference.data[0];
    }

    public KLongReferenceValue(long value) {
        this();
        this.set(value);
    }

    public long get() {
        return this.data[0];
    }

    public long[] getData() {
        return this.data;
    }

    public void set(final long value) {
        this.data[0] = value;
    }

    public void set(final KLongReferenceValue value) {
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
        final KLongReferenceValue ref = (KLongReferenceValue) o;
        return this.data[0] == ref.data[0];
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.data[0]);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KLongReferenceValue clone() {
        return new KLongReferenceValue(this);
    }

    @Override
    public int compareTo(final KLongReferenceValue o) {
        return Long.compare(this.get(), o.get());
    }

    @Override
    public int intValue() {
        return (int) this.get();
    }

    @Override
    public long longValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return (float) this.get();
    }

    @Override
    public double doubleValue() {
        return (double) this.get();
    }
}
