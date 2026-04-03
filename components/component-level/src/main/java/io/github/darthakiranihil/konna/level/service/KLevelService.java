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

package io.github.darthakiranihil.konna.level.service;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.level.KLevelLoader;
import io.github.darthakiranihil.konna.level.KLevelMetadata;
import io.github.darthakiranihil.konna.level.asset.KLevelMetadataCollection;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.asset.KLevelGeneratorMetadataCollection;
import io.github.darthakiranihil.konna.level.except.KGenerationException;
import io.github.darthakiranihil.konna.level.generator.KLevelGenerator;
import io.github.darthakiranihil.konna.level.generator.KLevelGeneratorMetadata;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Level service for handling current active level like loading a new level,
 * changing level etc.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */

@KSingleton
@KComponentServiceMetaInfo(
    name = "LevelService"
)
@KRequiresEvent(name = "levelLoaded", simple = false, type = KLevel.class)
@KRequiresEvent(name = "levelUnloaded")
@SuppressWarnings("FieldCanBeLocal")
public class KLevelService extends KObject {

    private final KLevelMetadataCollection levelCollection;
    private final KLevelGeneratorMetadataCollection generatorMetadataCollection;
    private final KActivator activator;
    private final KLevelLoader levelLoader;

    private final KEvent<KLevel> levelLoaded;
    private final KSimpleEvent levelUnloaded;

    private @Nullable KLevel currentLevel;
    // todo: do we really need it?
    private @Nullable KLevelSector currentSector;
    private @Nullable KMessenger messenger;

    /**
     * Standard constructor.
     * @param levelCollection Level collection to get levels from
     * @param eventSystem Event system to get {@code levelLoaded} and {@code levelUnloaded}
     *                    events to invoke.
     * @param activator Activator to use to create {@link KLevelGenerator}
     * @param generatorMetadataCollection Generator metadata collection to get
     *                                    generator metadata from
     * @param levelLoader Level loader to load levels from metadata
     */
    public KLevelService(
        @KInject final KEventSystem eventSystem,
        @KInject final KActivator activator,
        @KInject final KLevelMetadataCollection levelCollection,
        @KInject final KLevelGeneratorMetadataCollection generatorMetadataCollection,
        @KInject final KLevelLoader levelLoader
    ) {
        super("Level.LevelService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));

        this.levelCollection = levelCollection;
        this.generatorMetadataCollection = generatorMetadataCollection;
        this.activator = activator;
        this.levelLoader = levelLoader;

        this.levelLoaded = Objects.requireNonNull(
            eventSystem.getEvent("levelLoaded")
        );
        this.levelUnloaded = Objects.requireNonNull(
            eventSystem.getSimpleEvent("levelUnloaded")
        );
    }

    /**
     * Loads a level. When it succeeds (level with specified name exists),
     * Level.levelLoaded message is produced.
     * @param levelName Name of the loaded level
     * @param deploymentSector Name of sector to be set as current sector
     */
    @KServiceEndpoint(route = "loadLevel")
    protected void loadLevel(
        @KBodyValue("level_name") final String levelName,
        @KBodyValue("sector") final String deploymentSector
    ) {

        try {
            KLevelMetadata levelMetadata = this.levelCollection.getAsset(levelName);
            KLevel newLevel = this.levelLoader.load(levelMetadata);
            if (this.currentLevel != null) {
                this.currentLevel.unload();
                this.levelUnloaded.invokeSync();
            }
            this.currentLevel = newLevel;
        } catch (KAssetLoadingException e) {
            KSystemLogger.warning(
                this.name,
                "Could not load level with name %s",
                levelName
            );
            return;
        }

        this.currentSector = this.currentLevel.getSector(deploymentSector);

        this.levelLoaded.invokeSync(this.currentLevel);

        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("level", this.currentLevel);
        body.put("sector", this.currentSector);

        this.messenger.sendRegular(
            "levelLoaded", body
        );

    }

    /**
     * Generates a level and loads it.
     * @param generatorId ID of generator to use
     * @param seed Initial seed
     * @param deploymentSector Name of sector to be set as current sector
     */
    @KServiceEndpoint(route = "generateLevelAndLoad")
    protected void generateLevelAndLoad(
        @KBodyValue("generator") final String generatorId,
        @KBodyValue("seed") final Long seed,
        @KBodyValue("sector") final String deploymentSector
    ) {

        KLevelGeneratorMetadata metadata;
        try {
            metadata = this.generatorMetadataCollection.getAsset(generatorId);
        } catch (KAssetLoadingException e) {
            KSystemLogger.warning(
                this.name,
                "Could not load generator with id %s",
                generatorId
            );
            return;
        }

        KLevelGenerator generator = new KLevelGenerator(
            String.format(
                "generator:%s_%d",
                generatorId,
                seed
            ),
            this.activator,
            metadata
        );

        try {
            KLevel generated = generator.generate(seed);
            if (this.currentLevel != null) {
                this.currentLevel.unload();
                this.levelUnloaded.invokeSync();
            }
            this.currentLevel = generated;
        } catch (KGenerationException e) {
            KSystemLogger.error(
                this.name,
                "Could not generate level: %s",
                e.getMessage()
            );
            return;
        }

        this.currentSector = this.currentLevel.getSector(deploymentSector);
        this.levelLoaded.invokeSync(this.currentLevel);
        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("level", this.currentLevel);
        body.put("sector", this.currentSector);
        body.put("seed", seed);

        this.messenger.sendRegular(
            "generatedLevelLoaded", body
        );

    }

    /**
     * Sets messenger for this service.
     * @param messenger Messenger of Entity component
     */
    public void setMessenger(final KMessenger messenger) {
        this.messenger = messenger;
    }

}
