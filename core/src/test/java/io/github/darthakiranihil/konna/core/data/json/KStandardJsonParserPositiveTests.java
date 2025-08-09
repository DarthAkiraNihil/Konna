package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class KStandardJsonParserPositiveTests {

    @Test
    public void basicTestParseValidObject() {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer();
        KJsonParser parser = new KStandardJsonParser(tokenizer);

        try {
            KJsonValue result = parser.parse("{\"test\": 123}");
            Assertions.assertEquals(KJsonValueType.OBJECT, result.getType());
            Assertions.assertEquals(123, result.getProperty("test").getInt());
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }


    }

    @Test
    public void basicTestParseValidArray() {

        KJsonTokenizer tokenizer = new KStandardJsonTokenizer();
        KJsonParser parser = new KStandardJsonParser(tokenizer);

        try {
            KJsonValue result = parser.parse("[123, \"OLOLO\"]");
            Assertions.assertEquals(KJsonValueType.ARRAY, result.getType());
            int i = 0;
            for (Iterator<KJsonValue> it = result.iterator(); it.hasNext();) {
                KJsonValue entry = it.next();
                switch (i) {
                    case 0: {
                        Assertions.assertEquals(123, entry.getInt());
                        break;
                    }
                    case 1: {
                        Assertions.assertEquals("OLOLO", entry.getString());
                        break;
                    }
                }
                i++;
            }
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }


    }

}
