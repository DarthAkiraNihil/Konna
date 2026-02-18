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

package io.github.darthakiranihil.konna.core.io.std;

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link KAssetDefinition} that stores data inside a map.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public class KMapAssetDefinition implements KAssetDefinition {

    private final Map<String, Object> value;

    /**
     * Standard constructor.
     * @param value Map that stores asset definition data
     */
    public KMapAssetDefinition(final Map<String, Object> value) {
        this.value = value;
    }

    /**
     * Creates a definition with empty {@link HashMap} inside.
     */
    public KMapAssetDefinition() {
        this.value = new HashMap<>();
    }

    @Override
    public int getInt(final String property) {
        Object val = this.value.get(property);
        Class<?> valClass = val.getClass();
        if (Long.class.isAssignableFrom(valClass)) {
            return ((Long) val).intValue();
        }
        if (Short.class.isAssignableFrom(valClass)) {
            return ((Short) val).intValue();
        }
        if (Byte.class.isAssignableFrom(valClass)) {
            return ((Byte) val).intValue();
        }

        return (int) this.value.get(property);
    }

    @Override
    public float getFloat(final String property) {
        Object val = this.value.get(property);
        if (Double.class.isAssignableFrom(val.getClass())) {
            return ((Double) val).floatValue();
        }

        return (float) this.value.get(property);
    }

    @Override
    public boolean getBoolean(final String property) {
        return (boolean) this.value.get(property);
    }

    @Override
    public @Nullable String getString(final String property) {
        return (String) this.value.get(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public KAssetDefinition getSubdefinition(final String property) {
        Object v = this.value.get(property);
        if (v == null) {
            throw KAssetDefinitionError.propertyNotFound(property);
        }

        if (KAssetDefinition.class.isAssignableFrom(v.getClass())) {
            return (KAssetDefinition) v;
        }

        if (Map.class.isAssignableFrom(v.getClass())) {
            return new KMapAssetDefinition((Map<String, Object>) v);
        }

        throw KAssetDefinitionError.propertyNotFound(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getEnum(final String property, final Class<T> enumClass) {
        Object rawValue = this.value.get(property);
        if (rawValue != null && rawValue.getClass() == enumClass) {
            return (T) rawValue;
        }

        String v = this.getString(property);
        if (v == null) {
            throw new KAssetDefinitionError(
                String.format(
                    "Value of property %s is null",
                    property
                )
            );
        }

        try {
            return Enum.valueOf(enumClass, v);
        } catch (IllegalArgumentException e) {
            throw new KAssetDefinitionError(e.getMessage());
        }
    }

    @Override
    public int[] getIntArray(final String property) {
        return this.castToIntArray(this.value.get(property));
    }

    @Override
    public float[] getFloatArray(final String property) {
        return this.castToFloatArray(this.value.get(property));
    }

    @Override
    public boolean[] getBooleanArray(final String property) {
        Object val = this.value.get(property);
        if (boolean[].class.isAssignableFrom(val.getClass())) {
            return (boolean[]) val;
        }

        Boolean[] booleanArrayValue = (Boolean[]) val;
        boolean[] result = new boolean[booleanArrayValue.length];
        for (int i = 0; i < booleanArrayValue.length; i++) {
            result[i] = booleanArrayValue[i];
        }
        return result;
    }

    @Override
    public String @Nullable [] getStringArray(final String property) {
        return (String[]) this.value.get(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public KAssetDefinition[] getSubdefinitionArray(final String property) {
        Object array = this.value.get(property);
        if (array == null) {
            throw KAssetDefinitionError.propertyNotFound(property);
        }

        if (KAssetDefinition[].class.isAssignableFrom(array.getClass())) {
            return (KAssetDefinition[]) array;
        }

        if (Map[].class.isAssignableFrom(array.getClass())) {
            Map<String, Object>[] castArray = (Map<String, Object>[]) array;
            KAssetDefinition[] defs = new KAssetDefinition[castArray.length];
            for (int i = 0; i < castArray.length; i++) {
                defs[i] = new KMapAssetDefinition(castArray[i]);
            }
            return defs;
        }

        throw KAssetDefinitionError.propertyNotFound(property);
    }

    @Override
    public boolean hasInt(final String property) {
        return
                this.hasPrimitiveProperty(property, int.class, Integer.class)
            ||  this.hasPrimitiveProperty(property, long.class, Long.class)
            ||  this.hasPrimitiveProperty(property, short.class, Short.class)
            ||  this.hasPrimitiveProperty(property, byte.class, Byte.class);
    }

    @Override
    public boolean hasFloat(final String property) {
        return
                this.hasPrimitiveProperty(property, float.class, Float.class)
            ||  this.hasPrimitiveProperty(property, double.class, Double.class);
    }

    @Override
    public boolean hasBoolean(final String property) {
        return this.hasPrimitiveProperty(property, boolean.class, Boolean.class);
    }

    @Override
    public boolean hasString(final String property) {
        return this.hasProperty(property, String.class);
    }

    @Override
    public boolean hasSubdefinition(final String property) {
        return
                this.hasProperty(property, KAssetDefinition.class)
            ||  this.hasProperty(property, Map.class);
    }

    @Override
    public <T extends Enum<T>> boolean hasEnum(final String property, final Class<T> enumClass) {
        try {
            this.getEnum(property, enumClass);
            return true;
        } catch (KAssetDefinitionError | ClassCastException e) {
            return false;
        }
    }

    @Override
    public boolean hasIntArray(final String property) {
        return
                this.hasPrimitiveProperty(property, int[].class, Integer[].class)
            ||  this.hasPrimitiveProperty(property, long[].class, Long[].class)
            ||  this.hasPrimitiveProperty(property, short[].class, Short[].class)
            ||  this.hasPrimitiveProperty(property, byte[].class, Byte[].class);
    }

    @Override
    public boolean hasFloatArray(final String property) {
        return
                this.hasPrimitiveProperty(property, float[].class, Float[].class)
            ||  this.hasPrimitiveProperty(property, double[].class, Double[].class);
    }

    @Override
    public boolean hasBooleanArray(final String property) {
        return this.hasPrimitiveProperty(property, boolean[].class, Boolean[].class);
    }

    @Override
    public boolean hasStringArray(final String property) {
        return this.hasProperty(property, String[].class);
    }

    @Override
    public boolean hasSubdefinitionArray(final String property) {
        return
                this.hasProperty(property, KAssetDefinition[].class)
            ||  this.hasProperty(property, Map[].class);
    }

    private boolean hasPrimitiveProperty(
        final String property,
        final Class<?> class1,
        final Class<?> class2
    ) {
        boolean flag = this.value.containsKey(property);
        if (!flag) {
            return false;
        }

        Object val = this.value.get(property);
        return val.getClass() == class1 || val.getClass() == class2;
    }

    private boolean hasProperty(final String property, final Class<?> clazz) {
        boolean flag = this.value.containsKey(property);
        if (!flag) {
            return false;
        }

        Object val = this.value.get(property);
        return val.getClass() == clazz || clazz.isAssignableFrom(val.getClass());
    }

    private int[] castToIntArray(final Object v) {

        if (int[].class.isAssignableFrom(v.getClass())) {
            return (int[]) v;
        }

        int arrayLength = Array.getLength(v);
        int[] result = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            Object arrayValue = Array.get(v, i);
            if (Number.class.isAssignableFrom(arrayValue.getClass())) {
                result[i] = ((Number) arrayValue).intValue();
            } else {
                result[i] = (int) Array.get(v, i);
            }
        }
        return result;

    }

    private float[] castToFloatArray(final Object v) {

        if (float[].class.isAssignableFrom(v.getClass())) {
            return (float[]) v;
        }

        int arrayLength = Array.getLength(v);
        float[] result = new float[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            Object arrayValue = Array.get(v, i);
            if (Number.class.isAssignableFrom(arrayValue.getClass())) {
                result[i] = ((Number) arrayValue).floatValue();
            } else {
                result[i] = (float) Array.get(v, i);
            }
        }
        return result;

    }

    @Override
    public Class<?> getClassObject(
        final String property
    ) {
        Object val = this.value.get(property);
        if (val == null) {
            throw KAssetDefinitionError.propertyNotFound(property);
        }

        if (Class.class.isAssignableFrom(val.getClass())) {
            return (Class<?>) val;
        }

        return KClassUtils.getForName(
            this.getString(property)
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getClassObject(
        final String property,
        final Class<T> targetClass
    ) {
        Class<?> clazz = this.getClassObject(property);
        if (targetClass.isAssignableFrom(clazz)) {
            return (Class<? extends T>) clazz;
        }

        throw KAssetDefinitionError.propertyNotFound(property);
    }

    @Override
    public Class<?>[] getClassObjectArray(final String property) {
        Object val = this.value.get(property);
        if (val == null) {
            throw KAssetDefinitionError.propertyNotFound(property);
        }
        if (Class[].class.isAssignableFrom(val.getClass())) {
            return (Class<?>[]) val;
        }

        String[] strings = Objects.requireNonNull(this.getStringArray(property));

        Class<?>[] classes = new Class[strings.length];
        for (int i = 0; i < strings.length; i++) {
            classes[i] = KClassUtils.getForName(strings[i]);
        }
        return classes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T>[] getClassObjectArray(
        final String property,
        final Class<T> targetClass
    ) {
        Object val = this.value.get(property);
        if (Class[].class.isAssignableFrom(val.getClass())) {
            var array = (Class<?>[]) val;
            if (array.length == 0) {
                return new Class[0];
            }

            if (!targetClass.isAssignableFrom(array[0])) {
                throw KAssetDefinitionError.propertyNotFound(property);
            }

            return (Class<? extends T>[]) array;
        }

        String[] strings = Objects.requireNonNull(this.getStringArray(property));

        Class<? extends T>[] classes = new Class[strings.length];
        for (int i = 0; i < strings.length; i++) {
            Class<?> clazz = KClassUtils.getForName(strings[i]);
            if (!targetClass.isAssignableFrom(clazz)) {
                throw new KAssetDefinitionError(
                    String.format(
                        "Cannot cast class array element %d to %s",
                        i,
                        targetClass
                    )
                );
            }

            classes[i] = (Class<? extends T>) clazz;
        }
        return classes;
    }

    @Override
    public boolean hasClassObject(final String property) {
        try {
            this.getClassObject(property);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public <T> boolean hasClassObject(
        final String property,
        final Class<T> targetClass
    ) {
        try {
            this.getClassObject(property, targetClass);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public boolean hasClassObjectArray(final String property) {
        try {
            this.getClassObjectArray(property);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public <T> boolean hasClassObjectArray(
        final String property,
        final Class<T> targetClass
    ) {
        try {
            this.getClassObjectArray(property, targetClass);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

}
