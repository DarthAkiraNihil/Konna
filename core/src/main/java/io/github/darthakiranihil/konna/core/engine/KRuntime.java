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

import io.github.darthakiranihil.konna.core.KonnaBootstrapConfig;
import io.github.darthakiranihil.konna.core.app.KApplicationInfo;
import io.github.darthakiranihil.konna.core.app.KVersion;
import org.jspecify.annotations.Nullable;

public interface KRuntime {

    // always non-null
    KVersion getKonnaVersion();
    KApplicationInfo getApplicationInfo();
    KonnaBootstrapConfig getBootstrapConfig();
    boolean isRunning();

    Runtime getRealRuntime();

    long getTotalMemorySize();
    long getUsedMemorySize();
    long getFreeMemorySize();

    boolean isDebug();
    String @Nullable [] getCmdlineArgs();
    @Nullable KEngineHypervisorRuntime getHypervisorRuntime();

}
