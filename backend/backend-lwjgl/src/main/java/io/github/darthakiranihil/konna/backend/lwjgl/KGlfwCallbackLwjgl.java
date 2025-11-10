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

package io.github.darthakiranihil.konna.backend.lwjgl;

import io.github.darthakiranihil.konna.libfrontend.glfw.KGlfwCallbacks;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;

public final class KGlfwCallbackLwjgl implements KGlfwCallbacks {

    @Override
    public void glfwFreeCallbacks(long window) {
        Callbacks.glfwFreeCallbacks(window);
    }

    @Override
    public void freeLastCallback(long window) {
        var callback =GLFW.glfwSetCharCallback(window, null);
        if (callback != null) {
            callback.free();
        }
    }

}
