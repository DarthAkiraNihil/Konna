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

        Exception thrownNonString = Assertions.assertThrowsExactly(KJsonValueException.class, nonStringChar::getChar);
        Exception thrownNull = Assertions.assertThrowsExactly(KJsonValueException.class, nullChar::getChar);
        Exception thrownLong = Assertions.assertThrowsExactly(KJsonValueException.class, longChar::getChar);

        Assertions.assertEquals(String.format("Cannot get string from the json value: it's not a string. The actual type is: %s", KJsonValueType.NUMBER_INT), thrownNonString.getMessage());
        Assertions.assertEquals("Cannot get char value from the json value: the value is null", thrownNull.getMessage());
        Assertions.assertEquals("Cannot get char value from the json value: the string is more than 1 symbol in length!", thrownLong.getMessage());

    }

    @Test
    public void testCallObjectMethodsOfNonObject() {

        KJsonValue nonObject = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, () -> nonObject.getProperty("123"));
        Assertions.assertThrowsExactly(KJsonValueException.class, () -> nonObject.hasProperty("123"));
        Assertions.assertThrowsExactly(KJsonValueException.class, nonObject::entrySet);

    }

    @Test
    public void testGetIteratorOfNonArray() {

        KJsonValue nonArray = KJsonValue.fromNumber(0);

        Assertions.assertThrowsExactly(KJsonValueException.class, nonArray::iterator);

    }
}
