package io.github.darthakiranihil.konna.core.test;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonSerializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenizer;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonSerializer;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;

public class KStandardTestClass {

    protected static KJsonTokenizer jsonTokenizer;
    protected static KJsonParser jsonParser;
    protected static KJsonSerializer jsonSerializer;
    protected static KJsonDeserializer jsonDeserializer;

    static {

        KStandardTestClass.jsonTokenizer = new KStandardJsonTokenizer();
        KStandardTestClass.jsonParser = new KStandardJsonParser(KStandardTestClass.jsonTokenizer);
        KStandardTestClass.jsonSerializer = new KStandardJsonSerializer();
        KStandardTestClass.jsonDeserializer = new KStandardJsonDeserializer();

    }

}
