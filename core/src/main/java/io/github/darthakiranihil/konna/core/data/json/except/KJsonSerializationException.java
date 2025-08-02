package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

public class KJsonSerializationException extends RuntimeException implements KThrowable {

    public KJsonSerializationException() {
        super("Could not initialize JSON deserializator: theUnsafe field of Unsafe does not exist");
    }

    public KJsonSerializationException(Throwable cause) {
        super(cause);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
