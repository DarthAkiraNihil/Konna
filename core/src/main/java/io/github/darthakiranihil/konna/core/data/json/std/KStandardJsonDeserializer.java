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

package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import sun.misc.Unsafe; //! I don't like this but there is no other way

import java.lang.reflect.*;
import java.util.*;

/**
 * Standard implementation of {@link KJsonDeserializer}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardJsonDeserializer extends KObject implements KJsonDeserializer {

    private static Unsafe theUnsafe;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            KStandardJsonDeserializer.theUnsafe = (Unsafe) theUnsafeField.get(null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public KStandardJsonDeserializer() {
        super("std_json_deserializer", KStructUtils.setOfTags(KTag.DefaultTags.STD));
    }

    /**
     * Deserializes a json value into a new object of passed type.
     * This implementation requires that all list-like fields are provided
     * with {@link KJsonArray} annotation, since type erasure does not allow
     * to get list generic type at runtime
     * @param value Json value to deserialize
     * @param clazz Class of destination object
     * @return Deserialized object
     * @param <T> Generic type of deserialized object
     * @throws KJsonSerializationException If it fails to deserialize, mostly because
     * of attempting to deserialize object with structure that differs from json value
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(
        final KJsonValue value,
        final Class<?> clazz
    ) throws KJsonSerializationException {

        KJsonValueType valueType = value.getType();

        switch (valueType) {
            case NUMBER_INT -> {
                return (T) (Integer) value.getInt();
            }
            case NUMBER_FLOAT -> {
                return (T) (Float) value.getFloat();
            }
            case NULL -> {
                return null;
            }
            case BOOLEAN -> {
                return (T) (Boolean) value.getBoolean();
            }
            case STRING -> {
                String val = value.getString();
                if (Class.class.isAssignableFrom(clazz)) {
                    try {
                        return (T) Class.forName(val);
                    } catch (ClassNotFoundException e) {
                        throw new KJsonSerializationException(e);
                    }
                }

                return (T) val;
            }
            case ARRAY -> {
                List<?> list = new ArrayList<>();

                for (KJsonValue entry : value) {
                    list.add(this.deserialize(entry, clazz));
                }

                return (T) list;
            }
            case OBJECT -> {

                try {

                    var deserialized = KStandardJsonDeserializer.theUnsafe.allocateInstance(clazz);

                    for (var entry: value.entrySet()) {

                        if (clazz.isRecord()) {
                            deserialized = this.setRecordField(
                                clazz,
                                entry.getKey(),
                                entry.getValue(),
                                deserialized
                            );
                        } else {
                            this.setObjectField(
                                clazz,
                                entry.getKey(),
                                entry.getValue(),
                                deserialized
                            );
                        }

                    }

                    return (T) deserialized;
                } catch (
                        InstantiationException
                    |   IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException
                    |   NoSuchFieldException e
                ) {
                    throw new KJsonSerializationException(e);
                }
            }
        }

        return null;
    }

    private Field getField(final Class<?> clazz, final String name) throws NoSuchFieldException {
        Class<?> klass = clazz;
        while (klass != Object.class) {
            for (Field field : klass.getDeclaredFields()) {

                if (field.isAnnotationPresent(KJsonIgnored.class)) {
                    continue;
                }

                boolean isPrivateAndNotSerialized =
                        Modifier.isPrivate(field.getModifiers())
                    && !field.isAnnotationPresent(KJsonSerialized.class);

                if (isPrivateAndNotSerialized) {
                    continue;
                }


                if (field.isAnnotationPresent(KJsonCustomName.class)) {
                    KJsonCustomName meta = field.getAnnotation(KJsonCustomName.class);
                    if (meta.name().equals(name)) {
                        return field;
                    }
                }

                if (field.getName().equals(name)) {
                    return field;
                }
            }
            klass = klass.getSuperclass();
        }
        throw new NoSuchFieldException(name);
    }

    private boolean pointsToSameComponent(final RecordComponent recordComponent, final String name) {

        if (recordComponent.isAnnotationPresent(KJsonIgnored.class)) {
            return false;
        }

        if (recordComponent.isAnnotationPresent(KJsonCustomName.class)) {
            KJsonCustomName meta = recordComponent.getAnnotation(KJsonCustomName.class);
            if (meta.name().equals(name)) {
                return true;
            }
        }

        return recordComponent.getName().equals(name);
    }

    private void setObjectField(
        final Class<?> clazz,
        final String key,
        final KJsonValue value,
        final Object deserialized
    ) throws
        IllegalAccessException,
        NoSuchFieldException,
        KJsonSerializationException {
        Field field = this.getField(clazz, key);
        field.setAccessible(true);

        if (value.getType() == KJsonValueType.ARRAY) {
            if (!field.isAnnotationPresent(KJsonArray.class)) {
                throw new KJsonSerializationException(
                    String.format(
                        "Could not deserialize field %s, as it is an list-like"
                            +   "and the KJsonArray annotation is not provided",
                        field.getName()
                    )
                );
            }

            KJsonArray meta = field.getAnnotation(KJsonArray.class);
            field.set(
                deserialized,
                this.deserialize(value, meta.elementType())
            );
        } else {
            field.set(
                deserialized,
                this.deserialize(value, field.getType())
            );
        }
    }

    @SuppressWarnings("unchecked")
    private Object setRecordField(
        final Class<?> clazz,
        final String key,
        final KJsonValue value,
        final Object deserialized
    ) throws IllegalAccessException, NoSuchMethodException, KJsonSerializationException, InvocationTargetException, InstantiationException {
        var components = clazz.getRecordComponents();
        var params = new Object[components.length];
        for (int i = 0; i < components.length; i++) {
            var component = components[i];
            if (this.pointsToSameComponent(component, key)) {
                if (value.getType() == KJsonValueType.ARRAY) {
                    if (!component.isAnnotationPresent(KJsonArray.class)) {
                        throw new KJsonSerializationException(
                            String.format(
                                "Could not deserialize field %s, as it is an list-like"
                                    +   "and the KJsonArray annotation is not provided",
                                component.getName()
                            )
                        );
                    }

                    KJsonArray meta = component.getAnnotation(KJsonArray.class);
                    params[i] = this.deserialize(value, meta.elementType());
                } else {
                    params[i] = this.deserialize(value, component.getType());
                }
            } else {
                params[i] = component.getAccessor().invoke(deserialized);
            }
        }
        return clazz.getConstructors()[0].newInstance(params);

    }

}
