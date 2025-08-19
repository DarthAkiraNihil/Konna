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

package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValueException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Representation of a Json value for different purposes
 */
public class KJsonValue {

    private final KJsonValueType type;
    private final Object value;

    /**
     * Default constructor. Requires specification of concrete value type, and it may be a bit
     * difficult to specify it in all cases when you need this. from* methods should be used instead
     * @param type Type of json value
     * @param value The value itself
     */
    public KJsonValue(KJsonValueType type, Object value) {
        if (value == null && type != KJsonValueType.STRING) {
            this.type = KJsonValueType.NULL;
        } else {
            this.type = type;
        }
        this.value = value;
    }

    /**
     * Constructs a number value from integer
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(int value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from long
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(long value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from byte
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(byte value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from short
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(short value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from float
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(float value) {
        return new KJsonValue(KJsonValueType.NUMBER_FLOAT, value);
    }

    /**
     * Constructs a number value from value
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(double value) {
        return new KJsonValue(KJsonValueType.NUMBER_FLOAT, value);
    }

    /**
     * Constructs a boolean value from boolean
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromBoolean(boolean value) {
        return new KJsonValue(KJsonValueType.BOOLEAN, value);
    }

    /**
     * Constructs a string value from string
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromString(String value) {
        return new KJsonValue(KJsonValueType.STRING, value);
    }

    /**
     * Constructs a string value from char. The json value will be represented as string
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromChar(char value) {
        return new KJsonValue(KJsonValueType.STRING, String.valueOf(value));
    }

    /**
     * Constructs an array value from list
     * @param list The list itself
     * @return Constructed json value
     */
    public static KJsonValue fromList(List<KJsonValue> list) {
        return new KJsonValue(KJsonValueType.ARRAY, list);
    }

    /**
     * Constructs an object value from map
     * @param map The map itself
     * @return Constructed json value
     */
    public static KJsonValue fromMap(Map<String, KJsonValue> map) {
        return new KJsonValue(KJsonValueType.OBJECT, map);
    }

    /**
     * Returns type of json value
     * @return The type of the value
     */
    public KJsonValueType getType() {
        return this.type;
    }

    /**
     * Null-check. The value is null if its type is specified as NULL or its value is null
     * @return If the value is null
     */
    public boolean isNull() {
        return this.value == null || this.type == KJsonValueType.NULL;
    }

    /**
     * Returns an iterator for iterating over array elements. Pay attention to the fact it may
     * throw {@link KJsonValueException} if the value type differs from {@link KJsonValueType}.ARRAY
     * @return The iterator for json array
     */
    @SuppressWarnings("unchecked")
    public Iterator<KJsonValue> iterator() {
        if (this.type != KJsonValueType.ARRAY) {
            throw new KJsonValueException(
                String.format("Cannot iterate over json value: it's not an array. The actual type is: %s", this.type)
            );
        }

        return ((Iterable<KJsonValue>) this.value).iterator();
    }

    /**
     * Returns all json object key-value entries. If the value is not an object, {@link KJsonValueException} will be thrown
     * @return Set of key-value entries of the object
     */
    @SuppressWarnings("unchecked")
    public Set<Map.Entry<String, KJsonValue>> entrySet() {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format("Cannot iterate over json object: it's not an object. The actual type is: %s", this.type)
            );
        }

        return ((Map<String, KJsonValue>) this.value).entrySet();
    }

    /**
     * Returns the json value with specified key of a json object. If the value is not an object,
     * {@link KJsonValueException} will be thrown
     * @param key The key of the property
     * @return The value assigned to the key
     */
    @SuppressWarnings("unchecked")
    public KJsonValue getProperty(String key) {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format("Cannot get property from the json value: it's not an object. The actual type is: %s", this.type)
            );
        }

        return ((Map<String, KJsonValue>) this.value).get(key);
    }

    /**
     * Returns status of object key containment of json value. If the value is not an object,
     * {@link KJsonValueException} will be thrown
     * @param key The key of the property
     * @return true if the object contains specified property
     */
    @SuppressWarnings("unchecked")
    public boolean hasProperty(String key) {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format("Cannot get information of property containment in the json value: it's not an object. The actual type is: %s", this.type)
            );
        }

        return ((Map<String, KJsonValue>) this.value).containsKey(key);
    }

    /**
     * Returns json value as a boolean
     * @return Boolean representation of a value.
     * @see KJsonValueException
     */
    public boolean getBoolean() {
        if (this.type != KJsonValueType.BOOLEAN) {
            throw new KJsonValueException(
                String.format("Cannot get boolean from the json value: it's not a boolean. The actual type is: %s", this.type)
            );
        }

        return (boolean) this.value;
    }

    /**
     * Returns json value as an int
     * @return Int representation of a value.
     * @see KJsonValueException
     */
    public int getInt() {
        if (this.type != KJsonValueType.NUMBER_INT) {
            throw new KJsonValueException(
                String.format("Cannot get int from the json value: it's not an int. The actual type is: %s", this.type)
            );
        }

        return (int) this.value;
    }

    /**
     * Returns json value as a long
     * @return Long representation of a value.
     * @see KJsonValueException
     */
    public long getLong() {
        if (this.type != KJsonValueType.NUMBER_INT) {
            throw new KJsonValueException(
                String.format("Cannot get long from the json value: it's not a long. The actual type is: %s", this.type)
            );
        }

        return (long) this.value;
    }

    /**
     * Returns json value as a byte
     * @return Byte representation of a value.
     * @see KJsonValueException
     */
    public byte getByte() {
        if (this.type != KJsonValueType.NUMBER_INT) {
            throw new KJsonValueException(
                String.format("Cannot get short from the json value: it's not a short. The actual type is: %s", this.type)
            );
        }

        return (byte) this.value;
    }

    /**
     * Returns json value as a short
     * @return Short representation of a value.
     * @see KJsonValueException
     */
    public short getShort() {
        if (this.type != KJsonValueType.NUMBER_INT) {
            throw new KJsonValueException(
                String.format("Cannot get short from the json value: it's not a short. The actual type is: %s", this.type)
            );
        }

        return (short) this.value;
    }

    /**
     * Returns json value as a float
     * @return Float representation of a value.
     * @see KJsonValueException
     */
    public float getFloat() {
        if (this.type != KJsonValueType.NUMBER_FLOAT) {
            throw new KJsonValueException(
                String.format("Cannot get float from the json value: it's not a float. The actual type is: %s", this.type)
            );
        }

        return (float) this.value;
    }

    /**
     * Returns json value as a double
     * @return Double representation of a value.
     * @see KJsonValueException
     */
    public double getDouble() {
        if (this.type != KJsonValueType.NUMBER_FLOAT) {
            throw new KJsonValueException(
                String.format("Cannot get double from the json value: it's not a double. The actual type is: %s", this.type)
            );
        }

        return (double) this.value;
    }

    /**
     * Returns json value as a char. Throws {@link KJsonValueException} if the string representation of char
     * has more than one symbol in length
     * @return Char representation of a value
     * @see KJsonValueException
     */
    public char getChar() {
        if (this.type != KJsonValueType.STRING) {
            throw new KJsonValueException(
                String.format("Cannot get string from the json value: it's not a string. The actual type is: %s", this.type)
            );
        }

        if (this.isNull()) {
            throw new KJsonValueException(
                "Cannot get char value from the json value: the value is null"
            );
        }

        String string = (String) this.value;
        if (string.length() > 1) {
            throw new KJsonValueException(
                "Cannot get char value from the json value: the string is more than 1 symbol in length!"
            );
        }

        return string.charAt(0);
    }

    /**
     * Returns json value as a string.
     * @return String representation of a value or null if the contained value is null
     * @see KJsonValueException
     */
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

    /**
     * Returns raw json value without casting to any type. It is useful for different test purposes,
     * however it is not limited, though you need to cast it to required time yourself
     * @return Raw json value represented by {@link java.lang.Object}
     */
    public Object getRawObject() {
        return this.value;
    }

    /**
     * Returns json value as a object
     * @return Object representation of a value or null if the contained value is null
     * @see KJsonValueException
     */
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
