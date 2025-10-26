package io.github.darthakiranihil.konna.core.message;

/**
 * Representation of method to be called when an event is invoked.
 * @param <T> Type of event argument, that must be the same as of the called method
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KEventAction<T> {
    /**
     * Invokes the assigned method.
     * @param arg Method argument
     */
    void accept(T arg);
}
