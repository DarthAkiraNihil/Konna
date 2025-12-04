package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.except.KRuntimeException;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

public class KJsonValidationError extends KRuntimeException {

    public static KJsonValidationError incorrectType(final KJsonValueType expected, final KJsonValueType actual) {
        return new KJsonValidationError(
            String.format(
                "Incorrect value type. Expected %s but got %s",
                expected,
                actual
            )
        );
    }

    public KJsonValidationError(final String message) {
        super(message);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }
}
