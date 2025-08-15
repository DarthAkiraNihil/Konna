package io.github.darthakiranihil.konna.core.test;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.*;

public class KStandardTestClass {

    protected static KJsonTokenizer jsonTokenizer;
    protected static KJsonParser jsonParser;
    protected static KJsonSerializer jsonSerializer;
    protected static KJsonDeserializer jsonDeserializer;
    protected static KJsonStringifier jsonStringifier;

    static {

        KStandardTestClass.jsonTokenizer = new KStandardJsonTokenizer();
        KStandardTestClass.jsonParser = new KStandardJsonParser(KStandardTestClass.jsonTokenizer);
        KStandardTestClass.jsonSerializer = new KStandardJsonSerializer();
        KStandardTestClass.jsonDeserializer = new KStandardJsonDeserializer();
        KStandardTestClass.jsonStringifier = new KStandardJsonStringifier();

    }

}
