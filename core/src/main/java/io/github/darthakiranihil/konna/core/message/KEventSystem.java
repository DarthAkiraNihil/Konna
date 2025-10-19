package io.github.darthakiranihil.konna.core.message;

/**
 * Represents an event system - a service that holds all created events
 * and provides opportunity to get them so the caller class will be able
 * to subscribe to them.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEventSystem {

    /**
     * Registers a new event in the system.
     * @param event Event to register.
     * @param <T> Type of the event argument
     */
    <T> void registerEvent(KEvent<T> event);
    /**
     * Registers a new simple event in the system.
     * @param event Event to register.
     */
    void registerEvent(KSimpleEvent event);

    /**
     * Returns an event, registered in the system, by its name. If it is not presented
     * in the system, {@code null} will be returned.
     * @param eventName Name of the event.
     * @return The event with given name or {@code null}, if not presented
     * @param <T> Type of the event argument.
     */
    <T> KEvent<T> getEvent(String eventName);
    /**
     * Returns a simple event, registered in the system, by its name. If it is not presented
     * in the system, {@code null} will be returned.
     * @param eventName Name of the event.
     * @return The event with given name or {@code null}, if not presented
     */
    KSimpleEvent getSimpleEvent(String eventName);

}
