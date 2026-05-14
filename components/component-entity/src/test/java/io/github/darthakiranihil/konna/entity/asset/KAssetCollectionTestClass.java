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

package io.github.darthakiranihil.konna.entity.asset;

import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KJsonAssetLoader;
import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.entity.KEntityComponent;
import io.github.darthakiranihil.konna.test.KStandardTestClass;

import java.util.List;

public class KAssetCollectionTestClass extends KStandardTestClass {

    protected final KAssetLoader assetLoader;

    protected KAssetCollectionTestClass() {
        super();

        this.assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(ClassLoader.getSystemClassLoader()))
            ),
            new KStandardJsonParser(new KStandardJsonTokenizer()),
            new String[] { "classpath:assets/" },
            new KAssetTypedef[][]{
                KEntityComponent.getAssetTypedefs2()
            }
        );
    }
}
