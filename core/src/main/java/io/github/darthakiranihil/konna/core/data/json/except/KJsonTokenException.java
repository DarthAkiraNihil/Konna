package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception that is caused when json tokenizer can't get the next token due to different errors
 * such as unclosed string, incorrect exponential-format number or so on.
 * By default, exception is an error because it is certainly not good, but it depends on goals, sometimes invalid json
 * just leads to not loading some kind of content
 */
public class KJsonTokenException extends Exception implements KThrowable {

    /**
     * Default constructor with error message
     * @param line Line of error occurring
     * @param column Column of error occurring
     */
    public KJsonTokenException(long line, long column) {
        super(String.format("Error reading json token at line: %d, column: %d", line, column));
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }

}
