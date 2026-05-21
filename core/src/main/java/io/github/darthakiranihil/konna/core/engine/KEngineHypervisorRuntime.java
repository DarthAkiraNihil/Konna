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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

/**
 * Interface for accessing engine hypervisor runtime information.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KEngineHypervisorRuntime {

    /**
     * @return Config used for launching this hypervisor
     */
    KEngineHypervisorConfig getConfig();

    /**
     * @return Map of loaded engine components
     */
    @Unmodifiable
    Map<String, KComponent> getLoadedComponents();

    /**
     * @return Map of loaded debuggers, that is empty if the app is started not in debug mode
     */
    @Unmodifiable
    Map<String, Object> getLoadedDebuggers();

    /**
     * @return Parsed application features
     */
    KApplicationFeatures getApplicationFeatures();

    /**
     * @return System features of running hypervisor
     */
    KSystemFeatures getSystemFeatures();

    /**
     * @return Created engine module of running hypervisor
     */
    KEngineModule getEngineModule();

    /**
     * @return Frame created by running hypervisor
     */
    KFrame getFrame();

}
