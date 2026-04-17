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

package io.github.darthakiranihil.konna.core;

import io.github.darthakiranihil.konna.core.app.KApplicationArgument;
import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KArgumentParser;
import io.github.darthakiranihil.konna.core.app.KVersion;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.except.KBootstrapException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KDefaultTags;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Konna. The heart of a game application that performs
 * all required work to start it and its subsystems.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class Konna extends KObject implements Runnable {

    /**
     * Konna's version.
     */
    public static final KVersion VERSION = new KVersion(0, 6, 0, "dev");

    private final List<KApplicationArgument> applicationArgsOptions;
    private final String[] args;

    private final KonnaBootstrapConfig bootstrapConfig;

    private final Thread shutdownHook;

    private static List<KApplicationArgument> defaultAndCustom(
        final List<KApplicationArgument> customArgs
    ) {
        List<KApplicationArgument>
            concatenated = new ArrayList<>(KApplicationArgument.DEFAULT_ARGS);
        concatenated.addAll(customArgs);
        return Collections.unmodifiableList(concatenated);
    }

    /**
     * Standard constructor.
     * @param args Application args provided by {@code main}
     *             method.
     * @param bootstrap Bootstrap config
     * @see Konna#run()
     */
    public Konna(final String[] args, final KonnaBootstrapConfig bootstrap) {
        super("Konna", Collections.singleton(KDefaultTags.SYSTEM));
        this.applicationArgsOptions = KApplicationArgument.DEFAULT_ARGS;
        this.args = args;
        this.shutdownHook = new Thread(this::delete);
        this.bootstrapConfig = bootstrap;
    }

    /**
     * Standard constructor for cases the application uses non-standard arguments
     * that are defined before Konna application launching.
     * @param args Application args provided by {@code main}
     *             method.
     * @param customArgs Options of custom args that will be used by {@link KArgumentParser}
     *                   to parse them into application features
     * @param bootstrap Bootstrap config
     * @see Konna#run()
     */
    public Konna(
        final String[] args,
        final List<KApplicationArgument> customArgs,
        final KonnaBootstrapConfig bootstrap
    ) {
        super("Konna", Collections.singleton(KDefaultTags.SYSTEM));
        this.applicationArgsOptions = Konna.defaultAndCustom(customArgs);
        this.args = args;
        this.shutdownHook = new Thread(this::delete);
        this.bootstrapConfig = bootstrap;
    }

    /**
     * <p>
     *     Starts Konna and its subsystems.
     * </p>
     * <p>
     *     For correct starting up argument parser must contain only a zero-arg constructor,
     *     and hypervisor must have only that constructor accepting hypervisor config, presented by
     *     {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig}.
     *     No matter what kind of error is, it will be wrapped in {@link KBootstrapException}.
     *     Application is launched in a detached thread,
     *     so in order to graceful shutdown it the Konna thread
     *     must be stopped.
     * </p>
     */
    @Override
    public void run() {

        KSystemLogger.info(this.name, "Starting Konna. Version: %s", VERSION);

        KArgumentParser argParser = this.createArgumentParser();
        KApplicationFeatures features = argParser.parse(this.args, this.applicationArgsOptions);
        Runtime.getRuntime().addShutdownHook(this.shutdownHook);

        KEngineHypervisor hypervisor = this.createHypervisor();
        this.addChild(hypervisor);

        try {
            hypervisor.launch(features);
        } catch (Throwable e) {
            KSystemLogger.fatal(this.name, "An unhandled fatal exception occurred");
            KSystemLogger.fatal(this.name, e);
        }

        // !! the only correct usage of self-destruction
        this.delete();

    }

    private KArgumentParser createArgumentParser() {
        var constructor = KReflectionUtils.getConstructor(
            this.bootstrapConfig.argParser()
        );

        if (constructor == null) {
            throw new KBootstrapException(
                String.format(
                    "Argument parser (%s) does not provide a zero-arg constructor",
                    this.bootstrapConfig.argParser()
                )
            );
        }

        try {
            return KReflectionUtils.newInstance(constructor);
        } catch (Throwable e) {
            throw new KBootstrapException("Could not get argument parser", e);
        }
    }

    private KEngineHypervisor createHypervisor() {
        var constructor = KReflectionUtils.getConstructor(
            this.bootstrapConfig.hypervisor(),
            KEngineHypervisorConfig.class
        );

        if (constructor == null) {
            throw new KBootstrapException(
                String.format(
                        "Hypervisor class (%s) does not provide a constructor "
                    +   "with the only argument of type KEngineHypervisor",
                    this.bootstrapConfig.hypervisor()
                )
            );
        }

        try {
            return KReflectionUtils.newInstance(
                constructor,
                this.bootstrapConfig.hypervisorConfig()
            );
        } catch (Throwable e) {
            throw new KBootstrapException(
                "Could not create engine hypervisor",
                e
            );
        }
    }

}
