package io.github.darthakiranihil.konna.core.except;

/**
 * Interface for all standard Konna exceptions
 */
public interface KThrowable {
    /**
     * Getter for a severity level of a throwable
     * @return The severity level of the throwable
     */
    KThrowableSeverity getSeverity();
}
