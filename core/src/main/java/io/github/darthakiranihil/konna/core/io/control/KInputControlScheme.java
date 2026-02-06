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

import java.util.LinkedList;
import java.util.List;

/**
 * Representation of an input control scheme that is a group
 * of input bindings representing logically similar actions.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public class KInputControlScheme {

    private final String name;
    private final KInputBinding[] bindings;
    private boolean enabled;

    /**
     * Standard constructor. Creates an enabled scheme
     * @param name Name of the scheme
     * @param bindings Bindings assigned to this scheme
     */
    public KInputControlScheme(final String name, final KInputBinding... bindings) {
        this.name = name;
        this.bindings = bindings;
        this.enabled = true;
    }

    /**
     * Returns all triggered actions for this input data if it is enabled.
     * @param inputData Raw input data
     * @return List of data of all triggered actions for this input data.
     *         If it is disabled, an empty list will be returned.
     */
    public List<KInputEventData> getPerformedActions(final KInputData inputData) {
        List<KInputEventData> performedActions = new LinkedList<>();
        if (!this.enabled) {
            return performedActions;
        }

        for (KInputBinding binding: this.bindings) {
            if (binding.isActionPerformed(inputData)) {
                performedActions.add(
                    new KInputEventData(this.name, binding.getActionName())
                );
            }
        }
        return performedActions;
    }

    /**
     * Overrides binding in this scheme. If it is not found, then nothing happens.
     * @param binding Binding to override in this scheme
     */
    public void overrideAction(final KInputBinding binding) {

        String actionName = binding.getActionName();
        for (int i = 0; i < this.bindings.length; i++) {
            if (this.bindings[i].getActionName().equals(actionName)) {
                this.bindings[i] = binding;
                return;
            }
        }

    }

    /**
     * @return Name of this scheme
     */
    public String getName() {
        return this.name;
    }

    /**
     * Enables this scheme. Now it will process input events.
     */
    public void enable() {
        this.enabled = true;
    }

    /**
     * Disables this scheme. Now it won't process input events.
     */
    public void disable() {
        this.enabled = false;
    }
}
