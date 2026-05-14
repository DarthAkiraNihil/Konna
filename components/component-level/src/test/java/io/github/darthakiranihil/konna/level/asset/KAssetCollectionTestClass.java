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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KJsonTransformerBasedAssetLoader;
import io.github.darthakiranihil.konna.level.type.KLevelGeneratorMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KLevelMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;
import io.github.darthakiranihil.konna.test.KStandardTestClass;

public class KAssetCollectionTestClass extends KStandardTestClass {

    protected final KAssetLoader assetLoader;
    protected final KEngineModule engineModule;

    protected KAssetCollectionTestClass() {
        super();

        this.engineModule = KStandardTestClass.getModule();
        this.assetLoader = new KJsonTransformerBasedAssetLoader(
            this.engineModule.resourceLoader(),
            new KStandardJsonParser(new KStandardJsonTokenizer()),
            new String[] { "classpath:assets/" }
        );

        this.assetLoader.addAssetTypedef(new KTilePropertyTypedef());
        this.assetLoader.addAssetTypedef(new KTileTypedef());
        this.assetLoader.addAssetTypedef(new KLevelMetadataTypedef());
        this.assetLoader.addAssetTypedef(new KLevelGeneratorMetadataTypedef());
    }
}
