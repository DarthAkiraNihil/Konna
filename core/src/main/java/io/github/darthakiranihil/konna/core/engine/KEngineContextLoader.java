package io.github.darthakiranihil.konna.core.engine;

/**
 * Represents interface for loading the engine context, which
 * means it must initialize all essential classes.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEngineContextLoader {
    /**
     * Loads the engine context - initializes all required classes
     * and returns it.
     * @return Loaded context
     */
    KEngineContext load();
}
