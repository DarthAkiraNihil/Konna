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

package io.github.darthakiranihil.konna.core.imgui;

public interface KImGuiInputTextCallback {

    interface Data {

        KImGuiContext getCtx();
        void setCtx(KImGuiContext value);
        int getEventFlag();
        boolean hasEventFlag(int flags);
        int getFlags();
        boolean hasFlags(int flags);
        int getEventChar();
        void setEventChar(int value);
        int getEventKey();
        String getBuf();
        void setBuf(String value);
        int getBufTextLen();
        void setBufTextLen(int value);
        boolean getBufDirty();
        void setBufDirty(boolean value);
        int getCursorPos();
        void setCursorPos(int value);
        int getSelectionStart();
        void setSelectionStart(int value);
        int getSelectionEnd();
        void setSelectionEnd(int value);
        void deleteChars(int pos, int bytesCount);
        void insertChars(int pos, String str);
        void selectAll();
        void clearSelection();
        boolean hasSelection();
        
    }

    void accept(Data data);

}
