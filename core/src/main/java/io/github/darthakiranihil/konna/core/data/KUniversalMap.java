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

package io.github.darthakiranihil.konna.core.data;

import java.util.*;

/**
 * Universal container for data that can be absolutely everything. Literally just represents
 * a wrapper of {@link HashMap}(String, Object) with some methods that simplify working with the container.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KUniversalMap extends HashMap<String, Object> {

    /**
     * Default constructor that calls parent class' constructor.
     */
    public KUniversalMap() {
        super();
    }

    /**
     * Returns a value from the map with specified type.
     * Acts like standard map's get method, but additionally performs class cast to desired type.
     * There is no checking of class equality so {@link ClassCastException} will be thrown if you try to
     * get value of incorrect type. Also, all standard Map.get() exceptions also could be thrown.
     * This method does not work with primitive types' classes (like int.class), so use boxed classes for it
     * @param key The key of the value
     * @param clazz Class to cast the value
     * @return Map's value with passed key cast to required type
     * @param <T> The type to cast the value
     */
    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(this.get(key));
    }

    /**
     * Returns a value from the map with specified type or null if there is type mismatch.
     * Acts like standard map's get method, but additionally performs class cast to desired type.
     * There is checking of class equality so if type mismatch is detected, null will be returned.
     * Also, all standard Map.get() exceptions also could be thrown.
     * This method does not work with primitive types' classes (like int.class), so use boxed classes for it
     * @param key The key of the value
     * @param clazz Class to cast the value
     * @return Map's value with passed key cast to required type
     * @param <T> The type to cast the value
     */
    public <T> T getSafe(String key, Class<T> clazz) {
        Object raw = this.get(key);
        if (clazz != raw.getClass()) {
            return null;
        }

        return clazz.cast(this.get(key));
    }

}
