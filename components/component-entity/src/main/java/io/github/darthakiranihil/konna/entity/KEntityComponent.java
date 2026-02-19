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
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KServiceLoader;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.entity.service.KActiveEntitiesService;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;

/**
 * Konna Entity component, used for handling game entities (i.e. creating, deleting,
 * updating, controlling, etc.)
 * Provided endpoints:
 * <ul>
 *     <li>none</li>
 * </ul>
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

    public KEntityComponent(
        @KInject final KServiceLoader serviceLoader,
        final String name,
        final KEngineContext ctx,
        final String servicesPackage,
        final KJsonValue config
    ) {
        super(serviceLoader, name, ctx, servicesPackage, config);
    }

    @Override
    public KAssetTypedef[] getAssetTypedefs() {
        return new KAssetTypedef[] {
            new KEntityMetadataTypedef()
        };
    }

    @Override
    protected void applyConfig(final KJsonValue config) {

        KJsonDeserializer deserializer = this.ctx.createObject(KJsonDeserializer.class);
        KEntityComponentConfig cfg = deserializer.deserialize(config, KEntityComponentConfig.class);
        if (cfg == null) {
            throw new KComponentLoadingException("Could not read component config");
        }

        KContainer container = this.ctx.getContainer();

        container.add(KEntityFactory.class, cfg.entityFactoryClass());

    }

    @Override
    public void postInit() {

        KActiveEntitiesService activeEntitiesService = this.ctx.createObject(
            KActiveEntitiesService.class
        );
        activeEntitiesService.setMessenger(this.messenger);

    }
}
