package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;

public abstract class KJsonTokenizer {

    protected String source;

    public KJsonTokenizer(String source) {
        this.source = source;
    }

    public abstract KJsonTokenPair getNextToken() throws KJsonTokenException;

}
