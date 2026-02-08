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
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class KMapAssetDefinition implements KAssetDefinition {

    private final Map<String, Object> value;

    public KMapAssetDefinition(final Map<String, Object> value) {
        this.value = value;
    }

    public KMapAssetDefinition() {
        this.value = new HashMap<>();
    }

    @Override
    public int getInt(String property) {
        return (int) this.value.get(property);
    }

    @Override
    public float getFloat(String property) {
        return (float) this.value.get(property);
    }

    @Override
    public boolean getBoolean(String property) {
        return (boolean) this.value.get(property);
    }

    @Override
    public @Nullable String getString(String property) {
        return (String) this.value.get(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public KAssetDefinition getSubdefinition(String property) {
        Object value = this.value.get(property);
        if (value == null) {
            throw new KAssetDefinitionError("subdefinition is null");
        }

        if (KAssetDefinition.class.isAssignableFrom(value.getClass())) {
            return (KAssetDefinition) value;
        }

        if (Map.class.isAssignableFrom(value.getClass())) {
            return new KMapAssetDefinition((Map<String, Object>) value);
        }

        throw KAssetDefinitionError.propertyNotFound(property);
    }

    @Override
    public <T extends Enum<T>> T getEnum(String property, Class<T> enumClass) {
        String value = this.getString(property);
        if (value == null) {
            throw new KAssetDefinitionError(
                String.format(
                    "Value of property %s is null",
                    property
                )
            );
        }

        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            throw new KAssetDefinitionError(e.getMessage());
        }
    }

    @Override
    public int[] getIntArray(String property) {
        return (int[]) this.value.get(property);
    }

    @Override
    public float[] getFloatArray(String property) {
        return (float[]) this.value.get(property);
    }

    @Override
    public boolean[] getBooleanArray(String property) {
        return (boolean[]) this.value.get(property);
    }

    @Override
    public String @Nullable [] getStringArray(String property) {
        return (String[]) this.value.get(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public KAssetDefinition[] getSubdefinitionArray(String property) {
        Object array = this.value.get(property);
        if (array == null) {
            throw new KAssetDefinitionError("subdefinition array is null");
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
    public boolean hasInt(String property) {
        return
                this.hasPrimitiveProperty(property, int.class, Integer.class)
            ||  this.hasPrimitiveProperty(property, long.class, Long.class)
            ||  this.hasPrimitiveProperty(property, short.class, Short.class)
            ||  this.hasPrimitiveProperty(property, byte.class, Byte.class);
    }

    @Override
    public boolean hasFloat(String property) {
        return
                this.hasPrimitiveProperty(property, float.class, Float.class)
            ||  this.hasPrimitiveProperty(property, double.class, Double.class);
    }

    @Override
    public boolean hasBoolean(String property) {
        return this.hasPrimitiveProperty(property, boolean.class, Boolean.class);
    }

    @Override
    public boolean hasString(String property) {
        return this.hasProperty(property, String.class);
    }

    @Override
    public boolean hasSubdefinition(String property) {
        return
                this.hasProperty(property, KAssetDefinition.class)
            ||  this.hasProperty(property, Map.class);
    }

    @Override
    public <T extends Enum<T>> boolean hasEnum(String property, Class<T> enumClass) {
        try {
            this.getEnum(property, enumClass);
            return true;
        } catch (KAssetDefinitionError e) {
            return false;
        }
    }

    @Override
    public boolean hasIntArray(String property) {
        return
                this.hasPrimitiveProperty(property, int[].class, Integer[].class)
            ||  this.hasPrimitiveProperty(property, long[].class, Long[].class)
            ||  this.hasPrimitiveProperty(property, short[].class, Short[].class)
            ||  this.hasPrimitiveProperty(property, byte[].class, Byte[].class);
    }

    @Override
    public boolean hasFloatArray(String property) {
        return
                this.hasPrimitiveProperty(property, float[].class, Float[].class)
            ||  this.hasPrimitiveProperty(property, double[].class, Double[].class);
    }

    @Override
    public boolean hasBooleanArray(String property) {
        return this.hasPrimitiveProperty(property, boolean[].class, Boolean[].class);
    }

    @Override
    public boolean hasStringArray(String property) {
        return this.hasProperty(property, String[].class);
    }

    @Override
    public boolean hasSubdefinitionArray(String property) {
        return
                this.hasProperty(property, KAssetDefinition[].class)
            ||  this.hasProperty(property, Map[].class);
    }

    private boolean hasPrimitiveProperty(final String property, Class<?> class1, Class<?> class2) {
        boolean flag = this.value.containsKey(property);
        if (!flag) {
            return false;
        }

        Object val = this.value.get(property);
        return val.getClass() == class1 || val.getClass() == class2;
    }

    private boolean hasProperty(final String property, Class<?> clazz) {
        boolean flag = this.value.containsKey(property);
        if (!flag) {
            return false;
        }

        Object val = this.value.get(property);
        return val.getClass() == clazz || clazz.isAssignableFrom(val.getClass());
    }

}
