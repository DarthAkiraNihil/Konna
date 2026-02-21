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

import imgui.*;
import imgui.internal.ImGuiContext;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.*;

@KExcludeFromGeneratedCoverageReport
final class KImGuiSpairUnwrapper extends KUninstantiable {

    private KImGuiSpairUnwrapper() {
        super();
    }

    public static KVector2f wrap(final ImVec2 original) {
        return new KVector2f(original.x, original.y);
    }

    public static KVector4f wrap(final ImVec4 original) {
        return new KVector4f(original.x, original.y, original.z, original.w);
    }

    public static KImGuiContext wrap(final ImGuiContext original) {
        return () -> original.ptr;
    }

    public static KImGuiInputTextCallback.Data wrap(final ImGuiInputTextCallbackData original) {
        return new KImGuiInputTextCallbackDataSpair(original);
    }

    public static KImGuiStyle wrap(final ImGuiStyle original) {
        return new KImGuiStyleSpair(original);
    }

    public static KImGuiViewport wrap(final ImGuiViewport original) {
        return new KImGuiViewportSpair(original);
    }

    public static KImFontGlyph wrap(final ImFontGlyph original) {
        return new KImFontGlyphSpair(original);
    }

    public static KImFont wrap(final ImFont original) {
        return new KImFontSpair(original);
    }

    public static KImDrawList wrap(final ImDrawList original) {
        return new KImDrawListSpair(original);
    }

    public static KImDrawData wrap(final ImDrawData original) {
        return new KImDrawDataSpair(original);
    }

    public static KImGuiWindowClass wrap(final ImGuiWindowClass original) {
        return new KImGuiWindowClassSpair(original);
    }

    public static KImGuiPlatformIo wrap(final ImGuiPlatformIO original) {
        return new KImGuiPlatformIoSpair(original);
    }

    public static KImGuiKeyData wrap(final ImGuiKeyData original) {
        return new KImGuiKeyDataSpair(original);
    }

    public static KImFontAtlas wrap(final ImFontAtlas original) {
        return new KImFontAtlasSpair(original);
    }

    public static KImGuiIo wrap(final ImGuiIO original) {
        return new KImGuiIoSpair(original);
    }

    public static KImGuiTableColumnSortSpecs wrap(final ImGuiTableColumnSortSpecs original) {
        return new KImGuiTableColumnSortSpecsSpair(original);
    }

    public static KImGuiTableSortSpecs wrap(final ImGuiTableSortSpecs original) {

        ImGuiTableColumnSortSpecs[] specs = original.getSpecs();
        KImGuiTableColumnSortSpecs[] cs = new KImGuiTableColumnSortSpecs[specs.length];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = KImGuiSpairUnwrapper.wrap(specs[i]);
        }

        return new KImGuiTableSortSpecsSpair(original, cs);
    }

    public static KImGuiStorage wrap(final ImGuiStorage original) {
        return new KImGuiStorageSpair(original);
    }

    public static KImGuiPlatformMonitor wrap(final ImGuiPlatformMonitor original) {
        return new KImGuiPlatformMonitorSpair(original);
    }

}
