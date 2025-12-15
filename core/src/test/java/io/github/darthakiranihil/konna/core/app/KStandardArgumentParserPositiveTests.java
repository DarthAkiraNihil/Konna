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

import io.github.darthakiranihil.konna.core.app.std.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KStandardArgumentParserPositiveTests extends KStandardTestClass {

    private final KArgumentParser argumentParser;

    public KStandardArgumentParserPositiveTests() {
        this.argumentParser = new KStandardArgumentParser();
    }

    @Test
    public void testParseArgumentsSuccessWithoutValidators() {

        List<KApplicationArgument> opts = List.of(
            new KApplicationArgument("a1", "arg1"),
            new KApplicationArgument("a2", "arg2", false),
            new KApplicationArgument("a3", "arg3", "aboba"),
            new KApplicationArgument("a4", "arg4", false, "123", KArgumentValidator.INTEGER),
            new KApplicationArgument("a5", "arg5", "123", KArgumentValidator.INTEGER)
        );

        KApplicationFeatures features = this.argumentParser.parse(
            new String[] {
                "-Ka1aboba",
                "-a2amogus",
                "-Ka3",
                "-a4",
                "-Ka5123",
            },
            opts
        );

        Assertions.assertEquals("aboba", features.getFeature("arg1"));
        Assertions.assertEquals("amogus", features.getFeature("arg2"));
        Assertions.assertEquals("aboba", features.getFeature("arg3"));
        Assertions.assertEquals("123", features.getFeature("arg4"));
        Assertions.assertEquals("123", features.getFeature("arg5"));

        features = this.argumentParser.parse(
            new String[] {
                "--Karg1=aboba",
                "--arg2=amogus",
                "--Karg3",
                "--arg4",
                "--Karg5",
            },
            opts
        );

        Assertions.assertEquals("aboba", features.getFeature("arg1"));
        Assertions.assertEquals("amogus", features.getFeature("arg2"));
        Assertions.assertEquals("aboba", features.getFeature("arg3"));
        Assertions.assertEquals("123", features.getFeature("arg4"));
        Assertions.assertEquals("123", features.getFeature("arg5"));

    }
}
