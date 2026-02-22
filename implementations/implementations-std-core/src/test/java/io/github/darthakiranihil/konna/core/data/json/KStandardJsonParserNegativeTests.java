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
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardJsonParserNegativeTests extends KStandardTestClass {

    private final KJsonParser parser;

    public KStandardJsonParserNegativeTests() {
        this.parser = this.jsonParser;
    }

    private void test(String input, KJsonToken expectedUnexpectedToken) {

        Exception thrown = Assertions.assertThrowsExactly(
            KJsonParseException.class, () -> parser.parse(input)
        );

        String message = String.format("Error parsing json, unexpected token: %s", expectedUnexpectedToken);
        Assertions.assertEquals(message, thrown.getMessage());

    }

    @Test
    public void testTryParseIncorrectValue() {
        this.test("]", KJsonToken.CLOSE_SQUARE_BRACKET);
    }

    @Test
    public void testTryParseIncorrectArrayValue() {
        this.test("[}]", KJsonToken.CLOSE_BRACE);
    }

    @Test
    public void testTryParseObjectWithIncorrectKey() {
        this.test("{123:14}", KJsonToken.NUMBER_INT);
    }

    @Test
    public void testTryParseObjectWithoutSemicolonAfterKey() {
        this.test("{\"123\",14}", KJsonToken.COMMA);
    }

    @Test
    public void testParseMultiJson() {
        Assertions.assertThrowsExactly(KJsonParseException.class, () -> this.parser.parse("{}{}"));
    }

    @Test
    public void testParseIncorrectToken() {
        Assertions.assertThrows(KJsonParseException.class, () -> this.parser.parse("{asdasdasda}"));
    }
}
