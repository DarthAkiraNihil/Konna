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

package io.github.darthakiranihil.konna.level.entity.state;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class KMapEntityState {

    private final String name;
    private final List<KMapEntityStateTransition> transitions;

    public KMapEntityState(final String name, final KUniversalMap params) {
        this.name = name;
        this.transitions = new ArrayList<>();
    }

    public abstract KVector2i getNextMoveDirection();

    public final String getName() {
        return this.name;
    }

    public final KMapEntityState addTransition(
        final KMapEntityStateTransition transition
    ) {
        this.transitions.add(transition);
        return this;
    }

    public final KMapEntityState getNextState() {

        for (var transition: this.transitions) {
            KMapEntityState next = transition.tryGetTargetState();
            if (next != null) {
                return next;
            }
        }

        return this;
    }

}
