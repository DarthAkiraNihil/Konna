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

package io.github.darthakiranihil.konna.test;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameTaskExecutor;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;

import java.util.function.Consumer;

public class KTestHypervisor extends KEngineHypervisor {

    private final Consumer<KEngineHypervisor> checker;

    public KTestHypervisor(
        KEngineHypervisorConfig config,
        Consumer<KEngineHypervisor> checker
    ) {
        super(config);
        this.checker = checker;
    }

    @Override
    protected void frameLoop(
        KSystemFeatures systemFeatures,
        KFrame frame,
        KMessageSystem messageSystem,
        KFrameTaskExecutor frameTaskExecutor,
        long nanosPerFrame
    ) {
        this.checker.accept(this);
        super.frameLoop(systemFeatures, frame, messageSystem, frameTaskExecutor, nanosPerFrame);
    }
}
