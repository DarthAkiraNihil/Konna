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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import org.jspecify.annotations.NullMarked;

@KComponentMetaInfo(
    name = "TestComponentAgain",
    configFilename = "classpath:test_config.json",
    servicesPackage = "io.github.darthakiranihil.konna.core.engine.impl"
)
@NullMarked
public class TestComponentAgain extends KComponent {
    public TestComponentAgain(
        @KInject KServiceLoader serviceLoader,
        String name,
        KEngineContext ctx,
        String servicesPackage,
        KJsonValue config
    ) {
        super(serviceLoader, name, ctx, servicesPackage, config);
    }

    @Override
    protected void applyConfig(KJsonValue config) {

    }

    @Override
    public KAssetTypedef[] getAssetTypedefs() {
        return new KAssetTypedef[0];
    }
}
