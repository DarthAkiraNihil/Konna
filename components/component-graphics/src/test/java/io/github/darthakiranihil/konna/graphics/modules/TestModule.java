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

package io.github.darthakiranihil.konna.graphics.modules;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.di.KAbstractModule;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KModule;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.util.KClassGraphClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.graphics.KTransformMatrixCalculator;
import io.github.darthakiranihil.konna.graphics.impl.TestRenderFrontend;
import io.github.darthakiranihil.konna.graphics.opengl33.KGl33TransformMatrixCalculator;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.test.KTestFrame;

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
    public KClasspathSearchEngine classpathSearchEngine() {
        return new KClassGraphClasspathSearchEngine();
    }

    @KSingleton
    public KJsonParser jsonParser() {
        return new KStandardJsonParser(new KStandardJsonTokenizer());
    }

    @KSingleton
    public KJsonDeserializer jsonDeserializer() {
        return new KStandardJsonDeserializer();
    }

    @KSingleton
    public KTransformMatrixCalculator transformMatrixCalculator() {
        return new KGl33TransformMatrixCalculator(KSize.squared(640));
    }

    @KSingleton
    public KRenderFrontend renderFrontend() {
        return new TestRenderFrontend();
    }

    @KSingleton
    public KFrame frame() {
        return new KTestFrame(
            this.appContainer.getInstanceInferred(KFrameTaskScheduler.class)
        );
    }


}
