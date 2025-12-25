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

package io.github.darthakiranihil.konna.backend.lwjgl.opengl;

import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;
import org.jspecify.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL33;

import java.nio.*;

/**
 * OpenGL 3.3 library frontend implementation,
 * using corresponding bindings from {@link GL33}.
 *
 * @author Darth Akira Nihil
 * @version 0.1.0
 */
@KExcludeFromGeneratedCoverageReport
public final class KGl33Lwjgl extends KGl20Lwjgl implements KGl33 {

    @Override
    public void glUniformMatrix2x3fv(int location, boolean transpose, final FloatBuffer value) {
        GL33.glUniformMatrix2x3fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3x2fv(int location, boolean transpose, final FloatBuffer value) {
        GL33.glUniformMatrix3x2fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix2x4fv(int location, boolean transpose, final FloatBuffer value) {
        GL33.glUniformMatrix2x4fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4x2fv(int location, boolean transpose, final FloatBuffer value) {
        GL33.glUniformMatrix4x2fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3x4fv(int location, boolean transpose, final FloatBuffer value) {
        GL33.glUniformMatrix3x4fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4x3fv(int location, boolean transpose, final FloatBuffer value) {
        GL33.glUniformMatrix4x3fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix2x3fv(int location, boolean transpose, final float[] value) {
        GL33.glUniformMatrix2x3fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3x2fv(int location, boolean transpose, final float[] value) {
        GL33.glUniformMatrix3x2fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix2x4fv(int location, boolean transpose, final float[] value) {
        GL33.glUniformMatrix2x4fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4x2fv(int location, boolean transpose, final float[] value) {
        GL33.glUniformMatrix4x2fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3x4fv(int location, boolean transpose, final float[] value) {
        GL33.glUniformMatrix3x4fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4x3fv(int location, boolean transpose, final float[] value) {
        GL33.glUniformMatrix4x3fv(location, transpose, value);
    }

    @Override
    public @Nullable String glGetStringi(int name, int index) {
        return GL33.glGetStringi(name, index);
    }

    @Override
    public void glClearBufferiv(int buffer, int drawbuffer, final IntBuffer value) {
        GL33.glClearBufferiv(buffer, drawbuffer, value);
    }

    @Override
    public void glClearBufferuiv(int buffer, int drawbuffer, final IntBuffer value) {
        GL33.glClearBufferuiv(buffer, drawbuffer, value);
    }

    @Override
    public void glClearBufferfv(int buffer, int drawbuffer, final FloatBuffer value) {
        GL33.glClearBufferfv(buffer, drawbuffer, value);
    }

    @Override
    public void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) {
        GL33.glClearBufferfi(buffer, drawbuffer, depth, stencil);
    }

    @Override
    public void glVertexAttribI1i(int index, int x) {
        GL33.glVertexAttribI1i(index, x);
    }

    @Override
    public void glVertexAttribI2i(int index, int x, int y) {
        GL33.glVertexAttribI2i(index, x, y);
    }

    @Override
    public void glVertexAttribI3i(int index, int x, int y, int z) {
        GL33.glVertexAttribI3i(index, x, y, z);
    }

    @Override
    public void glVertexAttribI4i(int index, int x, int y, int z, int w) {
        GL33.glVertexAttribI4i(index, x, y, z, w);
    }

    @Override
    public void glVertexAttribI1ui(int index, int x) {
        GL33.glVertexAttribI1ui(index, x);
    }

    @Override
    public void glVertexAttribI2ui(int index, int x, int y) {
        GL33.glVertexAttribI2ui(index, x, y);
    }

    @Override
    public void glVertexAttribI3ui(int index, int x, int y, int z) {
        GL33.glVertexAttribI3ui(index, x, y, z);
    }

    @Override
    public void glVertexAttribI4ui(int index, int x, int y, int z, int w) {
        GL33.glVertexAttribI4ui(index, x, y, z, w);
    }

    @Override
    public void glVertexAttribI1iv(int index, final IntBuffer v) {
        GL33.glVertexAttribI1iv(index, v);
    }

    @Override
    public void glVertexAttribI2iv(int index, final IntBuffer v) {
        GL33.glVertexAttribI2iv(index, v);
    }

    @Override
    public void glVertexAttribI3iv(int index, final IntBuffer v) {
        GL33.glVertexAttribI3iv(index, v);
    }

    @Override
    public void glVertexAttribI4iv(int index, final IntBuffer v) {
        GL33.glVertexAttribI4iv(index, v);
    }

    @Override
    public void glVertexAttribI1uiv(int index, final IntBuffer v) {
        GL33.glVertexAttribI1uiv(index, v);
    }

    @Override
    public void glVertexAttribI2uiv(int index, final IntBuffer v) {
        GL33.glVertexAttribI2uiv(index, v);
    }

    @Override
    public void glVertexAttribI3uiv(int index, final IntBuffer v) {
        GL33.glVertexAttribI3uiv(index, v);
    }

    @Override
    public void glVertexAttribI4uiv(int index, final IntBuffer v) {
        GL33.glVertexAttribI4uiv(index, v);
    }

    @Override
    public void glVertexAttribI4bv(int index, final ByteBuffer v) {
        GL33.glVertexAttribI4bv(index, v);
    }

    @Override
    public void glVertexAttribI4sv(int index, final ShortBuffer v) {
        GL33.glVertexAttribI4sv(index, v);
    }

    @Override
    public void glVertexAttribI4ubv(int index, final ByteBuffer v) {
        GL33.glVertexAttribI4ubv(index, v);
    }

    @Override
    public void glVertexAttribI4usv(int index, final ShortBuffer v) {
        GL33.glVertexAttribI4usv(index, v);
    }

    @Override
    public void glVertexAttribIPointer(
        int index,
        int size,
        int type,
        int stride,
        final ByteBuffer pointer
    ) {
        GL33.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    @Override
    public void glVertexAttribIPointer(int index, int size, int type, int stride, long pointer) {
        GL33.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    @Override
    public void glVertexAttribIPointer(
        int index,
        int size,
        int type,
        int stride,
        final ShortBuffer pointer
    ) {
        GL33.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    @Override
    public void glVertexAttribIPointer(
        int index,
        int size,
        int type,
        int stride,
        final IntBuffer pointer
    ) {
        GL33.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    @Override
    public void glGetVertexAttribIiv(int index, int pname, final IntBuffer params) {
        GL33.glGetVertexAttribIiv(index, pname, params);
    }

    @Override
    public int glGetVertexAttribIi(int index, int pname) {
        return GL33.glGetVertexAttribIi(index, pname);
    }

    @Override
    public void glGetVertexAttribIuiv(int index, int pname, final IntBuffer params) {
        GL33.glGetVertexAttribIuiv(index, pname, params);
    }

    @Override
    public int glGetVertexAttribIui(int index, int pname) {
        return GL33.glGetVertexAttribIui(index, pname);
    }

    @Override
    public void glUniform1ui(int location, int v0) {
        GL33.glUniform1ui(location, v0);
    }

    @Override
    public void glUniform2ui(int location, int v0, int v1) {
        GL33.glUniform2ui(location, v0, v1);
    }

    @Override
    public void glUniform3ui(int location, int v0, int v1, int v2) {
        GL33.glUniform3ui(location, v0, v1, v2);
    }

    @Override
    public void glUniform4ui(int location, int v0, int v1, int v2, int v3) {
        GL33.glUniform4ui(location, v0, v1, v2, v3);
    }

    @Override
    public void glUniform1uiv(int location, final IntBuffer value) {
        GL33.glUniform1uiv(location, value);
    }

    @Override
    public void glUniform2uiv(int location, final IntBuffer value) {
        GL33.glUniform2uiv(location, value);
    }

    @Override
    public void glUniform3uiv(int location, final IntBuffer value) {
        GL33.glUniform3uiv(location, value);
    }

    @Override
    public void glUniform4uiv(int location, final IntBuffer value) {
        GL33.glUniform4uiv(location, value);
    }

    @Override
    public void glGetUniformuiv(int program, int location, final IntBuffer params) {
        GL33.glGetUniformuiv(program, location, params);
    }

    @Override
    public int glGetUniformui(int program, int location) {
        return GL33.glGetUniformui(program, location);
    }

    @Override
    public void glBindFragDataLocation(int program, int colorNumber, final ByteBuffer name) {
        GL33.glBindFragDataLocation(program, colorNumber, name);
    }

    @Override
    public void glBindFragDataLocation(int program, int colorNumber, final CharSequence name) {
        GL33.glBindFragDataLocation(program, colorNumber, name);
    }

    @Override
    public int glGetFragDataLocation(int program, final ByteBuffer name) {
        return GL33.glGetFragDataLocation(program, name);
    }

    @Override
    public int glGetFragDataLocation(int program, final CharSequence name) {
        return GL33.glGetFragDataLocation(program, name);
    }

    @Override
    public void glBeginConditionalRender(int id, int mode) {
        GL33.glBeginConditionalRender(id, mode);
    }

    @Override
    public void glEndConditionalRender() {
        GL33.glEndConditionalRender();
    }

    @Override
    public @Nullable ByteBuffer glMapBufferRange(int target, long offset, long length, int access) {
        return GL33.glMapBufferRange(target, offset, length, access);
    }

    @Override
    public @Nullable ByteBuffer glMapBufferRange(
        int target,
        long offset,
        long length,
        int access,
        final @Nullable ByteBuffer old_buffer
    ) {
        return GL33.glMapBufferRange(target, offset, length, access, old_buffer);
    }

    @Override
    public void glFlushMappedBufferRange(int target, long offset, long length) {
        GL33.glFlushMappedBufferRange(target, offset, length);
    }

    @Override
    public void glClampColor(int target, int clamp) {
        GL33.glClampColor(target, clamp);
    }

    @Override
    public boolean glIsRenderbuffer(int renderbuffer) {
        return GL33.glIsRenderbuffer(renderbuffer);
    }

    @Override
    public void glBindRenderbuffer(int target, int renderbuffer) {
        GL33.glBindRenderbuffer(target, renderbuffer);
    }

    @Override
    public void glDeleteRenderbuffers(final IntBuffer renderbuffers) {
        GL33.glDeleteRenderbuffers(renderbuffers);
    }

    @Override
    public void glDeleteRenderbuffers(int renderbuffer) {
        GL33.glDeleteRenderbuffers(renderbuffer);
    }

    @Override
    public void glGenRenderbuffers(final IntBuffer renderbuffers) {
        GL33.glGenRenderbuffers(renderbuffers);
    }

    @Override
    public int glGenRenderbuffers() {
        return GL33.glGenRenderbuffers();
    }

    @Override
    public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
        GL33.glRenderbufferStorage(target, internalformat, width, height);
    }

    @Override
    public void glRenderbufferStorageMultisample(
        int target,
        int samples,
        int internalformat,
        int width,
        int height
    ) {
        GL33.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
    }

    @Override
    public void glGetRenderbufferParameteriv(int target, int pname, final IntBuffer params) {
        GL33.glGetRenderbufferParameteriv(target, pname, params);
    }

    @Override
    public int glGetRenderbufferParameteri(int target, int pname) {
        return GL33.glGetRenderbufferParameteri(target, pname);
    }

    @Override
    public boolean glIsFramebuffer(int framebuffer) {
        return GL33.glIsFramebuffer(framebuffer);
    }

    @Override
    public void glBindFramebuffer(int target, int framebuffer) {
        GL33.glBindFramebuffer(target, framebuffer);
    }

    @Override
    public void glDeleteFramebuffers(final IntBuffer framebuffers) {
        GL33.glDeleteFramebuffers(framebuffers);
    }

    @Override
    public void glDeleteFramebuffers(int framebuffer) {
        GL33.glDeleteFramebuffers(framebuffer);
    }

    @Override
    public void glGenFramebuffers(final IntBuffer framebuffers) {
        GL33.glGenFramebuffers(framebuffers);
    }

    @Override
    public int glGenFramebuffers() {
        return GL33.glGenFramebuffers();
    }

    @Override
    public int glCheckFramebufferStatus(int target) {
        return GL33.glCheckFramebufferStatus(target);
    }

    @Override
    public void glFramebufferTexture1D(
        int target,
        int attachment,
        int textarget,
        int texture,
        int level
    ) {
        GL33.glFramebufferTexture1D(target, attachment, textarget, texture, level);
    }

    @Override
    public void glFramebufferTexture2D(
        int target,
        int attachment,
        int textarget,
        int texture,
        int level
    ) {
        GL33.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    @Override
    public void glFramebufferTexture3D(
        int target,
        int attachment,
        int textarget,
        int texture,
        int level,
        int layer
    ) {
        GL33.glFramebufferTexture3D(target, attachment, textarget, texture, level, layer);
    }

    @Override
    public void glFramebufferTextureLayer(
        int target,
        int attachment,
        int texture,
        int level,
        int layer
    ) {
        GL33.glFramebufferTextureLayer(target, attachment, texture, level, layer);
    }

    @Override
    public void glFramebufferRenderbuffer(
        int target,
        int attachment,
        int renderbuffertarget,
        int renderbuffer
    ) {
        GL33.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv(
        int target,
        int attachment,
        int pname,
        final IntBuffer params
    ) {
        GL33.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
    }

    @Override
    public int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname) {
        return GL33.glGetFramebufferAttachmentParameteri(target, attachment, pname);
    }

    @Override
    public void glBlitFramebuffer(
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
    ) {
        GL33.glBlitFramebuffer(
            srcX0,
            srcY0,
            srcX1,
            srcY1,
            dstX0,
            dstY0,
            dstX1,
            dstY1,
            mask,
            filter
        );
    }

    @Override
    public void glGenerateMipmap(int target) {
        GL33.glGenerateMipmap(target);
    }

    @Override
    public void glTexParameterIiv(int target, int pname, final IntBuffer params) {
        GL33.glTexParameterIiv(target, pname, params);
    }

    @Override
    public void glTexParameterIi(int target, int pname, int param) {
        GL33.glTexParameterIi(target, pname, param);
    }

    @Override
    public void glTexParameterIuiv(int target, int pname, final IntBuffer params) {
        GL33.glTexParameterIuiv(target, pname, params);
    }

    @Override
    public void glTexParameterIui(int target, int pname, int param) {
        GL33.glTexParameterIui(target, pname, param);
    }

    @Override
    public void glGetTexParameterIiv(int target, int pname, final IntBuffer params) {
        GL33.glGetTexParameterIiv(target, pname, params);
    }

    @Override
    public int glGetTexParameterIi(int target, int pname) {
        return GL33.glGetTexParameterIi(target, pname);
    }

    @Override
    public void glGetTexParameterIuiv(int target, int pname, final IntBuffer params) {
        GL33.glGetTexParameterIuiv(target, pname, params);
    }

    @Override
    public int glGetTexParameterIui(int target, int pname) {
        return GL33.glGetTexParameterIui(target, pname);
    }

    @Override
    public void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a) {
        GL33.glColorMaski(buf, r, g, b, a);
    }

    @Override
    public void glGetBooleani_v(int target, int index, final ByteBuffer data) {
        GL33.glGetBooleani_v(target, index, data);
    }

    @Override
    public boolean glGetBooleani(int target, int index) {
        return GL33.glGetBooleani(target, index);
    }

    @Override
    public void glGetIntegeri_v(int target, int index, final IntBuffer data) {
        GL33.glGetIntegeri_v(target, index, data);
    }

    @Override
    public int glGetIntegeri(int target, int index) {
        return GL33.glGetIntegeri(target, index);
    }

    @Override
    public void glEnablei(int cap, int index) {
        GL33.glEnablei(cap, index);
    }

    @Override
    public void glDisablei(int target, int index) {
        GL33.glDisablei(target, index);
    }

    @Override
    public boolean glIsEnabledi(int target, int index) {
        return GL33.glIsEnabledi(target, index);
    }

    @Override
    public void glBindBufferRange(int target, int index, int buffer, long offset, long size) {
        GL33.glBindBufferRange(target, index, buffer, offset, size);
    }

    @Override
    public void glBindBufferBase(int target, int index, int buffer) {
        GL33.glBindBufferBase(target, index, buffer);
    }

    @Override
    public void glBeginTransformFeedback(int primitiveMode) {
        GL33.glBeginTransformFeedback(primitiveMode);
    }

    @Override
    public void glEndTransformFeedback() {
        GL33.glEndTransformFeedback();
    }

    @Override
    public void glTransformFeedbackVaryings(
        int program,
        final LongBuffer varyings,
        int bufferMode
    ) {
        PointerBuffer buf = BufferUtils.createPointerBuffer(varyings.capacity());
        buf.put(varyings);

        GL33.glTransformFeedbackVaryings(program, buf, bufferMode);

        LongBuffer result = LongBuffer.allocate(varyings.capacity());
        for (int i = 0; i < varyings.capacity(); i++) {
            result.put(buf.get(i));
        }
    }

    @Override
    public void glTransformFeedbackVaryings(
        int program,
        final CharSequence[] varyings,
        int bufferMode
    ) {
        GL33.glTransformFeedbackVaryings(program, varyings, bufferMode);
    }

    @Override
    public void glTransformFeedbackVaryings(
        int program,
        final CharSequence varying,
        int bufferMode
    ) {
        GL33.glTransformFeedbackVaryings(program, varying, bufferMode);
    }

    @Override
    public void glGetTransformFeedbackVarying(
        int program,
        int index,
        final @Nullable IntBuffer length,
        final IntBuffer size,
        final IntBuffer type,
        final ByteBuffer name
    ) {
        GL33.glGetTransformFeedbackVarying(program, index, length, size, type, name);
    }

    @Override
    public String glGetTransformFeedbackVarying(
        int program,
        int index,
        int bufSize,
        final IntBuffer size,
        final IntBuffer type
    ) {
        return GL33.glGetTransformFeedbackVarying(program, index, bufSize, size, type);
    }

    @Override
    public String glGetTransformFeedbackVarying(
        int program,
        int index,
        final IntBuffer size,
        final IntBuffer type
    ) {
        return GL33.glGetTransformFeedbackVarying(program, index, size, type);
    }

    @Override
    public void glBindVertexArray(int array) {
        GL33.glBindVertexArray(array);
    }

    @Override
    public void glDeleteVertexArrays(final IntBuffer arrays) {
        GL33.glDeleteVertexArrays(arrays);
    }

    @Override
    public void glDeleteVertexArrays(int array) {
        GL33.glDeleteVertexArrays(array);
    }

    @Override
    public void glGenVertexArrays(final IntBuffer arrays) {
        GL33.glGenVertexArrays(arrays);
    }

    @Override
    public int glGenVertexArrays() {
        return GL33.glGenVertexArrays();
    }

    @Override
    public boolean glIsVertexArray(int array) {
        return GL33.glIsVertexArray(array);
    }

    @Override
    public void glClearBufferiv(int buffer, int drawbuffer, final int[] value) {
        GL33.glClearBufferiv(buffer, drawbuffer, value);
    }

    @Override
    public void glClearBufferuiv(int buffer, int drawbuffer, final int[] value) {
        GL33.glClearBufferuiv(buffer, drawbuffer, value);
    }

    @Override
    public void glClearBufferfv(int buffer, int drawbuffer, final float[] value) {
        GL33.glClearBufferfv(buffer, drawbuffer, value);
    }

    @Override
    public void glVertexAttribI1iv(int index, final int[] v) {
        GL33.glVertexAttribI1iv(index, v);
    }

    @Override
    public void glVertexAttribI2iv(int index, final int[] v) {
        GL33.glVertexAttribI2iv(index, v);
    }

    @Override
    public void glVertexAttribI3iv(int index, final int[] v) {
        GL33.glVertexAttribI3iv(index, v);
    }

    @Override
    public void glVertexAttribI4iv(int index, final int[] v) {
        GL33.glVertexAttribI4iv(index, v);
    }

    @Override
    public void glVertexAttribI1uiv(int index, final int[] v) {
        GL33.glVertexAttribI1uiv(index, v);
    }

    @Override
    public void glVertexAttribI2uiv(int index, final int[] v) {
        GL33.glVertexAttribI2uiv(index, v);
    }

    @Override
    public void glVertexAttribI3uiv(int index, final int[] v) {
        GL33.glVertexAttribI3uiv(index, v);
    }

    @Override
    public void glVertexAttribI4uiv(int index, final int[] v) {
        GL33.glVertexAttribI4uiv(index, v);
    }

    @Override
    public void glVertexAttribI4sv(int index, final short[] v) {
        GL33.glVertexAttribI4sv(index, v);
    }

    @Override
    public void glVertexAttribI4usv(int index, final short[] v) {
        GL33.glVertexAttribI4usv(index, v);
    }

    @Override
    public void glGetVertexAttribIiv(int index, int pname, final int[] params) {
        GL33.glGetVertexAttribIiv(index, pname, params);
    }

    @Override
    public void glGetVertexAttribIuiv(int index, int pname, final int[] params) {
        GL33.glGetVertexAttribIuiv(index, pname, params);
    }

    @Override
    public void glUniform1uiv(int location, final int[] value) {
        GL33.glUniform1uiv(location, value);
    }

    @Override
    public void glUniform2uiv(int location, final int[] value) {
        GL33.glUniform2uiv(location, value);
    }

    @Override
    public void glUniform3uiv(int location, final int[] value) {
        GL33.glUniform3uiv(location, value);
    }

    @Override
    public void glUniform4uiv(int location, final int[] value) {
        GL33.glUniform4uiv(location, value);
    }

    @Override
    public void glGetUniformuiv(int program, int location, final int[] params) {
        GL33.glGetUniformuiv(program, location, params);
    }

    @Override
    public void glDeleteRenderbuffers(final int[] renderbuffers) {
        GL33.glDeleteRenderbuffers(renderbuffers);
    }

    @Override
    public void glGenRenderbuffers(final int[] renderbuffers) {
        GL33.glGenRenderbuffers(renderbuffers);
    }

    @Override
    public void glGetRenderbufferParameteriv(int target, int pname, final int[] params) {
        GL33.glGetRenderbufferParameteriv(target, pname, params);
    }

    @Override
    public void glDeleteFramebuffers(final int[] framebuffers) {
        GL33.glDeleteFramebuffers(framebuffers);
    }

    @Override
    public void glGenFramebuffers(final int[] framebuffers) {
        GL33.glGenFramebuffers(framebuffers);
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv(
        int target,
        int attachment,
        int pname,
        final int[] params
    ) {
        GL33.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
    }

    @Override
    public void glTexParameterIiv(int target, int pname, final int[] params) {
        GL33.glTexParameterIiv(target, pname, params);
    }

    @Override
    public void glTexParameterIuiv(int target, int pname, final int[] params) {
        GL33.glTexParameterIuiv(target, pname, params);
    }

    @Override
    public void glGetTexParameterIiv(int target, int pname, final int[] params) {
        GL33.glGetTexParameterIiv(target, pname, params);
    }

    @Override
    public void glGetTexParameterIuiv(int target, int pname, final int[] params) {
        GL33.glGetTexParameterIuiv(target, pname, params);
    }

    @Override
    public void glGetIntegeri_v(int target, int index, final int[] data) {
        GL33.glGetIntegeri_v(target, index, data);
    }

    @Override
    public void glGetTransformFeedbackVarying(
        int program,
        int index,
        final int @Nullable [] length,
        final int[] size,
        final int[] type,
        final ByteBuffer name
    ) {
        GL33.glGetTransformFeedbackVarying(program, index, length, size, type, name);
    }

    @Override
    public void glDeleteVertexArrays(final int[] arrays) {
        GL33.glDeleteVertexArrays(arrays);
    }

    @Override
    public void glGenVertexArrays(final int[] arrays) {
        GL33.glGenVertexArrays(arrays);
    }

    @Override
    public void glDrawArraysInstanced(int mode, int first, int count, int primcount) {
        GL33.glDrawArraysInstanced(mode, first, count, primcount);
    }

    @Override
    public void glDrawElementsInstanced(
        int mode,
        int count,
        int type,
        long indices,
        int primcount
    ) {
        GL33.glDrawElementsInstanced(mode, count, type, indices, primcount);
    }

    @Override
    public void glDrawElementsInstanced(
        int mode,
        int type,
        final ByteBuffer indices,
        int primcount
    ) {
        GL33.glDrawElementsInstanced(mode, type, indices, primcount);
    }

    @Override
    public void glDrawElementsInstanced(int mode, final ByteBuffer indices, int primcount) {
        GL33.glDrawElementsInstanced(mode, indices, primcount);
    }

    @Override
    public void glDrawElementsInstanced(int mode, final ShortBuffer indices, int primcount) {
        GL33.glDrawElementsInstanced(mode, indices, primcount);
    }

    @Override
    public void glDrawElementsInstanced(int mode, final IntBuffer indices, int primcount) {
        GL33.glDrawElementsInstanced(mode, indices, primcount);
    }

    @Override
    public void glCopyBufferSubData(
        int readTarget,
        int writeTarget,
        long readOffset,
        long writeOffset,
        long size
    ) {
        GL33.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
    }

    @Override
    public void glPrimitiveRestartIndex(int index) {
        GL33.glPrimitiveRestartIndex(index);
    }

    @Override
    public void glTexBuffer(int target, int internalformat, int buffer) {
        GL33.glTexBuffer(target, internalformat, buffer);
    }

    @Override
    public void glGetUniformIndices(
        int program,
        final LongBuffer uniformNames,
        final IntBuffer uniformIndices
    ) {

        PointerBuffer buf = BufferUtils.createPointerBuffer(uniformNames.capacity());
        buf.put(uniformNames);

        GL33.glGetUniformIndices(program, buf, uniformIndices);

        LongBuffer result = LongBuffer.allocate(uniformNames.capacity());
        for (int i = 0; i < uniformNames.capacity(); i++) {
            result.put(buf.get(i));
        }
        buf.free();

    }

    @Override
    public void glGetUniformIndices(
        int program,
        final CharSequence[] uniformNames,
        final IntBuffer uniformIndices
    ) {
        GL33.glGetUniformIndices(program, uniformNames, uniformIndices);
    }

    @Override
    public int glGetUniformIndices(int program, final CharSequence uniformName) {
        return GL33.glGetUniformIndices(program, uniformName);
    }

    @Override
    public void glGetActiveUniformsiv(
        int program,
        final IntBuffer uniformIndices,
        int pname,
        final IntBuffer params
    ) {
        GL33.glGetActiveUniformsiv(program, uniformIndices, pname, params);
    }

    @Override
    public int glGetActiveUniformsi(int program, int uniformIndex, int pname) {
        return GL33.glGetActiveUniformsi(program, uniformIndex, pname);
    }

    @Override
    public void glGetActiveUniformName(
        int program,
        int uniformIndex,
        final @Nullable IntBuffer length,
        final ByteBuffer uniformName
    ) {
        GL33.glGetActiveUniformName(program, uniformIndex, length, uniformName);
    }

    @Override
    public String glGetActiveUniformName(int program, int uniformIndex, int bufSize) {
        return GL33.glGetActiveUniformName(program, uniformIndex, bufSize);
    }

    @Override
    public String glGetActiveUniformName(int program, int uniformIndex) {
        return GL33.glGetActiveUniformName(program, uniformIndex);
    }

    @Override
    public int glGetUniformBlockIndex(int program, final ByteBuffer uniformBlockName) {
        return GL33.glGetUniformBlockIndex(program, uniformBlockName);
    }

    @Override
    public int glGetUniformBlockIndex(int program, final CharSequence uniformBlockName) {
        return GL33.glGetUniformBlockIndex(program, uniformBlockName);
    }

    @Override
    public void glGetActiveUniformBlockiv(
        int program,
        int uniformBlockIndex,
        int pname,
        final IntBuffer params
    ) {
        GL33.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
    }

    @Override
    public int glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname) {
        return GL33.glGetActiveUniformBlocki(program, uniformBlockIndex, pname);
    }

    @Override
    public void glGetActiveUniformBlockName(
        int program,
        int uniformBlockIndex,
        final @Nullable IntBuffer length,
        final ByteBuffer uniformBlockName
    ) {
        GL33.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
    }

    @Override
    public String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize) {
        return GL33.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
    }

    @Override
    public String glGetActiveUniformBlockName(int program, int uniformBlockIndex) {
        return GL33.glGetActiveUniformBlockName(program, uniformBlockIndex);
    }

    @Override
    public void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) {
        GL33.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
    }

    @Override
    public void glGetUniformIndices(
        int program,
        final LongBuffer uniformNames,
        final int[] uniformIndices
    ) {
        PointerBuffer buf = BufferUtils.createPointerBuffer(uniformNames.capacity());
        buf.put(uniformNames);

        GL33.glGetUniformIndices(program, buf, uniformIndices);

        LongBuffer result = LongBuffer.allocate(uniformNames.capacity());
        for (int i = 0; i < uniformNames.capacity(); i++) {
            result.put(buf.get(i));
        }
        buf.free();
    }

    @Override
    public void glGetActiveUniformsiv(
        int program,
        final int[] uniformIndices,
        int pname,
        final int[] params
    ) {
        GL33.glGetActiveUniformsiv(program, uniformIndices, pname, params);
    }

    @Override
    public void glGetActiveUniformName(
        int program,
        int uniformIndex,
        final int @Nullable [] length,
        final ByteBuffer uniformName
    ) {
        GL33.glGetActiveUniformName(program, uniformIndex, length, uniformName);
    }

    @Override
    public void glGetActiveUniformBlockiv(
        int program,
        int uniformBlockIndex,
        int pname,
        final int[] params
    ) {
        GL33.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
    }

    @Override
    public void glGetActiveUniformBlockName(
        int program,
        int uniformBlockIndex,
        final int @Nullable [] length,
        final ByteBuffer uniformBlockName
    ) {
        GL33.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
    }

    @Override
    public void glGetBufferParameteri64v(int target, int pname, final LongBuffer params) {
        GL33.glGetBufferParameteri64v(target, pname, params);
    }

    @Override
    public long glGetBufferParameteri64(int target, int pname) {
        return GL33.glGetBufferParameteri64(target, pname);
    }

    @Override
    public void glDrawElementsBaseVertex(
        int mode,
        int count,
        int type,
        long indices,
        int basevertex
    ) {
        GL33.glDrawElementsBaseVertex(mode, count, type, indices, basevertex);
    }

    @Override
    public void glDrawElementsBaseVertex(
        int mode,
        int type,
        final ByteBuffer indices,
        int basevertex
    ) {
        GL33.glDrawElementsBaseVertex(mode, type, indices, basevertex);
    }

    @Override
    public void glDrawElementsBaseVertex(int mode, final ByteBuffer indices, int basevertex) {
        GL33.glDrawElementsBaseVertex(mode, indices, basevertex);
    }

    @Override
    public void glDrawElementsBaseVertex(int mode, final ShortBuffer indices, int basevertex) {
        GL33.glDrawElementsBaseVertex(mode, indices, basevertex);
    }

    @Override
    public void glDrawElementsBaseVertex(int mode, final IntBuffer indices, int basevertex) {
        GL33.glDrawElementsBaseVertex(mode, indices, basevertex);
    }

    @Override
    public void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        int count,
        int type,
        long indices,
        int basevertex
    ) {
        GL33.glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, basevertex);
    }

    @Override
    public void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        int type,
        final ByteBuffer indices,
        int basevertex
    ) {
        GL33.glDrawRangeElementsBaseVertex(mode, start, end, type, indices, basevertex);
    }

    @Override
    public void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        final ByteBuffer indices,
        int basevertex
    ) {
        GL33.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
    }

    @Override
    public void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        final ShortBuffer indices,
        int basevertex
    ) {
        GL33.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
    }

    @Override
    public void glDrawRangeElementsBaseVertex(
        int mode,
        int start,
        int end,
        final IntBuffer indices,
        int basevertex
    ) {
        GL33.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
    }

    @Override
    public void glDrawElementsInstancedBaseVertex(
        int mode,
        int count,
        int type,
        long indices,
        int primcount,
        int basevertex
    ) {
        GL33.glDrawElementsInstancedBaseVertex(mode, count, type, indices, primcount, basevertex);
    }

    @Override
    public void glDrawElementsInstancedBaseVertex(
        int mode,
        int type,
        final ByteBuffer indices,
        int primcount,
        int basevertex
    ) {
        GL33.glDrawElementsInstancedBaseVertex(mode, type, indices, primcount, basevertex);
    }

    @Override
    public void glDrawElementsInstancedBaseVertex(
        int mode,
        final ByteBuffer indices,
        int primcount,
        int basevertex
    ) {
        GL33.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
    }

    @Override
    public void glDrawElementsInstancedBaseVertex(
        int mode,
        final ShortBuffer indices,
        int primcount,
        int basevertex
    ) {
        GL33.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
    }

    @Override
    public void glDrawElementsInstancedBaseVertex(
        int mode,
        final IntBuffer indices,
        int primcount,
        int basevertex
    ) {
        GL33.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
    }

    @Override
    public void glMultiDrawElementsBaseVertex(
        int mode,
        final IntBuffer count,
        int type,
        final LongBuffer indices,
        final IntBuffer basevertex
    ) {
        PointerBuffer buf = BufferUtils.createPointerBuffer(indices.capacity());
        buf.put(indices);

        GL33.glMultiDrawElementsBaseVertex(mode, count, type, buf, basevertex);

        LongBuffer result = LongBuffer.allocate(indices.capacity());
        for (int i = 0; i < indices.capacity(); i++) {
            result.put(buf.get(i));
        }
        buf.free();
    }

    @Override
    public void glProvokingVertex(int mode) {
        GL33.glProvokingVertex(mode);
    }

    @Override
    public void glTexImage2DMultisample(
        int target,
        int samples,
        int internalformat,
        int width,
        int height,
        boolean fixedsamplelocations
    ) {
        GL33.glTexImage2DMultisample(
            target,
            samples,
            internalformat,
            width,
            height,
            fixedsamplelocations
        );
    }

    @Override
    public void glTexImage3DMultisample(
        int target,
        int samples,
        int internalformat,
        int width,
        int height,
        int depth,
        boolean fixedsamplelocations
    ) {
        GL33.glTexImage3DMultisample(
            target,
            samples,
            internalformat,
            width,
            height,
            depth,
            fixedsamplelocations
        );
    }

    @Override
    public void glGetMultisamplefv(int pname, int index, final FloatBuffer val) {
        GL33.glGetMultisamplefv(pname, index, val);
    }

    @Override
    public float glGetMultisamplef(int pname, int index) {
        return GL33.glGetMultisamplef(pname, index);
    }

    @Override
    public void glSampleMaski(int index, int mask) {
        GL33.glSampleMaski(index, mask);
    }

    @Override
    public void glFramebufferTexture(int target, int attachment, int texture, int level) {
        GL33.glFramebufferTexture(target, attachment, texture, level);
    }

    @Override
    public long glFenceSync(int condition, int flags) {
        return GL33.glFenceSync(condition, flags);
    }

    @Override
    public boolean glIsSync(long sync) {
        return GL33.glIsSync(sync);
    }

    @Override
    public void glDeleteSync(long sync) {
        GL33.glDeleteSync(sync);
    }

    @Override
    public int glClientWaitSync(long sync, int flags, long timeout) {
        return GL33.glClientWaitSync(sync, flags, timeout);
    }

    @Override
    public void glWaitSync(long sync, int flags, long timeout) {
        GL33.glWaitSync(sync, flags, timeout);
    }

    @Override
    public void glGetInteger64v(int pname, final LongBuffer params) {
        GL33.glGetInteger64v(pname, params);
    }

    @Override
    public long glGetInteger64(int pname) {
        return GL33.glGetInteger64(pname);
    }

    @Override
    public void glGetInteger64i_v(int pname, int index, final LongBuffer params) {
        GL33.glGetInteger64i_v(pname, index, params);
    }

    @Override
    public long glGetInteger64i(int pname, int index) {
        return GL33.glGetInteger64i(pname, index);
    }

    @Override
    public void glGetSynciv(
        long sync,
        int pname,
        final @Nullable IntBuffer length,
        final IntBuffer values
    ) {
        GL33.glGetSynciv(sync, pname, length, values);
    }

    @Override
    public int glGetSynci(long sync, int pname, final @Nullable IntBuffer length) {
        return GL33.glGetSynci(sync, pname, length);
    }

    @Override
    public void glGetBufferParameteri64v(int target, int pname, final long[] params) {
        GL33.glGetBufferParameteri64v(target, pname, params);
    }

    @Override
    public void glMultiDrawElementsBaseVertex(
        int mode,
        final int[] count,
        int type,
        final LongBuffer indices,
        final int[] basevertex
    ) {
        PointerBuffer buf = BufferUtils.createPointerBuffer(indices.capacity());
        buf.put(indices);

        GL33.glMultiDrawElementsBaseVertex(mode, count, type, buf, basevertex);

        LongBuffer result = LongBuffer.allocate(indices.capacity());
        for (int i = 0; i < indices.capacity(); i++) {
            result.put(buf.get(i));
        }
        buf.free();
    }

    @Override
    public void glGetMultisamplefv(int pname, int index, final float[] val) {
        GL33.glGetMultisamplefv(pname, index, val);
    }

    @Override
    public void glGetInteger64v(int pname, final long[] params) {
        GL33.glGetInteger64v(pname, params);
    }

    @Override
    public void glGetInteger64i_v(int pname, int index, final long[] params) {
        GL33.glGetInteger64i_v(pname, index, params);
    }

    @Override
    public void glGetSynciv(
        long sync,
        int pname,
        final int @Nullable [] length,
        final int[] values
    ) {
        GL33.glGetSynciv(sync, pname, length, values);
    }

    @Override
    public void glBindFragDataLocationIndexed(
        int program,
        int colorNumber,
        int index,
        final ByteBuffer name
    ) {
        GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
    }

    @Override
    public void glBindFragDataLocationIndexed(
        int program,
        int colorNumber,
        int index,
        final CharSequence name
    ) {
        GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
    }

    @Override
    public int glGetFragDataIndex(int program, final ByteBuffer name) {
        return GL33.glGetFragDataIndex(program, name);
    }

    @Override
    public int glGetFragDataIndex(int program, final CharSequence name) {
        return GL33.glGetFragDataIndex(program, name);
    }

    @Override
    public void glGenSamplers(final IntBuffer samplers) {
        GL33.glGenSamplers(samplers);
    }

    @Override
    public int glGenSamplers() {
        return GL33.glGenSamplers();
    }

    @Override
    public void glDeleteSamplers(final IntBuffer samplers) {
        GL33.glDeleteSamplers(samplers);
    }

    @Override
    public void glDeleteSamplers(int sampler) {
        GL33.glDeleteSamplers(sampler);
    }

    @Override
    public boolean glIsSampler(int sampler) {
        return GL33.glIsSampler(sampler);
    }

    @Override
    public void glBindSampler(int unit, int sampler) {
        GL33.glBindSampler(unit, sampler);
    }

    @Override
    public void glSamplerParameteri(int sampler, int pname, int param) {
        GL33.glSamplerParameteri(sampler, pname, param);
    }

    @Override
    public void glSamplerParameterf(int sampler, int pname, float param) {
        GL33.glSamplerParameterf(sampler, pname, param);
    }

    @Override
    public void glSamplerParameteriv(int sampler, int pname, final IntBuffer params) {
        GL33.glSamplerParameteriv(sampler, pname, params);
    }

    @Override
    public void glSamplerParameterfv(int sampler, int pname, final FloatBuffer params) {
        GL33.glSamplerParameterfv(sampler, pname, params);
    }

    @Override
    public void glSamplerParameterIiv(int sampler, int pname, final IntBuffer params) {
        GL33.glSamplerParameterIiv(sampler, pname, params);
    }

    @Override
    public void glSamplerParameterIuiv(int sampler, int pname, final IntBuffer params) {
        GL33.glSamplerParameterIuiv(sampler, pname, params);
    }

    @Override
    public void glGetSamplerParameteriv(int sampler, int pname, final IntBuffer params) {
        GL33.glGetSamplerParameteriv(sampler, pname, params);
    }

    @Override
    public int glGetSamplerParameteri(int sampler, int pname) {
        return GL33.glGetSamplerParameteri(sampler, pname);
    }

    @Override
    public void glGetSamplerParameterfv(int sampler, int pname, final FloatBuffer params) {
        GL33.glGetSamplerParameterfv(sampler, pname, params);
    }

    @Override
    public float glGetSamplerParameterf(int sampler, int pname) {
        return GL33.glGetSamplerParameterf(sampler, pname);
    }

    @Override
    public void glGetSamplerParameterIiv(int sampler, int pname, final IntBuffer params) {
        GL33.glGetSamplerParameterIiv(sampler, pname, params);
    }

    @Override
    public int glGetSamplerParameterIi(int sampler, int pname) {
        return GL33.glGetSamplerParameterIi(sampler, pname);
    }

    @Override
    public void glGetSamplerParameterIuiv(int sampler, int pname, final IntBuffer params) {
        GL33.glGetSamplerParameterIuiv(sampler, pname, params);
    }

    @Override
    public int glGetSamplerParameterIui(int sampler, int pname) {
        return GL33.glGetSamplerParameterIui(sampler, pname);
    }

    @Override
    public void glQueryCounter(int id, int target) {
        GL33.glQueryCounter(id, target);
    }

    @Override
    public void glGetQueryObjecti64v(int id, int pname, final LongBuffer params) {
        GL33.glGetQueryObjecti64v(id, pname, params);
    }

    @Override
    public void glGetQueryObjecti64v(int id, int pname, long params) {
        GL33.glGetQueryObjecti64v(id, pname, params);
    }

    @Override
    public long glGetQueryObjecti64(int id, int pname) {
        return GL33.glGetQueryObjecti64(id, pname);
    }

    @Override
    public void glGetQueryObjectui64v(int id, int pname, final LongBuffer params) {
        GL33.glGetQueryObjectui64v(id, pname, params);
    }

    @Override
    public void glGetQueryObjectui64v(int id, int pname, long params) {
        GL33.glGetQueryObjectui64v(id, pname, params);
    }

    @Override
    public long glGetQueryObjectui64(int id, int pname) {
        return GL33.glGetQueryObjectui64(id, pname);
    }

    @Override
    public void glVertexAttribDivisor(int index, int divisor) {
        GL33.glVertexAttribDivisor(index, divisor);
    }

    @Override
    public void glVertexP2ui(int type, int value) {
        GL33.glVertexP2ui(type, value);
    }

    @Override
    public void glVertexP3ui(int type, int value) {
        GL33.glVertexP3ui(type, value);
    }

    @Override
    public void glVertexP4ui(int type, int value) {
        GL33.glVertexP4ui(type, value);
    }

    @Override
    public void glVertexP2uiv(int type, final IntBuffer value) {
        GL33.glVertexP2uiv(type, value);
    }

    @Override
    public void glVertexP3uiv(int type, final IntBuffer value) {
        GL33.glVertexP3uiv(type, value);
    }

    @Override
    public void glVertexP4uiv(int type, final IntBuffer value) {
        GL33.glVertexP4uiv(type, value);
    }

    @Override
    public void glTexCoordP1ui(int type, int coords) {
        GL33.glTexCoordP1ui(type, coords);
    }

    @Override
    public void glTexCoordP2ui(int type, int coords) {
        GL33.glTexCoordP2ui(type, coords);
    }

    @Override
    public void glTexCoordP3ui(int type, int coords) {
        GL33.glTexCoordP3ui(type, coords);
    }

    @Override
    public void glTexCoordP4ui(int type, int coords) {
        GL33.glTexCoordP4ui(type, coords);
    }

    @Override
    public void glTexCoordP1uiv(int type, final IntBuffer coords) {
        GL33.glTexCoordP1uiv(type, coords);
    }

    @Override
    public void glTexCoordP2uiv(int type, final IntBuffer coords) {
        GL33.glTexCoordP2uiv(type, coords);
    }

    @Override
    public void glTexCoordP4uiv(int type, final IntBuffer coords) {
        GL33.glTexCoordP4uiv(type, coords);
    }

    @Override
    public void glMultiTexCoordP1ui(int texture, int type, int coords) {
        GL33.glMultiTexCoordP1ui(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP2ui(int texture, int type, int coords) {
        GL33.glMultiTexCoordP2ui(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP3ui(int texture, int type, int coords) {
        GL33.glMultiTexCoordP3ui(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP4ui(int texture, int type, int coords) {
        GL33.glMultiTexCoordP4ui(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP1uiv(int texture, int type, final IntBuffer coords) {
        GL33.glMultiTexCoordP1uiv(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP2uiv(int texture, int type, final IntBuffer coords) {
        GL33.glMultiTexCoordP2uiv(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP3uiv(int texture, int type, final IntBuffer coords) {
        GL33.glMultiTexCoordP3uiv(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP4uiv(int texture, int type, final IntBuffer coords) {
        GL33.glMultiTexCoordP4uiv(texture, type, coords);
    }

    @Override
    public void glNormalP3ui(int type, int coords) {
        GL33.glNormalP3ui(type, coords);
    }

    @Override
    public void glNormalP3uiv(int type, final IntBuffer coords) {
        GL33.glNormalP3uiv(type, coords);
    }

    @Override
    public void glColorP3ui(int type, int color) {
        GL33.glColorP3ui(type, color);
    }

    @Override
    public void glColorP4ui(int type, int color) {
        GL33.glColorP4ui(type, color);
    }

    @Override
    public void glColorP3uiv(int type, final IntBuffer color) {
        GL33.glColorP3uiv(type, color);
    }

    @Override
    public void glColorP4uiv(int type, final IntBuffer color) {
        GL33.glColorP4uiv(type, color);
    }

    @Override
    public void glSecondaryColorP3ui(int type, int color) {
        GL33.glSecondaryColorP3ui(type, color);
    }

    @Override
    public void glSecondaryColorP3uiv(int type, final IntBuffer color) {
        GL33.glSecondaryColorP3uiv(type, color);
    }

    @Override
    public void glVertexAttribP1ui(int index, int type, boolean normalized, int value) {
        GL33.glVertexAttribP1ui(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP2ui(int index, int type, boolean normalized, int value) {
        GL33.glVertexAttribP2ui(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP3ui(int index, int type, boolean normalized, int value) {
        GL33.glVertexAttribP3ui(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP4ui(int index, int type, boolean normalized, int value) {
        GL33.glVertexAttribP4ui(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP1uiv(
        int index,
        int type,
        boolean normalized,
        final IntBuffer value
    ) {
        GL33.glVertexAttribP1uiv(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP2uiv(
        int index,
        int type,
        boolean normalized,
        final IntBuffer value
    ) {
        GL33.glVertexAttribP2uiv(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP3uiv(
        int index,
        int type,
        boolean normalized,
        final IntBuffer value
    ) {
        GL33.glVertexAttribP3uiv(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP4uiv(
        int index,
        int type,
        boolean normalized,
        final IntBuffer value
    ) {
        GL33.glVertexAttribP4uiv(index, type, normalized, value);
    }

    @Override
    public void glGenSamplers(final int[] samplers) {
        GL33.glGenSamplers(samplers);
    }

    @Override
    public void glDeleteSamplers(final int[] samplers) {
        GL33.glDeleteSamplers(samplers);
    }

    @Override
    public void glSamplerParameteriv(int sampler, int pname, final int[] params) {
        GL33.glSamplerParameteriv(sampler, pname, params);
    }

    @Override
    public void glSamplerParameterfv(int sampler, int pname, final float[] params) {
        GL33.glSamplerParameterfv(sampler, pname, params);
    }

    @Override
    public void glSamplerParameterIiv(int sampler, int pname, final int[] params) {
        GL33.glSamplerParameterIiv(sampler, pname, params);
    }

    @Override
    public void glSamplerParameterIuiv(int sampler, int pname, final int[] params) {
        GL33.glSamplerParameterIuiv(sampler, pname, params);
    }

    @Override
    public void glGetSamplerParameteriv(int sampler, int pname, final int[] params) {
        GL33.glGetSamplerParameteriv(sampler, pname, params);
    }

    @Override
    public void glGetSamplerParameterfv(int sampler, int pname, final float[] params) {
        GL33.glGetSamplerParameterfv(sampler, pname, params);
    }

    @Override
    public void glGetSamplerParameterIiv(int sampler, int pname, final int[] params) {
        GL33.glGetSamplerParameterIiv(sampler, pname, params);
    }

    @Override
    public void glGetSamplerParameterIuiv(int sampler, int pname, final int[] params) {
        GL33.glGetSamplerParameterIuiv(sampler, pname, params);
    }

    @Override
    public void glGetQueryObjecti64v(int id, int pname, final long[] params) {
        GL33.glGetQueryObjecti64v(id, pname, params);
    }

    @Override
    public void glGetQueryObjectui64v(int id, int pname, final long[] params) {
        GL33.glGetQueryObjectui64v(id, pname, params);
    }

    @Override
    public void glVertexP2uiv(int type, final int[] value) {
        GL33.glVertexP2uiv(type, value);
    }

    @Override
    public void glVertexP3uiv(int type, final int[] value) {
        GL33.glVertexP3uiv(type, value);
    }

    @Override
    public void glVertexP4uiv(int type, final int[] value) {
        GL33.glVertexP4uiv(type, value);
    }

    @Override
    public void glTexCoordP1uiv(int type, final int[] coords) {
        GL33.glTexCoordP1uiv(type, coords);
    }

    @Override
    public void glTexCoordP2uiv(int type, final int[] coords) {
        GL33.glTexCoordP2uiv(type, coords);
    }

    @Override
    public void glTexCoordP3uiv(int type, final int[] coords) {
        GL33.glTexCoordP3uiv(type, coords);
    }

    @Override
    public void glTexCoordP4uiv(int type, final int[] coords) {
        GL33.glTexCoordP4uiv(type, coords);
    }

    @Override
    public void glMultiTexCoordP1uiv(int texture, int type, final int[] coords) {
        GL33.glMultiTexCoordP1uiv(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP2uiv(int texture, int type, final int[] coords) {
        GL33.glMultiTexCoordP2uiv(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP3uiv(int texture, int type, final int[] coords) {
        GL33.glMultiTexCoordP3uiv(texture, type, coords);
    }

    @Override
    public void glMultiTexCoordP4uiv(int texture, int type, final int[] coords) {
        GL33.glMultiTexCoordP4uiv(texture, type, coords);
    }

    @Override
    public void glNormalP3uiv(int type, final int[] coords) {
        GL33.glNormalP3uiv(type, coords);
    }

    @Override
    public void glColorP3uiv(int type, final int[] color) {
        GL33.glColorP3uiv(type, color);
    }

    @Override
    public void glColorP4uiv(int type, final int[] color) {
        GL33.glColorP4uiv(type, color);
    }

    @Override
    public void glSecondaryColorP3uiv(int type, final int[] color) {
        GL33.glSecondaryColorP3uiv(type, color);
    }

    @Override
    public void glVertexAttribP1uiv(int index, int type, boolean normalized, final int[] value) {
        GL33.glVertexAttribP1uiv(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP2uiv(int index, int type, boolean normalized, final int[] value) {
        GL33.glVertexAttribP2uiv(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP3uiv(int index, int type, boolean normalized, final int[] value) {
        GL33.glVertexAttribP3uiv(index, type, normalized, value);
    }

    @Override
    public void glVertexAttribP4uiv(int index, int type, boolean normalized, final int[] value) {
        GL33.glVertexAttribP4uiv(index, type, normalized, value);
    }

}
