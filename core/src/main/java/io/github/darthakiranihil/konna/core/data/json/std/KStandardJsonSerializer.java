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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Standard implementation of {@link KJsonSerializer}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KStandardJsonSerializer implements KJsonSerializer {

    private <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = t.getClass();
        while (clazz != Object.class) {
            for (var field: clazz.getDeclaredFields()) {

                if (field.isAnnotationPresent(KJsonIgnored.class)) {
                    continue;
                }

                if (Modifier.isPrivate(field.getModifiers()) && !field.isAnnotationPresent(KJsonSerialized.class)) {
                    continue;
                }

                fields.add(field);

            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @Override
    public <T> KJsonValue serialize(T object, Class<? extends T> clazz) throws KJsonSerializationException {
        if (clazz == Integer.class || clazz == int.class) {
            return KJsonValue.fromNumber((int) object);
        }

        if (clazz == Byte.class || clazz == byte.class) {
            return KJsonValue.fromNumber((byte) object);
        }

        if (clazz == Short.class || clazz == short.class) {
            return KJsonValue.fromNumber((short) object);
        }

        if (clazz == Long.class || clazz == long.class) {
            return KJsonValue.fromNumber((long) object);
        }

        if (clazz == Float.class || clazz == float.class) {
            return KJsonValue.fromNumber((float) object);
        }

        if (clazz == Double.class || clazz == double.class) {
            return KJsonValue.fromNumber((double) object);
        }

        if (clazz == Boolean.class || clazz == boolean.class) {
            return KJsonValue.fromBoolean((boolean) object);
        }

        if (clazz == Character.class || clazz == char.class) {
            return KJsonValue.fromChar((char) object);
        }

        if (clazz == String.class) {
            return KJsonValue.fromString((String) object);
        }

        if (List.class.isAssignableFrom(clazz)) {
            List<KJsonValue> list = new LinkedList<>();
            for (var element: (List<?>) object) {
                list.add(this.serialize(element, element.getClass()));
            }
            return KJsonValue.fromList(list);
        }

        if (Map.class.isAssignableFrom(clazz)) {
            Map<String, KJsonValue> map = new HashMap<>();
            for (var entry: ((Map<?, ?>) object).entrySet()) {
                var value = entry.getValue();
                map.put(
                    entry.getKey().toString(),
                    this.serialize(value, value.getClass())
                );
            }
            return KJsonValue.fromMap(map);
        }

        HashMap<String, KJsonValue> objectData = new HashMap<>();
        List<Field> fields = this.getFields(object);

        for (var field: fields) {

            var fieldType = field.getType();
            field.setAccessible(true);

            try {
                String fieldName;
                if (field.isAnnotationPresent(KJsonCustomName.class)) {
                    KJsonCustomName meta = field.getAnnotation(KJsonCustomName.class);
                    fieldName = meta.name();
                } else {
                    fieldName = field.getName();
                }

                objectData.put(fieldName, this.serialize(field.get(object), fieldType));
            } catch (IllegalAccessException e) {
                throw new KJsonSerializationException(e);
            }

        }

        return KJsonValue.fromMap(objectData);

    }
}
