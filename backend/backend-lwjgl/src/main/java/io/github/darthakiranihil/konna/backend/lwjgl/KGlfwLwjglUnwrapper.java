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

import java.nio.ShortBuffer;

final class KGlfwLwjglUnwrapper extends KUninstantiable {

    private KGlfwLwjglUnwrapper() {
        super();
    }


    public static KGlfwJoystickCallback wrap(final GLFWJoystickCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowContentScaleCallback wrap(
        final GLFWWindowContentScaleCallbackI original
    ) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwCharModsCallback wrap(final GLFWCharModsCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }

    public static KGlfwCharCallback wrap(final GLFWCharCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwKeyCallback wrap(final GLFWKeyCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowFocusCallback wrap(final GLFWWindowFocusCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwCursorEnterCallback wrap(final GLFWCursorEnterCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwMonitorCallback wrap(final GLFWMonitorCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowIconifyCallback wrap(final GLFWWindowIconifyCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwCursorPosCallback wrap(final GLFWCursorPosCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwMouseButtonCallback wrap(final GLFWMouseButtonCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowMaximizeCallback wrap(final GLFWWindowMaximizeCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwDropCallback wrap(final GLFWDropCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwPreeditCallback wrap(final GLFWPreeditCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowPosCallback wrap(final GLFWWindowPosCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwErrorCallback wrap(final GLFWErrorCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwPreeditCandidateCallback wrap(final GLFWPreeditCandidateCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowRefreshCallback wrap(final GLFWWindowRefreshCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwFramebufferSizeCallback wrap(final GLFWFramebufferSizeCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwScrollCallback wrap(final GLFWScrollCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowSizeCallback wrap(final GLFWWindowSizeCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwGammaRamp wrap(final GLFWGammaRamp original) {
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


    public static KGlfwVidMode wrap(final GLFWVidMode original) {
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


    public static KGlfwImeStatusCallback wrap(final GLFWIMEStatusCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }


    public static KGlfwWindowCloseCallback wrap(final GLFWWindowCloseCallbackI original) {
        if (original == null) {
            return null;
        }
        return original::invoke;
    }
}
