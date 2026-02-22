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

package io.github.darthakiranihil.konna.backend.lwjgl.glfw;

import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.glfw.*;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.*;

@KExcludeFromGeneratedCoverageReport
final class KGlfwLwjglWrapper extends KUninstantiable {

    public static @Nullable GLFWAllocator wrap(final @Nullable KGlfwAllocator original) {
        if (original == null) {
            return null;
        }
        var internal = GLFWAllocator.create();
        internal.set(
            (size, user) -> original.allocate().invoke(size, user),
            (block, size, user) -> original.reallocate().invoke(block, size, user),
            (block, user) -> original.deallocate().invoke(block, user),
            original.user()
        );
        return internal;
    }


    public static @Nullable GLFWFramebufferSizeCallbackI wrap(
        final @Nullable KGlfwFramebufferSizeCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWMouseButtonCallbackI wrap(
        final @Nullable KGlfwMouseButtonCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowIconifyCallbackI wrap(
        final @Nullable KGlfwWindowIconifyCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWCharCallbackI wrap(final @Nullable KGlfwCharCallback original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }

    public static GLFWGammaRamp wrap(final KGlfwGammaRamp original) {
        GLFWGammaRamp internal = GLFWGammaRamp.create();
        internal.set(original.red(), original.green(), original.blue(), original.size());
        return internal;
    }


    public static @Nullable GLFWPreeditCallbackI wrap(
        final @Nullable KGlfwPreeditCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowMaximizeCallbackI wrap(
        final @Nullable KGlfwWindowMaximizeCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWCharModsCallbackI wrap(
        final @Nullable KGlfwCharModsCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }

    public static GLFWImage wrap(final KGlfwImage original) {
        var internal = GLFWImage.create();
        internal.set(original.width(), original.height(), original.pixels());
        return internal;
    }


    public static @Nullable GLFWPreeditCandidateCallbackI wrap(
        final @Nullable KGlfwPreeditCandidateCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowPosCallbackI wrap(
        final @Nullable KGlfwWindowPosCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWCursorEnterCallbackI wrap(
        final @Nullable KGlfwCursorEnterCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWIMEStatusCallbackI wrap(
        final @Nullable KGlfwImeStatusCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWScrollCallbackI wrap(final @Nullable KGlfwScrollCallback original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowRefreshCallbackI wrap(
        final @Nullable KGlfwWindowRefreshCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWCursorPosCallbackI wrap(
        final @Nullable KGlfwCursorPosCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWJoystickCallbackI wrap(
        final @Nullable KGlfwJoystickCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowCloseCallbackI wrap(
        final @Nullable KGlfwWindowCloseCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowSizeCallbackI wrap(
        final @Nullable KGlfwWindowSizeCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWDropCallbackI wrap(final @Nullable KGlfwDropCallback original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWKeyCallbackI wrap(final @Nullable KGlfwKeyCallback original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowContentScaleCallbackI wrap(
        final @Nullable KGlfwWindowContentScaleCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWErrorCallbackI wrap(final @Nullable KGlfwErrorCallback original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWMonitorCallbackI wrap(
        final @Nullable KGlfwMonitorCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable GLFWWindowFocusCallbackI wrap(
        final @Nullable KGlfwWindowFocusCallback original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }
}
