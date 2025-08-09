package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardJsonTokenizerPositiveTests {

    private void singleTokenTest(String input, KJsonToken token) {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer(input);
        try {
            KJsonToken result = tokenizer.getNextToken().token();
            Assertions.assertEquals(token, result);
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    private <T> void singleTokenTest(String input, KJsonToken token, T expectedValue) {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer(input);
        try {
            KJsonTokenPair result = tokenizer.getNextToken();
            Assertions.assertEquals(token, result.token());
            Assertions.assertEquals(expectedValue, result.value());
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    private void multiTokenTest(String input, KJsonToken[] tokens) {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer(input);
        try {
            for (KJsonToken token: tokens) {
                KJsonToken result = tokenizer.getNextToken().token();
                Assertions.assertEquals(token, result);
            }
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    private void multiTokenTest(String input, KJsonTokenPair[] tokens) {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer(input);
        try {
            for (KJsonTokenPair token: tokens) {
                KJsonTokenPair result = tokenizer.getNextToken();
                Assertions.assertEquals(token.token(), result.token());
                Assertions.assertEquals(token.value(), result.value());
            }
        } catch (KJsonTokenException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void basicTestGetOpenBraceToken() {
        this.singleTokenTest("{", KJsonToken.OPEN_BRACE);
    }

    @Test
    public void basicTestGetCloseBraceToken() {
        this.singleTokenTest("}", KJsonToken.CLOSE_BRACE);
    }

    @Test
    public void basicTestGetOpenSquareBracketToken() {
        this.singleTokenTest("[", KJsonToken.OPEN_SQUARE_BRACKET);
    }

    @Test
    public void basicTestGetCloseSquareBracketToken() {
        this.singleTokenTest("]", KJsonToken.CLOSE_SQUARE_BRACKET);
    }

    @Test
    public void basicTestGetStringToken() {
        this.singleTokenTest("\"123\"", KJsonToken.STRING, "123");
    }

    @Test
    public void basicTestGetStringTokenWithEscape() {
        this.singleTokenTest("\"123\\\"123\"", KJsonToken.STRING, "123\\\"123");
    }

    @Test
    public void basicTestGetIntegerNumberToken() {
        this.singleTokenTest("123", KJsonToken.NUMBER_INT, 123);
    }

    @Test
    public void basicTestGetIntegerPositiveNumberToken() {
        this.singleTokenTest("+123", KJsonToken.NUMBER_INT, 123);
    }

    @Test
    public void basicTestGetIntegerNegativeNumberToken() {
        this.singleTokenTest("-123", KJsonToken.NUMBER_INT, -123);
    }

    @Test
    public void basicTestGetFloatNumberToken() {
        this.singleTokenTest("123.3", KJsonToken.NUMBER_FLOAT, 123.3f);
    }

    @Test
    public void basicTestGetPositiveFloatNumberToken() {
        this.singleTokenTest("+123.3", KJsonToken.NUMBER_FLOAT, 123.3f);
    }

    @Test
    public void basicTestGetNegativeFloatNumberToken() {
        this.singleTokenTest("-123.3", KJsonToken.NUMBER_FLOAT, -123.3f);
    }

    @Test
    public void basicTestGetExponentialFloatNumberToken() {
        this.singleTokenTest("123.3e+1", KJsonToken.NUMBER_FLOAT, 1233.0f);
    }

    @Test
    public void basicTestGetExponentialPositiveFloatNumberToken() {
        this.singleTokenTest("+123.3e+1", KJsonToken.NUMBER_FLOAT, 1233.0f);
    }

    @Test
    public void basicTestGetExponentialNegativeFloatNumberToken() {
        this.singleTokenTest("-123.3e+1", KJsonToken.NUMBER_FLOAT, -1233.0f);
    }

    @Test
    public void basicTestGetCommaToken() {
        this.singleTokenTest(",", KJsonToken.COMMA);
    }

    @Test
    public void basicTestGetSemicolonToken() {
        this.singleTokenTest(":", KJsonToken.SEMICOLON);
    }

    @Test
    public void basicTestGetTrueToken() {
        this.singleTokenTest("true", KJsonToken.TRUE);
    }

    @Test
    public void basicTestGetFalseToken() {
        this.singleTokenTest("false", KJsonToken.FALSE);
    }

    @Test
    public void basicTestGetNullToken() {
        this.singleTokenTest("null", KJsonToken.NULL);
    }

    @Test
    public void basicTestGetEofToken() {
        this.singleTokenTest("", KJsonToken.EOF);
    }

    @Test
    public void multiTestGetArrayTokens() {
        this.multiTokenTest(
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
        );
    }

    @Test
    public void multiTestGetObjectTokens() {
        this.multiTokenTest(
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
        );
    }

    @Test
    public void multiTestGetTokensAndCheckValues() {
        this.multiTokenTest(
            "{\"asdf\\\"ghjk\": 123, \"qwer\": 1.0e+4, \"zxcv\": []}",
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

}
