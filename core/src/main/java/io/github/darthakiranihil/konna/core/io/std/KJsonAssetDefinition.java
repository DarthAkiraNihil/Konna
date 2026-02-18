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

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValueException;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link KAssetDefinition} for definitions stored in json format.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KJsonAssetDefinition implements KAssetDefinition {

    private final KJsonValue value;

    /**
     * Standard constructor, that validates wrapped json value according to passed schema.
     * @param value Json value, containing asset definition
     * @param schema Json value schema
     */
    public KJsonAssetDefinition(final KJsonValue value, final KJsonValidator schema) {
        if (value.getType() != KJsonValueType.OBJECT) {
            throw new KAssetDefinitionError(
                "Cannot create asset definition: provided json value is not an object"
            );
        }

        schema.validate(value);
        this.value = value;
    }

    private KJsonAssetDefinition(final KJsonValue value) {
        if (value.getType() != KJsonValueType.OBJECT) {
            throw new KAssetDefinitionError(
                "Cannot create asset definition: provided json value is not an object"
            );
        }

        this.value = value;
    }

    @Override
    public int getInt(final String property) {
        return value.getProperty(property).getInt();
    }

    @Override
    public float getFloat(final String property) {
        return value.getProperty(property).getFloat();
    }

    @Override
    public boolean getBoolean(final String property) {
        return value.getProperty(property).getBoolean();
    }

    @Override
    public @Nullable String getString(final String property) {
        var propertyValue = value.getProperty(property);

        if (propertyValue == null) {
            throw KAssetDefinitionError.propertyNotFound(property);
        }

        return propertyValue.getString();
    }

    @Override
    public KAssetDefinition getSubdefinition(final String property) {
        return new KJsonAssetDefinition(value.getProperty(property));
    }

    @Override
    public int[] getIntArray(final String property) {
        KJsonValue propertyValue = this.value.getProperty(property);
        if (propertyValue == null) {
            throw KAssetDefinitionError.propertyNotFound(property);
        }
        List<KJsonValue> list = propertyValue.getList();
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getInt();
        }

        return array;
    }

    @Override
    public float[] getFloatArray(final String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getFloat();
        }

        return array;
    }

    @Override
    public boolean[] getBooleanArray(final String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        boolean[] array = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getBoolean();
        }

        return array;
    }

    @Override
    public String @Nullable[] getStringArray(final String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = Objects.requireNonNull(list.get(i).getString());
        }

        return array;
    }

    @Override
    public KAssetDefinition[] getSubdefinitionArray(final String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        KAssetDefinition[] array = new KAssetDefinition[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = new KJsonAssetDefinition(list.get(i));
        }

        return array;
    }

    @Override
    public boolean hasInt(final String property) {
        return this.hasProperty(property, KJsonValueType.NUMBER_INT);
    }

    @Override
    public boolean hasFloat(final String property) {
        return this.hasProperty(property, KJsonValueType.NUMBER_FLOAT);
    }

    @Override
    public boolean hasBoolean(final String property) {
        return this.hasProperty(property, KJsonValueType.BOOLEAN);
    }

    @Override
    public boolean hasString(final String property) {
        return this.hasProperty(property, KJsonValueType.STRING);
    }

    @Override
    public boolean hasSubdefinition(final String property) {
        return this.hasProperty(property, KJsonValueType.OBJECT);
    }

    @Override
    public boolean hasIntArray(final String property) {
        boolean flag = this.hasProperty(property, KJsonValueType.ARRAY);
        if (!flag) {
            return false;
        }

        try {
            this.getIntArray(property);
            return true;
        } catch (KJsonValueException e) {
            return false;
        }
    }

    @Override
    public boolean hasFloatArray(final String property) {
        boolean flag = this.hasProperty(property, KJsonValueType.ARRAY);
        if (!flag) {
            return false;
        }

        try {
            this.getFloatArray(property);
            return true;
        } catch (KJsonValueException e) {
            return false;
        }
    }

    @Override
    public boolean hasBooleanArray(final String property) {
        boolean flag = this.hasProperty(property, KJsonValueType.ARRAY);
        if (!flag) {
            return false;
        }

        try {
            this.getBooleanArray(property);
            return true;
        } catch (KJsonValueException e) {
            return false;
        }
    }

    @Override
    public boolean hasStringArray(final String property) {
        boolean flag = this.hasProperty(property, KJsonValueType.ARRAY);
        if (!flag) {
            return false;
        }

        try {
            this.getStringArray(property);
            return true;
        } catch (KJsonValueException e) {
            return false;
        }
    }

    @Override
    public boolean hasSubdefinitionArray(final String property) {
        boolean flag = this.hasProperty(property, KJsonValueType.ARRAY);
        if (!flag) {
            return false;
        }

        try {
            this.getSubdefinitionArray(property);
            return true;
        } catch (KJsonValueException | KAssetDefinitionError e) {
            return false;
        }
    }

    private boolean hasProperty(final String property, final KJsonValueType type) {

        boolean flag = this.value.hasProperty(property);
        if (!flag) {
            return false;
        }

        return this.value.getProperty(property).getType() == type;
    }

    @Override
    public <T extends Enum<T>> T getEnum(
        final String property,
        final Class<T> enumClass
    ) {
        String rawValue = this.getString(property);
        if (rawValue == null) {
            throw new KAssetDefinitionError(
                String.format(
                    "Value of property %s is null",
                    property
                )
            );
        }

        try {
            return Enum.valueOf(enumClass, rawValue);
        } catch (IllegalArgumentException e) {
            throw new KAssetDefinitionError(e.getMessage());
        }
    }

    @Override
    public <T extends Enum<T>> boolean hasEnum(
        final String property,
        final Class<T> enumClass
    ) {
        try {
            this.getEnum(property, enumClass);
            return true;
        } catch (KAssetDefinitionError e) {
            return false;
        }
    }

    @Override
    public Class<?> getClassObject(
        final String property
    ) {
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
