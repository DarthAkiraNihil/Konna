
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

package io.github.darthakiranihil.konna.backend.lwjgl.internal.wrapper;

import io.github.darthakiranihil.konna.core.object.KWrapper;
import io.github.darthakiranihil.konna.libfrontend.glfw.KGlfwCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public final class KGlfwCursorPosCallbackLwjglWrapper extends KWrapper<KGlfwCursorPosCallback, GLFWCursorPosCallbackI> {

    public KGlfwCursorPosCallbackLwjglWrapper(
        KGlfwCursorPosCallback original
    ) {
        super(original);
    }

    @Override
    protected GLFWCursorPosCallbackI wrap(KGlfwCursorPosCallback original) {
        return original::invoke;
    }
}
