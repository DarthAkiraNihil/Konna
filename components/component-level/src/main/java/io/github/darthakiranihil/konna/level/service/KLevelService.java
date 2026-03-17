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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.level.asset.KLevelCollection;
import io.github.darthakiranihil.konna.level.map.KLevel;
import io.github.darthakiranihil.konna.level.map.KMapSector;
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

    private final KLevelCollection levelCollection;

    private final KEvent<KLevel> levelLoaded;
    private final KSimpleEvent levelUnloaded;

    private @Nullable KLevel currentLevel;
    private @Nullable KMapSector currentSector;
    private @Nullable KMessenger messenger;

    /**
     * Standard constructor.
     * @param levelCollection Level collection to get levels from
     * @param eventSystem Event system to get {@code levelLoaded} and {@code levelUnloaded}
     *                    events to invoke.
     */
    public KLevelService(
        @KInject final KEventSystem eventSystem,
        @KInject final KLevelCollection levelCollection
    ) {
        super("Level.LevelService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));

        this.levelCollection = levelCollection;

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
     */
    @KServiceEndpoint(route = "loadLevel")
    protected void loadLevel(
        @KBodyValue("level_name") final String levelName
    ) {

        try {
            if (this.currentLevel != null) {
                this.currentLevel.unload();
                this.levelUnloaded.invokeSync();
            }

            this.currentLevel = this.levelCollection.getAsset(levelName);
        } catch (KAssetLoadingException e) {
            KSystemLogger.warning(
                this.name,
                "Could not load level with name %s",
                levelName
            );
            return;
        }

        // todo: specify sector to deploy after transition to the level
        this.currentSector = this.currentLevel.getSector(
            this.currentLevel.getSectorNames()[0]
        );

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
     * Sets messenger for this service.
     * @param messenger Messenger of Entity component
     */
    public void setMessenger(final KMessenger messenger) {
        this.messenger = messenger;
    }

}
