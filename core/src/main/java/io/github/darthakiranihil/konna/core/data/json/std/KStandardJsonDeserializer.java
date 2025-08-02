package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import sun.misc.Unsafe; //! I don't like this but there is no other way

import java.lang.reflect.Field;
import java.util.*;

public class KStandardJsonDeserializer implements KJsonDeserializer {

    private static Unsafe theUnsafe;

    public KStandardJsonDeserializer() {

        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            KStandardJsonDeserializer.theUnsafe = (Unsafe) theUnsafeField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new KJsonSerializationException();
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(KJsonValue value, Class<?> clazz) {

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
                return (T) value.getString();
            }
            case ARRAY -> {
                //todo annotation to get array element type
                List<?> list = new ArrayList<>();

                return (T) list;
            }
            case OBJECT -> {

                try {

                    var deserialized = KStandardJsonDeserializer.theUnsafe.allocateInstance(clazz);

                    for (var entry: value.entrySet()) {

                        Field field = clazz.getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        field.set(deserialized, this.deserialize(entry.getValue(), field.getType()));

                    }

                    return (T) deserialized;
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    throw new KJsonSerializationException(e);
                }
            }
        }

        return null;
    }
}
