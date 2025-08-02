package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;

/**
 * Interface for json parsers that constructs json values from tokens got from source
 * @see KStandardJsonParser
 */
public interface KJsonParser {

    // TODO: add stream overloadings etc.

    /**
     * Parses json string and constructs a json value if given string is a valid json.
     * @param string String source of a json
     * @return Parsed json value
     * @throws KJsonParseException If json is invalid (incorrect token or syntax)
     */
    KJsonValue parse(String string) throws KJsonParseException;

}
