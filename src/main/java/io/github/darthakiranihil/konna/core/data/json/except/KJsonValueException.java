package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KRuntimeException;
import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception that is caused by incorrect way of handling a Json value
 * The common reason may include:
 * <ul>
 *      <li>returning iterator from a non-array</li>
 *      <li>casting internal value into invalid type</li>
 * </ul>
 * By default, the exception is fatal
 */
public class KJsonValueException extends KRuntimeException implements KThrowable {

    public KJsonValueException(String message) {
        super(message);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
