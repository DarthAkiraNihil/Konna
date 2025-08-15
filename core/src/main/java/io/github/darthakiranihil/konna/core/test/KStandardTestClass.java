package io.github.darthakiranihil.konna.core.test;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.*;

/**
 * Standard test class, containing implementations of most common Konna classes
 */
public class KStandardTestClass {

    /**
     * Implementation of a json tokenizer
     */
    protected static KJsonTokenizer jsonTokenizer;
    /**
     * Implementation of a json parser
     */
    protected static KJsonParser jsonParser;
    /**
     * Implementation of a json serializer
     */
    protected static KJsonSerializer jsonSerializer;
    /**
     * Implementation of a json deserializer
     */
    protected static KJsonDeserializer jsonDeserializer;
    /**
     * Implementation of a json stringifier
     */
    protected static KJsonStringifier jsonStringifier;

    static {

        KStandardTestClass.jsonTokenizer = new KStandardJsonTokenizer();
        KStandardTestClass.jsonParser = new KStandardJsonParser(KStandardTestClass.jsonTokenizer);
        KStandardTestClass.jsonSerializer = new KStandardJsonSerializer();
        KStandardTestClass.jsonDeserializer = new KStandardJsonDeserializer();
        KStandardTestClass.jsonStringifier = new KStandardJsonStringifier();

    }

    /**
     * Default constructor
     */
    public KStandardTestClass() {
    }
}
