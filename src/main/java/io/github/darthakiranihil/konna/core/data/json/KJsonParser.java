package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;

public interface KJsonParser {

    // TODO: add stream overloadings etc.
    KJsonValue parse(String string) throws KJsonParseException;

}
