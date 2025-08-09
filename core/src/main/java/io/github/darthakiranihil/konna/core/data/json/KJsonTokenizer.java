package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;

/**
 * Base class for a json tokenizer.
 * It provides base methods to extract tokens from a json-containing value like string (so far).
 * Tokenizer extracts symbols from a source and then converts to a token pair that is used by parser to
 * construct a json value
 * @see KJsonTokenPair
 * @see KJsonValue
 * @see KStandardJsonTokenizer
 */
public abstract class KJsonTokenizer {

    /**
     * String source of a json
     */
    protected String source;

    /**
     * Constructs tokenizer from a string as a Json source
     * @param source String source of a json
     */
    public KJsonTokenizer(String source) {
        this.source = source;
    }

    /**
     * Returns the next token from json source
     * @return The next json token
     * @throws KJsonTokenException If it fails to read the token
     */
    public abstract KJsonTokenPair getNextToken() throws KJsonTokenException;
    public abstract void reset(String newSource);
}
