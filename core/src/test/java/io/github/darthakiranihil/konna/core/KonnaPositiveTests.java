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
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.engine.*;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import io.github.darthakiranihil.konna.test.KEmptyEventRegisterer;
import io.github.darthakiranihil.konna.test.KEmptyRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KonnaPositiveTests extends KStandardTestClass {

    private static final KEngineHypervisorConfig HYPERVISOR_CONFIG = new KEngineHypervisorConfig(
        KAppContainer.useGenerated(),
        List.of(KEmptyRouteConfigurer.class),
        List.of(KEmptyEventRegisterer.class),
        List.of(TestComponentLoader.class)
    );

    private static final KonnaBootstrapConfig BOOTSTRAP = new KonnaBootstrapConfig(
        KStandardArgumentParser.class,
        KEngineHypervisor.class,
        HYPERVISOR_CONFIG
    );

    @Test
    public void testStartKonna() {

        try {
            Konna konnaWithOnlyDefaultArgs = new Konna(KStandardTestClass.APP_INFO, BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run(new String[0]);
            Konna konnaWithCustomArgs = new Konna(KStandardTestClass.APP_INFO, List.of(new KApplicationArgument("a", "aaa", "wawa")), BOOTSTRAP);

            KThreadUtils.runAsync(() -> konnaWithCustomArgs.run(new String[0]));
            KThreadUtils.sleepForSeconds(1);

            KRuntime rt = konnaWithCustomArgs.getRuntime();

            Assertions.assertEquals(Konna.VERSION, rt.getKonnaVersion());
            Assertions.assertEquals(KStandardTestClass.APP_INFO, rt.getApplicationInfo());
            Assertions.assertEquals(BOOTSTRAP, rt.getBootstrapConfig());
            Assertions.assertTrue(rt.isRunning());

            Runtime realRt = rt.getRealRuntime();
            Assertions.assertEquals(realRt.totalMemory(), rt.getTotalMemorySize());
            Assertions.assertEquals(realRt.totalMemory() - realRt.freeMemory(), rt.getUsedMemorySize());
            Assertions.assertEquals(realRt.freeMemory(), rt.getFreeMemorySize());

            Assertions.assertFalse(rt.isDebug());

            String[] cmdLineArgs = rt.getCmdlineArgs();
            Assertions.assertNotNull(cmdLineArgs);
            Assertions.assertEquals(0, cmdLineArgs.length);

            KEngineHypervisorRuntime hypervisorRt = rt.getHypervisorRuntime();
            Assertions.assertNotNull(hypervisorRt);

            Assertions.assertEquals(HYPERVISOR_CONFIG, hypervisorRt.getConfig());

            Map<String, KComponent> loadedComponents = hypervisorRt.getLoadedComponents();
            Map<String, Object> loadedDebuggers = hypervisorRt.getLoadedDebuggers();

            Assertions.assertEquals(1, loadedComponents.size());
            Assertions.assertTrue(loadedComponents.containsKey("TestComponent"));
            Assertions.assertEquals(0, loadedDebuggers.size());

            KApplicationFeatures features = hypervisorRt.getApplicationFeatures();
            Assertions.assertEquals("wawa", features.getFeature("aaa"));

            KSystemFeatures systemFeatures = hypervisorRt.getSystemFeatures();
            Assertions.assertEquals(systemFeatures.isDebugEnabled(), rt.isDebug());

            Assertions.assertDoesNotThrow(hypervisorRt::getEngineModule);
            Assertions.assertDoesNotThrow(hypervisorRt::getFrame);

        } catch (Throwable e) {
            throw new KException(e);
        }

    }

    @Test
    public void testStartKonnaAndCheckRuntimeAfterStopping() {

        Konna konnaWithCustomArgs = new Konna(KStandardTestClass.APP_INFO, List.of(new KApplicationArgument("a", "aaa", "wawa")), BOOTSTRAP);
        konnaWithCustomArgs.run(new String[0]);

        KRuntime rt = konnaWithCustomArgs.getRuntime();

        Assertions.assertEquals(Konna.VERSION, rt.getKonnaVersion());
        Assertions.assertEquals(KStandardTestClass.APP_INFO, rt.getApplicationInfo());
        Assertions.assertEquals(BOOTSTRAP, rt.getBootstrapConfig());
        Assertions.assertFalse(rt.isRunning());

        Runtime realRt = rt.getRealRuntime();
        Assertions.assertEquals(realRt.totalMemory(), rt.getTotalMemorySize());
        Assertions.assertEquals(realRt.totalMemory() - realRt.freeMemory(), rt.getUsedMemorySize());
        Assertions.assertEquals(realRt.freeMemory(), rt.getFreeMemorySize());

        Assertions.assertFalse(rt.isDebug());

        String[] cmdLineArgs = rt.getCmdlineArgs();
        Assertions.assertNull(cmdLineArgs);

        KEngineHypervisorRuntime hypervisorRt = rt.getHypervisorRuntime();
        Assertions.assertNull(hypervisorRt);

        Assertions.assertFalse(rt.isDebug());
    }

    @Test
    public void testStartKonnaInDebugMode() {
        Konna konnaWithOnlyDefaultArgs = new Konna(KStandardTestClass.APP_INFO, BOOTSTRAP);
        konnaWithOnlyDefaultArgs.run(new String[0]);
        Konna konnaWithCustomArgs = new Konna(KStandardTestClass.APP_INFO,List.of(new KApplicationArgument("a", "aaa", "wawa")), BOOTSTRAP);

        KThreadUtils.runAsync(() -> konnaWithCustomArgs.run(new String[] { "--Kdebug=true" }));
        KThreadUtils.sleepForSeconds(1);

        KRuntime rt = konnaWithCustomArgs.getRuntime();

        Assertions.assertEquals(Konna.VERSION, rt.getKonnaVersion());
        Assertions.assertEquals(KStandardTestClass.APP_INFO, rt.getApplicationInfo());
        Assertions.assertEquals(BOOTSTRAP, rt.getBootstrapConfig());
        Assertions.assertTrue(rt.isRunning());

        Runtime realRt = rt.getRealRuntime();
        Assertions.assertEquals(realRt.totalMemory(), rt.getTotalMemorySize());
        Assertions.assertEquals(realRt.totalMemory() - realRt.freeMemory(), rt.getUsedMemorySize());
        Assertions.assertEquals(realRt.freeMemory(), rt.getFreeMemorySize());

        Assertions.assertTrue(rt.isDebug());

    }
}
