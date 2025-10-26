package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KIndex;

/**
 * Interface for the essential part of Konna Engine - the engine context.
 * It provides signatures for methods of getting the most important objects
 * used in Konna system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEngineContext {
    /**
     * Returns activator of current context.
     * @return Activator of current context
     */
    KActivator activator();
    /**
     * Returns container resolver of current context.
     * @return Container resolver of current context
     */
    KContainerResolver containerResolver();
    /**
     * Returns index of current context.
     * @return Index of current context
     */
    KIndex index();
    /**
     * Returns logger of current context.
     * @return Logger of current context
     */
    KLogger logger();
    /**
     * Returns object registry of current context.
     * @return Object registry of current context
     */
    KObjectRegistry objectRegistry();
    /**
     * Returns event system of current context.
     * @return Event system of current context
     */
    KEventSystem eventSystem();
    /**
     * Returns message system of current context.
     * @return Message system of current context
     */
    KMessageSystem messageSystem();
}

