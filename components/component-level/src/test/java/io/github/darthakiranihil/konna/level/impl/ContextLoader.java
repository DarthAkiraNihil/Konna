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

package io.github.darthakiranihil.konna.level.impl;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.di.KStandardContainerAccessor;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KEngineContextLoader;
import io.github.darthakiranihil.konna.core.engine.KProxiedEngineContext;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.log.KSimpleLogFormatter;
import io.github.darthakiranihil.konna.core.log.system.*;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KStandardActivator;
import io.github.darthakiranihil.konna.core.object.KStandardObjectRegistry;
import io.github.darthakiranihil.konna.core.util.*;
import io.github.darthakiranihil.konna.level.type.KLevelGeneratorMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KLevelMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Map;

@NullMarked
@KContainerModifier
public class ContextLoader implements KEngineContextLoader {

    @Override
    public KEngineContext load(KApplicationFeatures features) {
        var index = new KStandardIndex();
        var classpath = new KClassGraphClasspathSearchEngine();
        var containerResolver = new KStandardContainerAccessor(index);
        containerResolver
            .getContainer()
            .add(KJsonParser.class, KStandardJsonParser.class)
            .add(KJsonTokenizer.class, KStandardJsonTokenizer.class)
            .add(KJsonDeserializer.class, KStandardJsonDeserializer.class)
            .add(KJsonSerializer.class, KStandardJsonSerializer.class)
            .add(KActivator.class, KProxiedEngineContext.class)
            .add(KMessageSystem.class, KProxiedEngineContext.class)
            .add(KAssetLoader.class, KProxiedEngineContext.class)
            .add(KEventSystem.class, KProxiedEngineContext.class)
            .add(KMessenger.class, KStandardMessenger.class)
            .add(KLogger.class, KStandardLogger.class)
            .add(KClasspathSearchEngine.class, KClassGraphClasspathSearchEngine.class)
            .add(KCache.class, KHashMapBasedCache.class);

        var objectRegistry = new KStandardObjectRegistry();
        var activator = new KStandardActivator(containerResolver, objectRegistry, classpath);
        var messageSystem = new KStandardMessageSystem(activator);
        var eventSystem = new KStandardEventSystem();
        var resourceLoader = new KStandardResourceLoader(
            List.of(
                new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                )
            )
        );
        var assetLoader = new KJsonSubtypeBasedAssetLoader(
            resourceLoader,
            Map.of("tileProp", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTilePropertyTypedef.TILE_PROPERTY_ASSET_TYPE },
                new String[] {"classpath:assets/props.json"}
            ), "tile", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTileTypedef.TILE_ASSET_TYPE},
                new String[] {"classpath:assets/tiles.json"}
            ), "level", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KLevelMetadataTypedef.LEVEL_METADATA_ASSET_TYPE },
                new String[] {"classpath:assets/levels.json"}
            ), "generator", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KLevelGeneratorMetadataTypedef.LEVEL_GENERATOR_METADATA_TYPE },
                new String[] {"classpath:assets/generators.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );
        KSystemLogger.addLogHandler(new KFileLogHandler("_log.log", new KTimestampLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KColorfulTerminalLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KSimpleLogFormatter()));

        var ctx = new KProxiedEngineContext(
            activator,
            containerResolver,
            objectRegistry,
            eventSystem,
            messageSystem,
            resourceLoader,
            assetLoader,
            null
        );
        activator.addContext(ctx);
        return ctx;
    }
}
