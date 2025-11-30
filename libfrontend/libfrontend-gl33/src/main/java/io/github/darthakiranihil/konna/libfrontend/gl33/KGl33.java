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

package io.github.darthakiranihil.konna.libfrontend.gl33;

import java.nio.*;

import io.github.darthakiranihil.konna.libfrontend.gl20.KGl20;

public interface KGl33 extends KGl20 {


    // GL 2.1 Constants

    int GL_CURRENT_RASTER_SECONDARY_COLOR = 0x845F;

    int GL_FLOAT_MAT2x3 = 0x8B65,
        GL_FLOAT_MAT2x4 = 0x8B66,
        GL_FLOAT_MAT3x2 = 0x8B67,
        GL_FLOAT_MAT3x4 = 0x8B68,
        GL_FLOAT_MAT4x2 = 0x8B69,
        GL_FLOAT_MAT4x3 = 0x8B6A;

    int GL_PIXEL_PACK_BUFFER = 0x88EB,
        GL_PIXEL_UNPACK_BUFFER = 0x88EC;

    int GL_PIXEL_PACK_BUFFER_BINDING = 0x88ED,
        GL_PIXEL_UNPACK_BUFFER_BINDING = 0x88EF;

    int GL_SRGB = 0x8C40,
        GL_SRGB8 = 0x8C41,
        GL_SRGB_ALPHA = 0x8C42,
        GL_SRGB8_ALPHA8 = 0x8C43,
        GL_SLUMINANCE_ALPHA = 0x8C44,
        GL_SLUMINANCE8_ALPHA8 = 0x8C45,
        GL_SLUMINANCE = 0x8C46,
        GL_SLUMINANCE8 = 0x8C47,
        GL_COMPRESSED_SRGB = 0x8C48,
        GL_COMPRESSED_SRGB_ALPHA = 0x8C49,
        GL_COMPRESSED_SLUMINANCE = 0x8C4A,
        GL_COMPRESSED_SLUMINANCE_ALPHA = 0x8C4B;

    // GL 3.0 Constants

    int GL_MAJOR_VERSION = 0x821B,
        GL_MINOR_VERSION = 0x821C,
        GL_NUM_EXTENSIONS = 0x821D,
        GL_CONTEXT_FLAGS = 0x821E,
        GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 0x1;

    int GL_COMPARE_REF_TO_TEXTURE = KGl20.GL_COMPARE_R_TO_TEXTURE,
        GL_CLIP_DISTANCE0 = KGl20.GL_CLIP_PLANE0,
        GL_CLIP_DISTANCE1 = KGl20.GL_CLIP_PLANE1,
        GL_CLIP_DISTANCE2 = KGl20.GL_CLIP_PLANE2,
        GL_CLIP_DISTANCE3 = KGl20.GL_CLIP_PLANE3,
        GL_CLIP_DISTANCE4 = KGl20.GL_CLIP_PLANE4,
        GL_CLIP_DISTANCE5 = KGl20.GL_CLIP_PLANE5,
        GL_CLIP_DISTANCE6 = 0x3006,
        GL_CLIP_DISTANCE7 = 0x3007,
        GL_MAX_CLIP_DISTANCES = KGl20.GL_MAX_CLIP_PLANES,
        GL_MAX_VARYING_COMPONENTS = KGl20.GL_MAX_VARYING_FLOATS;

    int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 0x88FD;

    int GL_SAMPLER_1D_ARRAY = 0x8DC0,
        GL_SAMPLER_2D_ARRAY = 0x8DC1,
        GL_SAMPLER_1D_ARRAY_SHADOW = 0x8DC3,
        GL_SAMPLER_2D_ARRAY_SHADOW = 0x8DC4,
        GL_SAMPLER_CUBE_SHADOW = 0x8DC5,
        GL_UNSIGNED_INT_VEC2 = 0x8DC6,
        GL_UNSIGNED_INT_VEC3 = 0x8DC7,
        GL_UNSIGNED_INT_VEC4 = 0x8DC8,
        GL_INT_SAMPLER_1D = 0x8DC9,
        GL_INT_SAMPLER_2D = 0x8DCA,
        GL_INT_SAMPLER_3D = 0x8DCB,
        GL_INT_SAMPLER_CUBE = 0x8DCC,
        GL_INT_SAMPLER_1D_ARRAY = 0x8DCE,
        GL_INT_SAMPLER_2D_ARRAY = 0x8DCF,
        GL_UNSIGNED_INT_SAMPLER_1D = 0x8DD1,
        GL_UNSIGNED_INT_SAMPLER_2D = 0x8DD2,
        GL_UNSIGNED_INT_SAMPLER_3D = 0x8DD3,
        GL_UNSIGNED_INT_SAMPLER_CUBE = 0x8DD4,
        GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 0x8DD6,
        GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 0x8DD7;

    int GL_MIN_PROGRAM_TEXEL_OFFSET = 0x8904,
        GL_MAX_PROGRAM_TEXEL_OFFSET = 0x8905;

    int GL_QUERY_WAIT = 0x8E13,
        GL_QUERY_NO_WAIT = 0x8E14,
        GL_QUERY_BY_REGION_WAIT = 0x8E15,
        GL_QUERY_BY_REGION_NO_WAIT = 0x8E16;

    int GL_MAP_READ_BIT = 0x1,
        GL_MAP_WRITE_BIT = 0x2,
        GL_MAP_INVALIDATE_RANGE_BIT = 0x4,
        GL_MAP_INVALIDATE_BUFFER_BIT = 0x8,
        GL_MAP_FLUSH_EXPLICIT_BIT = 0x10,
        GL_MAP_UNSYNCHRONIZED_BIT = 0x20;

    int GL_BUFFER_ACCESS_FLAGS = 0x911F,
        GL_BUFFER_MAP_LENGTH = 0x9120,
        GL_BUFFER_MAP_OFFSET = 0x9121;

    int GL_CLAMP_VERTEX_COLOR = 0x891A,
        GL_CLAMP_FRAGMENT_COLOR = 0x891B,
        GL_CLAMP_READ_COLOR = 0x891C;

    int GL_FIXED_ONLY = 0x891D;

    int GL_DEPTH_COMPONENT32F = 0x8CAC,
        GL_DEPTH32F_STENCIL8 = 0x8CAD;

    int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 0x8DAD;

    int GL_TEXTURE_RED_TYPE = 0x8C10,
        GL_TEXTURE_GREEN_TYPE = 0x8C11,
        GL_TEXTURE_BLUE_TYPE = 0x8C12,
        GL_TEXTURE_ALPHA_TYPE = 0x8C13,
        GL_TEXTURE_LUMINANCE_TYPE = 0x8C14,
        GL_TEXTURE_INTENSITY_TYPE = 0x8C15,
        GL_TEXTURE_DEPTH_TYPE = 0x8C16;

    int GL_UNSIGNED_NORMALIZED = 0x8C17;

    int GL_RGBA32F = 0x8814,
        GL_RGB32F = 0x8815,
        GL_RGBA16F = 0x881A,
        GL_RGB16F = 0x881B;

    int GL_R11F_G11F_B10F = 0x8C3A;

    int GL_UNSIGNED_INT_10F_11F_11F_REV = 0x8C3B;

    int GL_RGB9_E5 = 0x8C3D;

    int GL_UNSIGNED_INT_5_9_9_9_REV = 0x8C3E;

    int GL_TEXTURE_SHARED_SIZE = 0x8C3F;

    int GL_FRAMEBUFFER = 0x8D40,
        GL_READ_FRAMEBUFFER = 0x8CA8,
        GL_DRAW_FRAMEBUFFER = 0x8CA9;

    int GL_RENDERBUFFER = 0x8D41;

    int GL_STENCIL_INDEX1 = 0x8D46,
        GL_STENCIL_INDEX4 = 0x8D47,
        GL_STENCIL_INDEX8 = 0x8D48,
        GL_STENCIL_INDEX16 = 0x8D49;

    int GL_RENDERBUFFER_WIDTH = 0x8D42,
        GL_RENDERBUFFER_HEIGHT = 0x8D43,
        GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44,
        GL_RENDERBUFFER_RED_SIZE = 0x8D50,
        GL_RENDERBUFFER_GREEN_SIZE = 0x8D51,
        GL_RENDERBUFFER_BLUE_SIZE = 0x8D52,
        GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53,
        GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54,
        GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55,
        GL_RENDERBUFFER_SAMPLES = 0x8CAB;

    int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0,
        GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1,
        L_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2,
        GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3,
        GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 0x8CD4,
        GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 0x8210,
        GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 0x8211,
        GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 0x8212,
        GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 0x8213,
        GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 0x8214,
        GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 0x8215,
        GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 0x8216,
        GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 0x8217;

    int GL_FRAMEBUFFER_DEFAULT = 0x8218, GL_INDEX = 0x8222;

    int GL_COLOR_ATTACHMENT0 = 0x8CE0,
        GL_COLOR_ATTACHMENT1 = 0x8CE1,
        GL_COLOR_ATTACHMENT2 = 0x8CE2,
        GL_COLOR_ATTACHMENT3 = 0x8CE3,
        GL_COLOR_ATTACHMENT4 = 0x8CE4,
        GL_COLOR_ATTACHMENT5 = 0x8CE5,
        GL_COLOR_ATTACHMENT6 = 0x8CE6,
        GL_COLOR_ATTACHMENT7 = 0x8CE7,
        GL_COLOR_ATTACHMENT8 = 0x8CE8,
        GL_COLOR_ATTACHMENT9 = 0x8CE9,
        GL_COLOR_ATTACHMENT10 = 0x8CEA,
        GL_COLOR_ATTACHMENT11 = 0x8CEB,
        GL_COLOR_ATTACHMENT12 = 0x8CEC,
        GL_COLOR_ATTACHMENT13 = 0x8CED,
        GL_COLOR_ATTACHMENT14 = 0x8CEE,
        GL_COLOR_ATTACHMENT15 = 0x8CEF,
        GL_COLOR_ATTACHMENT16 = 0x8CF0,
        GL_COLOR_ATTACHMENT17 = 0x8CF1,
        GL_COLOR_ATTACHMENT18 = 0x8CF2,
        GL_COLOR_ATTACHMENT19 = 0x8CF3,
        GL_COLOR_ATTACHMENT20 = 0x8CF4,
        GL_COLOR_ATTACHMENT21 = 0x8CF5,
        GL_COLOR_ATTACHMENT22 = 0x8CF6,
        GL_COLOR_ATTACHMENT23 = 0x8CF7,
        GL_COLOR_ATTACHMENT24 = 0x8CF8,
        GL_COLOR_ATTACHMENT25 = 0x8CF9,
        GL_COLOR_ATTACHMENT26 = 0x8CFA,
        GL_COLOR_ATTACHMENT27 = 0x8CFB,
        GL_COLOR_ATTACHMENT28 = 0x8CFC,
        GL_COLOR_ATTACHMENT29 = 0x8CFD,
        GL_COLOR_ATTACHMENT30 = 0x8CFE,
        GL_COLOR_ATTACHMENT31 = 0x8CFF,
        GL_DEPTH_ATTACHMENT = 0x8D00,
        GL_STENCIL_ATTACHMENT = 0x8D20,
        GL_DEPTH_STENCIL_ATTACHMENT = 0x821A;

    int GL_MAX_SAMPLES = 0x8D57;
    int GL_FRAMEBUFFER_COMPLETE = 0x8CD5,
        GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6,
        GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7,
        GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 0x8CDB,
        GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 0x8CDC,
        GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD,
        GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 0x8D56,
        GL_FRAMEBUFFER_UNDEFINED = 0x8219;

    int GL_FRAMEBUFFER_BINDING = 0x8CA6,
        GL_DRAW_FRAMEBUFFER_BINDING = 0x8CA6,
        GL_READ_FRAMEBUFFER_BINDING = 0x8CAA,
        GL_RENDERBUFFER_BINDING = 0x8CA7,
        GL_MAX_COLOR_ATTACHMENTS = 0x8CDF,
        GL_MAX_RENDERBUFFER_SIZE = 0x84E8;

    int GL_INVALID_FRAMEBUFFER_OPERATION = 0x506;

    int GL_DEPTH_STENCIL = 0x84F9;

    int GL_UNSIGNED_INT_24_8 = 0x84FA;

    int GL_DEPTH24_STENCIL8 = 0x88F0;

    int GL_TEXTURE_STENCIL_SIZE = 0x88F1;

    int GL_HALF_FLOAT = 0x140B;

    int GL_RGBA32UI = 0x8D70,
        GL_RGB32UI = 0x8D71,
        GL_RGBA16UI = 0x8D76,
        GL_RGB16UI = 0x8D77,
        GL_RGBA8UI = 0x8D7C,
        GL_RGB8UI = 0x8D7D,
        GL_RGBA32I = 0x8D82,
        GL_RGB32I = 0x8D83,
        GL_RGBA16I = 0x8D88,
        GL_RGB16I = 0x8D89,
        GL_RGBA8I = 0x8D8E,
        GL_RGB8I = 0x8D8F;

    int GL_RED_INTEGER = 0x8D94,
        GL_GREEN_INTEGER = 0x8D95,
        GL_BLUE_INTEGER = 0x8D96,
        GL_ALPHA_INTEGER = 0x8D97,
        GL_RGB_INTEGER = 0x8D98,
        GL_RGBA_INTEGER = 0x8D99,
        GL_BGR_INTEGER = 0x8D9A,
        GL_BGRA_INTEGER = 0x8D9B;

    int GL_TEXTURE_1D_ARRAY = 0x8C18,
        GL_TEXTURE_2D_ARRAY = 0x8C1A;

    int GL_PROXY_TEXTURE_2D_ARRAY = 0x8C1B;

    int GL_PROXY_TEXTURE_1D_ARRAY = 0x8C19;

    int GL_TEXTURE_BINDING_1D_ARRAY = 0x8C1C,
        GL_TEXTURE_BINDING_2D_ARRAY = 0x8C1D,
        GL_MAX_ARRAY_TEXTURE_LAYERS = 0x88FF;

    int GL_COMPRESSED_RED_RGTC1 = 0x8DBB,
        GL_COMPRESSED_SIGNED_RED_RGTC1 = 0x8DBC,
        GL_COMPRESSED_RG_RGTC2 = 0x8DBD,
        GL_COMPRESSED_SIGNED_RG_RGTC2 = 0x8DBE;

    int GL_R8 = 0x8229,
        GL_R16 = 0x822A,
        GL_RG8 = 0x822B,
        GL_RG16 = 0x822C,
        GL_R16F = 0x822D,
        GL_R32F = 0x822E,
        GL_RG16F = 0x822F,
        GL_RG32F = 0x8230,
        GL_R8I = 0x8231,
        GL_R8UI = 0x8232,
        GL_R16I = 0x8233,
        GL_R16UI = 0x8234,
        GL_R32I = 0x8235,
        GL_R32UI = 0x8236,
        GL_RG8I = 0x8237,
        GL_RG8UI = 0x8238,
        GL_RG16I = 0x8239,
        GL_RG16UI = 0x823A,
        GL_RG32I = 0x823B,
        GL_RG32UI = 0x823C,
        GL_RG = 0x8227,
        GL_COMPRESSED_RED = 0x8225,
        GL_COMPRESSED_RG = 0x8226;

    int GL_RG_INTEGER = 0x8228;

    int GL_TRANSFORM_FEEDBACK_BUFFER = 0x8C8E;

    int GL_TRANSFORM_FEEDBACK_BUFFER_START = 0x8C84,
        GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 0x8C85;

    int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 0x8C8F;

    int GL_INTERLEAVED_ATTRIBS = 0x8C8C, GL_SEPARATE_ATTRIBS = 0x8C8D;

    int GL_PRIMITIVES_GENERATED = 0x8C87,
        GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 0x8C88;

    int GL_RASTERIZER_DISCARD = 0x8C89;

    int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 0x8C8A,
        GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 0x8C8B,
        GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 0x8C80;

    int GL_TRANSFORM_FEEDBACK_VARYINGS = 0x8C83,
        GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 0x8C7F,
        GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 0x8C76;

    int GL_VERTEX_ARRAY_BINDING = 0x85B5;

    int GL_FRAMEBUFFER_SRGB = 0x8DB9;

    // GL 3.1 Constants

    int GL_R8_SNORM = 0x8F94,
        GL_RG8_SNORM = 0x8F95,
        GL_RGB8_SNORM = 0x8F96,
        GL_RGBA8_SNORM = 0x8F97,
        GL_R16_SNORM = 0x8F98,
        GL_RG16_SNORM = 0x8F99,
        GL_RGB16_SNORM = 0x8F9A,
        GL_RGBA16_SNORM = 0x8F9B;

    int GL_SIGNED_NORMALIZED = 0x8F9C;

    int GL_SAMPLER_BUFFER = 0x8DC2,
        GL_INT_SAMPLER_2D_RECT = 0x8DCD,
        GL_INT_SAMPLER_BUFFER = 0x8DD0,
        GL_UNSIGNED_INT_SAMPLER_2D_RECT = 0x8DD5,
        GL_UNSIGNED_INT_SAMPLER_BUFFER = 0x8DD8;

    int GL_COPY_READ_BUFFER = 0x8F36,
        GL_COPY_WRITE_BUFFER = 0x8F37;

    int GL_PRIMITIVE_RESTART = 0x8F9D;

    int GL_PRIMITIVE_RESTART_INDEX = 0x8F9E;

    int GL_TEXTURE_BUFFER = 0x8C2A;

    int GL_MAX_TEXTURE_BUFFER_SIZE = 0x8C2B,
        GL_TEXTURE_BINDING_BUFFER = 0x8C2C,
        GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 0x8C2D;

    int GL_TEXTURE_RECTANGLE = 0x84F5;

    int GL_TEXTURE_BINDING_RECTANGLE = 0x84F6;

    int GL_PROXY_TEXTURE_RECTANGLE = 0x84F7;

    int GL_MAX_RECTANGLE_TEXTURE_SIZE = 0x84F8;

    int GL_SAMPLER_2D_RECT = 0x8B63;

    int GL_SAMPLER_2D_RECT_SHADOW = 0x8B64;

    int GL_UNIFORM_BUFFER = 0x8A11;

    int GL_UNIFORM_BUFFER_BINDING = 0x8A28;

    int GL_UNIFORM_BUFFER_START = 0x8A29,
        GL_UNIFORM_BUFFER_SIZE = 0x8A2A;

    int GL_MAX_VERTEX_UNIFORM_BLOCKS = 0x8A2B,
        GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 0x8A2C,
        GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 0x8A2D,
        GL_MAX_COMBINED_UNIFORM_BLOCKS = 0x8A2E,
        GL_MAX_UNIFORM_BUFFER_BINDINGS = 0x8A2F,
        GL_MAX_UNIFORM_BLOCK_SIZE = 0x8A30,
        GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 0x8A31,
        GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 0x8A32,
        GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 0x8A33,
        GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 0x8A34;

    int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 0x8A35,
        GL_ACTIVE_UNIFORM_BLOCKS = 0x8A36;


    int GL_UNIFORM_TYPE = 0x8A37,
        GL_UNIFORM_SIZE = 0x8A38,
        GL_UNIFORM_NAME_LENGTH = 0x8A39,
        GL_UNIFORM_BLOCK_INDEX = 0x8A3A,
        GL_UNIFORM_OFFSET = 0x8A3B,
        GL_UNIFORM_ARRAY_STRIDE = 0x8A3C,
        GL_UNIFORM_MATRIX_STRIDE = 0x8A3D,
        GL_UNIFORM_IS_ROW_MAJOR = 0x8A3E;

    int GL_UNIFORM_BLOCK_BINDING = 0x8A3F,
        GL_UNIFORM_BLOCK_DATA_SIZE = 0x8A40,
        GL_UNIFORM_BLOCK_NAME_LENGTH = 0x8A41,
        GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 0x8A42,
        GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 0x8A43,
        GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 0x8A44,
        GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 0x8A45,
        GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 0x8A46;

    int GL_INVALID_INDEX = 0xFFFFFFFF;

    // GL 3.2 Constants

    int GL_CONTEXT_PROFILE_MASK = 0x9126;

    int GL_CONTEXT_CORE_PROFILE_BIT = 0x1,
        GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 0x2;

    int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 0x9122,
        GL_MAX_GEOMETRY_INPUT_COMPONENTS = 0x9123,
        GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 0x9124,
        GL_MAX_FRAGMENT_INPUT_COMPONENTS = 0x9125;

    int GL_FIRST_VERTEX_CONVENTION = 0x8E4D,
        GL_LAST_VERTEX_CONVENTION = 0x8E4E;

    int GL_PROVOKING_VERTEX = 0x8E4F,
        GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 0x8E4C;

    int GL_TEXTURE_CUBE_MAP_SEAMLESS = 0x884F;

    int GL_SAMPLE_POSITION = 0x8E50;

    int GL_SAMPLE_MASK = 0x8E51;

    int GL_SAMPLE_MASK_VALUE = 0x8E52;

    int GL_TEXTURE_2D_MULTISAMPLE = 0x9100;

    int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 0x9101;

    int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 0x9102;

    int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 0x9103;

    int GL_MAX_SAMPLE_MASK_WORDS = 0x8E59,
        GL_MAX_COLOR_TEXTURE_SAMPLES = 0x910E,
        GL_MAX_DEPTH_TEXTURE_SAMPLES = 0x910F,
        GL_MAX_INTEGER_SAMPLES = 0x9110,
        GL_TEXTURE_BINDING_2D_MULTISAMPLE = 0x9104,
        GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 0x9105;

    int GL_TEXTURE_SAMPLES = 0x9106,
        GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 0x9107;

    int GL_SAMPLER_2D_MULTISAMPLE = 0x9108,
        GL_INT_SAMPLER_2D_MULTISAMPLE = 0x9109,
        GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 0x910A,
        GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910B,
        GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910C,
        GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910D;

    int GL_DEPTH_CLAMP = 0x864F;

    int GL_GEOMETRY_SHADER = 0x8DD9;

    int GL_GEOMETRY_VERTICES_OUT = 0x8DDA,
        GL_GEOMETRY_INPUT_TYPE = 0x8DDB,
        GL_GEOMETRY_OUTPUT_TYPE = 0x8DDC;

    int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 0x8C29,
        GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 0x8DDF,
        GL_MAX_GEOMETRY_OUTPUT_VERTICES = 0x8DE0,
        GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 0x8DE1;

    int GL_LINES_ADJACENCY = 0xA,
        GL_LINE_STRIP_ADJACENCY = 0xB,
        GL_TRIANGLES_ADJACENCY = 0xC,
        GL_TRIANGLE_STRIP_ADJACENCY = 0xD;

    int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 0x8DA8;

    int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 0x8DA7;

    int GL_PROGRAM_POINT_SIZE = 0x8642;

    int GL_MAX_SERVER_WAIT_TIMEOUT = 0x9111;

    int GL_OBJECT_TYPE = 0x9112,
        GL_SYNC_CONDITION = 0x9113,
        GL_SYNC_STATUS = 0x9114,
        GL_SYNC_FLAGS = 0x9115;

    int GL_SYNC_FENCE = 0x9116;

    int GL_SYNC_GPU_COMMANDS_COMPLETE = 0x9117;

    int GL_UNSIGNALED = 0x9118,
        GL_SIGNALED = 0x9119;

    int GL_SYNC_FLUSH_COMMANDS_BIT = 0x1;

    long GL_TIMEOUT_IGNORED = 0xFFFFFFFFFFFFFFFFL;

    int GL_ALREADY_SIGNALED = 0x911A,
        GL_TIMEOUT_EXPIRED = 0x911B,
        GL_CONDITION_SATISFIED = 0x911C,
        GL_WAIT_FAILED = 0x911D;

    // GL 3.3 Constants

    int GL_SRC1_COLOR = 0x88F9,
        GL_ONE_MINUS_SRC1_COLOR = 0x88FA,
        GL_ONE_MINUS_SRC1_ALPHA = 0x88FB;

    int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 0x88FC;

    int GL_ANY_SAMPLES_PASSED = 0x8C2F;

    int GL_SAMPLER_BINDING = 0x8919;

    int GL_RGB10_A2UI = 0x906F;

    int GL_TEXTURE_SWIZZLE_R = 0x8E42,
        GL_TEXTURE_SWIZZLE_G = 0x8E43,
        GL_TEXTURE_SWIZZLE_B = 0x8E44,
        GL_TEXTURE_SWIZZLE_A = 0x8E45;

    int GL_TEXTURE_SWIZZLE_RGBA = 0x8E46;

    int GL_TIME_ELAPSED = 0x88BF;

    int GL_TIMESTAMP = 0x8E28;

    int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 0x88FE;

    int GL_INT_2_10_10_10_REV = 0x8D9F;

    // GL 2.1 Functions

    void glUniformMatrix2x3fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix3x2fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix2x4fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix4x2fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix3x4fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix4x3fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix2x3fv(int location, boolean transpose, float[] value);
    void glUniformMatrix3x2fv(int location, boolean transpose, float[] value);
    void glUniformMatrix2x4fv(int location, boolean transpose, float[] value);
    void glUniformMatrix4x2fv(int location, boolean transpose, float[] value);
    void glUniformMatrix3x4fv(int location, boolean transpose, float[] value);
    void glUniformMatrix4x3fv(int location, boolean transpose, float[] value);


    // GL 3.0 Functions

    String glGetStringi(int name, int index);
    void glClearBufferiv(int buffer, int drawbuffer, IntBuffer value);
    void glClearBufferuiv(int buffer, int drawbuffer, IntBuffer value);
    void glClearBufferfv(int buffer, int drawbuffer, FloatBuffer value);
    void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil);
    void glVertexAttribI1i(int index, int x);
    void glVertexAttribI2i(int index, int x, int y);
    void glVertexAttribI3i(int index, int x, int y, int z);
    void glVertexAttribI4i(int index, int x, int y, int z, int w);
    void glVertexAttribI1ui(int index, int x);
    void glVertexAttribI2ui(int index, int x, int y);
    void glVertexAttribI3ui(int index, int x, int y, int z);
    void glVertexAttribI4ui(int index, int x, int y, int z, int w);
    void glVertexAttribI1iv(int index, IntBuffer v);
    void glVertexAttribI2iv(int index, IntBuffer v);
    void glVertexAttribI3iv(int index, IntBuffer v);
    void glVertexAttribI4iv(int index, IntBuffer v);
    void glVertexAttribI1uiv(int index, IntBuffer v);
    void glVertexAttribI2uiv(int index, IntBuffer v);
    void glVertexAttribI3uiv(int index, IntBuffer v);
    void glVertexAttribI4uiv(int index, IntBuffer v);
    void glVertexAttribI4bv(int index, ByteBuffer v);
    void glVertexAttribI4sv(int index, ShortBuffer v);
    void glVertexAttribI4ubv(int index, ByteBuffer v);
    void glVertexAttribI4usv(int index, ShortBuffer v);
    void glVertexAttribIPointer(int index, int size, int type, int stride, ByteBuffer pointer);
    void glVertexAttribIPointer(int index, int size, int type, int stride, long pointer);
    void glVertexAttribIPointer(int index, int size, int type, int stride, ShortBuffer pointer);
    void glVertexAttribIPointer(int index, int size, int type, int stride, IntBuffer pointer);
    void glGetVertexAttribIiv(int index, int pname, IntBuffer params);
    int glGetVertexAttribIi(int index, int pname);
    void glGetVertexAttribIuiv(int index, int pname, IntBuffer params);
    int glGetVertexAttribIui(int index, int pname);
    void glUniform1ui(int location, int v0);
    void glUniform2ui(int location, int v0, int v1);
    void glUniform3ui(int location, int v0, int v1, int v2);
    void glUniform4ui(int location, int v0, int v1, int v2, int v3);
    void glUniform1uiv(int location, IntBuffer value);
    void glUniform2uiv(int location, IntBuffer value);
    void glUniform3uiv(int location, IntBuffer value);
    void glUniform4uiv(int location, IntBuffer value);
    void glGetUniformuiv(int program, int location, IntBuffer params);
    int glGetUniformui(int program, int location);
    void glBindFragDataLocation(int program, int colorNumber, ByteBuffer name);
    void glBindFragDataLocation(int program, int colorNumber, CharSequence name);
    int glGetFragDataLocation(int program, ByteBuffer name);
    int glGetFragDataLocation(int program, CharSequence name);
    void glBeginConditionalRender(int id, int mode);
    void glEndConditionalRender();
    ByteBuffer glMapBufferRange(int target, long offset, long length, int access);
    ByteBuffer glMapBufferRange(
        int target,
        long offset,
        long length,
        int access,
        ByteBuffer old_buffer
    );
    void glFlushMappedBufferRange(int target, long offset, long length);
    void glClampColor(int target, int clamp);
    boolean glIsRenderbuffer(int renderbuffer);
    void glBindRenderbuffer(int target, int renderbuffer);
    void glDeleteRenderbuffers(IntBuffer renderbuffers);
    void glDeleteRenderbuffers(int renderbuffer);
    void glGenRenderbuffers(IntBuffer renderbuffers);
    int glGenRenderbuffers();
    void glRenderbufferStorage(int target, int internalformat, int width, int height);
    void glRenderbufferStorageMultisample(
        int target,
        int samples,
        int internalformat,
        int width,
        int height
    );
    void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params);
    int glGetRenderbufferParameteri(int target, int pname);
    boolean glIsFramebuffer(int framebuffer);
    void glBindFramebuffer(int target, int framebuffer);
    void glDeleteFramebuffers(IntBuffer framebuffers);
    void glDeleteFramebuffers(int framebuffer);
    void glGenFramebuffers(IntBuffer framebuffers);
    int glGenFramebuffers();
    int glCheckFramebufferStatus(int target);
    void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level);
    void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);
    void glFramebufferTexture3D(
        int target,
        int attachment,
        int textarget,
        int texture,
        int level,
        int layer
    );
    void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer);
    void glFramebufferRenderbuffer(
        int target,
        int attachment,
        int renderbuffertarget,
        int renderbuffer
    );
    void glGetFramebufferAttachmentParameteriv(
        int target,
        int attachment,
        int pname,
        IntBuffer params
    );
    int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname);
    void glBlitFramebuffer(
        int srcX0,
        int srcY0,
        int srcX1,
        int srcY1,
        int dstX0,
        int dstY0,
        int dstX1,
        int dstY1,
        int mask,
        int filter
    );
    void glGenerateMipmap(int target);
    void glTexParameterIiv(int target, int pname, IntBuffer params);
    void glTexParameterIi(int target, int pname, int param);
    void glTexParameterIuiv(int target, int pname, IntBuffer params);
    void glTexParameterIui(int target, int pname, int param);
    void glGetTexParameterIiv(int target, int pname, IntBuffer params);
    int glGetTexParameterIi(int target, int pname);
    void glGetTexParameterIuiv(int target, int pname, IntBuffer params);
    int glGetTexParameterIui(int target, int pname);
    void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a);
    void glGetBooleani_v(int target, int index, ByteBuffer data);
    boolean glGetBooleani(int target, int index);
    void glGetIntegeri_v(int target, int index, IntBuffer data);
    int glGetIntegeri(int target, int index);
    void glEnablei(int cap, int index);
    void glDisablei(int target, int index);
    boolean glIsEnabledi(int target, int index);
    void glBindBufferRange(int target, int index, int buffer, long offset, long size);
    void glBindBufferBase(int target, int index, int buffer);
    void glBeginTransformFeedback(int primitiveMode);
    void glEndTransformFeedback();
    void glTransformFeedbackVaryings(int program, LongBuffer varyings, int bufferMode);
    void glTransformFeedbackVaryings(int program, CharSequence[] varyings, int bufferMode);
    void glTransformFeedbackVaryings(int program, CharSequence varying, int bufferMode);
    void glGetTransformFeedbackVarying(
        int program,
        int index,
        IntBuffer length,
        IntBuffer size,
        IntBuffer type,
        ByteBuffer name
    );
    String glGetTransformFeedbackVarying(
        int program,
        int index,
        int bufSize,
        IntBuffer size,
        IntBuffer type
    );
    String glGetTransformFeedbackVarying(int program, int index, IntBuffer size, IntBuffer type);
    void glBindVertexArray(int array);
    void glDeleteVertexArrays(IntBuffer arrays);
    void glDeleteVertexArrays(int array);
    void glGenVertexArrays(IntBuffer arrays);
    int glGenVertexArrays();
    boolean glIsVertexArray(int array);
    void glClearBufferiv(int buffer, int drawbuffer, int[] value);
    void glClearBufferuiv(int buffer, int drawbuffer, int[] value);
    void glClearBufferfv(int buffer, int drawbuffer, float[] value);
    void glVertexAttribI1iv(int index, int[] v);
    void glVertexAttribI2iv(int index, int[] v);
    void glVertexAttribI3iv(int index, int[] v);
    void glVertexAttribI4iv(int index, int[] v);
    void glVertexAttribI1uiv(int index, int[] v);
    void glVertexAttribI2uiv(int index, int[] v);
    void glVertexAttribI3uiv(int index, int[] v);
    void glVertexAttribI4uiv(int index, int[] v);
    void glVertexAttribI4sv(int index, short[] v);
    void glVertexAttribI4usv(int index, short[] v);
    void glGetVertexAttribIiv(int index, int pname, int[] params);
    void glGetVertexAttribIuiv(int index, int pname, int[] params);
    void glUniform1uiv(int location, int[] value);
    void glUniform2uiv(int location, int[] value);
    void glUniform3uiv(int location, int[] value);
    void glUniform4uiv(int location, int[] value);
    void glGetUniformuiv(int program, int location, int[] params);
    void glDeleteRenderbuffers(int[] renderbuffers);
    void glGenRenderbuffers(int[] renderbuffers);
    void glGetRenderbufferParameteriv(int target, int pname, int[] params);
    void glDeleteFramebuffers(int[] framebuffers);
    void glGenFramebuffers(int[] framebuffers);
    void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, int[] params);
    void glTexParameterIiv(int target, int pname, int[] params);
    void glTexParameterIuiv(int target, int pname, int[] params);
    void glGetTexParameterIiv(int target, int pname, int[] params);
    void glGetTexParameterIuiv(int target, int pname, int[] params);
    void glGetIntegeri_v(int target, int index, int[] data);

    void glGetTransformFeedbackVarying(
        int program,
        int index,
        int[] length,
        int[] size,
        int[] type,
        ByteBuffer name
    );
    void glDeleteVertexArrays(int[] arrays);
    void glGenVertexArrays(int[] arrays);

    // GL 3.1 Functions

    void glDrawArraysInstanced(int mode, int first, int count, int primcount);
    void glDrawElementsInstanced(int mode, int count, int type, long indices, int primcount);
    void glDrawElementsInstanced(int mode, int type, ByteBuffer indices, int primcount);
    void glDrawElementsInstanced(int mode, ByteBuffer indices, int primcount);
    void glDrawElementsInstanced(int mode, ShortBuffer indices, int primcount);
    void glDrawElementsInstanced(int mode, IntBuffer indices, int primcount);
    void glCopyBufferSubData(
        int readTarget,
        int writeTarget,
        long readOffset,
        long writeOffset,
        long size
    );
    void glPrimitiveRestartIndex(int index);
    void glTexBuffer(int target, int internalformat, int buffer);
    void glGetUniformIndices(int program, LongBuffer uniformNames, IntBuffer uniformIndices);
    void glGetUniformIndices(int program, CharSequence[] uniformNames, IntBuffer uniformIndices);
    int glGetUniformIndices(int program, CharSequence uniformName);
    void glGetActiveUniformsiv(int program, IntBuffer uniformIndices, int pname, IntBuffer params);
    int glGetActiveUniformsi(int program, int uniformIndex, int pname);
    void glGetActiveUniformName(
        int program,
        int uniformIndex,
        IntBuffer length,
        ByteBuffer uniformName
    );
    String glGetActiveUniformName(int program, int uniformIndex, int bufSize);
    String glGetActiveUniformName(int program, int uniformIndex);
    int glGetUniformBlockIndex(int program, ByteBuffer uniformBlockName);
    int glGetUniformBlockIndex(int program, CharSequence uniformBlockName);
    void glGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, IntBuffer params);
    int glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname);
    void glGetActiveUniformBlockName(
        int program,
        int uniformBlockIndex,
        IntBuffer length,
        ByteBuffer uniformBlockName
    );
    String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize);
    String glGetActiveUniformBlockName(int program, int uniformBlockIndex);
    void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding);
    void glGetUniformIndices(int program, LongBuffer uniformNames, int[] uniformIndices);
    void glGetActiveUniformsiv(int program, int[] uniformIndices, int pname, int[] params);
    void glGetActiveUniformName(
        int program,
        int uniformIndex,
        int[] length,
        ByteBuffer uniformName
    );
    void glGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, int[] params);
    void glGetActiveUniformBlockName(
        int program,
        int uniformBlockIndex,
        int[] length,
        ByteBuffer uniformBlockName
    );

    // GL 3.2 Functions

    void glGetBufferParameteri64v(int target, int pname, LongBuffer params);
    long glGetBufferParameteri64(int target, int pname);
    void glDrawElementsBaseVertex(int mode, int count, int type, long indices, int basevertex);
    void glDrawElementsBaseVertex(int mode, int type, ByteBuffer indices, int basevertex);
    void glDrawElementsBaseVertex(int mode, ByteBuffer indices, int basevertex);
    void glDrawElementsBaseVertex(int mode, ShortBuffer indices, int basevertex);
    void glDrawElementsBaseVertex(int mode, IntBuffer indices, int basevertex);
    void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        int count,
        int type,
        long indices,
        int basevertex
    );
    void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        int type,
        ByteBuffer indices,
        int basevertex
    );
    void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        ByteBuffer indices,
        int basevertex
    );
    void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        ShortBuffer indices,
        int basevertex
    );
    void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        IntBuffer indices,
        int basevertex
    );
    void glDrawElementsInstancedBaseVertex(
        int mode,
        int count,
        int type,
        long indices,
        int primcount,
        int basevertex
    );
    void glDrawElementsInstancedBaseVertex(
        int mode,
        int type,
        ByteBuffer indices,
        int primcount,
        int basevertex
    );
    void glDrawElementsInstancedBaseVertex(
        int mode,
        ByteBuffer indices,
        int primcount,
        int basevertex
    );
    void glDrawElementsInstancedBaseVertex(
        int mode,
        ShortBuffer indices,
        int primcount,
        int basevertex
    );
    void glDrawElementsInstancedBaseVertex(
        int mode,
        IntBuffer indices,
        int primcount,
        int basevertex
    );
    void glMultiDrawElementsBaseVertex(
        int mode,
        IntBuffer count,
        int type,
        LongBuffer indices,
        IntBuffer basevertex
    );
    void glProvokingVertex(int mode);
    void glTexImage2DMultisample(
        int target,
        int samples,
        int internalformat,
        int width,
        int height,
        boolean fixedsamplelocations
    );
    void glTexImage3DMultisample(
        int target,
        int samples,
        int internalformat,
        int width,
        int height,
        int depth,
        boolean fixedsamplelocations
    );
    void glGetMultisamplefv(int pname, int index, FloatBuffer val);
    float glGetMultisamplef(int pname, int index);
    void glSampleMaski(int index, int mask);
    void glFramebufferTexture(int target, int attachment, int texture, int level);
    long glFenceSync(int condition, int flags);
    boolean glIsSync(long sync);
    void glDeleteSync(long sync);
    int glClientWaitSync(long sync, int flags, long timeout);
    void glWaitSync(long sync, int flags, long timeout);
    void glGetInteger64v(int pname, LongBuffer params);
    long glGetInteger64(int pname);
    void glGetInteger64i_v(int pname, int index, LongBuffer params);
    long glGetInteger64i(int pname, int index);
    void glGetSynciv(long sync, int pname, IntBuffer length, IntBuffer values);
    int glGetSynci(long sync, int pname, IntBuffer length);
    void glGetBufferParameteri64v(int target, int pname, long[] params);
    void glMultiDrawElementsBaseVertex(
        int mode,
        int[] count,
        int type,
        LongBuffer indices,
        int[] basevertex
    );
    void glGetMultisamplefv(int pname, int index, float[] val);
    void glGetInteger64v(int pname, long[] params);
    void glGetInteger64i_v(int pname, int index, long[] params);
    void glGetSynciv(long sync, int pname, int[] length, int[] values);

    // GL 3.3 Functions

    void glBindFragDataLocationIndexed(int program, int colorNumber, int index, ByteBuffer name);
    void glBindFragDataLocationIndexed(int program, int colorNumber, int index, CharSequence name);
    int glGetFragDataIndex(int program, ByteBuffer name);
    int glGetFragDataIndex(int program, CharSequence name);
    void glGenSamplers(IntBuffer samplers);
    int glGenSamplers();
    void glDeleteSamplers(IntBuffer samplers);
    void glDeleteSamplers(int sampler);
    boolean glIsSampler(int sampler);
    void glBindSampler(int unit, int sampler);
    void glSamplerParameteri(int sampler, int pname, int param);
    void glSamplerParameterf(int sampler, int pname, float param);
    void glSamplerParameteriv(int sampler, int pname, IntBuffer params);
    void glSamplerParameterfv(int sampler, int pname, FloatBuffer params);
    void glSamplerParameterIiv(int sampler, int pname, IntBuffer params);
    void glSamplerParameterIuiv(int sampler, int pname, IntBuffer params);
    void glGetSamplerParameteriv(int sampler, int pname, IntBuffer params);
    int glGetSamplerParameteri(int sampler, int pname);
    void glGetSamplerParameterfv(int sampler, int pname, FloatBuffer params);
    float glGetSamplerParameterf(int sampler, int pname);
    void glGetSamplerParameterIiv(int sampler, int pname, IntBuffer params);
    int glGetSamplerParameterIi(int sampler, int pname);
    void glGetSamplerParameterIuiv(int sampler, int pname, IntBuffer params);
    int glGetSamplerParameterIui(int sampler, int pname);
    void glQueryCounter(int id, int target);
    void glGetQueryObjecti64v(int id, int pname, LongBuffer params);
    void glGetQueryObjecti64v(int id, int pname, long params);
    long glGetQueryObjecti64(int id, int pname);
    void glGetQueryObjectui64v(int id, int pname, LongBuffer params);
    void glGetQueryObjectui64v(int id, int pname, long params);
    long glGetQueryObjectui64(int id, int pname);
    void glVertexAttribDivisor(int index, int divisor);
    void glVertexP2ui(int type, int value);
    void glVertexP3ui(int type, int value);
    void glVertexP4ui(int type, int value);
    void glVertexP2uiv(int type, IntBuffer value);
    void glVertexP3uiv(int type, IntBuffer value);
    void glVertexP4uiv(int type, IntBuffer value);
    void glTexCoordP1ui(int type, int coords);
    void glTexCoordP2ui(int type, int coords);
    void glTexCoordP3ui(int type, int coords);
    void glTexCoordP4ui(int type, int coords);
    void glTexCoordP1uiv(int type, IntBuffer coords);
    void glTexCoordP2uiv(int type, IntBuffer coords);
    void glTexCoordP4uiv(int type, IntBuffer coords);
    void glMultiTexCoordP1ui(int texture, int type, int coords);
    void glMultiTexCoordP2ui(int texture, int type, int coords);
    void glMultiTexCoordP3ui(int texture, int type, int coords);
    void glMultiTexCoordP4ui(int texture, int type, int coords);
    void glMultiTexCoordP1uiv(int texture, int type, IntBuffer coords);
    void glMultiTexCoordP2uiv(int texture, int type, IntBuffer coords);
    void glMultiTexCoordP3uiv(int texture, int type, IntBuffer coords);
    void glMultiTexCoordP4uiv(int texture, int type, IntBuffer coords);
    void glNormalP3ui(int type, int coords);
    void glNormalP3uiv(int type, IntBuffer coords);
    void glColorP3ui(int type, int color);
    void glColorP4ui(int type, int color);
    void glColorP3uiv(int type, IntBuffer color);
    void glColorP4uiv(int type, IntBuffer color);
    void glSecondaryColorP3ui(int type, int color);
    void glSecondaryColorP3uiv(int type, IntBuffer color);
    void glVertexAttribP1ui(int index, int type, boolean normalized, int value);
    void glVertexAttribP2ui(int index, int type, boolean normalized, int value);
    void glVertexAttribP3ui(int index, int type, boolean normalized, int value);
    void glVertexAttribP4ui(int index, int type, boolean normalized, int value);
    void glVertexAttribP1uiv(int index, int type, boolean normalized, IntBuffer value);
    void glVertexAttribP2uiv(int index, int type, boolean normalized, IntBuffer value);
    void glVertexAttribP3uiv(int index, int type, boolean normalized, IntBuffer value);
    void glVertexAttribP4uiv(int index, int type, boolean normalized, IntBuffer value);
    void glGenSamplers(int[] samplers);
    void glDeleteSamplers(int[] samplers);
    void glSamplerParameteriv(int sampler, int pname, int[] params);
    void glSamplerParameterfv(int sampler, int pname, float[] params);
    void glSamplerParameterIiv(int sampler, int pname, int[] params);
    void glSamplerParameterIuiv(int sampler, int pname, int[] params);
    void glGetSamplerParameteriv(int sampler, int pname, int[] params);
    void glGetSamplerParameterfv(int sampler, int pname, float[] params);
    void glGetSamplerParameterIiv(int sampler, int pname, int[] params);
    void glGetSamplerParameterIuiv(int sampler, int pname, int[] params);
    void glGetQueryObjecti64v(int id, int pname, long[] params);
    void glGetQueryObjectui64v(int id, int pname, long[] params);
    void glVertexP2uiv(int type, int[] value);
    void glVertexP3uiv(int type, int[] value);
    void glVertexP4uiv(int type, int[] value);
    void glTexCoordP1uiv(int type, int[] coords);
    void glTexCoordP2uiv(int type, int[] coords);
    void glTexCoordP3uiv(int type, int[] coords);
    void glTexCoordP4uiv(int type, int[] coords);
    void glMultiTexCoordP1uiv(int texture, int type, int[] coords);
    void glMultiTexCoordP2uiv(int texture, int type, int[] coords);
    void glMultiTexCoordP3uiv(int texture, int type, int[] coords);
    void glMultiTexCoordP4uiv(int texture, int type, int[] coords);
    void glNormalP3uiv(int type, int[] coords);
    void glColorP3uiv(int type, int[] color);
    void glColorP4uiv(int type, int[] color);
    void glSecondaryColorP3uiv(int type, int[] color);
    void glVertexAttribP1uiv(int index, int type, boolean normalized, int[] value);
    void glVertexAttribP2uiv(int index, int type, boolean normalized, int[] value);
    void glVertexAttribP3uiv(int index, int type, boolean normalized, int[] value);
    void glVertexAttribP4uiv(int index, int type, boolean normalized, int[] value);

}
