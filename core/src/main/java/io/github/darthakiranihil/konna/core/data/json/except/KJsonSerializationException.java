package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception that is caused by different (de)serialization errors.
 * The common case is when a json value does not correspond to the deserialized class
 */
public class KJsonSerializationException extends KException implements KThrowable {

    /**
     * Constructs exception with a provided cause
     * @param cause Cause of the exception
     */
    public KJsonSerializationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs exception with a provided message
     * @param message Exception message
     */
    public KJsonSerializationException(String message) {
        super(message);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
