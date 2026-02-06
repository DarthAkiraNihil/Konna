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

/**
 * Interface representation of an action that is performed on certain input event.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public interface KInputBinding {

    /**
     * Creates input binding for that action that is performed on keyboard key press
     * or something like that.
     * @param action Action name
     * @param ofKey Key that is assigned to the action
     * @return Key binding to the action
     */
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
            public boolean isActionPerformed(final KInputData data) {
                if (!KKeyInputData.class.isAssignableFrom(data.getClass())) {
                    return false;
                }

                KKeyInputData keyData = (KKeyInputData) data;
                return keyData.key() == ofKey;
            }
        };
    }

    /**
     * @return Name of the action assigned to the binding
     */
    String getActionName();

    /**
     * Checks if the action can be performed for this acquired input data.
     * @param inputData Raw input data
     * @return Whether the action is triggered for this input data
     */
    boolean isActionPerformed(KInputData inputData);

}
