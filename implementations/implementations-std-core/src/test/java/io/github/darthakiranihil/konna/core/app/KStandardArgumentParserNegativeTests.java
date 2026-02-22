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

package io.github.darthakiranihil.konna.core.app;

import io.github.darthakiranihil.konna.core.app.except.KArgumentParseException;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KStandardArgumentParserNegativeTests extends KStandardTestClass {

    private final KArgumentParser argumentParser;

    public KStandardArgumentParserNegativeTests() {
        this.argumentParser = new KStandardArgumentParser();
    }

    @Test
    public void testParseArgumentAlreadyParsed() {
        List<KApplicationArgument> opts = List.of(
            new KApplicationArgument("a1", "arg1"),
            new KApplicationArgument("a1", "arg1")
        );

        Assertions.assertThrows(
            KArgumentParseException.class,
            () -> this.argumentParser.parse(
                new String[] {
                    "-Ka1aboba",
                    "--Karg1=123",
                },
                opts
            )
        );
    }

    @Test
    public void testParseArgumentRequiredNotProvided() {
        List<KApplicationArgument> opts = List.of(
            new KApplicationArgument("a1", "arg1")
        );

        Assertions.assertThrows(
            KArgumentParseException.class,
            () -> this.argumentParser.parse(
                new String[] {
                    "-Ka1",
                },
                opts
            )
        );
    }

    @Test
    public void testParseArgumentValidationFailed() {
        List<KApplicationArgument> opts = List.of(
            new KApplicationArgument("a1", "arg1", KArgumentValidator.INTEGER)
        );

        Assertions.assertThrows(
            KArgumentParseException.class,
            () -> this.argumentParser.parse(
                new String[] {
                    "-Ka1notanumber",
                },
                opts
            )
        );
    }
}
