package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardJsonTokenizerTest {

    @Test
    public void Test() throws KJsonTokenException {

        KJsonTokenizer tok = new KStandardJsonTokenizer("{");
        Assertions.assertEquals(tok.getNextToken().token(), KJsonToken.OPEN_BRACE);
        System.out.println("IT WORKS!");
    }

}
