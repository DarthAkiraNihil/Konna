package io.github.darthakiranihil.konna.core.message;

/**
 * Interface for classes that purpose is to configure routes
 * by that messages will be sent.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KMessageRoutesConfigurer {

    /**
     * Setups routes for given message system.
     * @param messageSystem Message system that is configured
     */
    void setupRoutes(KMessageSystem messageSystem);

}
