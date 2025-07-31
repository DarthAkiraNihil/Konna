package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception that is caused when json parser could not parse a json.
 * The common reason may include:
 * <ul>
 *       <li>tokenizer could not get the next token</li>
 *       <li>json tokens are in incorrect order that do not correspond to json specification</li>
 *  </ul>
 *  By default, the exception is fatal
 */
public class KJsonParseException extends Exception implements KThrowable {

    /**
     * Constructor for situations when the token got is unexpected
     * @param tokenValue The token value
     */
    public KJsonParseException(Object tokenValue) {
        super(String.format("Error parsing json, unexpected token: %s", tokenValue));
    }

    /**
     * Constructor for situations when the reason is out of parser's scope
     * @param cause The cause
     */
    public KJsonParseException(Throwable cause) {
        super(cause);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
