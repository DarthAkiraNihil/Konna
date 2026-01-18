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

public interface KImFontGlyph {

    int getColored();
    int setColored(int value);

    int getVisible();
    int setVisible(int value);

    int getCodepoint();
    int setCodepoint(int value);

    float getAdvanceX();
    float setAdvanceX(float value);

    float getX0();
    float setX0(float value);

    float getY0();
    float setY0(float value);

    float getX1();
    float setX1(float value);

    float getY1();
    float setY1(float value);

    float getU0();
    float setU0(float value);

    float getV0();
    float setV0(float value);

    float getU1();
    float setU1(float value);

    float getV1();
    float setV1(float value);

}
