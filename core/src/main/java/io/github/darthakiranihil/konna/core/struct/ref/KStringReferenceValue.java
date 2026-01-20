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

public final class KStringReferenceValue implements Cloneable {
    private String data;

    public KStringReferenceValue() {
        this.data = "";
    }

    public KStringReferenceValue(final KStringReferenceValue reference) {
        this.data = reference.data;
    }

    public KStringReferenceValue(final String value) {
        this.data = value;
    }

    public String get() {
        return this.data;
    }

    public void set(final String value) {
        this.data = value;
    }

    public void set(final KStringReferenceValue value) {
        this.set(value.get());
    }

    @Override
    public String toString() {
        return this.data;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KStringReferenceValue ref = (KStringReferenceValue) o;
        return this.data.equals(ref.data);
    }

    @Override
    public int hashCode() {
        return this.data.hashCode();
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KStringReferenceValue clone() {
        return new KStringReferenceValue(this);
    }

}
