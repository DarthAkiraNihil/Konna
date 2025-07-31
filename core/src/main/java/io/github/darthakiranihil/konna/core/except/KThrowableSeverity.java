package io.github.darthakiranihil.konna.core.except;

/**
 * Represents severity level of an engine exception (throwable).
 * The level could be important in exception handling since some
 * error are not direct the application to crash, so the work continues.
 */
public enum KThrowableSeverity {

    /**
     * The exception is expected to occur. Usually it means that there has been a test
     * that is performed before another action as a check operation. Another suitable
     * situation for this level is absolute exception suppression - the exception is just ignored
     */
    EXPECTED,
    /**
     * The occurred exception is not fatal for application, but the application could work unstable after it, that
     * however, it is not always if the exception is caused because of missing optional dependencies or the like
     */
    WARNING,
    /**
     * The occurred exception may not be fatal for the application but its work may be surely unstable.
     * You are likely to handle it in a proper way or else transform into a fatal error
     */
    ERROR,
    /**
     * The application cannot work after the exception has occurred
     */
    FATAL,

}
