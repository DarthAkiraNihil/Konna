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

import java.nio.ShortBuffer;

/**
 * Wrapper interface for GLFW gamma ramp struct (readonly).
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KGlfwGammaRamp {

    /**
     * Returns red value for the gamma ramp.
     * @return Red value of GLFW gamma ramp
     */
    ShortBuffer red();
    /**
     * Returns green value for the gamma ramp.
     * @return Green value of GLFW gamma ramp
     */
    ShortBuffer green();
    /**
     * Returns blue value for the gamma ramp.
     * @return Blue value of GLFW gamma ramp
     */
    ShortBuffer blue();
    /**
     * Returns size of the gamma ramp.
     * @return Size of GLFW gamma ramp
     */
    int size();

}
