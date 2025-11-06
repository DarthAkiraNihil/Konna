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

package io.github.darthakiranihil.konna.core.graphics.window.std;

import io.github.darthakiranihil.konna.core.graphics.window.KGlfw;
import io.github.darthakiranihil.konna.core.object.KObject;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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
    public void glfwInitAllocator(GLFWAllocator allocator) {
        GLFW.glfwInitAllocator(allocator);
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
    public int glfwGetError(PointerBuffer description) {
        return GLFW.glfwGetError(description);
    }

    @Override
    public GLFWErrorCallback glfwSetErrorCallback(GLFWErrorCallbackI cbfun) {
        return GLFW.glfwSetErrorCallback(cbfun);
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
    public PointerBuffer glfwGetMonitors() {
        return GLFW.glfwGetMonitors();
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
    public GLFWMonitorCallback glfwSetMonitorCallback(GLFWMonitorCallbackI cbfun) {
        return GLFW.glfwSetMonitorCallback(cbfun);
    }

    @Override
    public GLFWVidMode.Buffer glfwGetVideoModes(long monitor) {
        return GLFW.glfwGetVideoModes(monitor);
    }

    @Override
    public GLFWVidMode glfwGetVideoMode(long monitor) {
        return GLFW.glfwGetVideoMode(monitor);
    }

    @Override
    public void glfwSetGamma(long monitor, float gamma) {
        GLFW.glfwSetGamma(monitor, gamma);
    }

    @Override
    public GLFWGammaRamp glfwGetGammaRamp(long monitor) {
        return GLFW.glfwGetGammaRamp(monitor);
    }

    @Override
    public void glfwSetGammaRamp(long monitor, GLFWGammaRamp ramp) {
        GLFW.glfwSetGammaRamp(monitor, ramp);
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
    public void glfwSetWindowIcon(long window, GLFWImage.Buffer images) {
        GLFW.glfwSetWindowIcon(window, images);
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
        GLFW.glfwGetWindowSize(window, width, height);
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
    public GLFWWindowPosCallback glfwSetWindowPosCallback(long window, GLFWWindowPosCallbackI cbfun) {
        return GLFW.glfwSetWindowPosCallback(window, cbfun);
    }

    @Override
    public GLFWWindowSizeCallback glfwSetWindowSizeCallback(long window, GLFWWindowSizeCallbackI cbfun) {
        return GLFW.glfwSetWindowSizeCallback(window, cbfun);
    }

    @Override
    public GLFWWindowCloseCallback glfwSetWindowCloseCallback(long window, GLFWWindowCloseCallbackI cbfun) {
        return GLFW.glfwSetWindowCloseCallback(window, cbfun);
    }

    @Override
    public GLFWWindowRefreshCallback glfwSetWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI cbfun) {
        return GLFW.glfwSetWindowRefreshCallback(window, cbfun);
    }

    @Override
    public GLFWWindowFocusCallback glfwSetWindowFocusCallback(long window, GLFWWindowFocusCallbackI cbfun) {
        return GLFW.glfwSetWindowFocusCallback(window, cbfun);
    }

    @Override
    public GLFWWindowIconifyCallback glfwSetWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI cbfun) {
        return GLFW.glfwSetWindowIconifyCallback(window, cbfun);
    }

    @Override
    public GLFWWindowMaximizeCallback glfwSetWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI cbfun) {
        return GLFW.glfwSetWindowMaximizeCallback(window, cbfun);
    }

    @Override
    public GLFWFramebufferSizeCallback glfwSetFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI cbfun) {
        return GLFW.glfwSetFramebufferSizeCallback(window, cbfun);
    }

    @Override
    public GLFWWindowContentScaleCallback glfwSetWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI cbfun) {
        return GLFW.glfwSetWindowContentScaleCallback(window, cbfun);
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
    public long glfwCreateCursor(GLFWImage image, int xhot, int yhot) {
        return GLFW.glfwCreateCursor(image, xhot, yhot);
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
    public GLFWKeyCallback glfwSetKeyCallback(long window, GLFWKeyCallbackI cbfun) {
        return GLFW.glfwSetKeyCallback(window, cbfun);
    }

    @Override
    public GLFWCharCallback glfwSetCharCallback(long window, GLFWCharCallbackI cbfun) {
        return GLFW.glfwSetCharCallback(window, cbfun);
    }

    @Override
    public GLFWCharModsCallback glfwSetCharModsCallback(long window, GLFWCharModsCallbackI cbfun) {
        return GLFW.glfwSetCharModsCallback(window, cbfun);
    }

    @Override
    public GLFWPreeditCallback glfwSetPreeditCallback(long window, GLFWPreeditCallbackI cbfun) {
        return GLFW.glfwSetPreeditCallback(window, cbfun);
    }

    @Override
    public GLFWIMEStatusCallback glfwSetIMEStatusCallback(long window, GLFWIMEStatusCallbackI cbfun) {
        return GLFW.glfwSetIMEStatusCallback(window, cbfun);
    }

    @Override
    public GLFWPreeditCandidateCallback glfwSetPreeditCandidateCallback(long window, GLFWPreeditCandidateCallbackI cbfun) {
        return GLFW.glfwSetPreeditCandidateCallback(window, cbfun);
    }

    @Override
    public GLFWMouseButtonCallback glfwSetMouseButtonCallback(long window, GLFWMouseButtonCallbackI cbfun) {
        return GLFW.glfwSetMouseButtonCallback(window, cbfun);
    }

    @Override
    public GLFWCursorPosCallback glfwSetCursorPosCallback(long window, GLFWCursorPosCallbackI cbfun) {
        return GLFW.glfwSetCursorPosCallback(window, cbfun);
    }

    @Override
    public GLFWCursorEnterCallback glfwSetCursorEnterCallback(long window, GLFWCursorEnterCallbackI cbfun) {
        return GLFW.glfwSetCursorEnterCallback(window, cbfun);
    }

    @Override
    public GLFWScrollCallback glfwSetScrollCallback(long window, GLFWScrollCallbackI cbfun) {
        return GLFW.glfwSetScrollCallback(window, cbfun);
    }

    @Override
    public GLFWDropCallback glfwSetDropCallback(long window, GLFWDropCallbackI cbfun) {
        return GLFW.glfwSetDropCallback(window, cbfun);
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
    public GLFWJoystickCallback glfwSetJoystickCallback(GLFWJoystickCallbackI cbfun) {
        return GLFW.glfwSetJoystickCallback(cbfun);
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
    public boolean glfwGetGamepadState(int jid, GLFWGamepadState state) {
        return GLFW.glfwGetGamepadState(jid, state);
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
