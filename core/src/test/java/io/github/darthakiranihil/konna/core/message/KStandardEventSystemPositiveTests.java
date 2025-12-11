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

import io.github.darthakiranihil.konna.core.message.std.KStandardEventQueue;
import io.github.darthakiranihil.konna.core.message.std.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@NullMarked
public class KStandardEventSystemPositiveTests extends KStandardTestClass {

    private final KEventSystem eventSystem;

    private final KEvent<Integer> testEvent1;
    private final KSimpleEvent testEvent2;
    private final KEventQueue eventQueue;

    private int changeableField;

    private final KEventAction<Integer> action1 = this::handleEvent1;
    private final KSimpleEventAction action2 = this::handleEvent2;

    public KStandardEventSystemPositiveTests() {

        this.eventQueue = new KStandardEventQueue();
        this.eventSystem = new KStandardEventSystem(this.eventQueue);
        this.eventQueue.startPolling();

        this.testEvent1 = new KEvent<>("testEvent1");
        this.testEvent2 = new KSimpleEvent("testEvent2");

        this.eventSystem.registerEvent(this.testEvent1);
        this.eventSystem.registerEvent(this.testEvent2);

        this.testEvent1.subscribe(this.action1);
        this.testEvent2.subscribe(this.action2);

    }

    @AfterEach
    void tearDown() {
        this.eventQueue.stopPolling();
    }

    private void handleEvent1(int arg) {
        this.changeableField = arg;
    }

    private void handleEvent2() {
        this.changeableField = -1;
    }

    @Test
    public void testGetEvent() {

        var registeredTestEvent1 = this.eventSystem.getEvent("testEvent1");
        var registeredTestEvent2 = this.eventSystem.getSimpleEvent("testEvent2");

        Assertions.assertNotNull(registeredTestEvent1);
        Assertions.assertNotNull(registeredTestEvent2);
    }

    @Test
    public void testInvokeEvent() {

        this.testEvent1.invokeSync(10);
        Assertions.assertEquals(10, this.changeableField);

        this.testEvent2.invokeSync();
        Assertions.assertEquals(-1, this.changeableField);

    }

    @Test
    public void testInvokeEventAsync() throws InterruptedException {

        this.testEvent1.invoke(10);
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(10, this.changeableField);

        this.testEvent2.invoke();
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(-1, this.changeableField);

    }

    @Test
    public void testUnsubscribeEvent() {

        this.testEvent1.unsubscribe(this.action1);
        this.testEvent1.invokeSync(10);
        Assertions.assertNotEquals(10, this.changeableField);

        this.testEvent2.unsubscribe(this.action2);
        this.testEvent2.invokeSync();
        Assertions.assertNotEquals(-1, this.changeableField);

    }
}
