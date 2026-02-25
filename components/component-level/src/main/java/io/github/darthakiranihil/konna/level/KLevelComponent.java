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

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KServiceLoader;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.object.KSingleton;

/**
 * Konna Level component, used for managing game levels and level entities.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KContainerModifier
@KSingleton
@KComponentMetaInfo(
    name = "Level",
    configFilename = "classpath:config/level.json",
    servicesPackage = "io.github.darthakiranihil.konna.level.service"
)
public class KLevelComponent extends KComponent {

    public KLevelComponent(
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
        return new KAssetTypedef[0];
    }

    @Override
    protected void applyConfig(final KJsonValue config) {

    }
}
