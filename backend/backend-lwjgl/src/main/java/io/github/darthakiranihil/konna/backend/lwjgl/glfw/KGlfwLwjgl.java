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


import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.glfw.*;
import org.jspecify.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;

import java.nio.*;

/**
 * GLFW library frontend implementation,
 * using corresponding bindings from {@link GLFW}.
 *
 * @author Darth Akira Nihil
 * @version 0.3.0
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public final class KGlfwLwjgl extends KObject implements KGlfw, KGlfwCallbacks {

    @Override
    public void glfwFreeCallbacks(long window) {
        Callbacks.glfwFreeCallbacks(window);
    }

    @Override
    public void freeLastCallback(long window) {
        var callback = GLFW.glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

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
    public void glfwInitAllocator(final @Nullable KGlfwAllocator allocator) {
        GLFW.glfwInitAllocator(KGlfwLwjglWrapper.wrap(allocator));
    }

    @Override
    public void glfwGetVersion(
        final @Nullable IntBuffer major,
        final @Nullable IntBuffer minor,
        final @Nullable IntBuffer rev
    ) {
        GLFW.glfwGetVersion(major, minor, rev);
    }

    @Override
    public String glfwGetVersionString() {
        return GLFW.glfwGetVersionString();
    }

    @Override
    public int glfwGetError(final @Nullable LongBuffer description) {
        if (description == null) {
            return GLFW.glfwGetError(null);
        }

        PointerBuffer pointedDescription = BufferUtils.createPointerBuffer(description.capacity());
        pointedDescription.put(description);
        int error = GLFW.glfwGetError(pointedDescription);

        for (int i = 0; i < description.capacity(); i++) {
            description.put(i, pointedDescription.get(i));
        }
        pointedDescription.free();
        return error;
    }

    @Override
    public @Nullable KGlfwErrorCallback glfwSetErrorCallback(
        final @Nullable KGlfwErrorCallback cbfun
    ) {

        var result = GLFW.glfwSetErrorCallback(KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);

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
    public @Nullable LongBuffer glfwGetMonitors() {

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
    public void glfwGetMonitorPos(
        long monitor,
        final @Nullable IntBuffer xpos,
        final @Nullable IntBuffer ypos
    ) {
        GLFW.glfwGetMonitorPos(monitor, xpos, ypos);
    }

    @Override
    public void glfwGetMonitorWorkarea(
        long monitor,
        final @Nullable IntBuffer xpos,
        final @Nullable IntBuffer ypos,
        final @Nullable IntBuffer width,
        final @Nullable IntBuffer height
    ) {
        GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height);
    }

    @Override
    public void glfwGetMonitorPhysicalSize(
        long monitor,
        final @Nullable IntBuffer widthMM,
        final @Nullable IntBuffer heightMM
    ) {
        GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM);
    }

    @Override
    public void glfwGetMonitorContentScale(
        long monitor,
        final @Nullable FloatBuffer xscale,
        final @Nullable FloatBuffer yscale
    ) {
        GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale);
    }

    @Override
    public @Nullable String glfwGetMonitorName(long monitor) {
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
    public @Nullable KGlfwMonitorCallback glfwSetMonitorCallback(
        final @Nullable KGlfwMonitorCallback cbfun
    ) {

        var result = GLFW.glfwSetMonitorCallback(KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);

    }

    @Override
    public KGlfwVidMode @Nullable [] glfwGetVideoModes(long monitor) {

        var result = GLFW.glfwGetVideoModes(monitor);
        if (result == null) {
            return null;
        }

        KGlfwVidMode[] unwrapped = new KGlfwVidMode[result.capacity()];
        for (int i = 0; i < result.capacity(); i++) {
            unwrapped[i] = KGlfwLwjglUnwrapper.wrap(result.get(i));
        }
        return unwrapped;
    }

    @Override
    public @Nullable KGlfwVidMode glfwGetVideoMode(long monitor) {

        return KGlfwLwjglUnwrapper.wrap(GLFW.glfwGetVideoMode(monitor));

    }

    @Override
    public void glfwSetGamma(long monitor, float gamma) {
        GLFW.glfwSetGamma(monitor, gamma);
    }

    @Override
    public @Nullable KGlfwGammaRamp glfwGetGammaRamp(long monitor) {

        var ramp = GLFW.glfwGetGammaRamp(monitor);
        return KGlfwLwjglUnwrapper.wrap(ramp);

    }

    @Override
    public void glfwSetGammaRamp(long monitor, final KGlfwGammaRamp ramp) {
        GLFW.glfwSetGammaRamp(monitor, KGlfwLwjglWrapper.wrap(ramp));
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
    public void glfwWindowHintString(int hint, final ByteBuffer value) {
        GLFW.glfwWindowHintString(hint, value);
    }

    @Override
    public void glfwWindowHintString(int hint, final CharSequence value) {
        GLFW.glfwWindowHintString(hint, value);
    }

    @Override
    public long glfwCreateWindow(
        int width,
        int height,
        final ByteBuffer title,
        long monitor,
        long share
    ) {
        return GLFW.glfwCreateWindow(width, height, title, monitor, share);
    }

    @Override
    public long glfwCreateWindow(
        int width,
        int height,
        final CharSequence title,
        long monitor,
        long share
    ) {
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
    public @Nullable String glfwGetWindowTitle(long window) {
        return GLFW.glfwGetWindowTitle(window);
    }

    @Override
    public void glfwSetWindowTitle(long window, final ByteBuffer title) {
        GLFW.glfwSetWindowTitle(window, title);
    }

    @Override
    public void glfwSetWindowTitle(long window, final CharSequence title) {
        GLFW.glfwSetWindowTitle(window, title);
    }

    @Override
    public void glfwSetWindowIcon(long window, final KGlfwImage @Nullable[] images) {
        if (images == null) {
            GLFW.glfwSetWindowIcon(window, null);
            return;

        }
        GLFWImage.Buffer buffer = GLFWImage.create(images.length);
        for (int i = 0; i < images.length; i++) {
            buffer.put(i, KGlfwLwjglWrapper.wrap(images[i]));
        }
        GLFW.glfwSetWindowIcon(window, buffer);
        buffer.free();
    }

    @Override
    public void glfwGetWindowPos(
        long window,
        final @Nullable IntBuffer xpos,
        final @Nullable IntBuffer ypos
    ) {
        GLFW.glfwGetWindowPos(window, xpos, ypos);
    }

    @Override
    public void glfwSetWindowPos(long window, int xpos, int ypos) {
        GLFW.glfwSetWindowPos(window, xpos, ypos);
    }

    @Override
    public void glfwGetWindowSize(
        long window,
        final @Nullable IntBuffer width,
        final @Nullable IntBuffer height
    ) {
        try (MemoryStack ms = MemoryStack.stackPush()) {

            IntBuffer pWidth = ms.callocInt(1);
            IntBuffer pHeight = ms.callocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            if (width != null) {
                width.put(0, pWidth.get(0));
            }

            if (height != null) {
                height.put(0, pHeight.get(0));
            }
        }
    }

    @Override
    public void glfwSetWindowSizeLimits(
        long window,
        int minwidth,
        int minheight,
        int maxwidth,
        int maxheight
    ) {
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
    public void glfwGetFramebufferSize(
        long window,
        final @Nullable IntBuffer width,
        final @Nullable IntBuffer height
    ) {
        GLFW.glfwGetFramebufferSize(window, width, height);
    }

    @Override
    public void glfwGetWindowFrameSize(
        long window,
        final @Nullable IntBuffer left,
        final @Nullable IntBuffer top,
        final @Nullable IntBuffer right,
        final @Nullable IntBuffer bottom
    ) {
        GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom);
    }

    @Override
    public void glfwGetWindowContentScale(
        long window,
        final @Nullable FloatBuffer xscale,
        final @Nullable FloatBuffer yscale
    ) {
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
    public void glfwSetWindowMonitor(
        long window,
        long monitor,
        int xpos,
        int ypos,
        int width,
        int height,
        int refreshRate
    ) {
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
    public @Nullable KGlfwWindowPosCallback glfwSetWindowPosCallback(
        long window,
        final @Nullable KGlfwWindowPosCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowPosCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowSizeCallback glfwSetWindowSizeCallback(
        long window,
        final @Nullable KGlfwWindowSizeCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowSizeCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowCloseCallback glfwSetWindowCloseCallback(
        long window,
        final @Nullable KGlfwWindowCloseCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowCloseCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowRefreshCallback glfwSetWindowRefreshCallback(
        long window,
        final @Nullable KGlfwWindowRefreshCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowRefreshCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowFocusCallback glfwSetWindowFocusCallback(
        long window,
        final @Nullable KGlfwWindowFocusCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowFocusCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowIconifyCallback glfwSetWindowIconifyCallback(
        long window,
        final @Nullable KGlfwWindowIconifyCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowIconifyCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowMaximizeCallback glfwSetWindowMaximizeCallback(
        long window,
        final @Nullable KGlfwWindowMaximizeCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowMaximizeCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwFramebufferSizeCallback glfwSetFramebufferSizeCallback(
        long window,
        final @Nullable KGlfwFramebufferSizeCallback cbfun
    ) {
        var result = GLFW.glfwSetFramebufferSizeCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwWindowContentScaleCallback glfwSetWindowContentScaleCallback(
        long window,
        final @Nullable KGlfwWindowContentScaleCallback cbfun
    ) {
        var result = GLFW.glfwSetWindowContentScaleCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
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
    public @Nullable String glfwGetKeyName(int key, int scancode) {
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
    public void glfwGetCursorPos(
        long window,
        final @Nullable DoubleBuffer xpos,
        final @Nullable DoubleBuffer ypos
    ) {
        GLFW.glfwGetCursorPos(window, xpos, ypos);
    }

    @Override
    public void glfwSetCursorPos(long window, double xpos, double ypos) {
        GLFW.glfwSetCursorPos(window, xpos, ypos);
    }

    @Override
    public long glfwCreateCursor(final KGlfwImage image, int xhot, int yhot) {
        return GLFW.glfwCreateCursor(KGlfwLwjglWrapper.wrap(image), xhot, yhot);
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
    public void glfwGetPreeditCursorRectangle(
        long window,
        final @Nullable IntBuffer x,
        final @Nullable IntBuffer y,
        final @Nullable IntBuffer w,
        final @Nullable IntBuffer h
    ) {
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
    public @Nullable IntBuffer glfwGetPreeditCandidate(long window, int index) {
        return GLFW.glfwGetPreeditCandidate(window, index);
    }

    @Override
    public @Nullable KGlfwKeyCallback glfwSetKeyCallback(
        long window,
        final @Nullable KGlfwKeyCallback cbfun
    ) {
        var result = GLFW.glfwSetKeyCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwCharCallback glfwSetCharCallback(
        long window,
        final @Nullable KGlfwCharCallback cbfun
    ) {
        var result = GLFW.glfwSetCharCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwCharModsCallback glfwSetCharModsCallback(
        long window,
        final @Nullable KGlfwCharModsCallback cbfun
    ) {
        var result = GLFW.glfwSetCharModsCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwPreeditCallback glfwSetPreeditCallback(
        long window,
        final @Nullable KGlfwPreeditCallback cbfun
    ) {
        var result = GLFW.glfwSetPreeditCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwImeStatusCallback glfwSetImeStatusCallback(
        long window,
        final @Nullable KGlfwImeStatusCallback cbfun
    ) {
        var result = GLFW.glfwSetIMEStatusCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwPreeditCandidateCallback glfwSetPreeditCandidateCallback(
        long window,
        final @Nullable KGlfwPreeditCandidateCallback cbfun
    ) {
        var result = GLFW.glfwSetPreeditCandidateCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwMouseButtonCallback glfwSetMouseButtonCallback(
        long window,
        final@Nullable  KGlfwMouseButtonCallback cbfun
    ) {
        var result = GLFW.glfwSetMouseButtonCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwCursorPosCallback glfwSetCursorPosCallback(
        long window,
        final @Nullable KGlfwCursorPosCallback cbfun
    ) {
        var result = GLFW.glfwSetCursorPosCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwCursorEnterCallback glfwSetCursorEnterCallback(
        long window,
        final @Nullable KGlfwCursorEnterCallback cbfun
    ) {
        var result = GLFW.glfwSetCursorEnterCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwScrollCallback glfwSetScrollCallback(
        long window,
        final @Nullable KGlfwScrollCallback cbfun
    ) {
        var result = GLFW.glfwSetScrollCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public @Nullable KGlfwDropCallback glfwSetDropCallback(
        long window,
        final @Nullable KGlfwDropCallback cbfun
    ) {
        var result = GLFW.glfwSetDropCallback(window, KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public boolean glfwJoystickPresent(int jid) {
        return GLFW.glfwJoystickPresent(jid);
    }

    @Override
    public @Nullable FloatBuffer glfwGetJoystickAxes(int jid) {
        return GLFW.glfwGetJoystickAxes(jid);
    }

    @Override
    public @Nullable ByteBuffer glfwGetJoystickButtons(int jid) {
        return GLFW.glfwGetJoystickButtons(jid);
    }

    @Override
    public @Nullable ByteBuffer glfwGetJoystickHats(int jid) {
        return GLFW.glfwGetJoystickHats(jid);
    }

    @Override
    public @Nullable String glfwGetJoystickName(int jid) {
        return GLFW.glfwGetJoystickName(jid);
    }

    @Override
    public @Nullable String glfwGetJoystickGUID(int jid) {
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
    public @Nullable KGlfwJoystickCallback glfwSetJoystickCallback(
        final @Nullable KGlfwJoystickCallback cbfun
    ) {
        var result = GLFW.glfwSetJoystickCallback(KGlfwLwjglWrapper.wrap(cbfun));
        return KGlfwLwjglUnwrapper.wrap(result);
    }

    @Override
    public boolean glfwUpdateGamepadMappings(final ByteBuffer string) {
        return GLFW.glfwUpdateGamepadMappings(string);
    }

    @Override
    public @Nullable String glfwGetGamepadName(int jid) {
        return GLFW.glfwGetGamepadName(jid);
    }

    @Override
    public boolean glfwGetGamepadState(int jid, final KGlfwGamepadState state) {
        GLFWGamepadState internalState = GLFWGamepadState.create();
        boolean result = GLFW.glfwGetGamepadState(jid, internalState);
        state.setAxes(internalState.axes());
        state.setButtons(internalState.buttons());
        return result;
    }

    @Override
    public void glfwSetClipboardString(long window, final ByteBuffer string) {
        GLFW.glfwSetClipboardString(window, string);
    }

    @Override
    public void glfwSetClipboardString(long window, final CharSequence string) {
        GLFW.glfwSetClipboardString(window, string);
    }

    @Override
    public @Nullable String glfwGetClipboardString(long window) {
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
    public boolean glfwExtensionSupported(final ByteBuffer extension) {
        return GLFW.glfwExtensionSupported(extension);
    }

    @Override
    public boolean glfwExtensionSupported(final CharSequence extension) {
        return GLFW.glfwExtensionSupported(extension);
    }

    @Override
    public long glfwGetProcAddress(final ByteBuffer procname) {
        return GLFW.glfwGetProcAddress(procname);
    }

    @Override
    public long glfwGetProcAddress(final CharSequence procname) {
        return GLFW.glfwGetProcAddress(procname);
    }

    @Override
    public void glfwGetVersion(
        final int @Nullable [] major,
        final int @Nullable [] minor,
        final int @Nullable [] rev
    ) {
        GLFW.glfwGetVersion(major, minor, rev);
    }

    @Override
    public void glfwGetMonitorPos(
        long monitor,
        final int @Nullable [] xpos,
        final int @Nullable [] ypos
    ) {
        GLFW.glfwGetMonitorPos(monitor, xpos, ypos);
    }

    @Override
    public void glfwGetMonitorWorkarea(
        long monitor,
        final int @Nullable [] xpos,
        final int @Nullable [] ypos,
        final int @Nullable [] width,
        final int @Nullable [] height
    ) {
        GLFW.glfwGetMonitorWorkarea(monitor, xpos, ypos, width, height);
    }

    @Override
    public void glfwGetMonitorPhysicalSize(
        long monitor,
        final int @Nullable [] widthMM,
        final int @Nullable [] heightMM
    ) {
        GLFW.glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM);
    }

    @Override
    public void glfwGetMonitorContentScale(
        long monitor,
        final float @Nullable [] xscale,
        final float @Nullable [] yscale
    ) {
        GLFW.glfwGetMonitorContentScale(monitor, xscale, yscale);
    }

    @Override
    public void glfwGetWindowPos(
        long window,
        final int @Nullable [] xpos,
        final int @Nullable [] ypos
    ) {
        GLFW.glfwGetWindowPos(window, xpos, ypos);
    }

    @Override
    public void glfwGetWindowSize(
        long window,
        final int @Nullable [] width,
        final int @Nullable [] height
    ) {
        GLFW.glfwGetWindowSize(window, width, height);
    }

    @Override
    public void glfwGetFramebufferSize(
        long window,
        final int @Nullable [] width,
        final int @Nullable [] height
    ) {
        GLFW.glfwGetFramebufferSize(window, width, height);
    }

    @Override
    public void glfwGetWindowFrameSize(
        long window,
        final int @Nullable [] left,
        final int @Nullable [] top,
        final int @Nullable [] right,
        final int @Nullable [] bottom
    ) {
        GLFW.glfwGetWindowFrameSize(window, left, top, right, bottom);
    }

    @Override
    public void glfwGetWindowContentScale(
        long window,
        final float @Nullable [] xscale,
        final float @Nullable [] yscale
    ) {
        GLFW.glfwGetWindowContentScale(window, xscale, yscale);
    }

    @Override
    public void glfwGetCursorPos(
        long window,
        final double @Nullable [] xpos,
        final double @Nullable [] ypos
    ) {
        GLFW.glfwGetCursorPos(window, xpos, ypos);
    }

    @Override
    public void glfwGetPreeditCursorRectangle(
        long window,
        final int @Nullable [] x,
        final int @Nullable [] y,
        final int @Nullable [] w,
        final int @Nullable [] h
    ) {
        GLFW.glfwGetPreeditCursorRectangle(window, x, y, w, h);
    }

}
