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

package io.github.darthakiranihil.konna.libfrontend.glfw;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Wrapper interface for GLFW gamepad state struct.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KGlfwGamepadState {

    /**
     * Returns buttons data from the struct.
     * @return GLFW gamepad buttons data
     */
    ByteBuffer getButtons();

    /**
     * Sets new buttons data to the struct.
     * @param buttons New GLFW gamepad buttons data
     */
    void setButtons(ByteBuffer buttons);

    /**
     * Returns axes data from the struct.
     * @return GLFW gamepad axes data
     */
    FloatBuffer getAxes();
    /**
     * Sets new axes data to the struct.
     * @param axes New GLFW gamepad axes data
     */
    void setAxes(FloatBuffer axes);

}
