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

/**
 * Represents a passed-by-reference int value.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KIntReference extends Number implements Cloneable, Comparable<KIntReference> {

    private int data;

    public KIntReference() {
        this.data = 0;
    }

    public KIntReference(final KIntReference reference) {
        this();
        this.data = reference.data;
    }

    public KIntReference(int value) {
        this();
        this.set(value);
    }

    /**
     * @return Value contained by this reference
     */
    public int get() {
        return this.data;
    }

    /**
     * Sets a value to be hold by this reference.
     * @param value New reference value
     */
    public void set(int value) {
        this.data = value;
    }

    /**
     * Sets a value to be hold by this reference from another reference.
     * @param value Reference to set new value from
     */
    public void set(final KIntReference value) {
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
        final KIntReference ref = (KIntReference) o;
        return this.data == ref.data;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.data);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KIntReference clone() {
        return new KIntReference(this);
    }

    @Override
    public int compareTo(final KIntReference o) {
        return Integer.compare(this.get(), o.get());
    }

    @Override
    public int intValue() {
        return this.get();
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
        return this.get();
    }
    
}
