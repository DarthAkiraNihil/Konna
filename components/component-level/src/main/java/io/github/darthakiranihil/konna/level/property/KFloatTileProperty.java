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

package io.github.darthakiranihil.konna.level.property;

/**
 * Container for additional tile property that is a float.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KFloatTileProperty implements KTileProperty {

    private float value;

    /**
     * Constructs the property.
     * @param value Value of the property
     */
    public KFloatTileProperty(final float value) {
        this.value = value;
    }

    /**
     * @return Value contained in this property
     */
    public float getValue() {
        return this.value;
    }

    /**
     * Sets a new value for this property.
     * @param value New value of the property
     */
    public void setValue(float value) {
        this.value = value;
    }

}
