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

import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.libfrontend.glfw.*;
import org.lwjgl.glfw.*;

final class KGlfwLwjglWrapper extends KUninstantiable {

    public static GLFWAllocator wrap(KGlfwAllocator original) {
        var internal = GLFWAllocator.create();
        internal.set(
            (size, user) -> original.allocate().invoke(size, user),
            (block, size, user) -> original.reallocate().invoke(block, size, user),
            new GLFWDeallocateCallback() {
                @Override
                public void invoke(long block, long user) {
                    original.deallocate().invoke(block, user);
                }
            },
            original.user()
        );
        return internal;
    }


    public static GLFWFramebufferSizeCallbackI wrap(KGlfwFramebufferSizeCallback original) {
        return original::invoke;
    }


    public static GLFWMouseButtonCallbackI wrap(KGlfwMouseButtonCallback original) {
        return original::invoke;
    }


    public static GLFWWindowIconifyCallbackI wrap(KGlfwWindowIconifyCallback original) {
        return original::invoke;
    }


    public static GLFWCharCallbackI wrap(KGlfwCharCallback original) {
        return original::invoke;
    }

    public static GLFWGammaRamp wrap(KGlfwGammaRamp original) {
        GLFWGammaRamp internal = GLFWGammaRamp.create();
        internal.set(original.red(), original.green(), original.blue(), original.size());
        return internal;
    }


    public static GLFWPreeditCallbackI wrap(KGlfwPreeditCallback original) {
        return original::invoke;
    }


    public static GLFWWindowMaximizeCallbackI wrap(KGlfwWindowMaximizeCallback original) {
        return original::invoke;
    }


    public static GLFWCharModsCallbackI wrap(KGlfwCharModsCallback original) {
        return original::invoke;
    }

    public static GLFWImage wrap(KGlfwImage original) {
        var internal = GLFWImage.create();
        internal.set(original.width(), original.height(), original.pixels());
        return internal;
    }


    public static GLFWPreeditCandidateCallbackI wrap(KGlfwPreeditCandidateCallback original) {
        return original::invoke;
    }


    public static GLFWWindowPosCallbackI wrap(KGlfwWindowPosCallback original) {
        return original::invoke;
    }


    public static GLFWCursorEnterCallbackI wrap(KGlfwCursorEnterCallback original) {
        return original::invoke;
    }


    public static GLFWIMEStatusCallbackI wrap(KGlfwImeStatusCallback original) {
        return original::invoke;
    }


    public static GLFWScrollCallbackI wrap(KGlfwScrollCallback original) {
        return original::invoke;
    }


    public static GLFWWindowRefreshCallbackI wrap(KGlfwWindowRefreshCallback original) {
        return original::invoke;
    }


    public static GLFWCursorPosCallbackI wrap(KGlfwCursorPosCallback original) {
        return original::invoke;
    }


    public static GLFWJoystickCallbackI wrap(KGlfwJoystickCallback original) {
        return original::invoke;
    }


    public static GLFWWindowCloseCallbackI wrap(KGlfwWindowCloseCallback original) {
        return original::invoke;
    }


    public static GLFWWindowSizeCallbackI wrap(KGlfwWindowSizeCallback original) {
        return original::invoke;
    }


    public static GLFWDropCallbackI wrap(KGlfwDropCallback original) {
        return original::invoke;
    }


    public static GLFWKeyCallbackI wrap(KGlfwKeyCallback original) {
        return original::invoke;
    }


    public static GLFWWindowContentScaleCallbackI wrap(KGlfwWindowContentScaleCallback original) {
        return original::invoke;
    }


    public static GLFWErrorCallbackI wrap(KGlfwErrorCallback original) {
        return original::invoke;
    }


    public static GLFWMonitorCallbackI wrap(KGlfwMonitorCallback original) {
        return original::invoke;
    }


    public static GLFWWindowFocusCallbackI wrap(KGlfwWindowFocusCallback original) {
        return original::invoke;
    }
}
