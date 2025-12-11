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

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class KEventsPositiveTests extends KStandardTestClass {

    private final KEvent<Integer> event;
    private final KSimpleEvent simpleEvent;

    private int flag;
    private int simpleFlag;

    public KEventsPositiveTests() {

        this.event = new KEvent<>("event");
        this.simpleEvent = new KSimpleEvent("event");


        this.flag = 0;
        this.simpleFlag = 0;
    }

    @Test
    public void testInvokeEventAsyncWithoutQueue() {

        try {

            this.event.subscribe(this::listener);
            this.simpleEvent.subscribe(this::listener);

            this.event.invoke(8);
            this.simpleEvent.invoke();

            TimeUnit.SECONDS.sleep(2);

            Assertions.assertEquals(8, this.flag);
            Assertions.assertEquals(10, this.simpleFlag);


        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    private void listener(int arg) {
        this.flag = arg;
    }

    private void listener() {
        this.simpleFlag = 10;
    }
}
