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
import io.github.darthakiranihil.konna.libfrontend.imgui.*;

import java.nio.ByteBuffer;

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
        return new KImGuiInputTextCallback.Data() {
            @Override
            public KImGuiContext getCtx() {
                return KImGuiSpairUnwrapper.wrap(original.getCtx());
            }

            @Override
            public void setCtx(KImGuiContext value) {
                original.setCtx(
                    KImGuiSpairWrapper.wrap(value)
                );
            }

            @Override
            public int getEventFlag() {
                return original.getEventFlag();
            }

            @Override
            public boolean hasEventFlag(int flags) {
                return original.hasEventFlag(flags);
            }

            @Override
            public int getFlags() {
                return original.getFlags();
            }

            @Override
            public boolean hasFlags(int flags) {
                return original.hasFlags(flags);
            }

            @Override
            public int getEventChar() {
                return original.getEventChar();
            }

            @Override
            public void setEventChar(int value) {
                original.setEventChar(value);
            }

            @Override
            public int getEventKey() {
                return original.getEventKey();
            }

            @Override
            public String getBuf() {
                return original.getBuf();
            }

            @Override
            public void setBuf(String value) {
                original.setBuf(value);
            }

            @Override
            public int getBufTextLen() {
                return original.getBufTextLen();
            }

            @Override
            public void setBufTextLen(int value) {
                original.setBufTextLen(value);
            }

            @Override
            public boolean getBufDirty() {
                return original.getBufDirty();
            }

            @Override
            public void setBufDirty(boolean value) {
                original.setBufDirty(value);
            }

            @Override
            public int getCursorPos() {
                return original.getCursorPos();
            }

            @Override
            public void setCursorPos(int value) {
                original.setCursorPos(value);
            }

            @Override
            public int getSelectionStart() {
                return original.getSelectionStart();
            }

            @Override
            public void setSelectionStart(int value) {
                original.setSelectionStart(value);
            }

            @Override
            public int getSelectionEnd() {
                return original.getSelectionEnd();
            }

            @Override
            public void setSelectionEnd(int value) {
                original.setSelectionEnd(value);
            }

            @Override
            public void deleteChars(int pos, int bytesCount) {
                original.deleteChars(pos, bytesCount);
            }

            @Override
            public void insertChars(int pos, String str) {
                original.insertChars(pos, str);
            }

            @Override
            public void selectAll() {
                original.selectAll();
            }

            @Override
            public void clearSelection() {
                original.clearSelection();
            }

            @Override
            public boolean hasSelection() {
                return original.hasSelection();
            }
        };
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
        return new KImGuiTableColumnSortSpecs() {
            @Override
            public int getColumnUserID() {
                return original.getColumnUserID();
            }

            @Override
            public int getColumnIndex() {
                return original.getColumnIndex();
            }

            @Override
            public int getSortOrder() {
                return original.getSortOrder();
            }

            @Override
            public int getSortDirection() {
                return original.getSortDirection();
            }
        };
    }

    public static KImGuiTableSortSpecs wrap(final ImGuiTableSortSpecs original) {

        ImGuiTableColumnSortSpecs[] specs = original.getSpecs();
        KImGuiTableColumnSortSpecs[] cs = new KImGuiTableColumnSortSpecs[specs.length];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = KImGuiSpairUnwrapper.wrap(specs[i]);
        }

        return new KImGuiTableSortSpecs() {

            private final KImGuiTableColumnSortSpecs[] columnSpecs = cs;

            @Override
            public KImGuiTableColumnSortSpecs[] getSpecs() {
                return this.columnSpecs;
            }

            @Override
            public int getSpecsCount() {
                return original.getSpecsCount();
            }

            @Override
            public boolean getSpecsDirty() {
                return original.getSpecsDirty();
            }

            @Override
            public void setSpecsDirty(boolean value) {
                original.setSpecsDirty(value);
            }
        };
    }

    public static KImGuiStorage wrap(final ImGuiStorage original) {
        return new KImGuiStorageSpair(original);
    }

}
