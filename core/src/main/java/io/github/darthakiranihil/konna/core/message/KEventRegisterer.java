package io.github.darthakiranihil.konna.core.message;

/**
 * Interface for classes that registers events in an event system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEventRegisterer {

    /**
     * Registers events in given event system.
     * @param eventSystem Event system to register events in
     */
    void registerEvents(KEventSystem eventSystem);

}
