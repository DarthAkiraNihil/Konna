package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;

import java.util.Map;

/**
 * Interface of a service loader - class which task is to load and
 * instantiate given service class. Should be used inside {@link KComponentLoader}
 * implementations or the like.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KServiceLoader {

    /**
     * Loads given component service class in puts it in loadedServicesMap.
     * @param ctx Engine execution context
     * @param service Service class to load
     * @param loadedServicesMap Map of loaded services. Used to prevent loading two services
     *                          within one component with the same name,
     *                          provided by {@link KComponentServiceMetaInfo} annotation
     * @throws KServiceLoadingException when service failed to be loaded
     *                                  (see cause for details)
     */
    void load(
        KEngineContext ctx,
        Class<?> service,
        Map<String, KServiceEntry> loadedServicesMap
    ) throws KServiceLoadingException;

}
