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

package io.github.darthakiranihil.konna.core.graphics;

import io.github.darthakiranihil.konna.core.graphics.render.KGl20;
import io.github.darthakiranihil.konna.core.graphics.render.std.KGl20Lwjgl;
import io.github.darthakiranihil.konna.core.graphics.window.KGlfw;
import io.github.darthakiranihil.konna.core.graphics.window.std.KGlfwLwjgl;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class KDebugw {

    // The window handle
    private long window;
    final KGl20 gl20;
    final KGlfw glfw;

    public KDebugw() {
        this.gl20 = new KGl20Lwjgl();
        this.glfw = new KGlfwLwjgl();
    }

    private float theta = 0.0f;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop2();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfw.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfw.glfwTerminate();
        glfw.glfwSetErrorCallback(null).free();
    }

    private void loop2() {
        GL.createCapabilities();
//        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        glClear(GL_COLOR_BUFFER_BIT);
        while ( !glfwWindowShouldClose(window) ) {
            // System.out.println(Display));
            gl20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            gl20.glClear(KGl20.GL_COLOR_BUFFER_BIT);
            gl20.glPushMatrix();
            gl20.glRotatef(theta, 0.0f, 0.0f, 1.0f);

            gl20.glBegin(KGl20.GL_TRIANGLES);

            gl20.glColor3f(1.0f, 0.0f, 0.0f);   gl20.glVertex2f(0.0f,   1.0f);
            gl20.glColor3f(0.0f, 1.0f, 0.0f);   gl20.glVertex2f(0.87f,  -0.5f);
            gl20.glColor3f(0.0f, 0.0f, 1.0f);   gl20.glVertex2f(-0.87f, -0.5f);

            gl20.glEnd();

            gl20.glPopMatrix();
            glfw.glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfw.glfwPollEvents();
            theta += 0.5f;
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }


    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfw.glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfw.glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfw.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfw.glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfw.glfwCreateWindow(1000, 1000, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfw.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfw.glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfw.glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfw.glfwGetVideoMode(glfw.glfwGetPrimaryMonitor());

            // Center the window
            glfw.glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfw.glfwMakeContextCurrent(window);
        // Enable v-sync
        glfw.glfwSwapInterval(1);

        // Make the window visible
        glfw.glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        gl20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            gl20.glClear(KGl20.GL_COLOR_BUFFER_BIT | KGl20.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfw.glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfw.glfwPollEvents();
        }
    }

}
