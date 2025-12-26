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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KComponentMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KServiceLoader;
import io.github.darthakiranihil.konna.core.struct.KPair;

import java.util.List;

@KComponentMetaInfo(
    name = "Graphics",
    configFilename = "classpath:config/graphics.json",
    servicesPackage = "io.github.darthakiranihil.konna.graphics.service"
)
public class KGraphicsComponent extends KComponent {

    public KGraphicsComponent(
        KServiceLoader serviceLoader,
        String name,
        KEngineContext ctx,
        String servicesPackage,
        KJsonValue config
    ) {
        super(serviceLoader, name, ctx, servicesPackage, config);
    }

    @Override
    public List<KPair<String, KJsonValidator>> getAssetSchemas() {
        return List.of();
    }

    @Override
    protected void applyConfig(KJsonValue config) {

    }

}
