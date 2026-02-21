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

package io.github.darthakiranihil.konna.graphics.impl;

import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameSpawnOptions;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.test.KTestFrameLoader;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.TimeUnit;

@NullMarked
public class TestFrameLoader extends KTestFrameLoader {

    @Override
    public KFrame load(KEngineContext ctx, KFrameSpawnOptions spawnOptions) {
        var frame = super.load(ctx, spawnOptions);



        var thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
                throw new KException(e);
            }


        });

        thread.start();

        return frame;
    }
}
