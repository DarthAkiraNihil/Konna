package io.github.darthakiranihil.konna.core.test;

import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonSerializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenizer;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonSerializer;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;

public class KStandardTestClass {

    protected static KJsonTokenizer jsonTokenizer;
    protected static KJsonParser jsonParser;
    protected static KJsonSerializer jsonSerializer;

    static {

        KStandardTestClass.jsonTokenizer = new KStandardJsonTokenizer();
        KStandardTestClass.jsonParser = new KStandardJsonParser(KStandardTestClass.jsonTokenizer);
        KStandardTestClass.jsonSerializer = new KStandardJsonSerializer();

    }

}
