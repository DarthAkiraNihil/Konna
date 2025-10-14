package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KIndex;

public interface KEngineContext {
    KActivator activator();
    KContainerResolver containerResolver();
    KIndex index();
    KLogger logger();
    KObjectRegistry objectRegistry();
}
