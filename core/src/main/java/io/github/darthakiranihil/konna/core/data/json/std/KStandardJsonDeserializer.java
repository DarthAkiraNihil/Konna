package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import sun.misc.Unsafe; //! I don't like this but there is no other way
import sun.reflect.ReflectionFactory; //!

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class KStandardJsonDeserializer implements KJsonDeserializer {

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
    public <T> T deserialize(KJsonValue value, Class<?> clazz) {

        KJsonValueType valueType = value.getType();

        switch (valueType) {
            case NUMBER_INT -> {
                return (T) Integer.class.cast(value.getInt());
            }
            case NUMBER_FLOAT -> {
                return (T) Float.class.cast(value.getInt());
            }
            case NULL -> {
                return null;
            }
            case BOOLEAN -> {
                return (T) Boolean.class.cast(value.getBoolean());
            }
            case STRING -> {
                return (T) String.class.cast(value.getString());
            }
            case ARRAY -> {
                List<?> list = new ArrayList<>();

//                for (Iterator<KJsonValue> it = value.iterator(); it.hasNext(); ) {
//                    var entry = it.next();
//                    list.add(this.deserialize(entry, (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass())
//                        .getActualTypeArguments()[0]));
//                }

                return (T) list;
            }
            case OBJECT -> {

                try {

                    Field f = Unsafe.class.getDeclaredField("theUnsafe");
                    f.setAccessible(true);
                    Unsafe unsafe = (Unsafe) f.get(null);

                    // Allocate an instance without invoking the constructo

                    var deserialized = unsafe.allocateInstance(clazz);

                    for (var entry: value.entrySet()) {

                        Field field = clazz.getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        field.set(deserialized, this.deserialize(entry.getValue(), field.getType()));

                    }

                    return (T) deserialized;
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }


            }
        }

        return null;
    }
}
