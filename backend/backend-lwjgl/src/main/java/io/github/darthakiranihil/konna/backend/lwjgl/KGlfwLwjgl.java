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

import io.github.darthakiranihil.konna.backend.lwjgl.internal.unwrapper.*;
import io.github.darthakiranihil.konna.backend.lwjgl.internal.unwrapper.KGlfwGammaRampLwjglUnwrapper;
import io.github.darthakiranihil.konna.backend.lwjgl.internal.wrapper.*;
import io.github.darthakiranihil.konna.libfrontend.glfw.*;
import io.github.darthakiranihil.konna.core.object.KObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import java.nio.*;

public final class KGlfwLwjgl extends KObject implements KGlfw {

    @Override
    public boolean glfwInit() {
        return GLFW.glfwInit();
    }

    @Override
    public void glfwTerminate() {
        GLFW.glfwTerminate();
    }

    @Override
    public void glfwInitHint(int hint, int value) {
        GLFW.glfwInitHint(hint, value);
    }

    @Override
    public void glfwInitAllocator(KGlfwAllocator allocator) {
        var wrappedAllocator = new KGlfwAllocatorLwjglWrapper(allocator);

        GLFW.glfwInitAllocator(
            wrappedAllocator.wrapped()
        );
    }

    @Override
    public void glfwGetVersion(IntBuffer major, IntBuffer minor, IntBuffer rev) {
        GLFW.glfwGetVersion(major, minor, rev);
    }

    @Override
    public String glfwGetVersionString() {
        return GLFW.glfwGetVersionString();
    }

    @Override
    public int glfwGetError(LongBuffer description) {
        PointerBuffer pointedDescription = BufferUtils.createPointerBuffer(description.capacity());
        pointedDescription.put(description);
        return GLFW.glfwGetError(pointedDescription);
    }

    @Override
    public KGlfwErrorCallback glfwSetErrorCallback(KGlfwErrorCallback cbfun) {

        var wrappedCallback = new KGlfwErrorCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetErrorCallback(wrappedCallback.wrapped());
        return new KGlfwErrorCallbackLwjglUnwrapper(result).wrapped();

    }

    @Override
    public int glfwGetPlatform() {
        return GLFW.glfwGetPlatform();
    }

    @Override
    public boolean glfwPlatformSupported(int platform) {
        return GLFW.glfwPlatformSupported(platform);
    }

    @Override
    public LongBuffer glfwGetMonitors() {

        PointerBuffer rawMonitorPointers = GLFW.glfwGetMonitors();
        if (rawMonitorPointers == null) {
            return null;
        }

        LongBuffer monitorPointers = LongBuffer.allocate(rawMonitorPointers.capacity());
        for (int i = 0; i < rawMonitorPointers.capacity(); i++) {
            monitorPointers.put(i, rawMonitorPointers.get(0));
        }
        return monitorPointers;

    }

    @Override
    public long glfwGetPrimaryMonitor() {
        return GLFW.glfwGetPrimaryMonitor();
    }

    @Override
    public void glfwGetMonitorPos(long monitor, IntBuffer xpos, IntBuffer ypos) {
        GLFW.glfwGetMonitorPos(monitor, xpos, ypos);
    }

    @Override
    public void glfwGetMonitorWorkarea(long monitor, IntBuffer xpos, IntBuffer ypos, IntBuffer width, IntBuffer height) {
        GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height);
    }

    @Override
    public void glfwGetMonitorPhysicalSize(long monitor, IntBuffer widthMM, IntBuffer heightMM) {
        GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM);
    }

    @Override
    public void glfwGetMonitorContentScale(long monitor, FloatBuffer xscale, FloatBuffer yscale) {
        GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale);
    }

    @Override
    public String glfwGetMonitorName(long monitor) {
        return GLFW.glfwGetMonitorName(monitor);
    }

    @Override
    public void glfwSetMonitorUserPointer(long monitor, long pointer) {
        GLFW.glfwSetMonitorUserPointer(monitor, pointer);
    }

    @Override
    public long glfwGetMonitorUserPointer(long monitor) {
        return GLFW.glfwGetMonitorUserPointer(monitor);
    }

    @Override
    public KGlfwMonitorCallback glfwSetMonitorCallback(KGlfwMonitorCallback cbfun) {

        var wrappedCallback = new KGlfwMonitorCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetMonitorCallback(wrappedCallback.wrapped());
        return new KGlfwMonitorCallbackLwjglUnwrapper(result).wrapped();

    }

    @Override
    public KGlfwVidMode[] glfwGetVideoModes(long monitor) {

        var result = GLFW.glfwGetVideoModes(monitor);
        if (result == null) {
            return null;
        }

        KGlfwVidMode[] unwrapped = new KGlfwVidMode[result.capacity()];
        for (int i = 0; i < result.capacity(); i++) {
            unwrapped[i] = new KGlfwVidModeLwjglUnwrapper(result.get(i)).wrapped();
        }
        return unwrapped;
    }

    @Override
    public KGlfwVidMode glfwGetVideoMode(long monitor) {

        return new KGlfwVidModeLwjglUnwrapper(
            GLFW.glfwGetVideoMode(monitor)
        ).wrapped();

    }

    @Override
    public void glfwSetGamma(long monitor, float gamma) {
        GLFW.glfwSetGamma(monitor, gamma);
    }

    @Override
    public KGlfwGammaRamp glfwGetGammaRamp(long monitor) {

        var ramp = GLFW.glfwGetGammaRamp(monitor);
        var wrapper = new KGlfwGammaRampLwjglUnwrapper(ramp);
        return wrapper.wrapped();

    }

    @Override
    public void glfwSetGammaRamp(long monitor, KGlfwGammaRamp ramp) {
        var wrapper = new KGlfwGammaRampLwjglWrapper(ramp);
        GLFW.glfwSetGammaRamp(monitor, wrapper.wrapped());
    }

    @Override
    public void glfwDefaultWindowHints() {
        GLFW.glfwDefaultWindowHints();
    }

    @Override
    public void glfwWindowHint(int hint, int value) {
        GLFW.glfwWindowHint(hint, value);
    }

    @Override
    public void glfwWindowHintString(int hint, ByteBuffer value) {
        GLFW.glfwWindowHintString(hint, value);
    }

    @Override
    public void glfwWindowHintString(int hint, CharSequence value) {
        GLFW.glfwWindowHintString(hint, value);
    }

    @Override
    public long glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share) {
        return GLFW.glfwCreateWindow(width, height, title, monitor, share);
    }

    @Override
    public long glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share) {
        return GLFW.glfwCreateWindow(width, height, title, monitor, share);
    }

    @Override
    public void glfwDestroyWindow(long window) {
        GLFW.glfwDestroyWindow(window);
    }

    @Override
    public boolean glfwWindowShouldClose(long window) {
        return GLFW.glfwWindowShouldClose(window);
    }

    @Override
    public void glfwSetWindowShouldClose(long window, boolean value) {
        GLFW.glfwSetWindowShouldClose(window, value);
    }

    @Override
    public String glfwGetWindowTitle(long window) {
        return GLFW.glfwGetWindowTitle(window);
    }

    @Override
    public void glfwSetWindowTitle(long window, ByteBuffer title) {
        GLFW.glfwSetWindowTitle(window, title);
    }

    @Override
    public void glfwSetWindowTitle(long window, CharSequence title) {
        GLFW.glfwSetWindowTitle(window, title);
    }

    @Override
    public void glfwSetWindowIcon(long window, KGlfwImage[] images) {
        GLFWImage.Buffer buffer = GLFWImage.create(images.length);
        for (int i = 0; i < images.length; i++) {
            var wrapper = new KGlfwImageLwjglWrapper(images[i]);
            buffer.put(i, wrapper.wrapped());
        }
        GLFW.glfwSetWindowIcon(window, buffer);
    }

    @Override
    public void glfwGetWindowPos(long window, IntBuffer xpos, IntBuffer ypos) {
        GLFW.glfwGetWindowPos(window, xpos, ypos);
    }

    @Override
    public void glfwSetWindowPos(long window, int xpos, int ypos) {
        GLFW.glfwSetWindowPos(window, xpos, ypos);
    }

    @Override
    public void glfwGetWindowSize(long window, IntBuffer width, IntBuffer height) {
        try (MemoryStack ms = MemoryStack.stackPush()) {

            IntBuffer pWidth = ms.callocInt(1);
            IntBuffer pHeight = ms.callocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            width.put(0, pWidth.get(0));
            height.put(0, pHeight.get(0));
        }
    }

    @Override
    public void glfwSetWindowSizeLimits(long window, int minwidth, int minheight, int maxwidth, int maxheight) {
        GLFW.glfwSetWindowSizeLimits(window, minwidth, minheight, maxwidth, maxheight);
    }

    @Override
    public void glfwSetWindowAspectRatio(long window, int numer, int denom) {
        GLFW.glfwSetWindowAspectRatio(window, numer, denom);
    }

    @Override
    public void glfwSetWindowSize(long window, int width, int height) {
        GLFW.glfwSetWindowSize(window, width, height);
    }

    @Override
    public void glfwGetFramebufferSize(long window, IntBuffer width, IntBuffer height) {
        GLFW.glfwGetFramebufferSize(window, width, height);
    }

    @Override
    public void glfwGetWindowFrameSize(long window, IntBuffer left, IntBuffer top, IntBuffer right, IntBuffer bottom) {
        GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom);
    }

    @Override
    public void glfwGetWindowContentScale(long window, FloatBuffer xscale, FloatBuffer yscale) {
        GLFW.glfwGetWindowContentScale(window, xscale, yscale);
    }

    @Override
    public float glfwGetWindowOpacity(long window) {
        return GLFW.glfwGetWindowOpacity(window);
    }

    @Override
    public void glfwSetWindowOpacity(long window, float opacity) {
        GLFW.glfwSetWindowOpacity(window, opacity);
    }

    @Override
    public void glfwIconifyWindow(long window) {
        GLFW.glfwIconifyWindow(window);
    }

    @Override
    public void glfwRestoreWindow(long window) {
        GLFW.glfwRestoreWindow(window);
    }

    @Override
    public void glfwMaximizeWindow(long window) {
        GLFW.glfwMaximizeWindow(window);
    }

    @Override
    public void glfwShowWindow(long window) {
        GLFW.glfwShowWindow(window);
    }

    @Override
    public void glfwHideWindow(long window) {
        GLFW.glfwHideWindow(window);
    }

    @Override
    public void glfwFocusWindow(long window) {
        GLFW.glfwFocusWindow(window);
    }

    @Override
    public void glfwRequestWindowAttention(long window) {
        GLFW.glfwRequestWindowAttention(window);
    }

    @Override
    public long glfwGetWindowMonitor(long window) {
        return GLFW.glfwGetWindowMonitor(window);
    }

    @Override
    public void glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate) {
        GLFW.glfwSetWindowMonitor(window, monitor, xpos, ypos, width, height, refreshRate);
    }

    @Override
    public int glfwGetWindowAttrib(long window, int attrib) {
        return GLFW.glfwGetWindowAttrib(window, attrib);
    }

    @Override
    public void glfwSetWindowAttrib(long window, int attrib, int value) {
        GLFW.glfwSetWindowAttrib(window, attrib, value);
    }

    @Override
    public void glfwSetWindowUserPointer(long window, long pointer) {
        GLFW.glfwSetWindowUserPointer(window, pointer);
    }

    @Override
    public long glfwGetWindowUserPointer(long window) {
        return GLFW.glfwGetWindowUserPointer(window);
    }

    @Override
    public KGlfwWindowPosCallback glfwSetWindowPosCallback(long window, KGlfwWindowPosCallback cbfun) {
        var wrappedCallback = new KGlfwWindowPosCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowPosCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowPosCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowSizeCallback glfwSetWindowSizeCallback(long window, KGlfwWindowSizeCallback cbfun) {
        var wrappedCallback = new KGlfwWindowSizeCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowSizeCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowSizeCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowCloseCallback glfwSetWindowCloseCallback(long window, KGlfwWindowCloseCallback cbfun) {
        var wrappedCallback = new KGlfwWindowCloseCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowCloseCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowCloseCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowRefreshCallback glfwSetWindowRefreshCallback(long window, KGlfwWindowRefreshCallback cbfun) {
        var wrappedCallback = new KGlfwWindowRefreshCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowRefreshCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowRefreshCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowFocusCallback glfwSetWindowFocusCallback(long window, KGlfwWindowFocusCallback cbfun) {
        var wrappedCallback = new KGlfwWindowFocusCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowFocusCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowFocusCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowIconifyCallback glfwSetWindowIconifyCallback(long window, KGlfwWindowIconifyCallback cbfun) {
        var wrappedCallback = new KGlfwWindowIconifyCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowIconifyCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowIconifyCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowMaximizeCallback glfwSetWindowMaximizeCallback(long window, KGlfwWindowMaximizeCallback cbfun) {
        var wrappedCallback = new KGlfwWindowMaximizeCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowMaximizeCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowMaximizeCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwFramebufferSizeCallback glfwSetFramebufferSizeCallback(long window, KGlfwFramebufferSizeCallback cbfun) {
        var wrappedCallback = new KGlfwFramebufferSizeCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetFramebufferSizeCallback(window, wrappedCallback.wrapped());
        return new KGlfwFramebufferSizeCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwWindowContentScaleCallback glfwSetWindowContentScaleCallback(long window, KGlfwWindowContentScaleCallback cbfun) {
        var wrappedCallback = new KGlfwWindowContentScaleCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetWindowContentScaleCallback(window, wrappedCallback.wrapped());
        return new KGlfwWindowContentScaleCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public void glfwPollEvents() {
        GLFW.glfwPollEvents();
    }

    @Override
    public void glfwWaitEvents() {
        GLFW.glfwWaitEvents();
    }

    @Override
    public void glfwWaitEventsTimeout(double timeout) {
        GLFW.glfwWaitEventsTimeout(timeout);
    }

    @Override
    public void glfwPostEmptyEvent() {
        GLFW.glfwPostEmptyEvent();
    }

    @Override
    public int glfwGetInputMode(long window, int mode) {
        return GLFW.glfwGetInputMode(window, mode);
    }

    @Override
    public void glfwSetInputMode(long window, int mode, int value) {
        GLFW.glfwSetInputMode(window, mode, value);
    }

    @Override
    public boolean glfwRawMouseMotionSupported() {
        return GLFW.glfwRawMouseMotionSupported();
    }

    @Override
    public String glfwGetKeyName(int key, int scancode) {
        return GLFW.glfwGetKeyName(key, scancode);
    }

    @Override
    public int glfwGetKeyScancode(int key) {
        return GLFW.glfwGetKeyScancode(key);
    }

    @Override
    public int glfwGetKey(long window, int key) {
        return GLFW.glfwGetKey(window, key);
    }

    @Override
    public int glfwGetMouseButton(long window, int button) {
        return GLFW.glfwGetMouseButton(window, button);
    }

    @Override
    public void glfwGetCursorPos(long window, DoubleBuffer xpos, DoubleBuffer ypos) {
        GLFW.glfwGetCursorPos(window, xpos, ypos);
    }

    @Override
    public void glfwSetCursorPos(long window, double xpos, double ypos) {
        GLFW.glfwSetCursorPos(window, xpos, ypos);
    }

    @Override
    public long glfwCreateCursor(KGlfwImage image, int xhot, int yhot) {
        var wrapper = new KGlfwImageLwjglWrapper(image);
        return GLFW.glfwCreateCursor(wrapper.wrapped(), xhot, yhot);
    }

    @Override
    public long glfwCreateStandardCursor(int shape) {
        return GLFW.glfwCreateStandardCursor(shape);
    }

    @Override
    public void glfwDestroyCursor(long cursor) {
        GLFW.glfwDestroyCursor(cursor);
    }

    @Override
    public void glfwSetCursor(long window, long cursor) {
        GLFW.glfwSetCursor(window, cursor);
    }

    @Override
    public void glfwGetPreeditCursorRectangle(long window, IntBuffer x, IntBuffer y, IntBuffer w, IntBuffer h) {
        GLFW.glfwGetPreeditCursorRectangle(window, x, y, w, h);
    }

    @Override
    public void glfwSetPreeditCursorRectangle(long window, int x, int y, int w, int h) {
        GLFW.glfwSetPreeditCursorRectangle(window, x, y, w, h);
    }

    @Override
    public void glfwResetPreeditText(long window) {
        GLFW.glfwResetPreeditText(window);
    }

    @Override
    public IntBuffer glfwGetPreeditCandidate(long window, int index) {
        return GLFW.glfwGetPreeditCandidate(window, index);
    }

    @Override
    public KGlfwKeyCallback glfwSetKeyCallback(long window, KGlfwKeyCallback cbfun) {
        var wrappedCallback = new KGlfwKeyCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetKeyCallback(window, wrappedCallback.wrapped());
        return new KGlfwKeyCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwCharCallback glfwSetCharCallback(long window, KGlfwCharCallback cbfun) {
        var wrappedCallback = new KGlfwCharCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetCharCallback(window, wrappedCallback.wrapped());
        return new KGlfwCharCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwCharModsCallback glfwSetCharModsCallback(long window, KGlfwCharModsCallback cbfun) {
        var wrappedCallback = new KGlfwCharModsCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetCharModsCallback(window, wrappedCallback.wrapped());
        return new KGlfwCharModsCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwPreeditCallback glfwSetPreeditCallback(long window, KGlfwPreeditCallback cbfun) {
        var wrappedCallback = new KGlfwPreeditCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetPreeditCallback(window, wrappedCallback.wrapped());
        return new KGlfwPreeditCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwImeStatusCallback glfwSetImeStatusCallback(long window, KGlfwImeStatusCallback cbfun) {
        var wrappedCallback = new KGlfwImeStatusCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetIMEStatusCallback(window, wrappedCallback.wrapped());
        return new KGlfwImeStatusCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwPreeditCandidateCallback glfwSetPreeditCandidateCallback(long window, KGlfwPreeditCandidateCallback cbfun) {
        var wrappedCallback = new KGlfwPreeditCandidateCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetPreeditCandidateCallback(window, wrappedCallback.wrapped());
        return new KGlfwPreeditCandidateCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwMouseButtonCallback glfwSetMouseButtonCallback(long window, KGlfwMouseButtonCallback cbfun) {
        var wrappedCallback = new KGlfwMouseButtonCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetMouseButtonCallback(window, wrappedCallback.wrapped());
        return new KGlfwMouseButtonCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwCursorPosCallback glfwSetCursorPosCallback(long window, KGlfwCursorPosCallback cbfun) {
        var wrappedCallback = new KGlfwCursorPosCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetCursorPosCallback(window, wrappedCallback.wrapped());
        return new KGlfwCursorPosCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwCursorEnterCallback glfwSetCursorEnterCallback(long window, KGlfwCursorEnterCallback cbfun) {
        var wrappedCallback = new KGlfwCursorEnterCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetCursorEnterCallback(window, wrappedCallback.wrapped());
        return new KGlfwCursorEnterCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwScrollCallback glfwSetScrollCallback(long window, KGlfwScrollCallback cbfun) {
        var wrappedCallback = new KGlfwScrollCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetScrollCallback(window, wrappedCallback.wrapped());
        return new KGlfwScrollCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public KGlfwDropCallback glfwSetDropCallback(long window, KGlfwDropCallback cbfun) {
        var wrappedCallback = new KGlfwDropCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetDropCallback(window, wrappedCallback.wrapped());
        return new KGlfwDropCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public boolean glfwJoystickPresent(int jid) {
        return GLFW.glfwJoystickPresent(jid);
    }

    @Override
    public FloatBuffer glfwGetJoystickAxes(int jid) {
        return GLFW.glfwGetJoystickAxes(jid);
    }

    @Override
    public ByteBuffer glfwGetJoystickButtons(int jid) {
        return GLFW.glfwGetJoystickButtons(jid);
    }

    @Override
    public ByteBuffer glfwGetJoystickHats(int jid) {
        return GLFW.glfwGetJoystickHats(jid);
    }

    @Override
    public String glfwGetJoystickName(int jid) {
        return GLFW.glfwGetJoystickName(jid);
    }

    @Override
    public String glfwGetJoystickGUID(int jid) {
        return GLFW.glfwGetJoystickGUID(jid);
    }

    @Override
    public void glfwSetJoystickUserPointer(int jid, long pointer) {
        GLFW.glfwSetJoystickUserPointer(jid, pointer);
    }

    @Override
    public long glfwGetJoystickUserPointer(int jid) {
        return GLFW.glfwGetJoystickUserPointer(jid);
    }

    @Override
    public boolean glfwJoystickIsGamepad(int jid) {
        return GLFW.glfwJoystickIsGamepad(jid);
    }

    @Override
    public KGlfwJoystickCallback glfwSetJoystickCallback(KGlfwJoystickCallback cbfun) {
        var wrappedCallback = new KGlfwJoystickCallbackLwjglWrapper(cbfun);
        var result = GLFW.glfwSetJoystickCallback(wrappedCallback.wrapped());
        return new KGlfwJoystickCallbackLwjglUnwrapper(result).wrapped();
    }

    @Override
    public boolean glfwUpdateGamepadMappings(ByteBuffer string) {
        return GLFW.glfwUpdateGamepadMappings(string);
    }

    @Override
    public String glfwGetGamepadName(int jid) {
        return GLFW.glfwGetGamepadName(jid);
    }

    @Override
    public boolean glfwGetGamepadState(int jid, KGlfwGamepadState state) {
        GLFWGamepadState internalState = GLFWGamepadState.create();
        boolean result = GLFW.glfwGetGamepadState(jid, internalState);
        state.setAxes(internalState.axes());
        state.setButtons(internalState.buttons());
        return result;
    }

    @Override
    public void glfwSetClipboardString(long window, ByteBuffer string) {
        GLFW.glfwSetClipboardString(window, string);
    }

    @Override
    public void glfwSetClipboardString(long window, CharSequence string) {
        GLFW.glfwSetClipboardString(window, string);
    }

    @Override
    public String glfwGetClipboardString(long window) {
        return GLFW.glfwGetClipboardString(window);
    }

    @Override
    public double glfwGetTime() {
        return GLFW.glfwGetTime();
    }

    @Override
    public void glfwSetTime(double time) {
        GLFW.glfwSetTime(time);
    }

    @Override
    public long glfwGetTimerValue() {
        return GLFW.glfwGetTimerValue();
    }

    @Override
    public long glfwGetTimerFrequency() {
        return GLFW.glfwGetTimerFrequency();
    }

    @Override
    public void glfwMakeContextCurrent(long window) {
        GLFW.glfwMakeContextCurrent(window);
    }

    @Override
    public long glfwGetCurrentContext() {
        return GLFW.glfwGetCurrentContext();
    }

    @Override
    public void glfwSwapBuffers(long window) {
        GLFW.glfwSwapBuffers(window);
    }

    @Override
    public void glfwSwapInterval(int interval) {
        GLFW.glfwSwapInterval(interval);
    }

    @Override
    public boolean glfwExtensionSupported(ByteBuffer extension) {
        return GLFW.glfwExtensionSupported(extension);
    }

    @Override
    public boolean glfwExtensionSupported(CharSequence extension) {
        return GLFW.glfwExtensionSupported(extension);
    }

    @Override
    public long glfwGetProcAddress(ByteBuffer procname) {
        return GLFW.glfwGetProcAddress(procname);
    }

    @Override
    public long glfwGetProcAddress(CharSequence procname) {
        return GLFW.glfwGetProcAddress(procname);
    }

    @Override
    public void glfwGetVersion(int[] major, int[] minor, int[] rev) {
        GLFW.glfwGetVersion(major, minor, rev);
    }

    @Override
    public void glfwGetMonitorPos(long monitor, int[] xpos, int[] ypos) {
        GLFW.glfwGetMonitorPos(monitor, xpos, ypos);
    }

    @Override
    public void glfwGetMonitorWorkarea(long monitor, int[] xpos, int[] ypos, int[] width, int[] height) {
        GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height);
    }

    @Override
    public void glfwGetMonitorPhysicalSize(long monitor, int[] widthMM, int[] heightMM) {
        GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM);
    }

    @Override
    public void glfwGetMonitorContentScale(long monitor, float[] xscale, float[] yscale) {
        GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale);
    }

    @Override
    public void glfwGetWindowPos(long window, int[] xpos, int[] ypos) {
        GLFW.glfwGetWindowPos(window, xpos, ypos);
    }

    @Override
    public void glfwGetWindowSize(long window, int[] width, int[] height) {
        GLFW.glfwGetWindowSize(window, width, height);
    }

    @Override
    public void glfwGetFramebufferSize(long window, int[] width, int[] height) {
        GLFW.glfwGetFramebufferSize(window, width, height);
    }

    @Override
    public void glfwGetWindowFrameSize(long window, int[] left, int[] top, int[] right, int[] bottom) {
        GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom);
    }

    @Override
    public void glfwGetWindowContentScale(long window, float[] xscale, float[] yscale) {
        GLFW.glfwGetWindowContentScale(window, xscale, yscale);
    }

    @Override
    public void glfwGetCursorPos(long window, double[] xpos, double[] ypos) {
        GLFW.glfwGetCursorPos(window, xpos, ypos);
    }

    @Override
    public void glfwGetPreeditCursorRectangle(long window, int[] x, int[] y, int[] w, int[] h) {
        GLFW.glfwGetPreeditCursorRectangle(window, x, y, w, h);
    }

}
