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

import org.jspecify.annotations.Nullable;

/**
 * Represent a passed-by-reference value of desired type.
 * @param <T> Type of value hold by reference
 */
public final class KReference<T> implements Cloneable {

    private @Nullable T data;

    public KReference() {
        this.data = null;
    }

    public KReference(final @Nullable T data) {
        this.data = data;
    }

    /**
     * @return Value contained by this reference
     */
    public @Nullable T get() {
        return this.data;
    }

    /**
     * Sets a value to be hold by this reference.
     * @param obj New reference value
     */
    public void set(final @Nullable T obj) {
        this.data = obj;
    }

    /**
     * Sets a value to be hold by this reference from another reference.
     * @param value Reference to set new value from
     */
    public void set(final KReference<T> value) {
        this.set(value.get());
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public KReference<T> clone() {
        return new KReference<>(this.data);
    }
}
