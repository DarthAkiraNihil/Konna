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

package io.github.darthakiranihil.konna.core.message;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.engine.*;
import io.github.darthakiranihil.konna.core.engine.another_impl.TestAnotherService;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.impl.TestService;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class KStandardMessengerPositiveTests extends KStandardTestClass {

    private final KComponent component1;
    private final KComponent component2;
    private final KQueueBasedMessageSystem messageSystem;

    private final Field serviceObjectField;
    private final Field componentServicesField;

    private final ConsumerTest consumerTest1;
    private final ConsumerTest consumerTest2;

    private static final class ConsumerTest implements Consumer<KMessage> {

        private @Nullable KMessage msg;

        @Override
        public void accept(KMessage message) {
            this.msg = message;
        }

        public @Nullable KMessage getMsg() {
            return msg;
        }
    }

    public KStandardMessengerPositiveTests() {

        KServiceLoader serviceLoader = new KStandardServiceLoader();

        try {
            this.component1 = new TestComponent(serviceLoader,
                "TestComponent",
                KStandardTestClass.context,
                "io.github.darthakiranihil.konna.core.engine.impl",
                KJsonValue.fromMap(new HashMap<>())
            );

            this.component2 = new TestComponentAgain(serviceLoader,
                "TestComponentAgain",
                KStandardTestClass.context,
                "io.github.darthakiranihil.konna.core.engine.another_impl",
                KJsonValue.fromMap(new HashMap<>())
            );

            this.consumerTest1 = new ConsumerTest();
            this.consumerTest2 = new ConsumerTest();

            this.messageSystem = KStandardTestClass.msgSystem;

            this.messageSystem.registerComponent(this.component1);
            this.messageSystem.registerComponent(this.component2);

            this.messageSystem
                .addMessageRoute(
                    "TestComponentAgain.aboba",
                    "TestComponent.TestService.testEndpoint"
                )
                .addMessageRoute(
                    "TestComponent.biba",
                    "TestComponentAgain.TestAnotherService.testEndpoint",
                    List.of(TunnelExample.class)
                )
                .addMessageRoute(
                    "TestComponentAgain.aboba", this.consumerTest1
                )
                .addMessageRoute(
                    "TestComponent.biba",
                    this.consumerTest2,
                    List.of(TunnelExample.class)
                );

            this.serviceObjectField = KServiceEntry.class.getDeclaredField("service");
            this.componentServicesField = KComponent.class.getDeclaredField("services");

            this.serviceObjectField.setAccessible(true);
            this.componentServicesField.setAccessible(true);

            this.messageSystem.startPolling();

        } catch (KComponentLoadingException | NoSuchFieldException e) {
            Assertions.fail(e);
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    void tearDown() {
        this.messageSystem.stopPolling();
    }

    @Test
    public void testSendRegularMessageSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendRegularSync,
            messenger2::sendRegularSync,
            false
        );
    }

    @Test
    public void testSendDebugMessageSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendDebugSync,
            messenger2::sendDebugSync,
            false
        );
    }

    @Test
    public void testSendMetricsMessageSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendMetricsSync,
            messenger2::sendMetricsSync,
            false
        );
    }

    @Test
    public void testSendSystemMessageSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendSystemSync,
            messenger2::sendSystemSync,
            false
        );
    }

    @Test
    public void testSendRegularMessageAsyncSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendRegular,
            messenger2::sendRegular,
            true
        );
    }

    @Test
    public void testSendDebugMessageAsyncSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendDebug,
            messenger2::sendDebug,
            true
        );
    }

    @Test
    public void testSendMetricsMessageAsyncSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendMetrics,
            messenger2::sendMetrics,
            true
        );
    }

    @Test
    public void testSendSystemMessageAsyncSuccess() {
        KMessenger messenger1 = new KStandardMessenger(this.messageSystem, "TestComponent");
        KMessenger messenger2 = new KStandardMessenger(this.messageSystem, "TestComponentAgain");

        this.sendTest(
            messenger1::sendSystem,
            messenger2::sendSystem,
            true
        );
    }

    @SuppressWarnings("unchecked")
    private void sendTest(
        BiConsumer<String, KUniversalMap> sendMethodForFirstMessenger,
        BiConsumer<String, KUniversalMap> sendMethodForSecondMessenger,
        boolean isAsync
    ) {

        var body = new KUniversalMap();
        body.put("test", 1);

        sendMethodForFirstMessenger.accept("biba", body);
        sendMethodForSecondMessenger.accept("aboba", new KUniversalMap());

        try {

            if (isAsync) {
                TimeUnit.SECONDS.sleep(4);
            }

            Map<String, KServiceEntry> component1Services = (Map<String, KServiceEntry>) this.componentServicesField.get(this.component1);
            Map<String, KServiceEntry> component2Services = (Map<String, KServiceEntry>) this.componentServicesField.get(this.component2);

            KServiceEntry serviceEntry1 = component1Services.get("TestService");
            KServiceEntry serviceEntry2 = component2Services.get("TestAnotherService");

            Assertions.assertEquals(2, ((TestAnotherService) this.serviceObjectField.get(serviceEntry2)).getTestVar());
            Assertions.assertNotEquals(-1, ((TestService) this.serviceObjectField.get(serviceEntry1)).getTestVar());

            Assertions.assertNotNull(this.consumerTest1.getMsg());
            Assertions.assertNotNull(this.consumerTest2.getMsg());

            this.messageSystem.deliverMessage(KMessage.DROP);
            this.messageSystem.deliverMessageSync(KMessage.DROP);

        } catch (Throwable e) {
            Assertions.fail(e);
        }
    }
}
