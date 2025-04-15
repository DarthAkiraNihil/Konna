package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

public class KJsonTokenException extends Exception implements KThrowable {

    public KJsonTokenException(long line, long column) {
        super(String.format("Error reading json token at line: %d, column: %d", line, column));
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }

}
