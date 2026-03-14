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

import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.message.KBodyValue;
import io.github.darthakiranihil.konna.core.message.KRequiresEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.map.KLocation;

import java.util.UUID;

/**
 * Level entity management service for handling game entities
 * inside current active level.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KComponentServiceMetaInfo(
    name = "LevelEntityManagementService"
)
@KRequiresEvent(name = "locationLoaded", simple = false, type = KLocation.class)
@KRequiresEvent(name = "locationUnloaded", simple = false, type = KLocation.class)
public class KLevelEntityManagementService extends KObject {

    @KServiceEndpoint(route = "triggerMove")
    protected void triggerMove() {

    }

    @KServiceEndpoint(route = "setDirectionForControllableEntity")
    protected void setDirectionForControllableEntity(
        @KBodyValue("entity_id") final UUID entityId,
        @KBodyValue("direction") final KVector2i direction
    ) {



    }

    @KServiceEndpoint(route = "createAutonomousEntity")
    protected void createAutonomousEntity(

    ) {

    }

    @KServiceEndpoint(route = "destroyAutonomousEntity")
    protected void destroyAutonomousEntity(

    ) {

    }

    @KServiceEndpoint(route = "createStaticEntity")
    protected void createStaticEntity(

    ) {

    }

    @KServiceEndpoint(route = "destroyStaticEntity")
    protected void destroyStaticEntity(

    ) {

    }

    @KServiceEndpoint(route = "createControllableEntity")
    protected void createControllableEntity(

    ) {

    }

    @KServiceEndpoint(route = "destroyControllableEntity")
    protected void destroyControllableEntity(

    ) {

    }

    protected void onLocationLoaded() {

    }

    protected void onLocationUnloaded() {

    }


}
