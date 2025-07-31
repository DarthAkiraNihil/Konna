package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

public class KJsonParseException extends Exception implements KThrowable {

    public KJsonParseException(Object tokenValue) {
        super(String.format("Error parsing json, unexpected token: %s", tokenValue));
    }

    public KJsonParseException(Throwable cause) {
        super(cause);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
