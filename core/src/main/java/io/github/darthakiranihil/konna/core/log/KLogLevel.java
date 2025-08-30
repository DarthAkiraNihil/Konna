package io.github.darthakiranihil.konna.core.log;

/**
 * Enumeration that describes severity and importance of logged messages.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public enum KLogLevel {
    /**
     * Indicates a severe error after that the application cannot continue
     * working? so it crashes.
     */
    FATAL,
    /**
     * Indicates an error that may break concrete functionality, but it
     * does not fatal for the whole application.
     */
    ERROR,
    /**
     * Indicates a potential issue occurred somewhere in the codebase
     * that may be a real problem if the issue is not fixed.
     */
    WARNING,
    /**
     * Describes general information about application execution processes.
     */
    INFO,
    /**
     * Contains different debug information that is useful during development
     * of application features or bug fixing.
     */
    DEBUG,
    /**
     * The most detailed level, providing the full data about execution process,
     * including entered methods etc.
     */
    TRACE,
}
