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

package io.github.darthakiranihil.konna.core.io.control;

import org.jspecify.annotations.Nullable;

public interface KInputBinding {

    static KInputBinding ofKey(
        final String action,
        final KKey ofKey
    ) {
        return new KInputBinding() {
            @Override
            public String getActionName() {
                return action;
            }

            @Override
            public @Nullable String isActionPerformed(KInputData data) {
                if (!KKeyInputData.class.isAssignableFrom(data.getClass())) {
                    return null;
                }

                KKeyInputData keyData = (KKeyInputData) data;
                return keyData.key() == ofKey ? action : null;
            }
        };
    }

    String getActionName();
    @Nullable String isActionPerformed(KInputData inputData);

}
