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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.*;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.level.service.KLevelEntityManagementService;
import io.github.darthakiranihil.konna.level.service.KLevelService;
import io.github.darthakiranihil.konna.level.type.KLevelGeneratorMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KLevelMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;

/**
 * <p>
 *     Konna Level component, used for managing game levels and level entities.
 * </p>
 * <p>
 *     <h2>Provided endpoints</h2>
 *     <ul>
 *         <li>
 *             <p>
 *                 <i>Level.LevelService.loadLevel</i> - loads a pre-defined level
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code level_name} -
 *                         {@link String} -
 *                         name of loaded level (that is actually its asset id)
 *                     </li>
 *                     <li>
 *                         {@code sector} -
 *                         {@link String} -
 *                         name of sector to set as current
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelService.generateLevelAndLoad</i> - generates a level and loads it
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code generator} -
 *                         {@link String} -
 *                         generator asset id
 *                     </li>
 *                     <li>
 *                         {@code seed} -
 *                         {@link Long} -
 *                         initial generator seed
 *                     </li>
 *                     <li>
 *                         {@code sector} -
 *                         {@link String} -
 *                         name of sector to set as current
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.moveAllEntities</i> - moves all entities
 *                 active at current moment
 *             </p>
 *             <p>
 *                 Message schema: none
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.setDirectionForControllableEntity</i> -
 *                 sets next move direction for a controllable entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of controllable entity to move
 *                     </li>
 *                     <li>
 *                         {@code direction} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KVector2i} -
 *                         next move direction to set
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.createAutonomousEntity</i> - creates
 *                 an autonomous entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code name} -
 *                         {@link String} -
 *                         name of created entity
 *                     </li>
 *                     <li>
 *                         {@code descriptor} -
 *                         {@link String} -
 *                         descriptor of created entity
 *                     </li>
 *                     <li>
 *                         {@code sector_name} -
 *                         {@link String} -
 *                         name of sector to deploy created entity in
 *                     </li>
 *                     <li>
 *                         {@code position} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KVector2i} -
 *                         position on deployment sector to place created entity on
 *                     </li>
 *                     <li>
 *                         {@code controller} -
 *                         {@link Class} that extends
 *              {@link io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController} -
 *                         class of controller of created entity
 *                     </li>
 *                     <li>
 *                         {@code params} -
 *                         {@link io.github.darthakiranihil.konna.core.io.KAssetDefinition} -
 *                         controller parameters
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.destroyAutonomousEntity</i> - destroys an
 *                 autonomous entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of destroyed entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.createStaticEntity</i> - creates a static
 *                 entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code name} -
 *                         {@link String} -
 *                         name of created entity
 *                     </li>
 *                     <li>
 *                         {@code descriptor} -
 *                         {@link String} -
 *                         descriptor of created entity
 *                     </li>
 *                     <li>
 *                         {@code sector_name} -
 *                         {@link String} -
 *                         name of sector to deploy created entity in
 *                     </li>
 *                     <li>
 *                         {@code position} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KVector2i} -
 *                         position on deployment sector to place created entity on
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.destroyStaticEntity</i> - destroys
 *                 a static entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of destroyed entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.createControllableEntity</i> - creates
 *                 a controllable entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code name} -
 *                         {@link String} -
 *                         name of created entity
 *                     </li>
 *                     <li>
 *                         {@code descriptor} -
 *                         {@link String} -
 *                         descriptor of created entity
 *                     </li>
 *                     <li>
 *                         {@code sector_name} -
 *                         {@link String} -
 *                         name of sector to deploy created entity in
 *                     </li>
 *                     <li>
 *                         {@code position} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KVector2i} -
 *                         position on deployment sector to place created entity on
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.LevelEntityManagementService.destroyControllableEntity</i> - destroys
 *                 a controllable entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of destroyed entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *     </ul>
 * </p>
 * <p>
 *     <h2>Produced messages:</h2>
 *     <ul>
 *         <li>
 *             <p>
 *                 <i>Level.levelLoaded</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code level} -
 *                         {@link KLevel} -
 *                         instance of loaded level
 *                     </li>
 *                     <li>
 *                         {@code sector} -
 *                         {@link KLevelSector} -
 *                         instance of sector to be deployed on loaded level
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.generatedLevelLoaded</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code level} -
 *                         {@link KLevel} -
 *                         instance of loaded level
 *                     </li>
 *                     <li>
 *                         {@code sector} -
 *                         {@link KLevelSector} -
 *                         instance of sector to be deployed on loaded level
 *                     </li>
 *                     <li>
 *                         {@code seed} -
 *                         {@link Long} -
 *                         seed used to generate loaded level
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.entitiesMoved</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code moved_controllables} -
 *                         {@link java.util.Set} of {@link java.util.UUID} -
 *                         set of all moved controllable entities ids
 *                     </li>
 *                     <li>
 *                         {@code controllables_destinations} -
 *                         {@link java.util.Map} from {@link java.util.UUID} to
 *                         {@link KLevelSectorSlice} - map of sector slices on controllable
 *                         entities destinations
 *                     </li>
 *                     <li>
 *                         {@code moved_autonomouses} -
 *                         {@link java.util.Set} of {@link java.util.UUID} -
 *                         set of all moved autonomous entities ids
 *                     </li>
 *                     <li>
 *                         {@code autonomouses_destinations} -
 *                         {@link java.util.Map} from {@link java.util.UUID} to
 *                         {@link KLevelSectorSlice} - map of sector slices on autonomous
 *                         entities destinations
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.autonomousEntityCreated</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code id} -
 *                         {@link java.util.UUID} -
 *                         id of created entity
 *                     </li>
 *                     <li>
 *                         {@code position} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KPair}
 *                         of {@link io.github.darthakiranihil.konna.core.struct.KVector2i}
 *                         and {@link KLevelSector} -
 *                         absolute position of created entity
 *                     </li>
 *                     <li>
 *                         {@code instance} -
 *                         {@link io.github.darthakiranihil.konna.level.entity.KLevelEntity} -
 *                         instance of created entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.staticEntityCreated</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code id} -
 *                         {@link java.util.UUID} -
 *                         id of created entity
 *                     </li>
 *                     <li>
 *                         {@code position} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KPair}
 *                         of {@link io.github.darthakiranihil.konna.core.struct.KVector2i}
 *                         and {@link KLevelSector} -
 *                         absolute position of created entity
 *                     </li>
 *                     <li>
 *                         {@code instance} -
 *                         {@link io.github.darthakiranihil.konna.level.entity.KLevelEntity} -
 *                         instance of created entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.controllableEntityCreated</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code id} -
 *                         {@link java.util.UUID} -
 *                         id of created entity
 *                     </li>
 *                     <li>
 *                         {@code position} -
 *                         {@link io.github.darthakiranihil.konna.core.struct.KPair}
 *                         of {@link io.github.darthakiranihil.konna.core.struct.KVector2i}
 *                         and {@link KLevelSector} -
 *                         absolute position of created entity
 *                     </li>
 *                     <li>
 *                         {@code instance} -
 *                         {@link io.github.darthakiranihil.konna.level.entity.KLevelEntity} -
 *                         instance of created entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Level.entitiesDestroyed</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code instances} -
 *                         {@link java.util.List} of
 *                         {@link io.github.darthakiranihil.konna.level.entity.KLevelEntity} -
 *                         list of all destroyed entities
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *     </ul>
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KContainerModifier
@KSingleton
public class KLevelComponent extends KComponent {

    protected final KLevelComponentConfig config;

    public KLevelComponent(
        final KEngineContext ctx,
        final KLevelService levelService,
        final KLevelEntityManagementService levelEntityManagementService,
        final KLevelComponentConfig config
    ) {
        super("Level", ctx, new KService[] {levelService, levelEntityManagementService} );
        this.config = config;
    }

    @Override
    public KAssetTypedef[] getAssetTypedefs() {
        return new KAssetTypedef[] {
            new KTilePropertyTypedef(),
            new KTileTypedef(),
            new KLevelMetadataTypedef(),
            new KLevelGeneratorMetadataTypedef()
        };
    }

}
