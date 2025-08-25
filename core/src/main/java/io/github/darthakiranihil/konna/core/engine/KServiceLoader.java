package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;

import java.util.Map;

public interface KServiceLoader {

    void load(
        Class<?> service,
        Map<String, KServiceEntry> loadedServicesMap
    ) throws KServiceLoadingException;

}
