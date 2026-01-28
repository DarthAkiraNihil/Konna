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

import imgui.ImGuiInputTextCallbackData;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiContext;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiInputTextCallback;

@KExcludeFromGeneratedCoverageReport
final class KImGuiInputTextCallbackDataSpair implements KImGuiInputTextCallback.Data {

    private final ImGuiInputTextCallbackData original;

    KImGuiInputTextCallbackDataSpair(final ImGuiInputTextCallbackData original) {
        this.original = original;
    }

    @Override
    public KImGuiContext getCtx() {
        return KImGuiSpairUnwrapper.wrap(this.original.getCtx());
    }

    @Override
    public void setCtx(final KImGuiContext value) {
        this.original.setCtx(
            KImGuiSpairWrapper.wrap(value)
        );
    }

    @Override
    public int getEventFlag() {
        return this.original.getEventFlag();
    }

    @Override
    public boolean hasEventFlag(int flags) {
        return this.original.hasEventFlag(flags);
    }

    @Override
    public int getFlags() {
        return this.original.getFlags();
    }

    @Override
    public boolean hasFlags(int flags) {
        return this.original.hasFlags(flags);
    }

    @Override
    public int getEventChar() {
        return this.original.getEventChar();
    }

    @Override
    public void setEventChar(int value) {
        this.original.setEventChar(value);
    }

    @Override
    public int getEventKey() {
        return this.original.getEventKey();
    }

    @Override
    public String getBuf() {
        return this.original.getBuf();
    }

    @Override
    public void setBuf(final String value) {
        this.original.setBuf(value);
    }

    @Override
    public int getBufTextLen() {
        return this.original.getBufTextLen();
    }

    @Override
    public void setBufTextLen(int value) {
        this.original.setBufTextLen(value);
    }

    @Override
    public boolean getBufDirty() {
        return this.original.getBufDirty();
    }

    @Override
    public void setBufDirty(boolean value) {
        this.original.setBufDirty(value);
    }

    @Override
    public int getCursorPos() {
        return this.original.getCursorPos();
    }

    @Override
    public void setCursorPos(int value) {
        this.original.setCursorPos(value);
    }

    @Override
    public int getSelectionStart() {
        return this.original.getSelectionStart();
    }

    @Override
    public void setSelectionStart(int value) {
        this.original.setSelectionStart(value);
    }

    @Override
    public int getSelectionEnd() {
        return this.original.getSelectionEnd();
    }

    @Override
    public void setSelectionEnd(int value) {
        this.original.setSelectionEnd(value);
    }

    @Override
    public void deleteChars(int pos, int bytesCount) {
        this.original.deleteChars(pos, bytesCount);
    }

    @Override
    public void insertChars(int pos, final String str) {
        this.original.insertChars(pos, str);
    }

    @Override
    public void selectAll() {
        this.original.selectAll();
    }

    @Override
    public void clearSelection() {
        this.original.clearSelection();
    }

    @Override
    public boolean hasSelection() {
        return this.original.hasSelection();
    }
    
}
