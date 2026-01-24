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

package io.github.darthakiranihil.konna.libfrontend.imgui;

/**
 * Interface representing ImFontGlyph of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImFontGlyph {

    int getColored();
    void setColored(int value);

    int getVisible();
    void setVisible(int value);

    int getCodepoint();
    void setCodepoint(int value);

    float getAdvanceX();
    void setAdvanceX(float value);

    float getX0();
    void setX0(float value);

    float getY0();
    void setY0(float value);

    float getX1();
    void setX1(float value);

    float getY1();
    void setY1(float value);

    float getU0();
    void setU0(float value);

    float getV0();
    void setV0(float value);

    float getU1();
    void setU1(float value);

    float getV1();
    void setV1(float value);

}
