package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.core.util.KPair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class KStandardJsonStringifierPositiveTests extends KStandardTestClass {

    private final KJsonStringifier stringifier;
    private static final int TEST_INDENT = 1;

    public KStandardJsonStringifierPositiveTests() {

        this.stringifier = KStandardTestClass.jsonStringifier;

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
        ), "[1,2,3]"),
        new KPair<>(KJsonValue.fromMap(
            new LinkedHashMap<>(Map.of(
                "123", KJsonValue.fromNumber(1),
                "124", KJsonValue.fromNumber(2)
            ))
        ), "{\"123\":1,\"124\":2}")
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
        ), "[\n 1,\n 2,\n 3\n]"),
        new KPair<>(KJsonValue.fromMap(
            new LinkedHashMap<>(Map.of(
                "123", KJsonValue.fromNumber(1),
                "124", KJsonValue.fromNumber(2)
            ))
        ), "{\n \"123\": 1,\n \"124\": 2\n}"),
        new KPair<>(KJsonValue.fromMap(
            new LinkedHashMap<>(Map.of(
                "k1", KJsonValue.fromList(
                    List.of(
                        KJsonValue.fromNumber(1),
                        KJsonValue.fromNumber(2),
                        KJsonValue.fromNumber(3)
                    )
                ),
                "k2", KJsonValue.fromMap(
                    new LinkedHashMap<>(Map.of(
                        "123", KJsonValue.fromNumber(1),
                        "124", KJsonValue.fromNumber(2)
                    ))
                )
            ))
        ), "{\n \"k1\": [\n  1,\n  2,\n  3\n ],\n \"k2\": {\n  \"123\": 1,\n  \"124\": 2\n }\n}")
    );

    @Test
    public void testSimpleStringify() {

        for (var data: KStandardJsonStringifierPositiveTests.simpleStringifyTestData) {

            Assertions.assertEquals(
                data.getSecond(),
                this.stringifier.stringify(data.getFirst())
            );

        }

    }

    @Test
    public void testPrettyStringify() {

        for (var data: KStandardJsonStringifierPositiveTests.prettyStringifyTestData) {

            Assertions.assertEquals(
                data.getSecond(),
                this.stringifier.stringify(data.getFirst(), KStandardJsonStringifierPositiveTests.TEST_INDENT)
            );

        }

    }
}
