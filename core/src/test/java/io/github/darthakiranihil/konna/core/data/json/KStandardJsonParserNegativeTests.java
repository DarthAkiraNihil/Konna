package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardJsonParserNegativeTests extends KStandardTestClass {

    private final KJsonParser parser;

    public KStandardJsonParserNegativeTests() {
        this.parser = KStandardTestClass.jsonParser;
    }

    private void test(String input, KJsonTokenPair expectedUnexpectedToken) {

        Exception thrown = Assertions.assertThrowsExactly(
            KJsonParseException.class, () -> parser.parse(input)
        );

        String message = String.format("Error parsing json, unexpected token: %s", expectedUnexpectedToken);
        Assertions.assertEquals(message, thrown.getMessage());

    }

    @Test
    void testTryParseIncorrectValue() {
        this.test("]", new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, );
    }

    @Test
    void testTryParseIncorrectArrayValue() {
        this.test("[]]", KJsonToken.CLOSE_SQUARE_BRACKET);
    }

    @Test
    void testTryParseObjectWithIncorrectKey() {
        this.test("{123:14}", KJsonToken.NUMBER_INT);
    }

    @Test
    void testTryParseObjectWithoutSemicolonAfterKey() {
        this.test("{\"123\",14}", KJsonToken.COMMA);
    }

    @Test
    void testParseMultiJson() {
        Assertions.assertThrowsExactly(KJsonParseException.class, () -> this.parser.parse("{}{}"));
    }

    @Test
    void testParseIncorrectToken() {
        Assertions.assertThrows(KJsonParseException.class, () -> this.parser.parse("{asdasdasda}"));
    }
}
