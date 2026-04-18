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

import io.github.darthakiranihil.konna.core.app.*;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.except.KBootstrapException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@NullMarked
public class KonnaNegativeTests extends KStandardTestClass {

    private static final class InvalidArgumentParser implements KArgumentParser {

        public InvalidArgumentParser(int value) {
            System.out.println(value);
        }

        @Override
        public KApplicationFeatures parse(String[] args, List<KApplicationArgument> options) {
            return new KStandardApplicationFeatures();
        }
    }

    private static final class ThrowingArgumentParser implements KArgumentParser {

        public ThrowingArgumentParser() {
            throw new KException("No you don't!");
        }

        @Override
        public KApplicationFeatures parse(String[] args, List<KApplicationArgument> options) {
            return new KStandardApplicationFeatures();
        }
    }

    private static final class InvalidHypervisor extends KEngineHypervisor {

        public InvalidHypervisor(KEngineHypervisorConfig config, int value) {
            super(config);
            System.out.println(value);
        }

    }

    private static final class ThrowingHypervisor extends KEngineHypervisor {

        public ThrowingHypervisor(KEngineHypervisorConfig config) {
            super(config);
            throw new KException("No you don't");
        }
    }

    private void runTest(
        Class<? extends KArgumentParser> argumentParser,
        Class<? extends KEngineHypervisor> engineHypervisor
    ) {

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                argumentParser,
                engineHypervisor,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(),
                    List.of(),
                    List.of()
                )
            )
        );
        Assertions.assertThrows(KBootstrapException.class, konna::run);

    }

    @Test
    public void testStartKonnaWithInvalidArgumentParser() {
        this.runTest(InvalidArgumentParser.class, KEngineHypervisor.class);
    }

    @Test
    public void testStartKonnaWithThrowingArgumentParser() {
        this.runTest(ThrowingArgumentParser.class, KEngineHypervisor.class);
    }

    @Test
    public void testStartKonnaWithInvalidHypervisor() {
        this.runTest(KStandardArgumentParser.class, InvalidHypervisor.class);
    }

    @Test
    public void testStartKonnaWithThrowingHypervisor() {
        this.runTest(KStandardArgumentParser.class, ThrowingHypervisor.class);
    }
}
