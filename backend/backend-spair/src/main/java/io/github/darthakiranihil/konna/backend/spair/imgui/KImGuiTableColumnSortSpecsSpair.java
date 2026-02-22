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

import imgui.ImGuiTableColumnSortSpecs;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiTableColumnSortSpecs;

@KExcludeFromGeneratedCoverageReport
final class KImGuiTableColumnSortSpecsSpair implements KImGuiTableColumnSortSpecs {

    private final ImGuiTableColumnSortSpecs original;

    KImGuiTableColumnSortSpecsSpair(final ImGuiTableColumnSortSpecs original) {
        this.original = original;
    }

    @Override
    public int getColumnUserID() {
        return this.original.getColumnUserID();
    }

    @Override
    public int getColumnIndex() {
        return this.original.getColumnIndex();
    }

    @Override
    public int getSortOrder() {
        return this.original.getSortOrder();
    }

    @Override
    public int getSortDirection() {
        return this.original.getSortDirection();
    }


}
