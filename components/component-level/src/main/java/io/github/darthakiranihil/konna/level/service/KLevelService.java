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
import io.github.darthakiranihil.konna.core.message.KBodyValue;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.level.asset.KLocationCollection;
import io.github.darthakiranihil.konna.level.map.KLocation;
import io.github.darthakiranihil.konna.level.map.KMapSector;
import org.jspecify.annotations.Nullable;

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
@SuppressWarnings("FieldCanBeLocal")
public class KLevelService extends KObject {

    private final KLocationCollection locationCollection;

    private @Nullable KLocation currentLocation;
    private @Nullable KMapSector currentSector;
    private @Nullable KMessenger messenger;

    /**
     * Standard constructor.
     * @param locationCollection Location collection to get locations from
     */
    public KLevelService(
        @KInject final KLocationCollection locationCollection
    ) {
        super("Level.LevelService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));

        this.locationCollection = locationCollection;
    }

    /**
     * Loads a location. When it succeeds (location with specified name exists),
     * Level.locationLoaded message is produced.
     * @param locationName Name of the loaded location
     */
    @KServiceEndpoint(route = "loadLocation")
    protected void loadLocation(
        @KBodyValue("location_name") final String locationName
    ) {

        try {
            if (this.currentLocation != null) {
                this.currentLocation.unload();
            }

            this.currentLocation = this.locationCollection.getAsset(locationName);
        } catch (KAssetLoadingException e) {
            KSystemLogger.warning(
                this.name,
                "Could not load location with name %s",
                locationName
            );
            return;
        }

        // todo: specify sector to deploy after transition to the location
        this.currentSector = this.currentLocation.getSector(
            this.currentLocation.getSectorNames()[0]
        );

        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("location", this.currentLocation);
        body.put("sector", this.currentSector);

        this.messenger.sendRegular(
            "locationLoaded", body
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
