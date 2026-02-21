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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * Standard implementation of {@link KComponentLoader}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
@KContainerModifier
public class KStandardComponentLoader extends KObject implements KComponentLoader {

    private final KJsonParser parser;

    public KStandardComponentLoader(@KInject final KJsonParser parser) {
        super(
            "std_component_loader",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );
        this.parser = parser;
    }

    @Override
    public void load(
        final KEngineContext ctx,
        final Class<? extends KComponent> component,
        final KServiceLoader serviceLoader,
        final Map<String, KComponent> loadedComponentMap
    ) {
        try {

            if (!component.isAnnotationPresent(KComponentMetaInfo.class)) {
                KSystemLogger.fatal(
                    this.name,
                    "Cannot load component %s: meta info not provided",
                    component
                );
                throw new KComponentLoadingException(
                    String.format(
                        "Cannot load component %s: meta info not provided",
                        component
                    )
                );
            }

            KComponentMetaInfo meta = component.getAnnotation(KComponentMetaInfo.class);
            String componentName = meta.name();

            KSystemLogger.info(this.name, "Loading component %s", componentName);
            
            if (loadedComponentMap.containsKey(componentName)) {
                KSystemLogger.fatal(
                    this.name,
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
            try (KResource config = ctx.loadResource(meta.configFilename())) {
                InputStream configStream = config.stream();
                if (!config.exists() || configStream == null) {
                    throw new KComponentLoadingException(
                        String.format(
                            "Component config file %s not found",
                            meta.configFilename()
                        )
                    );
                }
                parsedConfig = this.parser.parse(configStream);
            }

            KContainer master = ctx.getContainer();
            master
                .add(component)
                .add(KServiceLoader.class, serviceLoader.getClass());

            var loadedComponent = ctx.createObject(
                component, meta.name(), ctx, meta.servicesPackage(), parsedConfig
            );

            loadedComponentMap.put(
                componentName,
                loadedComponent
            );
            KSystemLogger.info(this.name, "Loaded component %s", componentName);
        } catch (Exception e) {
            KSystemLogger.fatal(this.name, e);
            throw new KComponentLoadingException(e);
        }
    }
}
