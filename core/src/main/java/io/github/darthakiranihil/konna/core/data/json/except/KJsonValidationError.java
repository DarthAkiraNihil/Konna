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

    public static KJsonValidationError generalError(final String property, final String message) {
        return new KJsonValidationError(
            String.format(
                "%s: %s",
                property,
                message
            )
        );
    }

    public static KJsonValidationError wrappedError(final String property, final KJsonValidationError cause) {
        return new KJsonValidationError(
            String.format(
                "%s: %s",
                property,
                cause.getMessage()
            ),
            cause
        );
    }

    public KJsonValidationError(final String message, final Throwable cause) {
        super(message, cause);
    }

    public KJsonValidationError(final String message) {
        super(message);
    }



    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }
}
