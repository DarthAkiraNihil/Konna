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

import java.util.*;
import java.util.function.Consumer;

/**
 * Representation of a Json value for different purposes.
 * It can contain a single value, array or an object with key-value pairs.
 * In the last case the json value provides read-write access through getting
 * and setting properties. Else the value is read-only
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KJsonValue implements Iterable<KJsonValue> {

    private final KJsonValueType type;
    private final Object containedValue;

    /**
     * Default constructor. Requires specification of concrete value type, and it may be a bit
     * difficult to specify it in all cases when you need this,
     * from* methods should be used instead.
     * Type checking is performed when an object is being created, so it is required for
     * object to represent the passed type. If types does not match then {@link KJsonValueException}
     * will be thrown, however, if passed value is null
     * or passed type is {@link KJsonValueType#NULL} then type check is not performed and type
     * will be set to {@link KJsonValueType#NULL} in any case.
     * @param type Type of json value
     * @param value The value itself
     */
    public KJsonValue(final KJsonValueType type, final Object value) {
        if (value == null || type == KJsonValueType.NULL) {
            this.type = KJsonValueType.NULL;
        } else {
            KJsonValueType checked = KJsonValueType.fromObject(value);
            if (type != checked) {
                throw new KJsonValueException(
                    String.format(
                        "Type mismatch: attempted to create %s but got %s (%s)",
                        type,
                        checked,
                        value
                    )
                );
            }
            this.type = type;
        }
        this.containedValue = value;
    }

    /**
     * Constructs a number value from integer.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(int value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from long.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(long value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from byte.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(byte value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from short.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(short value) {
        return new KJsonValue(KJsonValueType.NUMBER_INT, value);
    }

    /**
     * Constructs a number value from float.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(float value) {
        return new KJsonValue(KJsonValueType.NUMBER_FLOAT, value);
    }

    /**
     * Constructs a number value from value.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromNumber(double value) {
        return new KJsonValue(KJsonValueType.NUMBER_FLOAT, value);
    }

    /**
     * Constructs a boolean value from boolean.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromBoolean(boolean value) {
        return new KJsonValue(KJsonValueType.BOOLEAN, value);
    }

    /**
     * Constructs a string value from string.
     * @param value The value itself
     * @return Constructed json value
     */
    public static KJsonValue fromString(final String value) {
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
     * Constructs an array value from list.
     * @param list The list itself
     * @return Constructed json value
     */
    public static KJsonValue fromList(final List<KJsonValue> list) {
        return new KJsonValue(KJsonValueType.ARRAY, list);
    }

    /**
     * Constructs an object value from map.
     * @param map The map itself
     * @return Constructed json value
     */
    public static KJsonValue fromMap(final Map<String, KJsonValue> map) {
        return new KJsonValue(KJsonValueType.OBJECT, map);
    }

    /**
     * Returns type of json value.
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
        return this.containedValue == null || this.type == KJsonValueType.NULL;
    }

    /**
     * Returns an iterator for iterating over array elements. Pay attention to the fact it may
     * throw {@link KJsonValueException} if the value type differs from {@link KJsonValueType}.ARRAY
     * @return The iterator for json array
     */
    @Override
    @SuppressWarnings("unchecked")
    public Iterator<KJsonValue> iterator() {
        if (this.type != KJsonValueType.ARRAY) {
            throw new KJsonValueException(
                String.format(
                    "Cannot iterate over json value: it's not an array. The actual type is: %s",
                    this.type
                )
            );
        }

        return ((Iterable<KJsonValue>) this.containedValue).iterator();
    }

    /**
     * Returns an action for each element of the array. Pay attention to the fact it may
     * throw {@link KJsonValueException} if the value type differs from {@link KJsonValueType}.ARRAY
     */
    @Override
    @SuppressWarnings("unchecked")
    public void forEach(final Consumer<? super KJsonValue> action) {
        if (this.type != KJsonValueType.ARRAY) {
            throw new KJsonValueException(
                String.format(
                        "Cannot apply forEach to a json value: it's not an array. "
                    +   "The actual type is: %s",
                    this.type
                )
            );
        }

        ((Iterable<KJsonValue>) this.containedValue).forEach(action);
    }

    /**
     * Returns a spliterator for iterating over array elements. Pay attention to the fact it may
     * throw {@link KJsonValueException} if the value type differs from {@link KJsonValueType}.ARRAY
     * @return The iterator for json array
     */
    @Override
    @SuppressWarnings("unchecked")
    public Spliterator<KJsonValue> spliterator() {
        if (this.type != KJsonValueType.ARRAY) {
            throw new KJsonValueException(
                String.format(
                        "Cannot get spliterator from a json value: it's not an array. "
                    +   "The actual type is: %s",
                    this.type
                )
            );
        }

        return ((Iterable<KJsonValue>) this.containedValue).spliterator();
    }

    /**
     * Returns all json object key-value entries.
     * If the value is not an object, {@link KJsonValueException} will be thrown
     * @return Set of key-value entries of the object
     */
    @SuppressWarnings("unchecked")
    public Set<Map.Entry<String, KJsonValue>> entrySet() {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format(
                    "Cannot iterate over json object: it's not an object. The actual type is: %s",
                    this.type
                )
            );
        }

        return ((Map<String, KJsonValue>) this.containedValue).entrySet();
    }

    /**
     * Returns the json value with specified key of a json object. If the value is not an object,
     * {@link KJsonValueException} will be thrown
     * @param key The key of the property
     * @return The value assigned to the key
     */
    @SuppressWarnings("unchecked")
    public KJsonValue getProperty(final String key) {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format(
                        "Cannot get property from the json value: it's not an object."
                    +   "The actual type is: %s",
                    this.type
                )
            );
        }

        return ((Map<String, KJsonValue>) this.containedValue).get(key);
    }

    /**
     * Returns status of object key containment of json value. If the value is not an object,
     * {@link KJsonValueException} will be thrown
     * @param key The key of the property
     * @return true if the object contains specified property
     */
    @SuppressWarnings("unchecked")
    public boolean hasProperty(final String key) {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format(
                        "Cannot get information of property containment in the json value:"
                    +   "it's not an object. The actual type is: %s",
                    this.type
                )
            );
        }

        return ((Map<String, KJsonValue>) this.containedValue).containsKey(key);
    }

    /**
     * Sets a json value with specified key as a property of the json object.
     * If the json value is not an object, {@link KJsonValueException} will be thrown.
     * Overwrites value if the property specified with key already existed in the object.
     * @param key The key of the property
     * @param value Value to be set
     * @since 0.2.0
     */
    @SuppressWarnings("unchecked")
    public void setProperty(final String key, final KJsonValue value) {
        if (this.type != KJsonValueType.OBJECT) {
            throw new KJsonValueException(
                String.format(
                    "Cannot set property to the json value:"
                        +   "it's not an object. The actual type is: %s",
                    this.type
                )
            );
        }

        ((Map<String, KJsonValue>) this.containedValue).put(key, value);
    }

    private void checkTypeMatch(final KJsonValueType requested) {
        if (this.type != requested) {
            throw new KJsonValueException(requested, this.type);
        }
    }

    /**
     * Returns json value as a boolean.
     * @return Boolean representation of a value.
     * @see KJsonValueException
     */
    public boolean getBoolean() {
        this.checkTypeMatch(KJsonValueType.BOOLEAN);

        return (boolean) this.containedValue;
    }

    /**
     * Returns json value as an int.
     * @return Int representation of a value.
     * @see KJsonValueException
     */
    public int getInt() {
        this.checkTypeMatch(KJsonValueType.NUMBER_INT);

        return (int) this.containedValue;
    }

    /**
     * Returns json value as a long.
     * @return Long representation of a value.
     * @see KJsonValueException
     */
    public long getLong() {
        this.checkTypeMatch(KJsonValueType.NUMBER_INT);

        return (long) this.containedValue;
    }

    /**
     * Returns json value as a byte.
     * @return Byte representation of a value.
     * @see KJsonValueException
     */
    public byte getByte() {
        this.checkTypeMatch(KJsonValueType.NUMBER_INT);

        return (byte) this.containedValue;
    }

    /**
     * Returns json value as a short.
     * @return Short representation of a value.
     * @see KJsonValueException
     */
    public short getShort() {
        this.checkTypeMatch(KJsonValueType.NUMBER_INT);

        return (short) this.containedValue;
    }

    /**
     * Returns json value as a float.
     * @return Float representation of a value.
     * @see KJsonValueException
     */
    public float getFloat() {
        this.checkTypeMatch(KJsonValueType.NUMBER_FLOAT);

        return (float) this.containedValue;
    }

    /**
     * Returns json value as a double.
     * @return Double representation of a value.
     * @see KJsonValueException
     */
    public double getDouble() {
        this.checkTypeMatch(KJsonValueType.NUMBER_FLOAT);

        return (double) this.containedValue;
    }

    /**
     * Returns json value as a char.
     * Throws {@link KJsonValueException} if the string representation of char
     * has more than one symbol in length
     * @return Char representation of a value
     * @see KJsonValueException
     */
    public char getChar() {
        if (this.type != KJsonValueType.STRING) {
            throw new KJsonValueException(
                String.format(
                        "Cannot get char from the json value: it's not a string. "
                    +   "The actual type is: %s",
                    this.type
                )
            );
        }

        if (this.isNull()) {
            throw new KJsonValueException(
                "Cannot get char value from the json value: the value is null"
            );
        }

        String string = (String) this.containedValue;
        if (string.length() > 1) {
            throw new KJsonValueException(
                    "Cannot get char value from the json value: "
                +   "the string has more than 1 symbol in length!"
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
        this.checkTypeMatch(KJsonValueType.STRING);

        if (this.isNull()) {
            return null;
        }

        return (String) this.containedValue;
    }

    @SuppressWarnings("unchecked")
    public List<KJsonValue> getList() {
        this.checkTypeMatch(KJsonValueType.ARRAY);

        if (this.isNull()) {
            return null;
        }

        return (List<KJsonValue>) this.containedValue;
    }

    /**
     * Returns raw json value without casting to any type.
     * It is useful for different test purposes,
     * however it is not limited, though you need to cast it to required time yourself
     * @return Raw json value represented by {@link java.lang.Object}
     */
    public Object getRawObject() {
        return this.containedValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KJsonValue that = (KJsonValue) o;
        return this.type == that.type && Objects.equals(this.containedValue, that.containedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.containedValue);
    }

    @Override
    public String toString() {
        return String.format("KJsonValue[%s]{%s}", this.type, this.containedValue.getClass());
    }

}
