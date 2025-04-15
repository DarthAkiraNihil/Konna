package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValueException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KJsonValue {

    private final KJsonValueType type;
    private final Object value;

    public KJsonValue(KJsonValueType type, Object value) {
        if (value == null) {
            this.type = KJsonValueType.NULL;
        } else {
            this.type = type;
        }
        this.value = value;
    }

    public static KJsonValue fromNumber(int value) {
        return new KJsonValue(KJsonValueType.NUMBER, value);
    }

    public static KJsonValue fromNumber(long value) {
        return new KJsonValue(KJsonValueType.NUMBER, value);
    }

    public static KJsonValue fromNumber(short value) {
        return new KJsonValue(KJsonValueType.NUMBER, value);
    }

    public static KJsonValue fromNumber(float value) {
        return new KJsonValue(KJsonValueType.NUMBER, value);
    }

    public static KJsonValue fromNumber(double value) {
        return new KJsonValue(KJsonValueType.NUMBER, value);
    }

    public static KJsonValue fromBoolean(boolean value) {
        return new KJsonValue(KJsonValueType.BOOLEAN, value);
    }

    public static KJsonValue fromList(List<KJsonValue> list) {
        return new KJsonValue(KJsonValueType.ARRAY, list);
    }

    public static KJsonValue fromMap(Map<String, KJsonValue> map) {
        return new KJsonValue(KJsonValueType.OBJECT, map);
    }

    public KJsonValueType getType() {
        return type;
    }

    public boolean isNull() {
        return this.value == null || this.type == KJsonValueType.NULL;
    }

    @SuppressWarnings("unchecked")
    public Iterator<KJsonValue> iterator() {
        if (this.type != KJsonValueType.ARRAY) {
            throw new KJsonValueException(
                String.format("Cannot iterate over json value: it's not an array. The actual type is: %s", this.type)
            );
        }

        return ((Iterable<KJsonValue>) this.value).iterator();
    }

    @SuppressWarnings("unchecked")
    public KJsonValue getProperty(String key) {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format("Cannot get property from the json value: it's not an object. The actual type is: %s", this.type)
            );
        }

        return ((Map<String, KJsonValue>) this.value).get(key);
    }

    public boolean getBoolean() {
        if (this.type != KJsonValueType.BOOLEAN) {
            throw new KJsonValueException(
                String.format("Cannot get boolean from the json value: it's not a boolean. The actual type is: %s", this.type)
            );
        }

        return (boolean) this.value;
    }

    public int getInt() {
        if (this.type != KJsonValueType.NUMBER) {
            throw new KJsonValueException(
                String.format("Cannot get int from the json value: it's not an int. The actual type is: %s", this.type)
            );
        }

        return (int) this.value;
    }

    public long getLong() {
        if (this.type != KJsonValueType.NUMBER) {
            throw new KJsonValueException(
                String.format("Cannot get long from the json value: it's not a long. The actual type is: %s", this.type)
            );
        }

        return (long) this.value;
    }

    public short getShort() {
        if (this.type != KJsonValueType.NUMBER) {
            throw new KJsonValueException(
                String.format("Cannot get short from the json value: it's not a short. The actual type is: %s", this.type)
            );
        }

        return (short) this.value;
    }

    public float getFloat() {
        if (this.type != KJsonValueType.NUMBER) {
            throw new KJsonValueException(
                String.format("Cannot get float from the json value: it's not a float. The actual type is: %s", this.type)
            );
        }

        return (float) this.value;
    }

    public double getDouble() {
        if (this.type != KJsonValueType.NUMBER) {
            throw new KJsonValueException(
                String.format("Cannot get double from the json value: it's not a double. The actual type is: %s", this.type)
            );
        }

        return (double) this.value;
    }

    public String getString() {
        if (this.type != KJsonValueType.STRING) {
            throw new KJsonValueException(
                String.format("Cannot get string from the json value: it's not a string. The actual type is: %s", this.type)
            );
        }

        if (this.isNull()) {
            return null;
        }

        return (String) this.value;
    }

    public KJsonValue getObject() {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format("Cannot get object from the json value: it's not an int. The actual type is: %s", this.type)
            );
        }

        if (this.isNull()) {
            return null;
        }

        return (KJsonValue) this.value;
    }



}
