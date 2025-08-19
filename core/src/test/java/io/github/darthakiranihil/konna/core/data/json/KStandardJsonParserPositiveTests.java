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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.core.util.KPair;
import io.github.darthakiranihil.konna.core.util.KTriplet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

public class KStandardJsonParserPositiveTests extends KStandardTestClass {

    private final KJsonParser parser;

    private static final List<KTriplet<String, KJsonValueType, Object>> dataForSimpleTests = List.of(
        new KTriplet<>("123", KJsonValueType.NUMBER_INT, 123),
        new KTriplet<>("123.0", KJsonValueType.NUMBER_FLOAT, 123.0f),
        new KTriplet<>("\"123\"", KJsonValueType.STRING, "123"),
        new KTriplet<>("true", KJsonValueType.BOOLEAN, true),
        new KTriplet<>("false", KJsonValueType.BOOLEAN, false),
        new KTriplet<>("null", KJsonValueType.NULL, null)
    );

    private static final List<KPair<KJsonValueType, Object>> arrayMatchList = List.of(
        new KPair<>(KJsonValueType.NUMBER_INT, 123),
        new KPair<>(KJsonValueType.STRING, "OLOLO"),
        new KPair<>(KJsonValueType.NUMBER_FLOAT, 123.0f),
        new KPair<>(KJsonValueType.BOOLEAN, true),
        new KPair<>(KJsonValueType.BOOLEAN, false),
        new KPair<>(KJsonValueType.NULL, null)
    );

    public KStandardJsonParserPositiveTests() {
        this.parser = KStandardTestClass.jsonParser;
    }

    private void simpleTest(String input, KJsonValueType expectedType, Object expectedValue) {
        try {

            KJsonValue parsed = this.parser.parse(input);

            Assertions.assertEquals(expectedType, parsed.getType());
            Assertions.assertEquals(expectedValue, parsed.getRawObject());

        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testParseSimpleValue() {

        for (var data: KStandardJsonParserPositiveTests.dataForSimpleTests) {

            this.simpleTest(data.first(), data.second(), data.third());

        }

    }

    @Test
    public void testParseEmptyArray() {

        try {

            KJsonValue parsed = this.parser.parse("[]");

            Assertions.assertEquals(KJsonValueType.ARRAY, parsed.getType());
            int i = 0;
            for (var it = parsed.iterator(); it.hasNext();) {
                it.next();
                i++;
            }

            Assertions.assertEquals(0, i);

        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testParseEmptyObject() {

        try {

            KJsonValue parsed = this.parser.parse("{}");

            Assertions.assertEquals(KJsonValueType.OBJECT, parsed.getType());
            int i = 0;
            for (var ignored : parsed.entrySet()) {
                i++;
            }

            Assertions.assertEquals(0, i);

        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testParseValidObject() {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer();
        KJsonParser parser = new KStandardJsonParser(tokenizer);

        try {
            KJsonValue result = parser.parse("{\"test\": 123, \"aboba\": []}");

            Assertions.assertEquals(KJsonValueType.OBJECT, result.getType());
            Assertions.assertTrue(result.hasProperty("test"));
            Assertions.assertTrue(result.hasProperty("aboba"));

            Assertions.assertEquals(123, result.getProperty("test").getInt());
            Assertions.assertEquals(KJsonValueType.ARRAY, result.getProperty("aboba").getType());
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }


    }

    @Test
    public void testParseValidArray() {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer();
        KJsonParser parser = new KStandardJsonParser(tokenizer);

        try {
            KJsonValue result = parser.parse("[123, \"OLOLO\", 123.0, true, false, null]");

            Assertions.assertEquals(KJsonValueType.ARRAY, result.getType());

            int i = 0;
            for (Iterator<KJsonValue> it = result.iterator(); it.hasNext();) {
                KJsonValue entry = it.next();

                var match = KStandardJsonParserPositiveTests.arrayMatchList.get(i);
                Assertions.assertEquals(match.first(), entry.getType());
                Assertions.assertEquals(match.second(), entry.getRawObject());

                i++;
            }
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testParseValidArrayOfObject() {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer();
        KJsonParser parser = new KStandardJsonParser(tokenizer);

        try {
            KJsonValue result = parser.parse("[{}]");

            Assertions.assertEquals(KJsonValueType.ARRAY, result.getType());

            for (Iterator<KJsonValue> it = result.iterator(); it.hasNext();) {
                KJsonValue entry = it.next();

                Assertions.assertEquals(KJsonValueType.OBJECT, entry.getType());
            }
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testParseValidArrayOfArray() {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer();
        KJsonParser parser = new KStandardJsonParser(tokenizer);

        try {
            KJsonValue result = parser.parse("[[]]");

            Assertions.assertEquals(KJsonValueType.ARRAY, result.getType());

            for (Iterator<KJsonValue> it = result.iterator(); it.hasNext();) {
                KJsonValue entry = it.next();

                Assertions.assertEquals(KJsonValueType.ARRAY, entry.getType());
            }
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

}
