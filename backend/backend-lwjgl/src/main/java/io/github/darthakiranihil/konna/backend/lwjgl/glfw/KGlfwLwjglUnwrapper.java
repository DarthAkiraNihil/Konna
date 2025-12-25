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
import io.github.darthakiranihil.konna.libfrontend.glfw.*;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.*;

import java.nio.ShortBuffer;

final class KGlfwLwjglUnwrapper extends KUninstantiable {

    private KGlfwLwjglUnwrapper() {
        super();
    }


    public static @Nullable KGlfwJoystickCallback wrap(final @Nullable GLFWJoystickCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowContentScaleCallback wrap(
        final @Nullable GLFWWindowContentScaleCallbackI original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwCharModsCallback wrap(final @Nullable GLFWCharModsCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }

    public static @Nullable KGlfwCharCallback wrap(final @Nullable GLFWCharCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwKeyCallback wrap(final @Nullable GLFWKeyCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowFocusCallback wrap(final @Nullable GLFWWindowFocusCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwCursorEnterCallback wrap(final @Nullable GLFWCursorEnterCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwMonitorCallback wrap(final @Nullable GLFWMonitorCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowIconifyCallback wrap(final @Nullable GLFWWindowIconifyCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwCursorPosCallback wrap(final @Nullable GLFWCursorPosCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwMouseButtonCallback wrap(final @Nullable GLFWMouseButtonCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowMaximizeCallback wrap(final @Nullable GLFWWindowMaximizeCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwDropCallback wrap(final @Nullable GLFWDropCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwPreeditCallback wrap(final @Nullable GLFWPreeditCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowPosCallback wrap(final @Nullable GLFWWindowPosCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwErrorCallback wrap(final @Nullable GLFWErrorCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwPreeditCandidateCallback wrap(final @Nullable GLFWPreeditCandidateCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowRefreshCallback wrap(final @Nullable GLFWWindowRefreshCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwFramebufferSizeCallback wrap(final @Nullable GLFWFramebufferSizeCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwScrollCallback wrap(final @Nullable GLFWScrollCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowSizeCallback wrap(final @Nullable GLFWWindowSizeCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwGammaRamp wrap(final @Nullable GLFWGammaRamp original) {
        if (original == null) {
            return null;
        }

        return new KGlfwGammaRamp() {
            @Override
            public ShortBuffer red() {
                return original.red();
            }

            @Override
            public ShortBuffer green() {
                return original.green();
            }

            @Override
            public ShortBuffer blue() {
                return original.blue();
            }

            @Override
            public int size() {
                return original.size();
            }
        };
    }


    public static @Nullable KGlfwVidMode wrap(final @Nullable GLFWVidMode original) {
        if (original == null) {
            return null;
        }

        return new KGlfwVidMode() {
            @Override
            public int width() {
                return original.width();
            }

            @Override
            public int height() {
                return original.height();
            }

            @Override
            public int redBits() {
                return original.redBits();
            }

            @Override
            public int greenBits() {
                return original.greenBits();
            }

            @Override
            public int blueBits() {
                return original.blueBits();
            }

            @Override
            public int refreshRate() {
                return original.refreshRate();
            }
        };
    }


    public static @Nullable KGlfwImeStatusCallback wrap(final @Nullable GLFWIMEStatusCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static @Nullable KGlfwWindowCloseCallback wrap(final @Nullable GLFWWindowCloseCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }
}
