package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonSerializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;

import java.lang.reflect.Field;
import java.util.*;

public class KStandardJsonSerializer implements KJsonSerializer {

    private <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @Override
    public <T> KJsonValue serialize(T object, Class<? extends T> clazz) {
        if (clazz == Integer.class || clazz == int.class) {
            return KJsonValue.fromNumber((int) object);
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

            String fieldName = field.getName();
            var fieldType = field.getType();

            field.setAccessible(true);

            try {
                objectData.put(fieldName, this.serialize(field.get(object), fieldType));
            } catch (IllegalAccessException e) {
                throw new KJsonSerializationException(e);
            }

        }

        return KJsonValue.fromMap(objectData);

    }
}
