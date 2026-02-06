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

package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.io.control.*;
import io.github.darthakiranihil.konna.core.io.std.KStandardInputProcessor;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardInputProcessorTests extends KStandardTestClass {

    private static final class TestInputEventProcessor implements KInputEventProcessor {

        private int f;

        @Override
        public void process(KInputEventData data) {

            switch (data.action()) {
                case "abiba": {
                    this.f = 1;
                    break;
                }
                case "abibax": {
                    this.f = 3;
                    break;
                }
                default: {
                    this.f = 0;
                }
            }
        }
    }

    @Test
    public void testProcessEventSuccess() {

        TestInputEventProcessor p = new TestInputEventProcessor();
        KInputProcessor ip = new KStandardInputProcessor(p);
        KInputControlScheme sc = new KInputControlScheme(
            "test_1",
            KInputBinding.ofKey("abiba", KKey.A),
            KInputBinding.ofKey("abibax", KKey.B)
        );
        ip.addControlScheme(sc);

        Assertions.assertEquals(sc, ip.getControlScheme("test_1"));
        Assertions.assertNull(ip.getControlScheme("abiba"));

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);

        ip.keyReleased(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyReleased(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);

    }

    @Test
    public void testProcessEventButProcessorIsDisabled() {
        TestInputEventProcessor p = new TestInputEventProcessor();
        KInputProcessor ip = new KStandardInputProcessor(p);
        KInputControlScheme sc = new KInputControlScheme(
            "test_1",
            KInputBinding.ofKey("abiba", KKey.A),
            KInputBinding.ofKey("abibax", KKey.B)
        );
        ip.addControlScheme(sc);
        sc.disable();

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);

        ip.keyReleased(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);
        ip.keyReleased(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);

        sc.enable();

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);

        ip.keyReleased(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyReleased(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);

    }

    @Test
    public void testProcessEventButSchemeIsDisabled() {
        TestInputEventProcessor p = new TestInputEventProcessor();
        KInputProcessor ip = new KStandardInputProcessor(p);
        KInputControlScheme sc = new KInputControlScheme(
            "test_1",
            KInputBinding.ofKey("abiba", KKey.A),
            KInputBinding.ofKey("abibax", KKey.B)
        );
        ip.addControlScheme(sc);
        ip.disable();

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);

        ip.keyReleased(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);
        ip.keyReleased(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(0, p.f);

        ip.enable();

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);

        ip.keyReleased(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyReleased(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);
    }

    @Test
    public void testOverrideBinding() {
        TestInputEventProcessor p = new TestInputEventProcessor();
        KInputProcessor ip = new KStandardInputProcessor(p);
        KInputControlScheme sc = new KInputControlScheme(
            "test_1",
            KInputBinding.ofKey("abiba", KKey.A),
            KInputBinding.ofKey("abibax", KKey.B)
        );
        ip.addControlScheme(sc);
        sc.overrideAction(KInputBinding.ofKey("abibax", KKey.Y));

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyPressed(new KKeyInputData(KKey.Y, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);
    }

    @Test
    public void testOverrideBindingThatHasNotCompleted() {
        TestInputEventProcessor p = new TestInputEventProcessor();
        KInputProcessor ip = new KStandardInputProcessor(p);
        KInputControlScheme sc = new KInputControlScheme(
            "test_1",
            KInputBinding.ofKey("abiba", KKey.A),
            KInputBinding.ofKey("abibax", KKey.B)
        );
        ip.addControlScheme(sc);
        sc.overrideAction(KInputBinding.ofKey("abibay", KKey.Y));

        ip.keyPressed(new KKeyInputData(KKey.A, false, false, false, false, false));
        Assertions.assertEquals(1, p.f);
        ip.keyPressed(new KKeyInputData(KKey.B, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);
        ip.keyPressed(new KKeyInputData(KKey.Y, false, false, false, false, false));
        Assertions.assertEquals(3, p.f);

    }
}
