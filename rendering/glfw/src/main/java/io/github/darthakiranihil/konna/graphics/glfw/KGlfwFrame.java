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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.glfw.KGlfw;
import io.github.darthakiranihil.konna.libfrontend.glfw.KGlfwCallbacks;

import java.nio.IntBuffer;

@KExcludeFromGeneratedCoverageReport
public class KGlfwFrame extends KObject implements KFrame {

    private final KGlfw glfw;
    private final KGlfwCallbacks glfwCallbacks;
    private final IntBuffer pWidth;
    private final IntBuffer pHeight;

    private long handle;
    private KSize currentSize;


    public KGlfwFrame(
        @KInject final KGlfw glfw,
        @KInject final KGlfwCallbacks glfwCallbacks
//        final String title,
//        final KSize size
    ) {
        this.glfw = glfw;
        this.glfwCallbacks = glfwCallbacks;

        if (!this.glfw.glfwInit()) {
            throw new KException(
                "Unable to initialize frame: GLFW initialization failed"
            );
        }

        this.setResizable(true);

        KSize size = KSize.squared(600);
        String title = "TITLE";

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

        //! DEBUG ONLY
        glfw.glfwSetKeyCallback(this.handle, (w, key, scancode, action, mods) -> {
            if ( key == KGlfw.GLFW_KEY_ESCAPE && action == KGlfw.GLFW_RELEASE )
                glfw.glfwSetWindowShouldClose(w, true); // We will detect this in the rendering loop
        });

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

}
