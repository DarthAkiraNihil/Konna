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

import imgui.ImGuiKeyData;
import io.github.darthakiranihil.konna.annotation.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiKeyData;

@KExcludeFromGeneratedCoverageReport
final class KImGuiKeyDataSpair implements KImGuiKeyData {

    private final ImGuiKeyData boxed;

    KImGuiKeyDataSpair(final ImGuiKeyData boxed) {
        this.boxed = boxed;
    }

    KImGuiKeyDataSpair() {
        this.boxed = new ImGuiKeyData();
    }

    @Override
    public void destroy() {
        this.boxed.destroy();
    }

    @Override
    public boolean getDown() {
        return this.boxed.getDown();
    }

    @Override
    public void setDown(boolean value) {
        this.boxed.setDown(value);
    }

    @Override
    public float getDownDuration() {
        return this.boxed.getDownDuration();
    }

    @Override
    public void setDownDuration(float value) {
        this.boxed.setDownDuration(value);
    }

    @Override
    public float getDownDurationPrev() {
        return this.boxed.getDownDurationPrev();
    }

    @Override
    public void setDownDurationPrev(float value) {
        this.boxed.setDownDurationPrev(value);
    }

    @Override
    public float getAnalogValue() {
        return this.boxed.getAnalogValue();
    }

    @Override
    public void setAnalogValue(float value) {
        this.boxed.setAnalogValue(value);
    }
}
