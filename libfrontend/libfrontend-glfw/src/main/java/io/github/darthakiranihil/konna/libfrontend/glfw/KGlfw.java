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

package io.github.darthakiranihil.konna.libfrontend.glfw;

import org.jspecify.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public interface KGlfw {

    int GLFW_VERSION_MAJOR = 3;
    int GLFW_VERSION_MINOR = 4;
    int GLFW_VERSION_REVISION = 0;
    int
        GLFW_TRUE  = 1,
        GLFW_FALSE = 0;

    int GLFW_RELEASE = 0;

    int GLFW_PRESS = 1;

    int GLFW_REPEAT = 2;

    int
        GLFW_HAT_CENTERED   = 0,
        GLFW_HAT_UP         = 1,
        GLFW_HAT_RIGHT      = 2,
        GLFW_HAT_DOWN       = 4,
        GLFW_HAT_LEFT       = 8,
        GLFW_HAT_RIGHT_UP   = (GLFW_HAT_RIGHT | GLFW_HAT_UP),
        GLFW_HAT_RIGHT_DOWN = (GLFW_HAT_RIGHT | GLFW_HAT_DOWN),
        GLFW_HAT_LEFT_UP    = (GLFW_HAT_LEFT  | GLFW_HAT_UP),
        GLFW_HAT_LEFT_DOWN  = (GLFW_HAT_LEFT  | GLFW_HAT_DOWN);

    int GLFW_KEY_UNKNOWN = -1;

    int
        GLFW_KEY_SPACE         = 32,
        GLFW_KEY_APOSTROPHE    = 39,
        GLFW_KEY_COMMA         = 44,
        GLFW_KEY_MINUS         = 45,
        GLFW_KEY_PERIOD        = 46,
        GLFW_KEY_SLASH         = 47,
        GLFW_KEY_0             = 48,
        GLFW_KEY_1             = 49,
        GLFW_KEY_2             = 50,
        GLFW_KEY_3             = 51,
        GLFW_KEY_4             = 52,
        GLFW_KEY_5             = 53,
        GLFW_KEY_6             = 54,
        GLFW_KEY_7             = 55,
        GLFW_KEY_8             = 56,
        GLFW_KEY_9             = 57,
        GLFW_KEY_SEMICOLON     = 59,
        GLFW_KEY_EQUAL         = 61,
        GLFW_KEY_A             = 65,
        GLFW_KEY_B             = 66,
        GLFW_KEY_C             = 67,
        GLFW_KEY_D             = 68,
        GLFW_KEY_E             = 69,
        GLFW_KEY_F             = 70,
        GLFW_KEY_G             = 71,
        GLFW_KEY_H             = 72,
        GLFW_KEY_I             = 73,
        GLFW_KEY_J             = 74,
        GLFW_KEY_K             = 75,
        GLFW_KEY_L             = 76,
        GLFW_KEY_M             = 77,
        GLFW_KEY_N             = 78,
        GLFW_KEY_O             = 79,
        GLFW_KEY_P             = 80,
        GLFW_KEY_Q             = 81,
        GLFW_KEY_R             = 82,
        GLFW_KEY_S             = 83,
        GLFW_KEY_T             = 84,
        GLFW_KEY_U             = 85,
        GLFW_KEY_V             = 86,
        GLFW_KEY_W             = 87,
        GLFW_KEY_X             = 88,
        GLFW_KEY_Y             = 89,
        GLFW_KEY_Z             = 90,
        GLFW_KEY_LEFT_BRACKET  = 91,
        GLFW_KEY_BACKSLASH     = 92,
        GLFW_KEY_RIGHT_BRACKET = 93,
        GLFW_KEY_GRAVE_ACCENT  = 96,
        GLFW_KEY_WORLD_1       = 161,
        GLFW_KEY_WORLD_2       = 162;

    int
        GLFW_KEY_ESCAPE        = 256,
        GLFW_KEY_ENTER         = 257,
        GLFW_KEY_TAB           = 258,
        GLFW_KEY_BACKSPACE     = 259,
        GLFW_KEY_INSERT        = 260,
        GLFW_KEY_DELETE        = 261,
        GLFW_KEY_RIGHT         = 262,
        GLFW_KEY_LEFT          = 263,
        GLFW_KEY_DOWN          = 264,
        GLFW_KEY_UP            = 265,
        GLFW_KEY_PAGE_UP       = 266,
        GLFW_KEY_PAGE_DOWN     = 267,
        GLFW_KEY_HOME          = 268,
        GLFW_KEY_END           = 269,
        GLFW_KEY_CAPS_LOCK     = 280,
        GLFW_KEY_SCROLL_LOCK   = 281,
        GLFW_KEY_NUM_LOCK      = 282,
        GLFW_KEY_PRINT_SCREEN  = 283,
        GLFW_KEY_PAUSE         = 284,
        GLFW_KEY_F1            = 290,
        GLFW_KEY_F2            = 291,
        GLFW_KEY_F3            = 292,
        GLFW_KEY_F4            = 293,
        GLFW_KEY_F5            = 294,
        GLFW_KEY_F6            = 295,
        GLFW_KEY_F7            = 296,
        GLFW_KEY_F8            = 297,
        GLFW_KEY_F9            = 298,
        GLFW_KEY_F10           = 299,
        GLFW_KEY_F11           = 300,
        GLFW_KEY_F12           = 301,
        GLFW_KEY_F13           = 302,
        GLFW_KEY_F14           = 303,
        GLFW_KEY_F15           = 304,
        GLFW_KEY_F16           = 305,
        GLFW_KEY_F17           = 306,
        GLFW_KEY_F18           = 307,
        GLFW_KEY_F19           = 308,
        GLFW_KEY_F20           = 309,
        GLFW_KEY_F21           = 310,
        GLFW_KEY_F22           = 311,
        GLFW_KEY_F23           = 312,
        GLFW_KEY_F24           = 313,
        GLFW_KEY_F25           = 314,
        GLFW_KEY_KP_0          = 320,
        GLFW_KEY_KP_1          = 321,
        GLFW_KEY_KP_2          = 322,
        GLFW_KEY_KP_3          = 323,
        GLFW_KEY_KP_4          = 324,
        GLFW_KEY_KP_5          = 325,
        GLFW_KEY_KP_6          = 326,
        GLFW_KEY_KP_7          = 327,
        GLFW_KEY_KP_8          = 328,
        GLFW_KEY_KP_9          = 329,
        GLFW_KEY_KP_DECIMAL    = 330,
        GLFW_KEY_KP_DIVIDE     = 331,
        GLFW_KEY_KP_MULTIPLY   = 332,
        GLFW_KEY_KP_SUBTRACT   = 333,
        GLFW_KEY_KP_ADD        = 334,
        GLFW_KEY_KP_ENTER      = 335,
        GLFW_KEY_KP_EQUAL      = 336,
        GLFW_KEY_LEFT_SHIFT    = 340,
        GLFW_KEY_LEFT_CONTROL  = 341,
        GLFW_KEY_LEFT_ALT      = 342,
        GLFW_KEY_LEFT_SUPER    = 343,
        GLFW_KEY_RIGHT_SHIFT   = 344,
        GLFW_KEY_RIGHT_CONTROL = 345,
        GLFW_KEY_RIGHT_ALT     = 346,
        GLFW_KEY_RIGHT_SUPER   = 347,
        GLFW_KEY_MENU          = 348,
        GLFW_KEY_LAST          = GLFW_KEY_MENU;

    int GLFW_MOD_SHIFT = 0x1;
    int GLFW_MOD_CONTROL = 0x2;
    int GLFW_MOD_ALT = 0x4;
    int GLFW_MOD_SUPER = 0x8;
    int GLFW_MOD_CAPS_LOCK = 0x10;
    int GLFW_MOD_NUM_LOCK = 0x20;
    int
        GLFW_MOUSE_BUTTON_1      = 0,
        GLFW_MOUSE_BUTTON_2      = 1,
        GLFW_MOUSE_BUTTON_3      = 2,
        GLFW_MOUSE_BUTTON_4      = 3,
        GLFW_MOUSE_BUTTON_5      = 4,
        GLFW_MOUSE_BUTTON_6      = 5,
        GLFW_MOUSE_BUTTON_7      = 6,
        GLFW_MOUSE_BUTTON_8      = 7,
        GLFW_MOUSE_BUTTON_LAST   = GLFW_MOUSE_BUTTON_8,
        GLFW_MOUSE_BUTTON_LEFT   = GLFW_MOUSE_BUTTON_1,
        GLFW_MOUSE_BUTTON_RIGHT  = GLFW_MOUSE_BUTTON_2,
        GLFW_MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_3;

    int
        GLFW_JOYSTICK_1    = 0,
        GLFW_JOYSTICK_2    = 1,
        GLFW_JOYSTICK_3    = 2,
        GLFW_JOYSTICK_4    = 3,
        GLFW_JOYSTICK_5    = 4,
        GLFW_JOYSTICK_6    = 5,
        GLFW_JOYSTICK_7    = 6,
        GLFW_JOYSTICK_8    = 7,
        GLFW_JOYSTICK_9    = 8,
        GLFW_JOYSTICK_10   = 9,
        GLFW_JOYSTICK_11   = 10,
        GLFW_JOYSTICK_12   = 11,
        GLFW_JOYSTICK_13   = 12,
        GLFW_JOYSTICK_14   = 13,
        GLFW_JOYSTICK_15   = 14,
        GLFW_JOYSTICK_16   = 15,
        GLFW_JOYSTICK_LAST = GLFW_JOYSTICK_16;

    int
        GLFW_GAMEPAD_BUTTON_A            = 0,
        GLFW_GAMEPAD_BUTTON_B            = 1,
        GLFW_GAMEPAD_BUTTON_X            = 2,
        GLFW_GAMEPAD_BUTTON_Y            = 3,
        GLFW_GAMEPAD_BUTTON_LEFT_BUMPER  = 4,
        GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER = 5,
        GLFW_GAMEPAD_BUTTON_BACK         = 6,
        GLFW_GAMEPAD_BUTTON_START        = 7,
        GLFW_GAMEPAD_BUTTON_GUIDE        = 8,
        GLFW_GAMEPAD_BUTTON_LEFT_THUMB   = 9,
        GLFW_GAMEPAD_BUTTON_RIGHT_THUMB  = 10,
        GLFW_GAMEPAD_BUTTON_DPAD_UP      = 11,
        GLFW_GAMEPAD_BUTTON_DPAD_RIGHT   = 12,
        GLFW_GAMEPAD_BUTTON_DPAD_DOWN    = 13,
        GLFW_GAMEPAD_BUTTON_DPAD_LEFT    = 14,
        GLFW_GAMEPAD_BUTTON_LAST         = GLFW_GAMEPAD_BUTTON_DPAD_LEFT,
        GLFW_GAMEPAD_BUTTON_CROSS        = GLFW_GAMEPAD_BUTTON_A,
        GLFW_GAMEPAD_BUTTON_CIRCLE       = GLFW_GAMEPAD_BUTTON_B,
        GLFW_GAMEPAD_BUTTON_SQUARE       = GLFW_GAMEPAD_BUTTON_X,
        GLFW_GAMEPAD_BUTTON_TRIANGLE     = GLFW_GAMEPAD_BUTTON_Y;

    int
        GLFW_GAMEPAD_AXIS_LEFT_X        = 0,
        GLFW_GAMEPAD_AXIS_LEFT_Y        = 1,
        GLFW_GAMEPAD_AXIS_RIGHT_X       = 2,
        GLFW_GAMEPAD_AXIS_RIGHT_Y       = 3,
        GLFW_GAMEPAD_AXIS_LEFT_TRIGGER  = 4,
        GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER = 5,
        GLFW_GAMEPAD_AXIS_LAST          = GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER;

    int
        GLFW_NO_ERROR              = 0,
        GLFW_NOT_INITIALIZED       = 0x10001,
        GLFW_NO_CURRENT_CONTEXT    = 0x10002,
        GLFW_INVALID_ENUM          = 0x10003,
        GLFW_INVALID_VALUE         = 0x10004,
        GLFW_OUT_OF_MEMORY         = 0x10005,
        GLFW_API_UNAVAILABLE       = 0x10006,
        GLFW_VERSION_UNAVAILABLE   = 0x10007,
        GLFW_PLATFORM_ERROR        = 0x10008,
        GLFW_FORMAT_UNAVAILABLE    = 0x10009,
        GLFW_NO_WINDOW_CONTEXT     = 0x1000A,
        GLFW_CURSOR_UNAVAILABLE    = 0x1000B,
        GLFW_FEATURE_UNAVAILABLE   = 0x1000C,
        GLFW_FEATURE_UNIMPLEMENTED = 0x1000D,
        GLFW_PLATFORM_UNAVAILABLE  = 0x1000E;

    int
        GLFW_FOCUSED                 = 0x20001,
        GLFW_ICONIFIED               = 0x20002,
        GLFW_RESIZABLE               = 0x20003,
        GLFW_VISIBLE                 = 0x20004,
        GLFW_DECORATED               = 0x20005,
        GLFW_AUTO_ICONIFY            = 0x20006,
        GLFW_FLOATING                = 0x20007,
        GLFW_MAXIMIZED               = 0x20008,
        GLFW_CENTER_CURSOR           = 0x20009,
        GLFW_TRANSPARENT_FRAMEBUFFER = 0x2000A,
        GLFW_HOVERED                 = 0x2000B,
        GLFW_FOCUS_ON_SHOW           = 0x2000C,
        GLFW_MOUSE_PASSTHROUGH       = 0x2000D,
        GLFW_POSITION_X              = 0x2000E,
        GLFW_POSITION_Y              = 0x2000F,
        GLFW_SOFT_FULLSCREEN         = 0x20010;

    int
        GLFW_CURSOR                  = 0x33001,
        GLFW_STICKY_KEYS             = 0x33002,
        GLFW_STICKY_MOUSE_BUTTONS    = 0x33003,
        GLFW_LOCK_KEY_MODS           = 0x33004,
        GLFW_RAW_MOUSE_MOTION        = 0x33005,
        GLFW_UNLIMITED_MOUSE_BUTTONS = 0x33006,
        GLFW_IME                     = 0x33007;

    int
        GLFW_CURSOR_NORMAL   = 0x34001,
        GLFW_CURSOR_HIDDEN   = 0x34002,
        GLFW_CURSOR_DISABLED = 0x34003,
        GLFW_CURSOR_CAPTURED = 0x34004;

    int GLFW_ARROW_CURSOR = 0x36001;


    int GLFW_IBEAM_CURSOR = 0x36002;


    int GLFW_CROSSHAIR_CURSOR = 0x36003;


    int GLFW_POINTING_HAND_CURSOR = 0x36004;

    int GLFW_RESIZE_EW_CURSOR = 0x36005;

    int GLFW_RESIZE_NS_CURSOR = 0x36006;

    int GLFW_RESIZE_NWSE_CURSOR = 0x36007;

    int GLFW_RESIZE_NESW_CURSOR = 0x36008;

    int GLFW_RESIZE_ALL_CURSOR = 0x36009;

    int GLFW_NOT_ALLOWED_CURSOR = 0x3600A;


    int GLFW_HRESIZE_CURSOR = GLFW_RESIZE_EW_CURSOR;


    int GLFW_VRESIZE_CURSOR = GLFW_RESIZE_NS_CURSOR;


    int GLFW_HAND_CURSOR = GLFW_POINTING_HAND_CURSOR;


    int
        GLFW_CONNECTED    = 0x40001,
        GLFW_DISCONNECTED = 0x40002;

    int GLFW_JOYSTICK_HAT_BUTTONS = 0x50001;

    int GLFW_ANGLE_PLATFORM_TYPE = 0x50002;

    int GLFW_ANY_POSITION = 0x80000000;


    int GLFW_PLATFORM = 0x50003;


    int GLFW_MANAGE_PREEDIT_CANDIDATE = 0x50004;

    int GLFW_COCOA_CHDIR_RESOURCES = 0x51001;

    int GLFW_COCOA_MENUBAR = 0x51002;


    int GLFW_X11_XCB_VULKAN_SURFACE = 0x52001;


    int GLFW_X11_ONTHESPOT = 0x52002;


    int GLFW_WAYLAND_LIBDECOR = 0x53001;


    int
        GLFW_ANY_PLATFORM     = 0x60000,
        GLFW_PLATFORM_WIN32   = 0x60001,
        GLFW_PLATFORM_COCOA   = 0x60002,
        GLFW_PLATFORM_WAYLAND = 0x60003,
        GLFW_PLATFORM_X11     = 0x60004,
        GLFW_PLATFORM_NULL    = 0x60005;


    int GLFW_DONT_CARE = -1;


    int
        GLFW_RED_BITS         = 0x21001,
        GLFW_GREEN_BITS       = 0x21002,
        GLFW_BLUE_BITS        = 0x21003,
        GLFW_ALPHA_BITS       = 0x21004,
        GLFW_DEPTH_BITS       = 0x21005,
        GLFW_STENCIL_BITS     = 0x21006,
        GLFW_ACCUM_RED_BITS   = 0x21007,
        GLFW_ACCUM_GREEN_BITS = 0x21008,
        GLFW_ACCUM_BLUE_BITS  = 0x21009,
        GLFW_ACCUM_ALPHA_BITS = 0x2100A,
        GLFW_AUX_BUFFERS      = 0x2100B,
        GLFW_STEREO           = 0x2100C,
        GLFW_SAMPLES          = 0x2100D,
        GLFW_SRGB_CAPABLE     = 0x2100E,
        GLFW_REFRESH_RATE     = 0x2100F,
        GLFW_DOUBLEBUFFER     = 0x21010;

    int
        GLFW_CLIENT_API               = 0x22001,
        GLFW_CONTEXT_VERSION_MAJOR    = 0x22002,
        GLFW_CONTEXT_VERSION_MINOR    = 0x22003,
        GLFW_CONTEXT_REVISION         = 0x22004,
        GLFW_CONTEXT_ROBUSTNESS       = 0x22005,
        GLFW_OPENGL_FORWARD_COMPAT    = 0x22006,
        GLFW_CONTEXT_DEBUG            = 0x22007,
        GLFW_OPENGL_DEBUG_CONTEXT     = GLFW_CONTEXT_DEBUG,
        GLFW_OPENGL_PROFILE           = 0x22008,
        GLFW_CONTEXT_RELEASE_BEHAVIOR = 0x22009,
        GLFW_CONTEXT_NO_ERROR         = 0x2200A,
        GLFW_CONTEXT_CREATION_API     = 0x2200B,
        GLFW_SCALE_TO_MONITOR         = 0x2200C,
        GLFW_SCALE_FRAMEBUFFER        = 0x2200D;

    int GLFW_COCOA_RETINA_FRAMEBUFFER = 0x23001;

    int GLFW_COCOA_FRAME_NAME = 0x23002;

    int GLFW_COCOA_GRAPHICS_SWITCHING = 0x23003;

    int
        GLFW_X11_CLASS_NAME    = 0x24001,
        GLFW_X11_INSTANCE_NAME = 0x24002;

    int GLFW_WIN32_KEYBOARD_MENU = 0x25001;


    int GLFW_WIN32_SHOWDEFAULT = 0x25002;

    int GLFW_WAYLAND_APP_ID = 0x26001;


    int
        GLFW_NO_API        = 0,
        GLFW_OPENGL_API    = 0x30001,
        GLFW_OPENGL_ES_API = 0x30002;


    int
        GLFW_NO_ROBUSTNESS         = 0,
        GLFW_NO_RESET_NOTIFICATION = 0x31001,
        GLFW_LOSE_CONTEXT_ON_RESET = 0x31002;


    int
        GLFW_OPENGL_ANY_PROFILE    = 0,
        GLFW_OPENGL_CORE_PROFILE   = 0x32001,
        GLFW_OPENGL_COMPAT_PROFILE = 0x32002;


    int
        GLFW_ANY_RELEASE_BEHAVIOR   = 0,
        GLFW_RELEASE_BEHAVIOR_FLUSH = 0x35001,
        GLFW_RELEASE_BEHAVIOR_NONE  = 0x35002;


    int
        GLFW_NATIVE_CONTEXT_API = 0x36001,
        GLFW_EGL_CONTEXT_API    = 0x36002,
        GLFW_OSMESA_CONTEXT_API = 0x36003;


    int
        GLFW_ANGLE_PLATFORM_TYPE_NONE     = 0x37001,
        GLFW_ANGLE_PLATFORM_TYPE_OPENGL   = 0x37002,
        GLFW_ANGLE_PLATFORM_TYPE_OPENGLES = 0x37003,
        GLFW_ANGLE_PLATFORM_TYPE_D3D9     = 0x37004,
        GLFW_ANGLE_PLATFORM_TYPE_D3D11    = 0x37005,
        GLFW_ANGLE_PLATFORM_TYPE_VULKAN   = 0x37007,
        GLFW_ANGLE_PLATFORM_TYPE_METAL    = 0x37008;


    int
        GLFW_WAYLAND_PREFER_LIBDECOR  = 0x38001,
        GLFW_WAYLAND_DISABLE_LIBDECOR = 0x38002;

    boolean glfwInit();
    void glfwTerminate();
    void glfwInitHint(int hint, int value);
    void glfwInitAllocator(@Nullable KGlfwAllocator allocator);
    void glfwGetVersion(@Nullable IntBuffer major, @Nullable IntBuffer minor, @Nullable IntBuffer rev);
    String glfwGetVersionString();
    int glfwGetError(@Nullable LongBuffer description);
    @Nullable KGlfwErrorCallback glfwSetErrorCallback(@Nullable KGlfwErrorCallback cbfun);
    int glfwGetPlatform();
    boolean glfwPlatformSupported(int platform);
    @Nullable LongBuffer glfwGetMonitors();
    long glfwGetPrimaryMonitor();
    void glfwGetMonitorPos(long monitor, @Nullable IntBuffer xpos, @Nullable IntBuffer ypos);
    void glfwGetMonitorWorkarea(
        long monitor,
        @Nullable IntBuffer xpos,
        @Nullable IntBuffer ypos,
        @Nullable IntBuffer width,
        @Nullable IntBuffer height
    );
    void glfwGetMonitorPhysicalSize(long monitor, @Nullable IntBuffer widthMM, @Nullable IntBuffer heightMM);
    void glfwGetMonitorContentScale(long monitor, @Nullable FloatBuffer xscale, @Nullable FloatBuffer yscale);
    @Nullable String glfwGetMonitorName(long monitor);
    void glfwSetMonitorUserPointer(long monitor, long pointer);
    long glfwGetMonitorUserPointer(long monitor);
    @Nullable KGlfwMonitorCallback glfwSetMonitorCallback(@Nullable KGlfwMonitorCallback cbfun);
    KGlfwVidMode @Nullable[] glfwGetVideoModes(long monitor);
    @Nullable KGlfwVidMode glfwGetVideoMode(long monitor);
    void glfwSetGamma(long monitor, float gamma);
    @Nullable KGlfwGammaRamp glfwGetGammaRamp(long monitor);
    void glfwSetGammaRamp(long monitor, KGlfwGammaRamp ramp);
    void glfwDefaultWindowHints();
    void glfwWindowHint(int hint, int value);
    void glfwWindowHintString(int hint, ByteBuffer value);
    void glfwWindowHintString(int hint, CharSequence value);
    long glfwCreateWindow(int width, int height, ByteBuffer title, long monitor, long share);
    long glfwCreateWindow(int width, int height, CharSequence title, long monitor, long share);
    void glfwDestroyWindow(long window);
    boolean glfwWindowShouldClose(long window);
    void glfwSetWindowShouldClose(long window, boolean value);
    @Nullable String glfwGetWindowTitle(long window);
    void glfwSetWindowTitle(long window, ByteBuffer title);
    void glfwSetWindowTitle(long window, CharSequence title);
    void glfwSetWindowIcon(long window, KGlfwImage @Nullable [] images);
    void glfwGetWindowPos(long window, @Nullable IntBuffer xpos, @Nullable IntBuffer ypos);
    void glfwSetWindowPos(long window, int xpos, int ypos);
    void glfwGetWindowSize(long window, @Nullable IntBuffer width, @Nullable IntBuffer height);
    void glfwSetWindowSizeLimits(
        long window,
        int minwidth,
        int minheight,
        int maxwidth,
        int maxheight
    );
    void glfwSetWindowAspectRatio(long window, int numer, int denom);
    void glfwSetWindowSize(long window, int width, int height);
    void glfwGetFramebufferSize(long window, @Nullable IntBuffer width, @Nullable IntBuffer height);
    void glfwGetWindowFrameSize(
        long window,
        @Nullable IntBuffer left,
        @Nullable IntBuffer top,
        @Nullable IntBuffer right,
        @Nullable IntBuffer bottom
    );
    void glfwGetWindowContentScale(long window, @Nullable FloatBuffer xscale, @Nullable FloatBuffer yscale);
    float glfwGetWindowOpacity(long window);
    void glfwSetWindowOpacity(long window, float opacity);
    void glfwIconifyWindow(long window);
    void glfwRestoreWindow(long window);
    void glfwMaximizeWindow(long window);
    void glfwShowWindow(long window);
    void glfwHideWindow(long window);
    void glfwFocusWindow(long window);
    void glfwRequestWindowAttention(long window);
    long glfwGetWindowMonitor(long window);
    void glfwSetWindowMonitor(
        long window,
        long monitor,
        int xpos,
        int ypos,
        int width,
        int height,
        int refreshRate
    );
    int glfwGetWindowAttrib(long window, int attrib);
    void glfwSetWindowAttrib(long window, int attrib, int value);
    void glfwSetWindowUserPointer(long window, long pointer);
    long glfwGetWindowUserPointer(long window);
    @Nullable KGlfwWindowPosCallback glfwSetWindowPosCallback(long window, @Nullable KGlfwWindowPosCallback cbfun);
    @Nullable KGlfwWindowSizeCallback glfwSetWindowSizeCallback(long window, @Nullable KGlfwWindowSizeCallback cbfun);
    @Nullable KGlfwWindowCloseCallback glfwSetWindowCloseCallback(long window, @Nullable KGlfwWindowCloseCallback cbfun);
    @Nullable KGlfwWindowRefreshCallback glfwSetWindowRefreshCallback(
        long window,
        @Nullable KGlfwWindowRefreshCallback cbfun
    );
    @Nullable KGlfwWindowFocusCallback glfwSetWindowFocusCallback(long window, @Nullable KGlfwWindowFocusCallback cbfun);
    @Nullable KGlfwWindowIconifyCallback glfwSetWindowIconifyCallback(
        long window,
        @Nullable KGlfwWindowIconifyCallback cbfun
    );
    @Nullable KGlfwWindowMaximizeCallback glfwSetWindowMaximizeCallback(
        long window,
        @Nullable KGlfwWindowMaximizeCallback cbfun
    );
    @Nullable KGlfwFramebufferSizeCallback glfwSetFramebufferSizeCallback(
        long window,
        @Nullable KGlfwFramebufferSizeCallback cbfun
    );
    @Nullable KGlfwWindowContentScaleCallback glfwSetWindowContentScaleCallback(
        long window,
        @Nullable KGlfwWindowContentScaleCallback cbfun
    );
    void glfwPollEvents();
    void glfwWaitEvents();
    void glfwWaitEventsTimeout(double timeout);
    void glfwPostEmptyEvent();
    int glfwGetInputMode(long window, int mode);
    void glfwSetInputMode(long window, int mode, int value);
    boolean glfwRawMouseMotionSupported();
    @Nullable String glfwGetKeyName(int key, int scancode);
    int glfwGetKeyScancode(int key);
    int glfwGetKey(long window, int key);
    int glfwGetMouseButton(long window, int button);
    void glfwGetCursorPos(long window, @Nullable DoubleBuffer xpos, @Nullable DoubleBuffer ypos);
    void glfwSetCursorPos(long window, double xpos, double ypos);
    long glfwCreateCursor(KGlfwImage image, int xhot, int yhot);
    long glfwCreateStandardCursor(int shape);
    void glfwDestroyCursor(long cursor);
    void glfwSetCursor(long window, long cursor);
    void glfwGetPreeditCursorRectangle(
        long window,
        @Nullable IntBuffer x,
        @Nullable IntBuffer y,
        @Nullable IntBuffer w,
        @Nullable IntBuffer h
    );
    void glfwSetPreeditCursorRectangle(long window, int x, int y, int w, int h);
    void glfwResetPreeditText(long window);
    @Nullable IntBuffer glfwGetPreeditCandidate(long window, int index);
    @Nullable KGlfwKeyCallback glfwSetKeyCallback(long window, @Nullable KGlfwKeyCallback cbfun);
    @Nullable KGlfwCharCallback glfwSetCharCallback(long window, @Nullable KGlfwCharCallback cbfun);
    @Nullable KGlfwCharModsCallback glfwSetCharModsCallback(long window, @Nullable KGlfwCharModsCallback cbfun);
    @Nullable KGlfwPreeditCallback glfwSetPreeditCallback(long window, @Nullable KGlfwPreeditCallback cbfun);
    @Nullable KGlfwImeStatusCallback glfwSetImeStatusCallback(long window, @Nullable KGlfwImeStatusCallback cbfun);
    @Nullable KGlfwPreeditCandidateCallback glfwSetPreeditCandidateCallback(
        long window,
        @Nullable KGlfwPreeditCandidateCallback cbfun
    );
    @Nullable KGlfwMouseButtonCallback glfwSetMouseButtonCallback(long window, @Nullable KGlfwMouseButtonCallback cbfun);
    @Nullable KGlfwCursorPosCallback glfwSetCursorPosCallback(long window, @Nullable KGlfwCursorPosCallback cbfun);
    @Nullable KGlfwCursorEnterCallback glfwSetCursorEnterCallback(long window, @Nullable KGlfwCursorEnterCallback cbfun);
    @Nullable KGlfwScrollCallback glfwSetScrollCallback(long window, @Nullable KGlfwScrollCallback cbfun);
    @Nullable KGlfwDropCallback glfwSetDropCallback(long window, @Nullable KGlfwDropCallback cbfun);
    boolean glfwJoystickPresent(int jid);
    @Nullable FloatBuffer glfwGetJoystickAxes(int jid);
    @Nullable ByteBuffer glfwGetJoystickButtons(int jid);
    @Nullable ByteBuffer glfwGetJoystickHats(int jid);
    @Nullable String glfwGetJoystickName(int jid);
    @Nullable String glfwGetJoystickGUID(int jid);
    void glfwSetJoystickUserPointer(int jid, long pointer);
    long glfwGetJoystickUserPointer(int jid);
    boolean glfwJoystickIsGamepad(int jid);
    @Nullable KGlfwJoystickCallback glfwSetJoystickCallback(@Nullable KGlfwJoystickCallback cbfun);
    boolean glfwUpdateGamepadMappings(ByteBuffer string);
    @Nullable String glfwGetGamepadName(int jid);
    boolean glfwGetGamepadState(int jid, KGlfwGamepadState state);
    void glfwSetClipboardString(long window, ByteBuffer string);
    void glfwSetClipboardString(long window, CharSequence string);
    @Nullable String glfwGetClipboardString(long window);
    double glfwGetTime();
    void glfwSetTime(double time);
    long glfwGetTimerValue();
    long glfwGetTimerFrequency();
    void glfwMakeContextCurrent(long window);
    long glfwGetCurrentContext();
    void glfwSwapBuffers(long window);
    void glfwSwapInterval(int interval);
    boolean glfwExtensionSupported(ByteBuffer extension);
    boolean glfwExtensionSupported(CharSequence extension);
    long glfwGetProcAddress(ByteBuffer procname);
    long glfwGetProcAddress(CharSequence procname);
    void glfwGetVersion(int @Nullable [] major, int @Nullable [] minor, int @Nullable [] rev);
    void glfwGetMonitorPos(long monitor, int @Nullable [] xpos, int @Nullable [] ypos);
    void glfwGetMonitorWorkarea(long monitor, int @Nullable [] xpos, int @Nullable [] ypos, int @Nullable [] width, int @Nullable [] height);
    void glfwGetMonitorPhysicalSize(long monitor, int @Nullable [] widthMM, int @Nullable [] heightMM);
    void glfwGetMonitorContentScale(long monitor, float @Nullable [] xscale, float @Nullable [] yscale);
    void glfwGetWindowPos(long window, int @Nullable [] xpos, int @Nullable [] ypos);
    void glfwGetWindowSize(long window, int @Nullable [] width, int @Nullable [] height);
    void glfwGetFramebufferSize(long window, int @Nullable [] width, int @Nullable [] height);
    void glfwGetWindowFrameSize(long window, int @Nullable [] left, int @Nullable [] top, int @Nullable [] right, int @Nullable [] bottom);
    void glfwGetWindowContentScale(long window, float @Nullable [] xscale, float @Nullable [] yscale);
    void glfwGetCursorPos(long window, double @Nullable [] xpos, double @Nullable [] ypos);
    void glfwGetPreeditCursorRectangle(long window, int @Nullable [] x, int @Nullable [] y, int @Nullable [] w, int @Nullable [] h);

}
