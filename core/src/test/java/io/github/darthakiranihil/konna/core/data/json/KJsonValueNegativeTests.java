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
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class KJsonValueNegativeTests extends KStandardTestClass {

    @Test
    public void testGetValueOfIncompatibleType() {

        KJsonValue floatValue = KJsonValue.fromNumber(0.0f);
        KJsonValue intValue = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, floatValue::getInt);
        Assertions.assertThrowsExactly(KJsonValueException.class, floatValue::getByte);
        Assertions.assertThrowsExactly(KJsonValueException.class, floatValue::getShort);
        Assertions.assertThrowsExactly(KJsonValueException.class, floatValue::getLong);
        Assertions.assertThrowsExactly(KJsonValueException.class, floatValue::getString);

        Assertions.assertThrowsExactly(KJsonValueException.class, intValue::getFloat);
        Assertions.assertThrowsExactly(KJsonValueException.class, intValue::getDouble);
        Assertions.assertThrowsExactly(KJsonValueException.class, intValue::getBoolean);

    }

    @Test
    public void testGetInvalidChar() {

        KJsonValue nonStringChar = KJsonValue.fromNumber(1);
        KJsonValue nullChar = KJsonValue.fromString(null);
        KJsonValue longChar = KJsonValue.fromString("12");

        try {
            Field nullCharContainedField = KJsonValue.class.getDeclaredField("containedValue");
            Field nullCharType = KJsonValue.class.getDeclaredField("type");
            nullCharContainedField.setAccessible(true);
            nullCharType.setAccessible(true);
            nullCharContainedField.set(nullChar, null);
            nullCharType.set(nullChar, KJsonValueType.STRING);
        } catch (Throwable e) {
            Assertions.fail(e);
        }

        Exception thrownNonString = Assertions.assertThrowsExactly(KJsonValueException.class, nonStringChar::getChar);
        Exception thrownNull = Assertions.assertThrowsExactly(KJsonValueException.class, nullChar::getChar);
        Exception thrownLong = Assertions.assertThrowsExactly(KJsonValueException.class, longChar::getChar);

        Assertions.assertEquals(String.format("Cannot get object from the json value: type mismatch.Requested: STRING, actual type: %s", KJsonValueType.NUMBER_INT), thrownNonString.getMessage());
        Assertions.assertEquals("Cannot get char value from the json value: the value is null", thrownNull.getMessage());
        Assertions.assertEquals("Cannot get char value from the json value: the string has more than 1 symbol in length!", thrownLong.getMessage());

    }

    @Test
    public void testCallObjectMethodsOfNonObject() {

        KJsonValue nonObject = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, () -> nonObject.getProperty("123"));
        Assertions.assertThrowsExactly(KJsonValueException.class, () -> nonObject.hasProperty("123"));
        Assertions.assertThrowsExactly(KJsonValueException.class, () -> nonObject.setProperty("123", KJsonValue.fromNumber(1)));
        Assertions.assertThrowsExactly(KJsonValueException.class, nonObject::entrySet);

    }

    @Test
    public void testGetIteratorOfNonArray() {

        KJsonValue nonArray = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, nonArray::iterator);

    }

    @Test
    public void testApplyForEachOfNonArray() {

        KJsonValue nonArray = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, () -> nonArray.forEach((_p) -> {}));

    }

    @Test
    public void testGetSpliteratorOfNonArray() {

        KJsonValue nonArray = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, nonArray::spliterator);

    }

    @Test
    public void testTypeMismatch() {

        Assertions.assertThrowsExactly(KJsonValueException.class, () -> new KJsonValue(
            KJsonValueType.NUMBER_INT,
            "123"
        ));

    }

    @Test
    public void testJsonValueTypeFromObjectOfUnknownType() {

        Assertions.assertThrows(
            KJsonValueException.class,
            () -> KJsonValueType.fromObject(this)
        );

    }

}
