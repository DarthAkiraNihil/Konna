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

package io.github.darthakiranihil.konna.core.io;

/**
 * Interface for a universal container of asset data, that does not depend
 * on its format, however, it is supposed to be used for definitions
 * that use key-value pairs to define its properties.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KAssetDefinition {

    /**
     * Returns integer property of asset definition.
     * @param property Property name
     * @return Integer value of passed property
     */
    int getInt(String property);
    /**
     * Returns float property of asset definition.
     * @param property Property name
     * @return Float value of passed property
     */
    float getFloat(String property);
    /**
     * Returns boolean property of asset definition.
     * @param property Property name
     * @return Boolean value of passed property
     */
    boolean getBoolean(String property);
    /**
     * Returns string property of asset definition.
     * @param property Property name
     * @return String value of passed property
     */
    String getString(String property);
    /**
     * Returns subdefinition (nested definition) of asset definition.
     * @param property Property name
     * @return Asset definition of passed property
     */
    KAssetDefinition getSubdefinition(String property);

    /**
     * Returns integer array property of asset definition.
     * @param property Property name
     * @return Integer array of passed property
     */
    int[] getIntArray(String property);
    /**
     * Returns float array property of asset definition.
     * @param property Property name
     * @return Float array of passed property
     */
    float[] getFloatArray(String property);
    /**
     * Returns boolean array property of asset definition.
     * @param property Property name
     * @return Boolean array of passed property
     */
    boolean[] getBooleanArray(String property);
    /**
     * Returns string array property of asset definition.
     * @param property Property name
     * @return String array of passed property
     */
    String[] getStringArray(String property);
    /**
     * Returns subdefinition (nested definition) array property of asset definition.
     * @param property Property name
     * @return Subdefinition array of passed property
     */
    KAssetDefinition[] getSubdefinitionArray(String property);

}
