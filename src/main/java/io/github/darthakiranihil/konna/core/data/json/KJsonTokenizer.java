package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;

public interface KJsonTokenizer {

    KJsonToken getNextToken() throws KJsonTokenException;

}
