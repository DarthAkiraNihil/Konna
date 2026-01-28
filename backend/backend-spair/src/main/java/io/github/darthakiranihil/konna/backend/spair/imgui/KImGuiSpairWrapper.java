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

import imgui.ImGuiStyle;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.assertion.ImAssertCallback;
import imgui.callback.ImGuiInputTextCallback;
import imgui.internal.ImGuiContext;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImAssertCallback;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiContext;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiInputTextCallback;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiStyle;

@KExcludeFromGeneratedCoverageReport
final class KImGuiSpairWrapper extends KUninstantiable {

    private KImGuiSpairWrapper() {
        super();
    }

    public static ImVec2 wrap(final KVector2f original) {
        return new ImVec2(original.x(), original.y());
    }

    public static ImVec4 wrap(final KVector4f original) {
        return new ImVec4(original.x(), original.y(), original.z(), original.w());
    }

    public static ImGuiContext wrap(final KImGuiContext original) {
        return new ImGuiContext(original.handle());
    }

    public static ImAssertCallback wrap(final KImAssertCallback original) {
        return new KImAssertCallbackSpair(original);
    }

    public static ImGuiInputTextCallback wrap(final KImGuiInputTextCallback original) {
        return new KImGuiInputTextCallbackSpair(original);
    }

}
