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
import io.github.darthakiranihil.konna.core.util.KTriplet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class KStandardJsonTokenizerNegativeTests extends KStandardTestClass {

    private static final List<KTriplet<String, Integer, Integer>> invalidInputTestData = Arrays.asList(
        new KTriplet<>("\"ABOB", 1, 1),
        new KTriplet<>("\"ABOB\n\"", 1, 1),
        new KTriplet<>("\"ABOB\\", 1, 1),
        new KTriplet<>("\\uAB", 1, 1),
        new KTriplet<>("\\uasdf", 1, 1),
        new KTriplet<>("amogus", 1, 1),
        new KTriplet<>("123.", 1, 1),
        new KTriplet<>("123.a", 1, 1),
        new KTriplet<>("123e", 1, 1),
        new KTriplet<>("123ex3", 1, 1),
        new KTriplet<>("123e+", 1, 1),
        new KTriplet<>("123e-", 1, 1),
        new KTriplet<>("123e+c", 1, 1)
    );

    private void testForInvalidInput(String input, int expectedLine, int expectedColumn) {

        KJsonTokenizer tokenizer = KStandardJsonTokenizerPositiveTests.jsonTokenizer;
        int sequenceToken = tokenizer.addSource(input);

        Exception thrown = Assertions.assertThrowsExactly(KJsonTokenException.class, () -> tokenizer.getNextToken(sequenceToken));
        String message = String.format("Error reading json token at line: %d, column: %d", expectedLine, expectedColumn);
        Assertions.assertEquals(message, thrown.getMessage());

    }

    @Test
    public void testUnexpectedEof() {

        for (var data: KStandardJsonTokenizerNegativeTests.invalidInputTestData) {

            this.testForInvalidInput(data.first(), data.second(), data.third());

        }

    }
}
