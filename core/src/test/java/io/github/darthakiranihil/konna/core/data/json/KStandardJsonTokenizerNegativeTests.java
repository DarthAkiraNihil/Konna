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
        tokenizer.reset(input);

        Exception thrown = Assertions.assertThrowsExactly(KJsonTokenException.class, tokenizer::getNextToken);
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
