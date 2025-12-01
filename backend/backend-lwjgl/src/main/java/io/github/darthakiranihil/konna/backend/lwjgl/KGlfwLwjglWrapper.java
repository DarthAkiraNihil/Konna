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

    public static GLFWAllocator wrap(final KGlfwAllocator original) {
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


    public static GLFWFramebufferSizeCallbackI wrap(final KGlfwFramebufferSizeCallback original) {
        return original::invoke;
    }


    public static GLFWMouseButtonCallbackI wrap(final KGlfwMouseButtonCallback original) {
        return original::invoke;
    }


    public static GLFWWindowIconifyCallbackI wrap(final KGlfwWindowIconifyCallback original) {
        return original::invoke;
    }


    public static GLFWCharCallbackI wrap(final KGlfwCharCallback original) {
        return original::invoke;
    }

    public static GLFWGammaRamp wrap(final KGlfwGammaRamp original) {
        GLFWGammaRamp internal = GLFWGammaRamp.create();
        internal.set(original.red(), original.green(), original.blue(), original.size());
        return internal;
    }


    public static GLFWPreeditCallbackI wrap(final KGlfwPreeditCallback original) {
        return original::invoke;
    }


    public static GLFWWindowMaximizeCallbackI wrap(final KGlfwWindowMaximizeCallback original) {
        return original::invoke;
    }


    public static GLFWCharModsCallbackI wrap(final KGlfwCharModsCallback original) {
        return original::invoke;
    }

    public static GLFWImage wrap(final KGlfwImage original) {
        var internal = GLFWImage.create();
        internal.set(original.width(), original.height(), original.pixels());
        return internal;
    }


    public static GLFWPreeditCandidateCallbackI wrap(final KGlfwPreeditCandidateCallback original) {
        return original::invoke;
    }


    public static GLFWWindowPosCallbackI wrap(final KGlfwWindowPosCallback original) {
        return original::invoke;
    }


    public static GLFWCursorEnterCallbackI wrap(final KGlfwCursorEnterCallback original) {
        return original::invoke;
    }


    public static GLFWIMEStatusCallbackI wrap(final KGlfwImeStatusCallback original) {
        return original::invoke;
    }


    public static GLFWScrollCallbackI wrap(final KGlfwScrollCallback original) {
        return original::invoke;
    }


    public static GLFWWindowRefreshCallbackI wrap(final KGlfwWindowRefreshCallback original) {
        return original::invoke;
    }


    public static GLFWCursorPosCallbackI wrap(final KGlfwCursorPosCallback original) {
        return original::invoke;
    }


    public static GLFWJoystickCallbackI wrap(final KGlfwJoystickCallback original) {
        return original::invoke;
    }


    public static GLFWWindowCloseCallbackI wrap(final KGlfwWindowCloseCallback original) {
        return original::invoke;
    }


    public static GLFWWindowSizeCallbackI wrap(final KGlfwWindowSizeCallback original) {
        return original::invoke;
    }


    public static GLFWDropCallbackI wrap(final KGlfwDropCallback original) {
        return original::invoke;
    }


    public static GLFWKeyCallbackI wrap(final KGlfwKeyCallback original) {
        return original::invoke;
    }


    public static GLFWWindowContentScaleCallbackI wrap(
        final KGlfwWindowContentScaleCallback original
    ) {
        return original::invoke;
    }


    public static GLFWErrorCallbackI wrap(final KGlfwErrorCallback original) {
        return original::invoke;
    }


    public static GLFWMonitorCallbackI wrap(final KGlfwMonitorCallback original) {
        return original::invoke;
    }


    public static GLFWWindowFocusCallbackI wrap(final KGlfwWindowFocusCallback original) {
        return original::invoke;
    }
}
