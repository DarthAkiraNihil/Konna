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
}
