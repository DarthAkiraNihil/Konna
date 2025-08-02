package io.github.darthakiranihil.konna.core.except;

/**
 * Class for unchecked Konna exceptions
 * @see KThrowable
 */
public abstract class KRuntimeException extends RuntimeException implements KThrowable {
    /**
     * Default constructor
     */
    public KRuntimeException() {
        super();
    }

    /**
     * Constructs exception with a provided message
     * @param message Exception message
     */
    public KRuntimeException(String message) {
        super(message);
    }
}
