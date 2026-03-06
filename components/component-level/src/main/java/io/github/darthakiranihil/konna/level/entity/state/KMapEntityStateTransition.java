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
import org.jspecify.annotations.Nullable;

public abstract class KMapEntityStateTransition {

    private KMapEntityState targetState;

    public KMapEntityStateTransition(final KUniversalMap params) {

    }

    public final @Nullable KMapEntityState tryGetTargetState() {
        if (this.checkTransitionTriggered()) {
            return this.targetState;
        }

        return null;
    }

    protected abstract boolean checkTransitionTriggered();

}
