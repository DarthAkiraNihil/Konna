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

package main.java.io.github.darthakiranihil.konna.libfrontend.glfw;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.graphics.except.KInvalidGraphicsStateException;
import io.github.darthakiranihil.konna.core.graphics.frame.KFrame;
import io.github.darthakiranihil.konna.core.input.KKeyListener;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KSize;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.system.MemoryStack.stackPush;

public class KGlfwFrame extends KObject implements KFrame {

    private final KGlfw glfw;
    private long handle;
    private KSize currentSize;

    public KGlfwFrame(
        @KInject final KGlfw glfw,
        final String title,
        final KSize size
    ) {
        this.glfw = glfw;

        if (!this.glfw.glfwInit()) {
            throw new KInvalidGraphicsStateException(
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
            throw new KInvalidGraphicsStateException("Unable to initialize frame: failed to create GLFW window");
        }

        glfw.glfwMakeContextCurrent(this.handle);
        glfw.glfwSwapInterval(1);

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
    public void setSize(KSize newSize) {
        this.glfw.glfwSetWindowSize(this.handle, newSize.width(), newSize.height());
        this.currentSize = newSize;
    }

    @Override
    public void setResizable(boolean state) {

    }

    @Override
    public void addKeyListener(KKeyListener listener) {

    }

    @Override
    public void terminate() {
        Callbacks.glfwFreeCallbacks(this.handle);

        this.glfw.glfwDestroyWindow(this.handle);
        this.glfw.glfwTerminate();
        this.glfw.glfwSetErrorCallback(null).free();
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
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            this.glfw.glfwGetWindowSize(this.handle, pWidth, pHeight);
            this.currentSize = new KSize(pWidth.get(0), pHeight.get(0));
        }
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
}
