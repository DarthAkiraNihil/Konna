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

package io.github.darthakiranihil.konna.entity.modules;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.di.KAbstractModule;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KModule;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KClassGraphClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.entity.KEntityFactory;
import io.github.darthakiranihil.konna.entity.KStandardEntityFactory;
import io.github.darthakiranihil.konna.entity.asset.KEntityMetadataCollection;

@KModule
public class TestModule extends KAbstractModule {

    public TestModule(
        KApplicationFeatures applicationFeatures,
        KSystemFeatures systemFeatures,
        KAppContainer appContainer
    ) {
        super(applicationFeatures, systemFeatures, appContainer);
    }

    @KSingleton
    public KJsonParser getTokenizer() {
        return new KStandardJsonParser(new KStandardJsonTokenizer());
    }

    @KSingleton
    public KJsonDeserializer getDeserializer() {
        return new KStandardJsonDeserializer();
    }

    @KSingleton
    public KClasspathSearchEngine getClasspathSearchEngine() {
        return new KClassGraphClasspathSearchEngine();
    }

    @KSingleton
    public KEntityFactory getEntityFactory() {
        return new KStandardEntityFactory(
            this.appContainer.getInstanceInferred(KEntityMetadataCollection.class),
            this.appContainer.getInstanceInferred(KActivator.class),
            this.appContainer.getInstanceInferred(KJsonDeserializer.class),
            this.appContainer.getInstanceInferred(KObjectRegistry.class)
        );
    }

}
