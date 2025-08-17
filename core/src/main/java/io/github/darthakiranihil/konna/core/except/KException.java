package io.github.darthakiranihil.konna.core.except;

/**
 * Class for checked Konna exceptions
 * @see KThrowable
 */
public abstract class KException extends Exception implements KThrowable {
    /**
     * Default constructor
     */
    public KException() {
        super();
    }

    /**
     * Constructs exception with a provided message
     * @param message Exception message
     */
    public KException(String message) {
        super(message);
    }

    /**
     * Constructs exception with a provided cause
     * @param cause The throwable caused the exception
     */
    public KException(Throwable cause) {
        super(cause);
    }
}
