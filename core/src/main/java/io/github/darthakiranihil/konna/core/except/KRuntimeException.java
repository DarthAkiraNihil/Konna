package io.github.darthakiranihil.konna.core.except;

/**
 * Class for unchecked Konna exceptions
 * @see KThrowable
 */
public abstract class KRuntimeException extends RuntimeException implements KThrowable {
    public KRuntimeException() {
        super();
    }

    public KRuntimeException(String message) {
        super(message);
    }
}
