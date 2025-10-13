/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.core.engine.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.KMasterContainer;
import io.github.darthakiranihil.konna.core.di.KMasterContainerModifier;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentLoader;
import io.github.darthakiranihil.konna.core.engine.KComponentMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceLoader;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;

import java.io.InputStream;
import java.util.Map;

/**
 * Standard implementation of {@link KComponentLoader}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
@KMasterContainerModifier
public class KStandardComponentLoader extends KObject implements KComponentLoader {

    private final ClassLoader classLoader;
    private final KJsonParser parser;

    public KStandardComponentLoader(@KInject final KJsonParser parser) {
        super(KStandardComponentLoader.class.getSimpleName());
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.parser = parser;
    }

    @Override
    public void load(
        final Class<? extends KComponent> component,
        final KServiceLoader serviceLoader,
        final Map<String, KComponent> loadedComponentMap
    ) throws KComponentLoadingException {
        try {

            if (!component.isAnnotationPresent(KComponentMetaInfo.class)) {
                KLogger.fatal("Cannot load component %s: meta info not provided", component);
                throw new KComponentLoadingException(
                    String.format(
                        "Cannot load component %s: meta info not provided",
                        component
                    )
                );
            }

            KComponentMetaInfo meta = component.getAnnotation(KComponentMetaInfo.class);
            String componentName = meta.name();

            KLogger.info("Loading component %s", componentName);
            
            if (loadedComponentMap.containsKey(componentName)) {
                KLogger.fatal(
                    "Cannot load component %s: there is a component with the same name: %s",
                    component,
                    componentName
                );

                throw new KComponentLoadingException(
                    String.format(
                        "Cannot load component %s: there is a component with the same name: %s",
                        component,
                        componentName
                    )
                );
            }

            KJsonValue parsedConfig;
            try (InputStream config = this.classLoader.getResourceAsStream(meta.configFilename())) {
                parsedConfig = this.parser.parse(config);
            }

            KContainer master = KMasterContainer.getMaster();
            master
                .add(component)
                .add(KServiceLoader.class, serviceLoader.getClass());

            var loadedComponent = KActivator.create(
                component, meta.servicesPackage(), parsedConfig
            );

            loadedComponentMap.put(
                componentName,
                loadedComponent
            );
            KLogger.info("Loaded component %s", componentName);
        } catch (Exception e) {
            KLogger.fatal(e);
            throw new KComponentLoadingException(e);
        }
    }
}
