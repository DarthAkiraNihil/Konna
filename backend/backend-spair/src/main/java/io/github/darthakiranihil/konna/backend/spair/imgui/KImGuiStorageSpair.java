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

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImGuiStorage;
import io.github.darthakiranihil.konna.core.di.KInjectedConstructor;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiStorage;

@KExcludeFromGeneratedCoverageReport
final class KImGuiStorageSpair implements KImGuiStorage {

    private final ImGuiStorage boxed;

    KImGuiStorageSpair(final ImGuiStorage boxed) {
        this.boxed = boxed;
    }

    @KInjectedConstructor
    KImGuiStorageSpair() {
        this.boxed = new ImGuiStorage();
    }

    @Override
    public void destroy() {
        this.boxed.destroy();
    }

    @Override
    public void clear() {
        this.boxed.clear();
    }

    @Override
    public int getInt(int key) {
        return this.boxed.getInt(key);
    }

    @Override
    public int getInt(int key, int defaultVal) {
        return this.boxed.getInt(key, defaultVal);
    }

    @Override
    public void setInt(int key, int val) {
        this.boxed.setInt(key, val);
    }

    @Override
    public boolean getBool(int key) {
        return this.boxed.getBool(key);
    }

    @Override
    public boolean getBool(int key, boolean defaultVal) {
        return this.boxed.getBool(key, defaultVal);
    }

    @Override
    public void setBool(int key, boolean val) {
        this.boxed.setBool(key, val);
    }

    @Override
    public float getFloat(int key) {
        return this.boxed.getFloat(key);
    }

    @Override
    public float getFloat(int key, float defaultVal) {
        return this.boxed.getFloat(key, defaultVal);
    }

    @Override
    public void setFloat(int key, float val) {
        this.boxed.setFloat(key, val);
    }

    @Override
    public void buildSortByKey() {
        this.boxed.buildSortByKey();
    }

    @Override
    public void setAllInt(int val) {
        this.boxed.setAllInt(val);
    }

}
