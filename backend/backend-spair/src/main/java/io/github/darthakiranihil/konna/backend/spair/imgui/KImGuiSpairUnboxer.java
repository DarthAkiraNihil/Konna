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
import io.github.darthakiranihil.konna.core.except.KBackendError;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.libfrontend.imgui.*;

@KExcludeFromGeneratedCoverageReport
final class KImGuiSpairUnboxer extends KUninstantiable {

    public static ImFont unbox(final KImFont original) {
        ImFont f = KReflectionUtils.getField(
            KImFontSpair.class,
            original,
            "boxed",
            ImFont.class
        );

        if (f != null) {
            return f;
        }

        throw new KBackendError(
                "Could not unbox ImFont from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );

    }

    public static ImFontGlyph unbox(final KImFontGlyph original) {

        ImFontGlyph gl = KReflectionUtils.getField(
            KImFontGlyphSpair.class,
            original,
            "boxed",
            ImFontGlyph.class
        );

        if (gl != null) {
            return gl;
        }

        throw new KBackendError(
            "Could not unbox ImFontGlyph from passed wrapper. "
                +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImDrawList unbox(final KImDrawList original) {
        ImDrawList dl = KReflectionUtils.getField(
            KImDrawListSpair.class,
            original,
            "boxed",
            ImDrawList.class
        );

        if (dl != null) {
            return dl;
        }

        throw new KBackendError(
                "Could not unbox ImDrawList from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImDrawData unbox(final KImDrawData original) {
        ImDrawData dd = KReflectionUtils.getField(
            KImDrawDataSpair.class,
            original,
            "boxed",
            ImDrawData.class
        );

        if (dd != null) {
            return dd;
        }

        throw new KBackendError(
                "Could not unbox ImDrawData from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImGuiViewport unbox(final KImGuiViewport original) {
        ImGuiViewport vp = KReflectionUtils.getField(
            KImGuiViewportSpair.class,
            original,
            "boxed",
            ImGuiViewport.class
        );

        if (vp != null) {
            return vp;
        }

        throw new KBackendError(
                "Could not unbox ImGuiViewport from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImGuiWindowClass unbox(final KImGuiWindowClass original) {
        ImGuiWindowClass wc = KReflectionUtils.getField(
            KImGuiWindowClassSpair.class,
            original,
            "boxed",
            ImGuiWindowClass.class
        );

        if (wc != null) {
            return wc;
        }

        throw new KBackendError(
                "Could not unbox ImGuiWindowClass from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImGuiKeyData unbox(final KImGuiKeyData original) {
        ImGuiKeyData kd = KReflectionUtils.getField(
            KImGuiKeyDataSpair.class,
            original,
            "boxed",
            ImGuiKeyData.class
        );

        if (kd != null) {
            return kd;
        }

        throw new KBackendError(
                "Could not unbox ImGuiKeyData from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImFontConfig unbox(final KImFontConfig original) {
        ImFontConfig fc = KReflectionUtils.getField(
            KImFontConfigSpair.class,
            original,
            "boxed",
            ImFontConfig.class
        );

        if (fc != null) {
            return fc;
        }

        throw new KBackendError(
                "Could not unbox ImFontConfig from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImFontAtlas unbox(final KImFontAtlas original) {
        ImFontAtlas fc = KReflectionUtils.getField(
            KImFontAtlasSpair.class,
            original,
            "boxed",
            ImFontAtlas.class
        );

        if (fc != null) {
            return fc;
        }

        throw new KBackendError(
            "Could not unbox ImFontAtlas from passed wrapper. "
                +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImGuiStorage unbox(final KImGuiStorage original) {
        ImGuiStorage s = KReflectionUtils.getField(
            KImGuiStorageSpair.class,
            original,
            "boxed",
            ImGuiStorage.class
        );

        if (s != null) {
            return s;
        }

        throw new KBackendError(
                "Could not unbox ImGuiStorage from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

    public static ImGuiStyle unbox(final KImGuiStyle original) {
        ImGuiStyle s = KReflectionUtils.getField(
            KImGuiStyleSpair.class,
            original,
            "boxed",
            ImGuiStyle.class
        );

        if (s != null) {
            return s;
        }

        throw new KBackendError(
                "Could not unbox ImGuiStyle from passed wrapper. "
            +   "Maybe it does not belong to the backend?"
        );
    }

}
