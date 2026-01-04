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

package io.github.darthakiranihil.konna.graphics.glfw;

import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.input.KKey;
import io.github.darthakiranihil.konna.core.input.KKeyEventData;
import io.github.darthakiranihil.konna.core.input.KKeyListener;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.glfw.KGlfw;
import io.github.darthakiranihil.konna.libfrontend.glfw.KGlfwCallbacks;

import java.nio.IntBuffer;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Implementation of {@link KFrame} that uses GLFW for its functionality.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public class KGlfwFrame extends KObject implements KFrame {

    private static final Map<Integer, KKey> KEY_GLFW_TO_KONNA = Map.<Integer, KKey>ofEntries(
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_SPACE, KKey.SPACE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_APOSTROPHE, KKey.APOSTROPHE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_COMMA, KKey.COMMA),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_MINUS, KKey.MINUS),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_PERIOD, KKey.PERIOD),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_SLASH, KKey.SLASH),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_0, KKey.NUM_0),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_1, KKey.NUM_1),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_2, KKey.NUM_2),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_3, KKey.NUM_3),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_4, KKey.NUM_4),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_5, KKey.NUM_5),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_6, KKey.NUM_6),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_7, KKey.NUM_7),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_8, KKey.NUM_8),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_9, KKey.NUM_9),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_SEMICOLON, KKey.SEMICOLON),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_EQUAL, KKey.EQUAL),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_A, KKey.A),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_B, KKey.B),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_C, KKey.C),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_D, KKey.D),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_E, KKey.E),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F, KKey.F),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_G, KKey.G),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_H, KKey.H),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_I, KKey.I),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_J, KKey.J),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_K, KKey.K),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_L, KKey.L),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_M, KKey.M),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_N, KKey.N),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_O, KKey.O),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_P, KKey.P),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_Q, KKey.Q),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_R, KKey.R),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_S, KKey.S),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_T, KKey.T),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_U, KKey.U),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_V, KKey.V),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_W, KKey.W),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_X, KKey.X),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_Y, KKey.Y),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_Z, KKey.Z),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_LEFT_BRACKET, KKey.LEFT_BRACKET),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_BACKSLASH, KKey.BACKSLASH),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_RIGHT_BRACKET, KKey.RIGHT_BRACKET),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_GRAVE_ACCENT, KKey.GRAVE_ACCENT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_WORLD_1, KKey.WORLD_1),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_WORLD_2, KKey.WORLD_2),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_ESCAPE, KKey.ESCAPE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_ENTER, KKey.ENTER),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_TAB, KKey.TAB),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_BACKSPACE, KKey.BACKSPACE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_INSERT, KKey.INSERT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_DELETE, KKey.DELETE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_RIGHT, KKey.RIGHT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_LEFT, KKey.LEFT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_DOWN, KKey.DOWN),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_UP, KKey.UP),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_PAGE_UP, KKey.PAGE_UP),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_PAGE_DOWN, KKey.PAGE_DOWN),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_HOME, KKey.HOME),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_END, KKey.END),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_CAPS_LOCK, KKey.CAPS_LOCK),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_SCROLL_LOCK, KKey.SCROLL_LOCK),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_NUM_LOCK, KKey.NUM_LOCK),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_PRINT_SCREEN, KKey.PRINT_SCREEN),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_PAUSE, KKey.PAUSE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F1, KKey.F1),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F2, KKey.F2),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F3, KKey.F3),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F4, KKey.F4),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F5, KKey.F5),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F6, KKey.F6),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F7, KKey.F7),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F8, KKey.F8),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F9, KKey.F9),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F10, KKey.F10),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F11, KKey.F11),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F12, KKey.F12),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F13, KKey.F13),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F14, KKey.F14),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F15, KKey.F15),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F16, KKey.F16),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F17, KKey.F17),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F18, KKey.F18),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F19, KKey.F19),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F20, KKey.F20),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F21, KKey.F21),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F22, KKey.F22),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F23, KKey.F23),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F24, KKey.F24),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_F25, KKey.F25),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_0, KKey.KP_0),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_1, KKey.KP_1),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_2, KKey.KP_2),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_3, KKey.KP_3),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_4, KKey.KP_4),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_5, KKey.KP_5),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_6, KKey.KP_6),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_7, KKey.KP_7),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_8, KKey.KP_8),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_9, KKey.KP_9),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_DECIMAL, KKey.KP_DECIMAL),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_DIVIDE, KKey.KP_DIVIDE),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_MULTIPLY, KKey.KP_MULTIPLY),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_SUBTRACT, KKey.KP_SUBTRACT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_ADD, KKey.KP_ADD),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_ENTER, KKey.KP_ENTER),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_KP_EQUAL, KKey.KP_EQUAL),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_LEFT_SHIFT, KKey.LEFT_SHIFT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_LEFT_CONTROL, KKey.LEFT_CONTROL),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_LEFT_ALT, KKey.LEFT_ALT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_LEFT_SUPER, KKey.LEFT_SUPER),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_RIGHT_SHIFT, KKey.RIGHT_SHIFT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_RIGHT_CONTROL, KKey.RIGHT_CONTROL),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_RIGHT_ALT, KKey.RIGHT_ALT),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_RIGHT_SUPER, KKey.RIGHT_SUPER),
        new AbstractMap.SimpleEntry<>(KGlfw.GLFW_KEY_MENU, KKey.MENU)
    );

    private final KGlfw glfw;
    private final KGlfwCallbacks glfwCallbacks;
    private final IntBuffer pWidth;
    private final IntBuffer pHeight;

    private long handle;
    private KSize currentSize;


    public KGlfwFrame(
        @KInject final KGlfw glfw,
        @KInject final KGlfwCallbacks glfwCallbacks,
        final String title,
        final KSize size
    ) {
        this.glfw = glfw;
        this.glfwCallbacks = glfwCallbacks;

        if (!this.glfw.glfwInit()) {
            throw new KException(
                "Unable to initialize frame: GLFW initialization failed"
            );
        }

        this.setResizable(true);

        this.handle = this.glfw.glfwCreateWindow(
            size.width(),
            size.height(),
            title,
            0,
            0
        );

        if (this.handle == 0) {
            throw new KException(
                "Unable to initialize frame: failed to create GLFW window"
            );
        }

        //glfw.glfwMakeContextCurrent(this.handle);
        glfw.glfwSwapInterval(1);

        this.pHeight = IntBuffer.allocate(1);
        this.pWidth = IntBuffer.allocate(1);
        this.updateSize();

    }

    @Override
    public void setTitle(final String title) {
        this.glfw.glfwSetWindowTitle(this.handle, title);
    }

    @Override
    public KSize getSize() {
        return this.currentSize;
    }

    @Override
    public void setSize(final KSize newSize) {
        this.glfw.glfwSetWindowSize(this.handle, newSize.width(), newSize.height());
        this.currentSize = newSize;
    }

    @Override
    public void setResizable(boolean state) {

    }

    @Override
    public void terminate() {
        this.glfwCallbacks.glfwFreeCallbacks(this.handle);

        this.glfw.glfwDestroyWindow(this.handle);
        this.glfw.glfwTerminate();
        this.glfwCallbacks.freeLastCallback(this.handle);
    }

    @Override
    public void show() {
        this.glfw.glfwShowWindow(this.handle);
    }

    @Override
    public void hide() {
        this.glfw.glfwHideWindow(this.handle);
    }

    private void updateSize() {
        this.glfw.glfwGetWindowSize(this.handle, this.pWidth, this.pHeight);
        this.currentSize = new KSize(pWidth.get(0), pHeight.get(0));

    }

    @Override
    public boolean shouldClose() {
        return this.glfw.glfwWindowShouldClose(this.handle);
    }

    @Override
    public void setShouldClose(boolean flag) {
        this.glfw.glfwSetWindowShouldClose(this.handle, flag);
    }

    @Override
    public void swapBuffers() {
        this.glfw.glfwSwapBuffers(this.handle);
    }

    @Override
    public void pollEvents() {
        this.glfw.glfwPollEvents();
    }

    @Override
    public void addKeyListener(final KKeyListener listener) {

        glfw.glfwSetKeyCallback(
            this.handle,
            (w, key, scancode, action, mods) -> {

                KKeyEventData eventData = new KKeyEventData(
                    KEY_GLFW_TO_KONNA.get(key),
                    (mods & KGlfw.GLFW_MOD_SHIFT) != 0,
                    (mods & KGlfw.GLFW_MOD_ALT) != 0,
                    (mods & KGlfw.GLFW_MOD_SUPER) != 0,
                    (mods & KGlfw.GLFW_MOD_CAPS_LOCK) != 0,
                    (mods & KGlfw.GLFW_MOD_NUM_LOCK) != 0
                );

                switch (action) {
                    case KGlfw.GLFW_PRESS -> {
                        listener.keyPressed(eventData);
                    }
                    case KGlfw.GLFW_RELEASE -> {
                        listener.keyReleased(eventData);
                    }
                }
            }
        );
    }
}
