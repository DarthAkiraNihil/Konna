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
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.except.KBootstrapException;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
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

    private static final String BOOTSTRAP_CONFIG = "bootstrap.json";

    private final Thread shutdownHook;
    private volatile boolean running;
    private @Nullable Thread hypervisorThread;

    private final List<KApplicationArgument> applicationArgsOptions;
    private final String[] args;
    private @Nullable KEngineHypervisor hypervisor;

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
     * @see Konna#run()
     */
    public Konna(final String[] args) {
        super("Konna", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.applicationArgsOptions = KApplicationArgument.DEFAULT_ARGS;
        this.args = args;
        this.shutdownHook = new Thread(this::shutdown);
    }

    /**
     * Standard constructor for cases the application uses non-standard arguments
     * that are defined before Konna application launching.
     * @param args Application args provided by {@code main}
     *             method.
     * @param customArgs Options of custom args that will be used by {@link KArgumentParser}
     *                   to parse them into application features
     * @see Konna#run()
     */
    public Konna(final String[] args, final List<KApplicationArgument> customArgs) {
        super("Konna", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.applicationArgsOptions = Konna.defaultAndCustom(customArgs);
        this.args = args;
        this.shutdownHook = new Thread(this::shutdown);
    }

    /**
     * Starts Konna and its subsystems.
     * It is important that for correct startup it needs file
     * bootstrap.json, located in root of java application resources and
     * nowhere else. If required file is not found, an error will be thrown.
     * Same for situations when config is presented, but not valid according to
     * specified schema, consisting of following components:
     * <ul>
     *
     *     <li>Argument parser class ({@code arg_parser} key)</li>
     *     <li>
     *         Hypervisor data ({@code hypervisor} key)
     *         <ul>
     *             <li>Hypervisor class ({@code class} key)</li>
     *             <li>
     *             Hypervisor configuration ({@code config} key). It follows
     *             another schema, specified in
     * {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig#getSchema()}
     *             </li>
     *         </ul>
     *     </li>
     *
     * </ul>
     *
     * For correct starting up argument parser must contain only a zero-arg constructor,
     * and hypervisor must have only that constructor accepting hypervisor config, presented by
     * {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig}.
     * No matter what kind of error is, it will be wrapped in {@link KBootstrapException}.
     * Application is launched in a detached thread,
     * so in order to graceful shutdown it the Konna thread
     * must be stopped.
     */
    @Override
    public void run() {

        KApplicationFeatures features;
        try (InputStream bootstrapConfig = ClassLoader
            .getSystemClassLoader()
            .getResourceAsStream(BOOTSTRAP_CONFIG)
        ) {

            if (bootstrapConfig == null) {
                throw new KBootstrapException(
                    String.format(
                        "Cannot read bootstrap config file %s from resources",
                        BOOTSTRAP_CONFIG
                    )
                );
            }

            KJsonParser parser = new KStandardJsonParser(new KStandardJsonTokenizer());
            KJsonValue config = parser.parse(bootstrapConfig);
            KonnaBootstrap.getSchema().validate(config);
            KonnaBootstrap bootstrap = new KonnaBootstrap(config);

            KArgumentParser argParser = bootstrap.getArgumentParser();
            features = argParser.parse(this.args, this.applicationArgsOptions);
            this.hypervisor = bootstrap.createHypervisor();


        } catch (IOException e) {
            return;
        } catch (KJsonValidationError e) {
            KSystemLogger.fatal(this.name, e);
            return;
        }

        this.running = true;
        this.hypervisorThread = new Thread(
            () -> {
                try {
                    this.hypervisor.launch(features);
                    this.hypervisor.frameLoop();
                } catch (Throwable e) {
                    KSystemLogger.fatal(this.name, "An unhandled fatal exception occurred");
                    KSystemLogger.fatal(this.name, e);
                    this.hypervisor.shutdown();
                }
            }
        );

        this.hypervisorThread.setName("konna_hypervisor_thread");
        Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        this.hypervisorThread.start();
    }

    private void shutdown() {
        KSystemLogger.info(this.name, "Shutdown initiated");
        if (this.hypervisor == null) {
            return;
        }

        this.running = false;
        this.hypervisor.shutdown();
        this.hypervisorThread = null;
        KSystemLogger.info(this.name, "Shutdown finished");
    }
}
