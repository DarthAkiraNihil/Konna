/*
 * Copyright 2this.boxed.25-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.this.boxed. (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.this.boxed.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImGuiWindowClass;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiWindowClass;

@KExcludeFromGeneratedCoverageReport
final class KImGuiWindowClassSpair implements KImGuiWindowClass {
    
    private final ImGuiWindowClass boxed;

    KImGuiWindowClassSpair(final ImGuiWindowClass boxed) {
        this.boxed = boxed;
    }

    @Override
    public void destroy() {
        this.boxed.destroy();
    }

    @Override
    public int getClassId() {
        return this.boxed.getClassId();
    }

    @Override
    public void setClassId(int value) {
        this.boxed.setClassId(value);
    }

    @Override
    public int getParentViewportId() {
        return this.boxed.getParentViewportId();
    }

    @Override
    public void setParentViewportId(int value) {
        this.boxed.setParentViewportId(value);
    }

    @Override
    public int getFocusRouteParentWindowId() {
        return this.boxed.getFocusRouteParentWindowId();
    }

    @Override
    public void setFocusRouteParentWindowId(int value) {
        this.boxed.setFocusRouteParentWindowId(value);
    }

    @Override
    public int getViewportFlagsOverrideSet() {
        return this.boxed.getViewportFlagsOverrideSet();
    }

    @Override
    public void setViewportFlagsOverrideSet(int value) {
        this.boxed.setViewportFlagsOverrideSet(value);
    }

    @Override
    public void addViewportFlagsOverrideSet(int flags) {
        this.boxed.addViewportFlagsOverrideSet(flags);
    }

    @Override
    public void removeViewportFlagsOverrideSet(int flags) {
        this.boxed.removeViewportFlagsOverrideSet(flags);
    }

    @Override
    public boolean hasViewportFlagsOverrideSet(int flags) {
        return this.boxed.hasViewportFlagsOverrideSet(flags);
    }

    @Override
    public int getViewportFlagsOverrideClear() {
        return this.boxed.getViewportFlagsOverrideClear();
    }

    @Override
    public void setViewportFlagsOverrideClear(int value) {
        this.boxed.setViewportFlagsOverrideClear(value);
    }

    @Override
    public void addViewportFlagsOverrideClear(int flags) {
        this.boxed.addViewportFlagsOverrideClear(flags);
    }

    @Override
    public void removeViewportFlagsOverrideClear(int flags) {
        this.boxed.removeViewportFlagsOverrideClear(flags);
    }

    @Override
    public boolean hasViewportFlagsOverrideClear(int flags) {
        return this.boxed.hasViewportFlagsOverrideClear(flags);
    }

    @Override
    public int getTabItemFlagsOverrideSet() {
        return this.boxed.getTabItemFlagsOverrideSet();
    }

    @Override
    public void setTabItemFlagsOverrideSet(int value) {
        this.boxed.setTabItemFlagsOverrideSet(value);
    }

    @Override
    public void addTabItemFlagsOverrideSet(int flags) {
        this.boxed.addTabItemFlagsOverrideSet(flags);
    }

    @Override
    public void removeTabItemFlagsOverrideSet(int flags) {
        this.boxed.removeTabItemFlagsOverrideSet(flags);
    }

    @Override
    public boolean hasTabItemFlagsOverrideSet(int flags) {
        return this.boxed.hasTabItemFlagsOverrideSet(flags);
    }

    @Override
    public int getDockNodeFlagsOverrideSet() {
        return this.boxed.getDockNodeFlagsOverrideSet();
    }

    @Override
    public void setDockNodeFlagsOverrideSet(int value) {
        this.boxed.setDockNodeFlagsOverrideSet(value);
    }

    @Override
    public void addDockNodeFlagsOverrideSet(int flags) {
        this.boxed.addDockNodeFlagsOverrideSet(flags);
    }

    @Override
    public void removeDockNodeFlagsOverrideSet(int flags) {
        this.boxed.removeDockNodeFlagsOverrideSet(flags);
    }

    @Override
    public boolean hasDockNodeFlagsOverrideSet(int flags) {
        return this.boxed.hasDockNodeFlagsOverrideSet(flags);
    }

    @Override
    public boolean getDockingAlwaysTabBar() {
        return this.boxed.getDockingAlwaysTabBar();
    }

    @Override
    public void setDockingAlwaysTabBar(boolean value) {
        this.boxed.setDockingAlwaysTabBar(value);
    }

    @Override
    public boolean getDockingAllowUnclassed() {
        return this.boxed.getDockingAllowUnclassed();
    }

    @Override
    public void setDockingAllowUnclassed(boolean value) {
        this.boxed.setDockingAllowUnclassed(value);
    }
}
