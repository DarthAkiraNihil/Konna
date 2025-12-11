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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@NullMarked
public class KStandardJsonTokenizerPositiveTests extends KStandardTestClass {

    private final static List<KPair<String, KJsonToken>> singleTokenTestData = Arrays.asList(
        new KPair<>("{", KJsonToken.OPEN_BRACE),
        new KPair<>("}", KJsonToken.CLOSE_BRACE),
        new KPair<>("[", KJsonToken.OPEN_SQUARE_BRACKET),
        new KPair<>("]", KJsonToken.CLOSE_SQUARE_BRACKET),
        new KPair<>(",", KJsonToken.COMMA),
        new KPair<>(":", KJsonToken.SEMICOLON),
        new KPair<>("true", KJsonToken.TRUE),
        new KPair<>("true ", KJsonToken.TRUE),
        new KPair<>("false\n", KJsonToken.FALSE),
        new KPair<>("null", KJsonToken.NULL),
        new KPair<>("null\t", KJsonToken.NULL),
        new KPair<>("", KJsonToken.EOF)
    );

    private final static List<KTriplet<String, KJsonToken, Object>> singleTokenWithValueTestData = Arrays.asList(
        new KTriplet<>("\"123\"", KJsonToken.STRING, "123"),
        new KTriplet<>("\"123\\\"123\"", KJsonToken.STRING, "123\\\"123"),
        // new KTriplet<>("\"\u1234\u1234ABOBA\"", KJsonToken.STRING, "\u1234\u1234ABOBA"),
        new KTriplet<>("\"\\uabcd\"", KJsonToken.STRING, "\\uabcd"),
        new KTriplet<>("123", KJsonToken.NUMBER_INT, 123),
        new KTriplet<>("+123", KJsonToken.NUMBER_INT, 123),
        new KTriplet<>("-123", KJsonToken.NUMBER_INT, -123),
        new KTriplet<>("123.3", KJsonToken.NUMBER_FLOAT, 123.3f),
        new KTriplet<>("+123.3", KJsonToken.NUMBER_FLOAT, 123.3f),
        new KTriplet<>("-123.3", KJsonToken.NUMBER_FLOAT, -123.3f),
        new KTriplet<>("123.3e+1", KJsonToken.NUMBER_FLOAT, 1233.0f),
        new KTriplet<>("123e+1", KJsonToken.NUMBER_FLOAT, 1230.0f),
        new KTriplet<>("123E+1", KJsonToken.NUMBER_FLOAT, 1230.0f),
        new KTriplet<>("+123.3e+1", KJsonToken.NUMBER_FLOAT, 1233.0f),
        new KTriplet<>("+123.3e+10", KJsonToken.NUMBER_FLOAT, 123.3e+10f),
        new KTriplet<>("-123.3e+1", KJsonToken.NUMBER_FLOAT, -1233.0f),
        new KTriplet<>("-123.3E+1", KJsonToken.NUMBER_FLOAT, -1233.0f),
        new KTriplet<>("-123.356e+1", KJsonToken.NUMBER_FLOAT, -1233.56f),
        new KTriplet<>("-123.356a", KJsonToken.NUMBER_FLOAT, -123.356f)
    );

    @SuppressWarnings("NullableProblems")
    private final static List<KPair<String, KJsonToken[]>> multiTokenTestData = Arrays.asList(
        new KPair<>(
            "[123, {\"aboba\": true}]",
            new KJsonToken[]{
                KJsonToken.OPEN_SQUARE_BRACKET,
                KJsonToken.NUMBER_INT,
                KJsonToken.COMMA,
                KJsonToken.OPEN_BRACE,
                KJsonToken.STRING,
                KJsonToken.SEMICOLON,
                KJsonToken.TRUE,
                KJsonToken.CLOSE_BRACE,
                KJsonToken.CLOSE_SQUARE_BRACKET,
                KJsonToken.EOF
            }
        ),
        new KPair<>(
            "{\"aboba\": false, \"tete\": {}}",
            new KJsonToken[]{
                KJsonToken.OPEN_BRACE,
                KJsonToken.STRING,
                KJsonToken.SEMICOLON,
                KJsonToken.FALSE,
                KJsonToken.COMMA,
                KJsonToken.STRING,
                KJsonToken.SEMICOLON,
                KJsonToken.OPEN_BRACE,
                KJsonToken.CLOSE_BRACE,
                KJsonToken.CLOSE_BRACE,
                KJsonToken.EOF
            }
        )
    );

    private void singleTokenTest(String input, KJsonToken token) {

        KJsonTokenizer tokenizer = this.jsonTokenizer;
        int sequenceToken = tokenizer.addSource(input);

        try {
            KJsonToken result = tokenizer.getNextToken(sequenceToken).token();
            Assertions.assertEquals(token, result);
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    private void singleTokenTest(String input, KJsonToken token, Object expectedValue) {

        KJsonTokenizer tokenizer = this.jsonTokenizer;

        Reader reader = new StringReader(input);
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        char[] chars = input.toCharArray();

        int[] tokens = new int[] {
            tokenizer.addSource(input),
            tokenizer.addSource(reader),
            tokenizer.addSource(stream),
            tokenizer.addSource(chars)
        };

        try {
            for (var sequenceToken: tokens) {
                KJsonTokenPair result = tokenizer.getNextToken(sequenceToken);
                Assertions.assertEquals(token, result.token());
                Assertions.assertEquals(expectedValue, result.value());
            }
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    private void multiTokenTest(String input, KJsonToken[] tokens) {

        KJsonTokenizer tokenizer = this.jsonTokenizer;
        int sequenceToken = tokenizer.addSource(input);

        try {
            for (KJsonToken token: tokens) {
                KJsonToken result = tokenizer.getNextToken(sequenceToken).token();
                Assertions.assertEquals(token, result);
            }
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    private void multiTokenTest(String input, KJsonTokenPair[] tokens) {

        KJsonTokenizer tokenizer = this.jsonTokenizer;
        int sequenceToken = tokenizer.addSource(input);

        try {
            for (KJsonTokenPair token: tokens) {
                KJsonTokenPair result = tokenizer.getNextToken(sequenceToken);
                Assertions.assertEquals(token.token(), result.token());
                Assertions.assertEquals(token.value(), result.value());
            }
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testGetSingleToken() {

        for (var data: KStandardJsonTokenizerPositiveTests.singleTokenTestData) {
            this.singleTokenTest(data.first(), data.second());
        }

    }

    @Test
    public void testSetSingleTokenWithValueCheck() {

        for (var data: KStandardJsonTokenizerPositiveTests.singleTokenWithValueTestData) {
            this.singleTokenTest(data.first(), data.second(), data.third());
        }

    }

    @Test
    public void testGetMultiTokens() {

        for (var data: KStandardJsonTokenizerPositiveTests.multiTokenTestData) {
            this.multiTokenTest(data.first(), data.second());
        }

    }

    @Test
    public void multiTestGetTokensAndCheckValues() {
        this.multiTokenTest(
            "{\"asdf\\\"ghjk\": 123,\n\"qwer\": 1.0e+4, \"zxcv\": []}",
            new KJsonTokenPair[]{
                new KJsonTokenPair(KJsonToken.OPEN_BRACE, '{'),
                new KJsonTokenPair(KJsonToken.STRING, "asdf\\\"ghjk"),
                new KJsonTokenPair(KJsonToken.SEMICOLON, ':'),
                new KJsonTokenPair(KJsonToken.NUMBER_INT, 123),
                new KJsonTokenPair(KJsonToken.COMMA, ','),
                new KJsonTokenPair(KJsonToken.STRING, "qwer"),
                new KJsonTokenPair(KJsonToken.SEMICOLON, ':'),
                new KJsonTokenPair(KJsonToken.NUMBER_FLOAT, 10000.0f),
                new KJsonTokenPair(KJsonToken.COMMA, ','),
                new KJsonTokenPair(KJsonToken.STRING, "zxcv"),
                new KJsonTokenPair(KJsonToken.SEMICOLON, ':'),
                new KJsonTokenPair(KJsonToken.OPEN_SQUARE_BRACKET, '['),
                new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, ']'),
                new KJsonTokenPair(KJsonToken.CLOSE_BRACE, '}'),
                new KJsonTokenPair(KJsonToken.EOF, '\0'),
            }
        );
    }

    @Test
    public void multiTestGetTokensAndCheckValuesArray() {
        this.multiTokenTest(
            "[-1, 2, -3, 4]",
            new KJsonTokenPair[]{
                new KJsonTokenPair(KJsonToken.OPEN_SQUARE_BRACKET, '['),
                new KJsonTokenPair(KJsonToken.NUMBER_INT, -1),
                new KJsonTokenPair(KJsonToken.COMMA, ','),
                new KJsonTokenPair(KJsonToken.NUMBER_INT, 2),
                new KJsonTokenPair(KJsonToken.COMMA, ','),
                new KJsonTokenPair(KJsonToken.NUMBER_INT, -3),
                new KJsonTokenPair(KJsonToken.COMMA, ','),
                new KJsonTokenPair(KJsonToken.NUMBER_INT, 4),
                new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, ']'),
                new KJsonTokenPair(KJsonToken.EOF, '\0'),
            }
        );
    }

    @Test
    public void testGetNonExistentSequenceToken() {
        try {
            Assertions.assertEquals(
                KJsonTokenPair.EOF,
                this.jsonTokenizer.getNextToken(0)
            );
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }
    }
}
