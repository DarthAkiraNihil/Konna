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

package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.*;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.entity.service.KEntityManagementService;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;

/**
 * <p>
 *     Konna Entity component, used for handling game entities (i.e. creating, deleting,
 *     updating, controlling, etc.)
 * </p>
 * <p>
 *     <h2>Provided endpoints</h2>
 *     <ul>
 *         <li>
 *             <p>
 *                 <i>Entity.EntityManagementService.createEntity</i> - creates a new entity
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
 *                         {@code type} -
 *                         {@link String} -
 *                         type of created entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.EntityManagementService.restoreEntity</i> - creates a new entity with
 *                 its data restoration
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
 *                         {@code type} -
 *                         {@link String} -
 *                         type of created entity
 *                     </li>
 *                     <li>
 *                         {@code data} -
 *                         {@link KJsonValue} -
 *                         data of entity to be restored
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.EntityManagementService.deactivateEntity</i> - deactivates an entity
 *                 so it won't be updated on ticks
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of entity to deactivate
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.EntityManagementService.activateEntity</i> - activates an entity
 *                 so it will be updated on ticks
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of entity to activate
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.EntityManagementService.destroyEntity</i> - destroys an entity
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code entity_id} -
 *                         {@link java.util.UUID} -
 *                         id of entity to destroy
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *     </ul>
 * </p>
 * <p>
 *     <h2>Produced messages</h2>
 *     <ul>
 *         <li>
 *             <p>
 *                 <i>Entity.entityCreated</i>
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
 *                         {@code name} -
 *                         {@link String} -
 *                         name of created entity
 *                     </li>
 *                     <li>
 *                         {@code type} -
 *                         {@link String} -
 *                         type of created entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.entityActivated</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code id} -
 *                         {@link java.util.UUID} -
 *                         id of activated entity
 *                     </li>
 *                     <li>
 *                         {@code name} -
 *                         {@link String} -
 *                         name of activated entity
 *                     </li>
 *                     <li>
 *                         {@code type} -
 *                         {@link String} -
 *                         type of activated entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.entityDeactivated</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code id} -
 *                         {@link java.util.UUID} -
 *                         id of deactivated entity
 *                     </li>
 *                     <li>
 *                         {@code name} -
 *                         {@link String} -
 *                         name of deactivated entity
 *                     </li>
 *                     <li>
 *                         {@code type} -
 *                         {@link String} -
 *                         type of deactivated entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Entity.entityDestroyed</i>
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code id} -
 *                         {@link java.util.UUID} -
 *                         id of destroyed entity
 *                     </li>
 *                     <li>
 *                         {@code name} -
 *                         {@link String} -
 *                         name of destroyed entity
 *                     </li>
 *                     <li>
 *                         {@code type} -
 *                         {@link String} -
 *                         type of destroyed entity
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *     </ul>
 * </p>
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@KContainerModifier
@KSingleton
@KComponentMetaInfo(
    name = "Entity",
    configFilename = "classpath:config/entity.json",
    servicesPackage = "io.github.darthakiranihil.konna.entity.service"
)
public class KEntityComponent extends KComponent {

    protected final KEntityComponentConfig config;

    public KEntityComponent(
        final KEngineContext ctx,
        final KEntityManagementService entityManagementService,
        final KEntityComponentConfig config
    ) {
        super("Entity", ctx, new KService[] {entityManagementService} );
        this.config = config;
    }

    @Override
    public KAssetTypedef[] getAssetTypedefs() {
        return new KAssetTypedef[] {
            new KEntityMetadataTypedef()
        };
    }

}
