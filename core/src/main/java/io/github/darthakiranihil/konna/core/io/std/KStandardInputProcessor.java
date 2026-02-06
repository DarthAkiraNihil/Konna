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

package io.github.darthakiranihil.konna.core.io.std;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.control.KInputEventData;
import io.github.darthakiranihil.konna.core.io.control.KInputProcessor;
import io.github.darthakiranihil.konna.core.io.control.KKeyEventData;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.object.KObject;

public class KStandardInputProcessor extends KObject implements KInputProcessor {

    public final String INPUT_EVENT_NAME = "input_event";

    private final KEventSystem eventSystem;
    private final KEvent<KInputEventData> inputEvent;

    private boolean enabled;

    public KStandardInputProcessor(@KInject final KEventSystem eventSystem) {
        this.eventSystem = eventSystem;
        this.inputEvent = new KEvent<>(INPUT_EVENT_NAME);
        this.eventSystem.registerEvent(this.inputEvent);
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void keyPressed(KKeyEventData data) {
        if (!this.enabled) {
            return;
        }
    }

    @Override
    public void keyReleased(KKeyEventData data) {
        if (!this.enabled) {
            return;
        }
    }
}
