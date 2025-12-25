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

/**
 * Wrapper interface for GLFW VidMode struct (readonly).
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KGlfwVidMode {

    /**
     * Returns width of this video mode.
     * @return Width of this GLFW video mode
     */
    int width();
    /**
     * Returns height of this video mode.
     * @return height of this GLFW video mode
     */
    int height();
    /**
     * Returns red bits of this video mode.
     * @return Red bits of this GLFW video mode
     */
    int redBits();
    /**
     * Returns green bits of this video mode.
     * @return Green bits of this GLFW video mode
     */
    int greenBits();
    /**
     * Returns blue bits of this video mode.
     * @return Blue bits of this GLFW video mode
     */
    int blueBits();
    /**
     * Returns refresh rate of this video mode.
     * @return Refresh rate of this GLFW video mode
     */
    int refreshRate();

}
