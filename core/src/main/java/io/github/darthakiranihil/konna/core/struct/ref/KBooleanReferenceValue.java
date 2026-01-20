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

public final class KBooleanReferenceValue implements Cloneable, Comparable<KBooleanReferenceValue> {
    private final boolean[] data;

    public KBooleanReferenceValue() {
        this.data = new boolean[0];
    }

    public KBooleanReferenceValue(final KBooleanReferenceValue reference) {
        this();
        this.data[0] = reference.data[0];
    }

    public KBooleanReferenceValue(final boolean value) {
        this();
        this.data[0] = value;
    }

    public boolean get() {
        return this.data[0];
    }

    public boolean[] getData() {
        return this.data;
    }

    public void set(final boolean value) {
        this.data[0] = value;
    }

    public void set(final KBooleanReferenceValue value) {
        this.set(value.get());
    }

    @Override
    public String toString() {
        return String.valueOf(this.data[0]);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KBooleanReferenceValue ref = (KBooleanReferenceValue) o;
        return this.data[0] == ref.data[0];
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.data[0]);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KBooleanReferenceValue clone() {
        return new KBooleanReferenceValue(this);
    }

    @Override
    public int compareTo(final KBooleanReferenceValue o) {
        return Boolean.compare(this.get(), o.get());
    }
}
