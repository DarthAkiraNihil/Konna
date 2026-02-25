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

import org.jspecify.annotations.Nullable;

import java.util.List;

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
     * @return List of all properties names contained in this definition
     * @since 0.5.0
     */
    List<String> getProperties();

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
    @Nullable String getString(String property);
    /**
     * Returns subdefinition (nested definition) of asset definition.
     * @param property Property name
     * @return Asset definition of passed property
     */
    KAssetDefinition getSubdefinition(String property);
    /**
     * @param property Property name
     * @param enumClass Enum class to be extracted
     * @return Enum value of passed property
     * @param <T> Enum class type parameter
     * @since 0.4.0
     */
    <T extends Enum<T>> T getEnum(String property, Class<T> enumClass);

    /**
     * Returns {@link Class} object from asset definition.
     * @param property Property name
     * @return Class object contained by this property
     * @since 0.4.0
     */
    Class<?> getClassObject(String property);
    /**
     * Returns {@link Class} object from asset definition that must be assignable
     * to a specific class (usual case for user implementations of some class).
     * @param property Property name
     * @param targetClass What contained class must be assigned to
     * @param <T> Type parameter of target class
     * @return Class object contained by this property
     * @since 0.4.0
     */
    <T> Class<? extends T> getClassObject(String property, Class<T> targetClass);

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
    String @Nullable[] getStringArray(String property);
    /**
     * Returns subdefinition (nested definition) array property of asset definition.
     * @param property Property name
     * @return Subdefinition array of passed property
     */
    KAssetDefinition[] getSubdefinitionArray(String property);

    /**
     * Returns array of {@link Class} objects from asset definition.
     * @param property Property name
     * @return Class object array contained by this property
     * @since 0.4.0
     */
    Class<?>[] getClassObjectArray(String property);
    /**
     * Returns array of {@link Class} objects from asset definition whose elements
     * must be assignable
     * to a specific class (usual case for user implementations of some class).
     * @param property Property name
     * @param targetClass What contained array elements must be assigned to
     * @param <T> Type parameter of target class
     * @return Class object array contained by this property
     * @since 0.4.0
     */
    <T> Class<? extends T>[] getClassObjectArray(String property, Class<T> targetClass);

    /**
     * @param property Property name
     * @return Whether this definition has the int property with such name or not
     * @since 0.4.0
     */
    boolean hasInt(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the float property with such name or not
     * @since 0.4.0
     */
    boolean hasFloat(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the boolean property with such name or not
     * @since 0.4.0
     */
    boolean hasBoolean(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the string property with such name or not
     * @since 0.4.0
     */
    boolean hasString(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the subdefinition property with such name or not
     * @since 0.4.0
     */
    boolean hasSubdefinition(String property);
    /**
     * @param property Property name
     * @param enumClass Enum class to be checked
     * @param <T> Enum class type parameter
     * @return Whether this definition has the enum property with such name or not
     * @since 0.4.0
     */
    <T extends Enum<T>> boolean hasEnum(String property, Class<T> enumClass);

    /**
     * @param property Property name
     * @return Whether this definition contains class property with such name or not
     * @since 0.4.0
     */
    boolean hasClassObject(String property);
    /**
     * @param property Property name
     * @param targetClass What contained class must be assigned to
     * @param <T> Type parameter of target class
     * @return Whether this definition contains class property with such name or not,
     *         and whether contained class is assignable to targetClass
     * @since 0.4.0
     */
    <T> boolean hasClassObject(String property, Class<T> targetClass);

    /**
     * @param property Property name
     * @return Whether this definition has the int array property with such name or not
     * @since 0.4.0
     */
    boolean hasIntArray(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the float array property with such name or not
     * @since 0.4.0
     */
    boolean hasFloatArray(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the boolean array property with such name or not
     * @since 0.4.0
     */
    boolean hasBooleanArray(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the string array property with such name or not
     * @since 0.4.0
     */
    boolean hasStringArray(String property);
    /**
     * @param property Property name
     * @return Whether this definition has the subdefinition array property with such name or not
     * @since 0.4.0
     */
    boolean hasSubdefinitionArray(String property);

    /**
     * @param property Property name
     * @return Whether this definition contains class array property with such name or not
     * @since 0.4.0
     */
    boolean hasClassObjectArray(String property);
    /**
     * @param property Property name
     * @param targetClass What contained elements must be assigned to
     * @param <T> Type parameter of target class
     * @return Whether this definition contains class array property with such name or not,
     *         and if array elements represent class assignable to targetClass
     * @since 0.4.0
     */
    <T> boolean hasClassObjectArray(String property, Class<T> targetClass);

}
