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

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import io.github.darthakiranihil.konna.core.struct.KPair;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public class KStandardJsonStringifierPositiveTests extends KStandardTestClass {

    private final KJsonStringifier stringifier;
    private static final int TEST_INDENT = 1;

    public KStandardJsonStringifierPositiveTests() {

        this.stringifier = this.jsonStringifier;

    }

    private static final List<KPair<KJsonValue, String>> simpleStringifyTestData = List.of(
        new KPair<>(KJsonValue.fromNumber(0), "0"),
        new KPair<>(KJsonValue.fromNumber(0.0f), "0.0"),
        new KPair<>(KJsonValue.fromBoolean(true), "true"),
        new KPair<>(KJsonValue.fromMap(null), "null"),
        new KPair<>(KJsonValue.fromString("123"), "\"123\""),
        new KPair<>(KJsonValue.fromList(
            List.of(
                KJsonValue.fromNumber(1),
                KJsonValue.fromNumber(2),
                KJsonValue.fromNumber(3)
            )
        ), "[1,2,3]")
    );

    private static final List<KPair<KJsonValue, String>> prettyStringifyTestData = List.of(
        new KPair<>(KJsonValue.fromNumber(0), "0"),
        new KPair<>(KJsonValue.fromNumber(0.0f), "0.0"),
        new KPair<>(KJsonValue.fromBoolean(true), "true"),
        new KPair<>(KJsonValue.fromMap(null), "null"),
        new KPair<>(KJsonValue.fromString("123"), "\"123\""),
        new KPair<>(KJsonValue.fromList(
            List.of(
                KJsonValue.fromNumber(1),
                KJsonValue.fromNumber(2),
                KJsonValue.fromNumber(3)
            )
        ), "[\n 1,\n 2,\n 3\n]")
    );

    @Test
    public void testSimpleStringify() {

        for (var data: KStandardJsonStringifierPositiveTests.simpleStringifyTestData) {

            Assertions.assertEquals(
                data.second(),
                this.stringifier.stringify(data.first())
            );

        }

    }

    @Test
    public void testPrettyStringify() {

        for (var data: KStandardJsonStringifierPositiveTests.prettyStringifyTestData) {

            Assertions.assertEquals(
                data.second(),
                this.stringifier.stringify(data.first(), KStandardJsonStringifierPositiveTests.TEST_INDENT)
            );

        }

    }

    @Test
    public void testSimpleStringifyComplexValue() {

        Map<String, KJsonValue> source = new LinkedHashMap<>();
        source.put("123", KJsonValue.fromNumber(1));
        source.put("124", KJsonValue.fromNumber(2));

        Assertions.assertEquals("{\"123\":1,\"124\":2}", this.stringifier.stringify(KJsonValue.fromMap(source)));

    }

    @Test
    public void testPrettyStringifyComplexValue() {

        Map<String, KJsonValue> source1 = new LinkedHashMap<>();
        source1.put("123", KJsonValue.fromNumber(1));
        source1.put("124", KJsonValue.fromNumber(2));

        KJsonValue check1 = KJsonValue.fromMap(source1);

        Map<String, KJsonValue> source2 = new LinkedHashMap<>();
        source2.put("k1", KJsonValue.fromList(
            List.of(
                KJsonValue.fromNumber(1),
                KJsonValue.fromNumber(2),
                KJsonValue.fromNumber(3)
            )
        ));
        source2.put("k2", check1);

        KJsonValue check2 = KJsonValue.fromMap(source2);

        Assertions.assertEquals(
            "{\n \"123\": 1,\n \"124\": 2\n}",
            this.stringifier.stringify(check1, KStandardJsonStringifierPositiveTests.TEST_INDENT)
        );
        Assertions.assertEquals(
            "{\n \"k1\": [\n  1,\n  2,\n  3\n ],\n \"k2\": {\n  \"123\": 1,\n  \"124\": 2\n }\n}",
            this.stringifier.stringify(check2, KStandardJsonStringifierPositiveTests.TEST_INDENT)
        );

    }
}
