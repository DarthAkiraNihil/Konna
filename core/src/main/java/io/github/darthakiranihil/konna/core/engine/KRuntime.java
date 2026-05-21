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

/**
 * Interface for accessing Konna application's runtime information.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KRuntime {

    // always non-null

    /**
     * @return Konna version used to create launched application
     */
    KVersion getKonnaVersion();

    /**
     * @return Application's information
     */
    KApplicationInfo getApplicationInfo();

    /**
     * @return Application's bootstrap config
     */
    KonnaBootstrapConfig getBootstrapConfig();

    /**
     * @return Whether the app is running or not
     */
    boolean isRunning();

    /**
     * @return {@link Runtime} instance, attached to Konna application
     */
    Runtime getRealRuntime();

    /**
     * @return Total memory size (in bytes) used by the application
     */
    long getTotalMemorySize();

    /**
     * @return Used memory size (in bytes) of the application
     */
    long getUsedMemorySize();

    /**
     * @return Available memory size inside currently consumed memory (total) (in bytes)
     *         of the application
     */
    long getFreeMemorySize();

    /**
     * @return Whether the app is running in debug mode or not. Always false if
     *         the app is not started yet
     */
    boolean isDebug();

    /**
     * @return Array of command-line args passed to the application
     */
    String @Nullable [] getCmdlineArgs();

    /**
     * @return Runtime data of app's engine hypervisor if it is launched or {@link null} otherwise
     */
    @Nullable KEngineHypervisorRuntime getHypervisorRuntime();

}
