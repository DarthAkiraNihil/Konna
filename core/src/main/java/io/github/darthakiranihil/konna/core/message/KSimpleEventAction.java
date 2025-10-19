package io.github.darthakiranihil.konna.core.message;

/**
 * Representation of method to be called when an event is invoked.
 * Just the same as {@link KEventAction}, but does not accept arguments.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KSimpleEventAction {
    /**
     * Invokes the assigned method.
     */
    void accept();
}
