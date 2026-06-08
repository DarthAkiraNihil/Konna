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
 * Represents a passed-by-reference boolean value.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KBooleanReference implements Cloneable, Comparable<KBooleanReference> {
    private boolean data;

    public KBooleanReference() {
        this.data = false;
    }

    public KBooleanReference(final KBooleanReference reference) {
        this();
        this.data = reference.data;
    }

    public KBooleanReference(boolean value) {
        this();
        this.data = value;
    }

    /**
     * @return Value contained by this reference
     */
    public boolean get() {
        return this.data;
    }

    /**
     * Sets a value to be hold by this reference.
     * @param value New reference value
     */
    public void set(final boolean value) {
        this.data = value;
    }

    /**
     * Sets a value to be hold by this reference from another reference.
     * @param value Reference to set new value from
     */
    public void set(final KBooleanReference value) {
        this.set(value.get());
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KBooleanReference ref = (KBooleanReference) o;
        return this.data == ref.data;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.data);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KBooleanReference clone() {
        return new KBooleanReference(this);
    }

    @Override
    public int compareTo(final KBooleanReference o) {
        return Boolean.compare(this.get(), o.get());
    }
}
