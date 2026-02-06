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
import io.github.darthakiranihil.konna.core.io.control.*;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

@KSingleton
public class KStandardInputProcessor extends KObject implements KInputProcessor {

    private final List<KInputControlScheme> controlSchemes;
    private final KInputEventProcessor inputEventProcessor;

    private boolean enabled;

    public KStandardInputProcessor(
        @KInject final KInputEventProcessor inputEventProcessor
    ) {
        this.inputEventProcessor = inputEventProcessor;
        this.controlSchemes = new LinkedList<>();
        this.enabled = true;
    }

    @Override
    public void addControlScheme(KInputControlScheme scheme) {
        this.controlSchemes.add(scheme);
    }

    @Override
    public @Nullable KInputControlScheme getControlScheme(String name) {
        return this
            .controlSchemes
            .stream()
            .filter(s -> s.getName().equals(name))
            .findFirst()
            .orElse(null);
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
    public void keyPressed(KKeyInputData data) {
        if (!this.enabled) {
            return;
        }

        this.processEvent(data);
    }

    @Override
    public void keyReleased(KKeyInputData data) {
        if (!this.enabled) {
            return;
        }

        this.processEvent(data);
    }

    private void processEvent(final KInputData data) {
        for (KInputControlScheme scheme: this.controlSchemes) {
            var actions = scheme.getPerformedActions(data);
            for (KInputEventData action: actions) {
                this.inputEventProcessor.process(action);
            }
        }
    }
}
