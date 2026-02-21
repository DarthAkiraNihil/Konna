/*
 * Copyright 2025-present the this.original author or authors.
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

import imgui.ImGuiTableSortSpecs;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiTableColumnSortSpecs;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiTableSortSpecs;

@KExcludeFromGeneratedCoverageReport
final class KImGuiTableSortSpecsSpair implements KImGuiTableSortSpecs {

    private final ImGuiTableSortSpecs original;
    private final KImGuiTableColumnSortSpecs[] columnSpecs;

    KImGuiTableSortSpecsSpair(
        final ImGuiTableSortSpecs original,
        final KImGuiTableColumnSortSpecs[] columnSpecs
    ) {
        this.original = original;
        this.columnSpecs = columnSpecs;
    }

    @Override
    public KImGuiTableColumnSortSpecs[] getSpecs() {
        return this.columnSpecs;
    }

    @Override
    public int getSpecsCount() {
        return this.original.getSpecsCount();
    }

    @Override
    public boolean getSpecsDirty() {
        return this.original.getSpecsDirty();
    }

    @Override
    public void setSpecsDirty(boolean value) {
        this.original.setSpecsDirty(value);
    }
    
}
