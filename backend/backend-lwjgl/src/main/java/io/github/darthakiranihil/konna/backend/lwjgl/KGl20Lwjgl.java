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

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.libfrontend.gl20.KGl20;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;

import java.nio.*;

/**
 * OpenGL 2.0 library frontend implementation,
 * using corresponding bindings from {@link GL20}.
 *
 * @author Darth Akira Nihil
 * @version 0.1.0
 */
public sealed class KGl20Lwjgl extends KObject implements KGl20 permits KGl33Lwjgl {

    @Override
    public void createCapabilities() {
        GL.createCapabilities();
    }

    @Override
    public void glEnable(int target) {
        GL20.glEnable(target);
    }

    @Override
    public void glDisable(int target) {
        GL20.glDisable(target);
    }

    @Override
    public void glAccum(int op, float value) {
        GL20.glAccum(op, value);
    }

    @Override
    public void glAlphaFunc(int func, float ref) {
        GL20.glAlphaFunc(func, ref);
    }

    @Override
    public boolean glAreTexturesResident(final IntBuffer textures, final ByteBuffer residences) {
        return GL20.glAreTexturesResident(textures, residences);
    }

    @Override
    public boolean glAreTexturesResident(int texture, final ByteBuffer residences) {
        return GL20.glAreTexturesResident(texture, residences);
    }

    @Override
    public void glArrayElement(int i) {
        GL20.glArrayElement(i);
    }

    @Override
    public void glBegin(int mode) {
        GL20.glBegin(mode);
    }

    @Override
    public void glBindTexture(int target, int texture) {
        GL20.glBindTexture(target, texture);
    }

    @Override
    public void glBitmap(
        int w,
        int h,
        float xOrig,
        float yOrig,
        float xInc,
        float yInc,
        final ByteBuffer data
    ) {
        GL20.glBitmap(w, h, xOrig, yOrig, xInc, yInc, data);
    }

    @Override
    public void glBitmap(
        int w,
        int h,
        float xOrig,
        float yOrig,
        float xInc,
        float yInc,
        long data
    ) {
        GL20.glBitmap(w, h, xOrig, yOrig, xInc, yInc, data);
    }

    @Override
    public void glBlendFunc(int sfactor, int dfactor) {
        GL20.glBlendFunc(sfactor, dfactor);
    }

    @Override
    public void glCallList(int list) {
        GL20.glCallList(list);
    }

    @Override
    public void glCallLists(int type, final ByteBuffer lists) {
        GL20.glCallLists(type, lists);
    }

    @Override
    public void glCallLists(final ByteBuffer lists) {
        GL20.glCallLists(lists);
    }

    @Override
    public void glCallLists(final ShortBuffer lists) {
        GL20.glCallLists(lists);
    }

    @Override
    public void glCallLists(final IntBuffer lists) {
        GL20.glCallLists(lists);
    }

    @Override
    public void glClear(int mask) {
        GL20.glClear(mask);
    }

    @Override
    public void glClearAccum(float red, float green, float blue, float alpha) {
        GL20.glClearAccum(red, green, blue, alpha);
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GL20.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glClearDepth(double depth) {
        GL20.glClearDepth(depth);
    }

    @Override
    public void glClearIndex(float index) {
        GL20.glClearIndex(index);
    }

    @Override
    public void glClearStencil(int s) {
        GL20.glClearStencil(s);
    }

    @Override
    public void glClipPlane(int plane, final DoubleBuffer equation) {
        GL20.glClipPlane(plane, equation);
    }

    @Override
    public void glColor3b(byte red, byte green, byte blue) {
        GL20.glColor3b(red, green, blue);
    }

    @Override
    public void glColor3s(short red, short green, short blue) {
        GL20.glColor3s(red, green, blue);
    }

    @Override
    public void glColor3i(int red, int green, int blue) {
        GL20.glColor3i(red, green, blue);
    }

    @Override
    public void glColor3f(float red, float green, float blue) {
        GL20.glColor3f(red, green, blue);
    }

    @Override
    public void glColor3d(double red, double green, double blue) {
        GL20.glColor3d(red, green, blue);
    }

    @Override
    public void glColor3ub(byte red, byte green, byte blue) {
        GL20.glColor3ub(red, green, blue);
    }

    @Override
    public void glColor3us(short red, short green, short blue) {
        GL20.glColor3us(red, green, blue);
    }

    @Override
    public void glColor3ui(int red, int green, int blue) {
        GL20.glColor3ui(red, green, blue);
    }

    @Override
    public void glColor3bv(final ByteBuffer v) {
        GL20.glColor3bv(v);
    }

    @Override
    public void glColor3sv(final ShortBuffer v) {
        GL20.glColor3sv(v);
    }

    @Override
    public void glColor3iv(final IntBuffer v) {
        GL20.glColor3iv(v);
    }

    @Override
    public void glColor3fv(final FloatBuffer v) {
        GL20.glColor3fv(v);
    }

    @Override
    public void glColor3dv(final DoubleBuffer v) {
        GL20.glColor3dv(v);
    }

    @Override
    public void glColor3ubv(final ByteBuffer v) {
        GL20.glColor3ubv(v);
    }

    @Override
    public void glColor3usv(final ShortBuffer v) {
        GL20.glColor3usv(v);
    }

    @Override
    public void glColor3uiv(final IntBuffer v) {
        GL20.glColor3uiv(v);
    }

    @Override
    public void glColor4b(byte red, byte green, byte blue, byte alpha) {
        GL20.glColor4b(red, green, blue, alpha);
    }

    @Override
    public void glColor4s(short red, short green, short blue, short alpha) {
        GL20.glColor4s(red, green, blue, alpha);
    }

    @Override
    public void glColor4i(int red, int green, int blue, int alpha) {
        GL20.glColor4i(red, green, blue, alpha);
    }

    @Override
    public void glColor4f(float red, float green, float blue, float alpha) {
        GL20.glColor4f(red, green, blue, alpha);
    }

    @Override
    public void glColor4d(double red, double green, double blue, double alpha) {
        GL20.glColor4d(red, green, blue, alpha);
    }

    @Override
    public void glColor4ub(byte red, byte green, byte blue, byte alpha) {
        GL20.glColor4ub(red, green, blue, alpha);
    }

    @Override
    public void glColor4us(short red, short green, short blue, short alpha) {
        GL20.glColor4us(red, green, blue, alpha);
    }

    @Override
    public void glColor4ui(int red, int green, int blue, int alpha) {
        GL20.glColor4ui(red, green, blue, alpha);
    }

    @Override
    public void glColor4bv(final ByteBuffer v) {
        GL20.glColor4bv(v);
    }

    @Override
    public void glColor4sv(final ShortBuffer v) {
        GL20.glColor4sv(v);
    }

    @Override
    public void glColor4iv(final IntBuffer v) {
        GL20.glColor4iv(v);
    }

    @Override
    public void glColor4fv(final FloatBuffer v) {
        GL20.glColor4fv(v);
    }

    @Override
    public void glColor4dv(final DoubleBuffer v) {
        GL20.glColor4dv(v);
    }

    @Override
    public void glColor4ubv(final ByteBuffer v) {
        GL20.glColor4ubv(v);
    }

    @Override
    public void glColor4usv(final ShortBuffer v) {
        GL20.glColor4usv(v);
    }

    @Override
    public void glColor4uiv(final IntBuffer v) {
        GL20.glColor4uiv(v);
    }

    @Override
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        GL20.glColorMask(red, green, blue, alpha);
    }

    @Override
    public void glColorMaterial(int face, int mode) {
        GL20.glColorMaterial(face, mode);
    }

    @Override
    public void glColorPointer(int size, int type, int stride, final ByteBuffer pointer) {
        GL20.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glColorPointer(int size, int type, int stride, long pointer) {
        GL20.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glColorPointer(int size, int type, int stride, final ShortBuffer pointer) {
        GL20.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glColorPointer(int size, int type, int stride, final IntBuffer pointer) {
        GL20.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glColorPointer(int size, int type, int stride, final FloatBuffer pointer) {
        GL20.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glCopyPixels(int x, int y, int width, int height, int type) {
        GL20.glCopyPixels(x, y, width, height, type);
    }

    @Override
    public void glCullFace(int mode) {
        GL20.glCullFace(mode);
    }

    @Override
    public void glDeleteLists(int list, int range) {
        GL20.glDeleteLists(list, range);
    }

    @Override
    public void glDepthFunc(int func) {
        GL20.glDepthFunc(func);
    }

    @Override
    public void glDepthMask(boolean flag) {
        GL20.glDepthMask(flag);
    }

    @Override
    public void glDepthRange(double zNear, double zFar) {
        GL20.glDepthRange(zNear, zFar);
    }

    @Override
    public void glDisableClientState(int cap) {
        GL20.glDisableClientState(cap);
    }

    @Override
    public void glDrawArrays(int mode, int first, int count) {
        GL20.glDrawArrays(mode, first, count);
    }

    @Override
    public void glDrawBuffer(int buf) {
        GL20.glDrawBuffer(buf);
    }

    @Override
    public void glDrawElements(int mode, int count, int type, long indices) {
        GL20.glDrawElements(mode, count, type, indices);
    }

    @Override
    public void glDrawElements(int mode, int type, final ByteBuffer indices) {
        GL20.glDrawElements(mode, type, indices);
    }

    @Override
    public void glDrawElements(int mode, final ByteBuffer indices) {
        GL20.glDrawElements(mode, indices);
    }

    @Override
    public void glDrawElements(int mode, final ShortBuffer indices) {
        GL20.glDrawElements(mode, indices);
    }

    @Override
    public void glDrawElements(int mode, final IntBuffer indices) {
        GL20.glDrawElements(mode, indices);
    }

    @Override
    public void glDrawPixels(int width, int height, int format, int type, final ByteBuffer pixels) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glDrawPixels(int width, int height, int format, int type, long pixels) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glDrawPixels(
        int width,
        int height,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glDrawPixels(int width, int height, int format, int type, final IntBuffer pixels) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glDrawPixels(
        int width,
        int height,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glEdgeFlag(boolean flag) {
        GL20.glEdgeFlag(flag);
    }

    @Override
    public void glEdgeFlagv(final ByteBuffer flag) {
        GL20.glEdgeFlagv(flag);
    }

    @Override
    public void glEdgeFlagPointer(int stride, final ByteBuffer pointer) {
        GL20.glEdgeFlagPointer(stride, pointer);
    }

    @Override
    public void glEdgeFlagPointer(int stride, long pointer) {
        GL20.glEdgeFlagPointer(stride, pointer);
    }

    @Override
    public void glEnableClientState(int cap) {
        GL20.glEnableClientState(cap);
    }

    @Override
    public void glEnd() {
        GL20.glEnd();
    }

    @Override
    public void glEvalCoord1f(float u) {
        GL20.glEvalCoord1f(u);
    }

    @Override
    public void glEvalCoord1fv(final FloatBuffer u) {
        GL20.glEvalCoord1fv(u);
    }

    @Override
    public void glEvalCoord1d(double u) {
        GL20.glEvalCoord1d(u);
    }

    @Override
    public void glEvalCoord1dv(final DoubleBuffer u) {
        GL20.glEvalCoord1dv(u);
    }

    @Override
    public void glEvalCoord2f(float u, float v) {
        GL20.glEvalCoord2f(u, v);
    }

    @Override
    public void glEvalCoord2fv(final FloatBuffer u) {
        GL20.glEvalCoord2fv(u);
    }

    @Override
    public void glEvalCoord2d(double u, double v) {
        GL20.glEvalCoord2d(u, v);
    }

    @Override
    public void glEvalCoord2dv(final DoubleBuffer u) {
        GL20.glEvalCoord2dv(u);
    }

    @Override
    public void glEvalMesh1(int mode, int i1, int i2) {
        GL20.glEvalMesh1(mode, i1, i2);
    }

    @Override
    public void glEvalMesh2(int mode, int i1, int i2, int j1, int j2) {
        GL20.glEvalMesh2(mode, i1, i2, j1, j2);
    }

    @Override
    public void glEvalPoint1(int i) {
        GL20.glEvalPoint1(i);
    }

    @Override
    public void glEvalPoint2(int i, int j) {
        GL20.glEvalPoint2(i, j);
    }

    @Override
    public void glFeedbackBuffer(int type, final FloatBuffer buffer) {
        GL20.glFeedbackBuffer(type, buffer);
    }

    @Override
    public void glFinish() {
        GL20.glFinish();
    }

    @Override
    public void glFlush() {
        GL20.glFlush();
    }

    @Override
    public void glFogi(int pname, int param) {
        GL20.glFogi(pname, param);
    }

    @Override
    public void glFogiv(int pname, final IntBuffer params) {
        GL20.glFogiv(pname, params);
    }

    @Override
    public void glFogf(int pname, float param) {
        GL20.glFogf(pname, param);
    }

    @Override
    public void glFogfv(int pname, final FloatBuffer params) {
        GL20.glFogfv(pname, params);
    }

    @Override
    public void glFrontFace(int dir) {
        GL20.glFrontFace(dir);
    }

    @Override
    public int glGenLists(int s) {
        return GL20.glGenLists(s);
    }

    @Override
    public void glGenTextures(final IntBuffer textures) {
        GL20.glGenTextures(textures);
    }

    @Override
    public int glGenTextures() {
        return GL20.glGenTextures();
    }

    @Override
    public void glDeleteTextures(final IntBuffer textures) {
        GL20.glDeleteTextures(textures);
    }

    @Override
    public void glDeleteTextures(int texture) {
        GL20.glDeleteTextures(texture);
    }

    @Override
    public void glGetClipPlane(int plane, final DoubleBuffer equation) {
        GL20.glGetClipPlane(plane, equation);
    }

    @Override
    public void glGetBooleanv(int pname, final ByteBuffer params) {
        GL20.glGetBooleanv(pname, params);
    }

    @Override
    public boolean glGetBoolean(int pname) {
        return GL20.glGetBoolean(pname);
    }

    @Override
    public void glGetFloatv(int pname, final FloatBuffer params) {
        GL20.glGetFloatv(pname, params);
    }

    @Override
    public float glGetFloat(int pname) {
        return GL20.glGetFloat(pname);
    }

    @Override
    public void glGetIntegerv(int pname, final IntBuffer params) {
        GL20.glGetIntegerv(pname, params);
    }

    @Override
    public int glGetInteger(int pname) {
        return GL20.glGetInteger(pname);
    }

    @Override
    public void glGetDoublev(int pname, final DoubleBuffer params) {
        GL20.glGetDoublev(pname, params);
    }

    @Override
    public double glGetDouble(int pname) {
        return GL20.glGetDouble(pname);
    }

    @Override
    public int glGetError() {
        return GL20.glGetError();
    }

    @Override
    public void glGetLightiv(int light, int pname, final IntBuffer data) {
        GL20.glGetLightiv(light, pname, data);
    }

    @Override
    public int glGetLighti(int light, int pname) {
        return GL20.glGetLighti(light, pname);
    }

    @Override
    public void glGetLightfv(int light, int pname, final FloatBuffer data) {
        GL20.glGetLightfv(light, pname, data);
    }

    @Override
    public float glGetLightf(int light, int pname) {
        return GL20.glGetLightf(light, pname);
    }

    @Override
    public void glGetMapiv(int target, int query, final IntBuffer data) {
        GL20.glGetMapiv(target, query, data);
    }

    @Override
    public int glGetMapi(int target, int query) {
        return GL20.glGetMapi(target, query);
    }

    @Override
    public void glGetMapfv(int target, int query, final FloatBuffer data) {
        GL20.glGetMapfv(target, query, data);
    }

    @Override
    public float glGetMapf(int target, int query) {
        return GL20.glGetMapf(target, query);
    }

    @Override
    public void glGetMapdv(int target, int query, final DoubleBuffer data) {
        GL20.glGetMapdv(target, query, data);
    }

    @Override
    public double glGetMapd(int target, int query) {
        return GL20.glGetMapd(target, query);
    }

    @Override
    public void glGetMaterialiv(int face, int pname, final IntBuffer data) {
        GL20.glGetMaterialiv(face, pname, data);
    }

    @Override
    public void glGetMaterialfv(int face, int pname, final FloatBuffer data) {
        GL20.glGetMaterialfv(face, pname, data);
    }

    @Override
    public void glGetPixelMapfv(int map, final FloatBuffer data) {
        GL20.glGetPixelMapfv(map, data);
    }

    @Override
    public void glGetPixelMapfv(int map, long data) {
        GL20.glGetPixelMapfv(map, data);
    }

    @Override
    public void glGetPixelMapusv(int map, final ShortBuffer data) {
        GL20.glGetPixelMapusv(map, data);
    }

    @Override
    public void glGetPixelMapusv(int map, long data) {
        GL20.glGetPixelMapusv(map, data);
    }

    @Override
    public void glGetPixelMapuiv(int map, final IntBuffer data) {
        GL20.glGetPixelMapuiv(map, data);
    }

    @Override
    public void glGetPixelMapuiv(int map, long data) {
        GL20.glGetPixelMapuiv(map, data);
    }

    @Override
    public void glGetPointerv(int pname, final LongBuffer params) {
        PointerBuffer buf = BufferUtils.createPointerBuffer(params.capacity());
        buf.put(params);

        GL20.glGetPointerv(pname, buf);

        for (int i = 0; i < params.capacity(); i++) {
            params.put(i, buf.get(i));
        }
        buf.free();

    }

    @Override
    public long glGetPointer(int pname) {
        return GL20.glGetPointer(pname);
    }

    @Override
    public void glGetPolygonStipple(final ByteBuffer pattern) {
        GL20.glGetPolygonStipple(pattern);
    }

    @Override
    public void glGetPolygonStipple(long pattern) {
        GL20.glGetPolygonStipple(pattern);
    }

    @Override
    public String glGetString(int name) {
        return GL20.glGetString(name);
    }

    @Override
    public void glGetTexEnviv(int env, int pname, final IntBuffer data) {
        GL20.glGetTexEnviv(env, pname, data);
    }

    @Override
    public int glGetTexEnvi(int env, int pname) {
        return GL20.glGetTexEnvi(env, pname);
    }

    @Override
    public void glGetTexEnvfv(int env, int pname, final FloatBuffer data) {
        GL20.glGetTexEnvfv(env, pname, data);
    }

    @Override
    public float glGetTexEnvf(int env, int pname) {
        return GL20.glGetTexEnvf(env, pname);
    }

    @Override
    public void glGetTexGeniv(int coord, int pname, final IntBuffer data) {
        GL20.glGetTexGeniv(coord, pname, data);
    }

    @Override
    public int glGetTexGeni(int coord, int pname) {
        return GL20.glGetTexGeni(coord, pname);
    }

    @Override
    public void glGetTexGenfv(int coord, int pname, final FloatBuffer data) {
        GL20.glGetTexGenfv(coord, pname, data);
    }

    @Override
    public float glGetTexGenf(int coord, int pname) {
        return GL20.glGetTexGenf(coord, pname);
    }

    @Override
    public void glGetTexGendv(int coord, int pname, final DoubleBuffer data) {
        GL20.glGetTexGendv(coord, pname, data);
    }

    @Override
    public double glGetTexGend(int coord, int pname) {
        return GL20.glGetTexGend(coord, pname);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final ByteBuffer pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, long pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final ShortBuffer pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final IntBuffer pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final FloatBuffer pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final DoubleBuffer pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexLevelParameteriv(int target, int level, int pname, final IntBuffer params) {
        GL20.glGetTexLevelParameteriv(target, level, pname, params);
    }

    @Override
    public int glGetTexLevelParameteri(int target, int level, int pname) {
        return GL20.glGetTexLevelParameteri(target, level, pname);
    }

    @Override
    public void glGetTexLevelParameterfv(
        int target,
        int level,
        int pname,
        final FloatBuffer params
    ) {
        GL20.glGetTexLevelParameterfv(target, level, pname, params);
    }

    @Override
    public float glGetTexLevelParameterf(int target, int level, int pname) {
        return GL20.glGetTexLevelParameterf(target, level, pname);
    }

    @Override
    public void glGetTexParameteriv(int target, int pname, final IntBuffer params) {
        GL20.glGetTexParameteriv(target, pname, params);
    }

    @Override
    public int glGetTexParameteri(int target, int pname) {
        return GL20.glGetTexParameteri(target, pname);
    }

    @Override
    public void glGetTexParameterfv(int target, int pname, final FloatBuffer params) {
        GL20.glGetTexParameterfv(target, pname, params);
    }

    @Override
    public float glGetTexParameterf(int target, int pname) {
        return GL20.glGetTexParameterf(target, pname);
    }

    @Override
    public void glHint(int target, int hint) {
        GL20.glHint(target, hint);
    }

    @Override
    public void glIndexi(int index) {
        GL20.glIndexi(index);
    }

    @Override
    public void glIndexub(byte index) {
        GL20.glIndexub(index);
    }

    @Override
    public void glIndexs(short index) {
        GL20.glIndexs(index);
    }

    @Override
    public void glIndexf(float index) {
        GL20.glIndexf(index);
    }

    @Override
    public void glIndexd(double index) {
        GL20.glIndexd(index);
    }

    @Override
    public void glIndexiv(final IntBuffer index) {
        GL20.glIndexiv(index);
    }

    @Override
    public void glIndexubv(final ByteBuffer index) {
        GL20.glIndexubv(index);
    }

    @Override
    public void glIndexsv(final ShortBuffer index) {
        GL20.glIndexsv(index);
    }

    @Override
    public void glIndexfv(final FloatBuffer index) {
        GL20.glIndexfv(index);
    }

    @Override
    public void glIndexdv(final DoubleBuffer index) {
        GL20.glIndexdv(index);
    }

    @Override
    public void glIndexMask(int mask) {
        GL20.glIndexMask(mask);
    }

    @Override
    public void glIndexPointer(int type, int stride, final ByteBuffer pointer) {
        GL20.glIndexPointer(type, stride, pointer);
    }

    @Override
    public void glIndexPointer(int type, int stride, long pointer) {
        GL20.glIndexPointer(type, stride, pointer);
    }

    @Override
    public void glIndexPointer(int stride, final ByteBuffer pointer) {
        GL20.glIndexPointer(stride, pointer);
    }

    @Override
    public void glIndexPointer(int stride, final ShortBuffer pointer) {
        GL20.glIndexPointer(stride, pointer);
    }

    @Override
    public void glIndexPointer(int stride, final IntBuffer pointer) {
        GL20.glIndexPointer(stride, pointer);
    }

    @Override
    public void glInitNames() {
        GL20.glInitNames();
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final ByteBuffer pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, long pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final ShortBuffer pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final IntBuffer pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final FloatBuffer pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final DoubleBuffer pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public boolean glIsEnabled(int cap) {
        return GL20.glIsEnabled(cap);
    }

    @Override
    public boolean glIsList(int list) {
        return GL20.glIsList(list);
    }

    @Override
    public boolean glIsTexture(int texture) {
        return GL20.glIsTexture(texture);
    }

    @Override
    public void glLightModeli(int pname, int param) {
        GL20.glLightModeli(pname, param);
    }

    @Override
    public void glLightModelf(int pname, float param) {
        GL20.glLightModelf(pname, param);
    }

    @Override
    public void glLightModeliv(int pname, final IntBuffer params) {
        GL20.glLightModeliv(pname, params);
    }

    @Override
    public void glLightModelfv(int pname, final FloatBuffer params) {
        GL20.glLightModelfv(pname, params);
    }

    @Override
    public void glLighti(int light, int pname, int param) {
        GL20.glLighti(light, pname, param);
    }

    @Override
    public void glLightf(int light, int pname, float param) {
        GL20.glLightf(light, pname, param);
    }

    @Override
    public void glLightiv(int light, int pname, final IntBuffer params) {
        GL20.glLightiv(light, pname, params);
    }

    @Override
    public void glLightfv(int light, int pname, final FloatBuffer params) {
        GL20.glLightfv(light, pname, params);
    }

    @Override
    public void glLineStipple(int factor, short pattern) {
        GL20.glLineStipple(factor, pattern);
    }

    @Override
    public void glLineWidth(float width) {
        GL20.glLineWidth(width);
    }

    @Override
    public void glListBase(int base) {
        GL20.glListBase(base);
    }

    @Override
    public void glLoadMatrixf(final FloatBuffer m) {
        GL20.glLoadMatrixf(m);
    }

    @Override
    public void glLoadMatrixd(final DoubleBuffer m) {
        GL20.glLoadMatrixd(m);
    }

    @Override
    public void glLoadIdentity() {
        GL20.glLoadIdentity();
    }

    @Override
    public void glLoadName(int name) {
        GL20.glLoadName(name);
    }

    @Override
    public void glLogicOp(int op) {
        GL20.glLogicOp(op);
    }

    @Override
    public void glMap1f(
        int target,
        float u1,
        float u2,
        int stride,
        int order,
        final FloatBuffer points
    ) {
        GL20.glMap1f(target, u1, u2, stride, order, points);
    }

    @Override
    public void glMap1d(
        int target,
        double u1,
        double u2,
        int stride,
        int order,
        final DoubleBuffer points
    ) {
        GL20.glMap1d(target, u1, u2, stride, order, points);
    }

    @Override
    public void glMap2f(
        int target,
        float u1,
        float u2,
        int ustride,
        int uorder,
        float v1,
        float v2,
        int vstride,
        int vorder,
        final FloatBuffer points
    ) {
        GL20.glMap2f(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
    }

    @Override
    public void glMap2d(
        int target,
        double u1,
        double u2,
        int ustride,
        int uorder,
        double v1,
        double v2,
        int vstride,
        int vorder,
        final DoubleBuffer points
    ) {
        GL20.glMap2d(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
    }

    @Override
    public void glMapGrid1f(int n, float u1, float u2) {
        GL20.glMapGrid1f(n, u1, u2);
    }

    @Override
    public void glMapGrid1d(int n, double u1, double u2) {
        GL20.glMapGrid1d(n, u1, u2);
    }

    @Override
    public void glMapGrid2f(int un, float u1, float u2, int vn, float v1, float v2) {
        GL20.glMapGrid2f(un, u1, u2, vn, v1, v2);
    }

    @Override
    public void glMapGrid2d(int un, double u1, double u2, int vn, double v1, double v2) {
        GL20.glMapGrid2d(un, u1, u2, vn, v1, v2);
    }

    @Override
    public void glMateriali(int face, int pname, int param) {
        GL20.glMateriali(face, pname, param);
    }

    @Override
    public void glMaterialf(int face, int pname, float param) {
        GL20.glMaterialf(face, pname, param);
    }

    @Override
    public void glMaterialiv(int face, int pname, final IntBuffer params) {
        GL20.glMaterialiv(face, pname, params);
    }

    @Override
    public void glMaterialfv(int face, int pname, final FloatBuffer params) {
        GL20.glMaterialfv(face, pname, params);
    }

    @Override
    public void glMatrixMode(int mode) {
        GL20.glMatrixMode(mode);
    }

    @Override
    public void glMultMatrixf(final FloatBuffer m) {
        GL20.glMultMatrixf(m);
    }

    @Override
    public void glMultMatrixd(final DoubleBuffer m) {
        GL20.glMultMatrixd(m);
    }

    @Override
    public void glFrustum(double l, double r, double b, double t, double n, double f) {
        GL20.glFrustum(l, r, b, t, n, f);
    }

    @Override
    public void glNewList(int n, int mode) {
        GL20.glNewList(n, mode);
    }

    @Override
    public void glEndList() {
        GL20.glEndList();
    }

    @Override
    public void glNormal3f(float nx, float ny, float nz) {
        GL20.glNormal3f(nx, ny, nz);
    }

    @Override
    public void glNormal3b(byte nx, byte ny, byte nz) {
        GL20.glNormal3b(nx, ny, nz);
    }

    @Override
    public void glNormal3s(short nx, short ny, short nz) {
        GL20.glNormal3s(nx, ny, nz);
    }

    @Override
    public void glNormal3i(int nx, int ny, int nz) {
        GL20.glNormal3i(nx, ny, nz);
    }

    @Override
    public void glNormal3d(double nx, double ny, double nz) {
        GL20.glNormal3d(nx, ny, nz);
    }

    @Override
    public void glNormal3fv(final FloatBuffer v) {
        GL20.glNormal3fv(v);
    }

    @Override
    public void glNormal3bv(final ByteBuffer v) {
        GL20.glNormal3bv(v);
    }

    @Override
    public void glNormal3sv(final ShortBuffer v) {
        GL20.glNormal3sv(v);
    }

    @Override
    public void glNormal3iv(final IntBuffer v) {
        GL20.glNormal3iv(v);
    }

    @Override
    public void glNormal3dv(final DoubleBuffer v) {
        GL20.glNormal3dv(v);
    }

    @Override
    public void glNormalPointer(int type, int stride, final ByteBuffer pointer) {
        GL20.glNormalPointer(type, stride, pointer);
    }

    @Override
    public void glNormalPointer(int type, int stride, long pointer) {
        GL20.glNormalPointer(type, stride, pointer);
    }

    @Override
    public void glNormalPointer(int type, int stride, final ShortBuffer pointer) {
        GL20.glNormalPointer(type, stride, pointer);
    }

    @Override
    public void glNormalPointer(int type, int stride, final IntBuffer pointer) {
        GL20.glNormalPointer(type, stride, pointer);
    }

    @Override
    public void glNormalPointer(int type, int stride, final FloatBuffer pointer) {
        GL20.glNormalPointer(type, stride, pointer);
    }

    @Override
    public void glOrtho(double l, double r, double b, double t, double n, double f) {
        GL20.glOrtho(l, r, b, t, n, f);
    }

    @Override
    public void glPassThrough(float token) {
        GL20.glPassThrough(token);
    }

    @Override
    public void glPixelMapfv(int map, int size, long values) {
        GL20.glPixelMapfv(map, size, values);
    }

    @Override
    public void glPixelMapfv(int map, final FloatBuffer values) {
        GL20.glPixelMapfv(map, values);
    }

    @Override
    public void glPixelMapusv(int map, int size, long values) {
        GL20.glPixelMapusv(map, size, values);
    }

    @Override
    public void glPixelMapusv(int map, final ShortBuffer values) {
        GL20.glPixelMapusv(map, values);
    }

    @Override
    public void glPixelMapuiv(int map, int size, long values) {
        GL20.glPixelMapuiv(map, size, values);
    }

    @Override
    public void glPixelMapuiv(int map, final IntBuffer values) {
        GL20.glPixelMapuiv(map, values);
    }

    @Override
    public void glPixelStorei(int pname, int param) {
        GL20.glPixelStorei(pname, param);
    }

    @Override
    public void glPixelStoref(int pname, float param) {
        GL20.glPixelStoref(pname, param);
    }

    @Override
    public void glPixelTransferi(int pname, int param) {
        GL20.glPixelTransferi(pname, param);
    }

    @Override
    public void glPixelTransferf(int pname, float param) {
        GL20.glPixelTransferf(pname, param);
    }

    @Override
    public void glPixelZoom(float xfactor, float yfactor) {
        GL20.glPixelZoom(xfactor, yfactor);
    }

    @Override
    public void glPointSize(float size) {
        GL20.glPointSize(size);
    }

    @Override
    public void glPolygonMode(int face, int mode) {
        GL20.glPolygonMode(face, mode);
    }

    @Override
    public void glPolygonOffset(float factor, float units) {
        GL20.glPolygonOffset(factor, units);
    }

    @Override
    public void glPolygonStipple(final ByteBuffer pattern) {
        GL20.glPolygonStipple(pattern);
    }

    @Override
    public void glPolygonStipple(long pattern) {
        GL20.glPolygonStipple(pattern);
    }

    @Override
    public void glPushAttrib(int mask) {
        GL20.glPushAttrib(mask);
    }

    @Override
    public void glPushClientAttrib(int mask) {
        GL20.glPushClientAttrib(mask);
    }

    @Override
    public void glPopAttrib() {
        GL20.glPopAttrib();
    }

    @Override
    public void glPopClientAttrib() {
        GL20.glPopClientAttrib();
    }

    @Override
    public void glPopMatrix() {
        GL20.glPopMatrix();
    }

    @Override
    public void glPopName() {
        GL20.glPopName();
    }

    @Override
    public void glPrioritizeTextures(final IntBuffer textures, final FloatBuffer priorities) {
        GL20.glPrioritizeTextures(textures, priorities);
    }

    @Override
    public void glPushMatrix() {
        GL20.glPushMatrix();
    }

    @Override
    public void glPushName(int name) {
        GL20.glPushName(name);
    }

    @Override
    public void glRasterPos2i(int x, int y) {
        GL20.glRasterPos2i(x, y);
    }

    @Override
    public void glRasterPos2s(short x, short y) {
        GL20.glRasterPos2s(x, y);
    }

    @Override
    public void glRasterPos2f(float x, float y) {
        GL20.glRasterPos2f(x, y);
    }

    @Override
    public void glRasterPos2d(double x, double y) {
        GL20.glRasterPos2d(x, y);
    }

    @Override
    public void glRasterPos2iv(final IntBuffer coords) {
        GL20.glRasterPos2iv(coords);
    }

    @Override
    public void glRasterPos2sv(final ShortBuffer coords) {
        GL20.glRasterPos2sv(coords);
    }

    @Override
    public void glRasterPos2fv(final FloatBuffer coords) {
        GL20.glRasterPos2fv(coords);
    }

    @Override
    public void glRasterPos2dv(final DoubleBuffer coords) {
        GL20.glRasterPos2dv(coords);
    }

    @Override
    public void glRasterPos3i(int x, int y, int z) {
        GL20.glRasterPos3i(x, y, z);
    }

    @Override
    public void glRasterPos3s(short x, short y, short z) {
        GL20.glRasterPos3s(x, y, z);
    }

    @Override
    public void glRasterPos3f(float x, float y, float z) {
        GL20.glRasterPos3f(x, y, z);
    }

    @Override
    public void glRasterPos3d(double x, double y, double z) {
        GL20.glRasterPos3d(x, y, z);
    }

    @Override
    public void glRasterPos3iv(final IntBuffer coords) {
        GL20.glRasterPos3iv(coords);
    }

    @Override
    public void glRasterPos3sv(final ShortBuffer coords) {
        GL20.glRasterPos3sv(coords);
    }

    @Override
    public void glRasterPos3fv(final FloatBuffer coords) {
        GL20.glRasterPos3fv(coords);
    }

    @Override
    public void glRasterPos3dv(final DoubleBuffer coords) {
        GL20.glRasterPos3dv(coords);
    }

    @Override
    public void glRasterPos4i(int x, int y, int z, int w) {
        GL20.glRasterPos4i(x, y, z, w);
    }

    @Override
    public void glRasterPos4s(short x, short y, short z, short w) {
        GL20.glRasterPos4s(x, y, z, w);
    }

    @Override
    public void glRasterPos4f(float x, float y, float z, float w) {
        GL20.glRasterPos4f(x, y, z, w);
    }

    @Override
    public void glRasterPos4d(double x, double y, double z, double w) {
        GL20.glRasterPos4d(x, y, z, w);
    }

    @Override
    public void glRasterPos4iv(final IntBuffer coords) {
        GL20.glRasterPos4iv(coords);
    }

    @Override
    public void glRasterPos4sv(final ShortBuffer coords) {
        GL20.glRasterPos4sv(coords);
    }

    @Override
    public void glRasterPos4fv(final FloatBuffer coords) {
        GL20.glRasterPos4fv(coords);
    }

    @Override
    public void glRasterPos4dv(final DoubleBuffer coords) {
        GL20.glRasterPos4dv(coords);
    }

    @Override
    public void glReadBuffer(int src) {
        GL20.glReadBuffer(src);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        long pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glRecti(int x1, int y1, int x2, int y2) {
        GL20.glRecti(x1, y1, x2, y2);
    }

    @Override
    public void glRects(short x1, short y1, short x2, short y2) {
        GL20.glRects(x1, y1, x2, y2);
    }

    @Override
    public void glRectf(float x1, float y1, float x2, float y2) {
        GL20.glRectf(x1, y1, x2, y2);
    }

    @Override
    public void glRectd(double x1, double y1, double x2, double y2) {
        GL20.glRectd(x1, y1, x2, y2);
    }

    @Override
    public void glRectiv(final IntBuffer v1, final IntBuffer v2) {
        GL20.glRectiv(v1, v2);
    }

    @Override
    public void glRectsv(final ShortBuffer v1, final ShortBuffer v2) {
        GL20.glRectsv(v1, v2);
    }

    @Override
    public void glRectfv(final FloatBuffer v1, final FloatBuffer v2) {
        GL20.glRectfv(v1, v2);
    }

    @Override
    public void glRectdv(final DoubleBuffer v1, final DoubleBuffer v2) {
        GL20.glRectdv(v1, v2);
    }

    @Override
    public int glRenderMode(int mode) {
        return GL20.glRenderMode(mode);
    }

    @Override
    public void glRotatef(float angle, float x, float y, float z) {
        GL20.glRotatef(angle, x, y, z);
    }

    @Override
    public void glRotated(double angle, double x, double y, double z) {
        GL20.glRotated(angle, x, y, z);
    }

    @Override
    public void glScalef(float x, float y, float z) {
        GL20.glScalef(x, y, z);
    }

    @Override
    public void glScaled(double x, double y, double z) {
        GL20.glScaled(x, y, z);
    }

    @Override
    public void glScissor(int x, int y, int width, int height) {
        GL20.glScissor(x, y, width, height);
    }

    @Override
    public void glSelectBuffer(final IntBuffer buffer) {
        GL20.glSelectBuffer(buffer);
    }

    @Override
    public void glShadeModel(int mode) {
        GL20.glShadeModel(mode);
    }

    @Override
    public void glStencilFunc(int func, int ref, int mask) {
        GL20.glStencilFunc(func, ref, mask);
    }

    @Override
    public void glStencilMask(int mask) {
        GL20.glStencilMask(mask);
    }

    @Override
    public void glStencilOp(int sfail, int dpfail, int dppass) {
        GL20.glStencilOp(sfail, dpfail, dppass);
    }

    @Override
    public void glTexCoord1f(float s) {
        GL20.glTexCoord1f(s);
    }

    @Override
    public void glTexCoord1s(short s) {
        GL20.glTexCoord1s(s);
    }

    @Override
    public void glTexCoord1i(int s) {
        GL20.glTexCoord1i(s);
    }

    @Override
    public void glTexCoord1d(double s) {
        GL20.glTexCoord1d(s);
    }

    @Override
    public void glTexCoord1fv(final FloatBuffer v) {
        GL20.glTexCoord1fv(v);
    }

    @Override
    public void glTexCoord1sv(final ShortBuffer v) {
        GL20.glTexCoord1sv(v);
    }

    @Override
    public void glTexCoord1iv(final IntBuffer v) {
        GL20.glTexCoord1iv(v);
    }

    @Override
    public void glTexCoord1dv(final DoubleBuffer v) {
        GL20.glTexCoord1dv(v);
    }

    @Override
    public void glTexCoord2f(float s, float t) {
        GL20.glTexCoord2f(s, t);
    }

    @Override
    public void glTexCoord2s(short s, short t) {
        GL20.glTexCoord2s(s, t);
    }

    @Override
    public void glTexCoord2i(int s, int t) {
        GL20.glTexCoord2i(s, t);
    }

    @Override
    public void glTexCoord2d(double s, double t) {
        GL20.glTexCoord2d(s, t);
    }

    @Override
    public void glTexCoord2fv(final FloatBuffer v) {
        GL20.glTexCoord2fv(v);
    }

    @Override
    public void glTexCoord2sv(final ShortBuffer v) {
        GL20.glTexCoord2sv(v);
    }

    @Override
    public void glTexCoord2iv(final IntBuffer v) {
        GL20.glTexCoord2iv(v);
    }

    @Override
    public void glTexCoord2dv(final DoubleBuffer v) {
        GL20.glTexCoord2dv(v);
    }

    @Override
    public void glTexCoord3f(float s, float t, float r) {
        GL20.glTexCoord3f(s, t, r);
    }

    @Override
    public void glTexCoord3s(short s, short t, short r) {
        GL20.glTexCoord3s(s, t, r);
    }

    @Override
    public void glTexCoord3i(int s, int t, int r) {
        GL20.glTexCoord3i(s, t, r);
    }

    @Override
    public void glTexCoord3d(double s, double t, double r) {
        GL20.glTexCoord3d(s, t, r);
    }

    @Override
    public void glTexCoord3fv(final FloatBuffer v) {
        GL20.glTexCoord3fv(v);
    }

    @Override
    public void glTexCoord3sv(final ShortBuffer v) {
        GL20.glTexCoord3sv(v);
    }

    @Override
    public void glTexCoord3iv(final IntBuffer v) {
        GL20.glTexCoord3iv(v);
    }

    @Override
    public void glTexCoord3dv(final DoubleBuffer v) {
        GL20.glTexCoord3dv(v);
    }

    @Override
    public void glTexCoord4f(float s, float t, float r, float q) {
        GL20.glTexCoord4f(s, t, r, q);
    }

    @Override
    public void glTexCoord4s(short s, short t, short r, short q) {
        GL20.glTexCoord4s(s, t, r, q);
    }

    @Override
    public void glTexCoord4i(int s, int t, int r, int q) {
        GL20.glTexCoord4i(s, t, r, q);
    }

    @Override
    public void glTexCoord4d(double s, double t, double r, double q) {
        GL20.glTexCoord4d(s, t, r, q);
    }

    @Override
    public void glTexCoord4fv(final FloatBuffer v) {
        GL20.glTexCoord4fv(v);
    }

    @Override
    public void glTexCoord4sv(final ShortBuffer v) {
        GL20.glTexCoord4sv(v);
    }

    @Override
    public void glTexCoord4iv(final IntBuffer v) {
        GL20.glTexCoord4iv(v);
    }

    @Override
    public void glTexCoord4dv(final DoubleBuffer v) {
        GL20.glTexCoord4dv(v);
    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, final ByteBuffer pointer) {
        GL20.glTexCoordPointer(size, type, stride, pointer);
    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, long pointer) {
        GL20.glTexCoordPointer(size, type, stride, pointer);
    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, final ShortBuffer pointer) {
        GL20.glTexCoordPointer(size, type, stride, pointer);
    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, final IntBuffer pointer) {
        GL20.glTexCoordPointer(size, type, stride, pointer);
    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, final FloatBuffer pointer) {
        GL20.glTexCoordPointer(size, type, stride, pointer);
    }

    @Override
    public void glTexEnvi(int target, int pname, int param) {
        GL20.glTexEnvi(target, pname, param);
    }

    @Override
    public void glTexEnviv(int target, int pname, final IntBuffer params) {
        GL20.glTexEnviv(target, pname, params);
    }

    @Override
    public void glTexEnvf(int target, int pname, float param) {
        GL20.glTexEnvf(target, pname, param);
    }

    @Override
    public void glTexEnvfv(int target, int pname, final FloatBuffer params) {
        GL20.glTexEnvfv(target, pname, params);
    }

    @Override
    public void glTexGeni(int coord, int pname, int param) {
        GL20.glTexGeni(coord, pname, param);
    }

    @Override
    public void glTexGeniv(int coord, int pname, final IntBuffer params) {
        GL20.glTexGeniv(coord, pname, params);
    }

    @Override
    public void glTexGenf(int coord, int pname, float param) {
        GL20.glTexGenf(coord, pname, param);
    }

    @Override
    public void glTexGenfv(int coord, int pname, final FloatBuffer params) {
        GL20.glTexGenfv(coord, pname, params);
    }

    @Override
    public void glTexGend(int coord, int pname, double param) {
        GL20.glTexGend(coord, pname, param);
    }

    @Override
    public void glTexGendv(int coord, int pname, final DoubleBuffer params) {
        GL20.glTexGendv(coord, pname, params);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        long pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final DoubleBuffer pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        long pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final DoubleBuffer pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glCopyTexImage1D(
        int target,
        int level,
        int internalFormat,
        int x,
        int y,
        int width,
        int border
    ) {
        GL20.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
    }

    @Override
    public void glCopyTexImage2D(
        int target,
        int level,
        int internalFormat,
        int x,
        int y,
        int width,
        int height,
        int border
    ) {
        GL20.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
    }

    @Override
    public void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width) {
        GL20.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
    }

    @Override
    public void glCopyTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int x,
        int y,
        int width,
        int height
    ) {
        GL20.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    @Override
    public void glTexParameteri(int target, int pname, int param) {
        GL20.glTexParameteri(target, pname, param);
    }

    @Override
    public void glTexParameteriv(int target, int pname, final IntBuffer params) {
        GL20.glTexParameteriv(target, pname, params);
    }

    @Override
    public void glTexParameterf(int target, int pname, float param) {
        GL20.glTexParameterf(target, pname, param);
    }

    @Override
    public void glTexParameterfv(int target, int pname, final FloatBuffer params) {
        GL20.glTexParameterfv(target, pname, params);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        long pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final DoubleBuffer pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        long pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final DoubleBuffer pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTranslatef(float x, float y, float z) {
        GL20.glTranslatef(x, y, z);
    }

    @Override
    public void glTranslated(double x, double y, double z) {
        GL20.glTranslated(x, y, z);
    }

    @Override
    public void glVertex2f(float x, float y) {
        GL20.glVertex2f(x, y);
    }

    @Override
    public void glVertex2s(short x, short y) {
        GL20.glVertex2s(x, y);
    }

    @Override
    public void glVertex2i(int x, int y) {
        GL20.glVertex2i(x, y);
    }

    @Override
    public void glVertex2d(double x, double y) {
        GL20.glVertex2d(x, y);
    }

    @Override
    public void glVertex2fv(final FloatBuffer coords) {
        GL20.glVertex2fv(coords);
    }

    @Override
    public void glVertex2sv(final ShortBuffer coords) {
        GL20.glVertex2sv(coords);
    }

    @Override
    public void glVertex2iv(final IntBuffer coords) {
        GL20.glVertex2iv(coords);
    }

    @Override
    public void glVertex2dv(final DoubleBuffer coords) {
        GL20.glVertex2dv(coords);
    }

    @Override
    public void glVertex3f(float x, float y, float z) {
        GL20.glVertex3f(x, y, z);
    }

    @Override
    public void glVertex3s(short x, short y, short z) {
        GL20.glVertex3s(x, y, z);
    }

    @Override
    public void glVertex3i(int x, int y, int z) {
        GL20.glVertex3i(x, y, z);
    }

    @Override
    public void glVertex3d(double x, double y, double z) {
        GL20.glVertex3d(x, y, z);
    }

    @Override
    public void glVertex3fv(final FloatBuffer coords) {
        GL20.glVertex3fv(coords);
    }

    @Override
    public void glVertex3sv(final ShortBuffer coords) {
        GL20.glVertex3sv(coords);
    }

    @Override
    public void glVertex3iv(final IntBuffer coords) {
        GL20.glVertex3iv(coords);
    }

    @Override
    public void glVertex3dv(final DoubleBuffer coords) {
        GL20.glVertex3dv(coords);
    }

    @Override
    public void glVertex4f(float x, float y, float z, float w) {
        GL20.glVertex4f(x, y, z, w);
    }

    @Override
    public void glVertex4s(short x, short y, short z, short w) {
        GL20.glVertex4s(x, y, z, w);
    }

    @Override
    public void glVertex4i(int x, int y, int z, int w) {
        GL20.glVertex4i(x, y, z, w);
    }

    @Override
    public void glVertex4d(double x, double y, double z, double w) {
        GL20.glVertex4d(x, y, z, w);
    }

    @Override
    public void glVertex4fv(final FloatBuffer coords) {
        GL20.glVertex4fv(coords);
    }

    @Override
    public void glVertex4sv(final ShortBuffer coords) {
        GL20.glVertex4sv(coords);
    }

    @Override
    public void glVertex4iv(final IntBuffer coords) {
        GL20.glVertex4iv(coords);
    }

    @Override
    public void glVertex4dv(final DoubleBuffer coords) {
        GL20.glVertex4dv(coords);
    }

    @Override
    public void glVertexPointer(int size, int type, int stride, final ByteBuffer pointer) {
        GL20.glVertexPointer(size, type, stride, pointer);
    }

    @Override
    public void glVertexPointer(int size, int type, int stride, long pointer) {
        GL20.glVertexPointer(size, type, stride, pointer);
    }

    @Override
    public void glVertexPointer(int size, int type, int stride, final ShortBuffer pointer) {
        GL20.glVertexPointer(size, type, stride, pointer);
    }

    @Override
    public void glVertexPointer(int size, int type, int stride, final IntBuffer pointer) {
        GL20.glVertexPointer(size, type, stride, pointer);
    }

    @Override
    public void glVertexPointer(int size, int type, int stride, final FloatBuffer pointer) {
        GL20.glVertexPointer(size, type, stride, pointer);
    }

    @Override
    public void glViewport(int x, int y, int w, int h) {
        GL20.glViewport(x, y, w, h);
    }

    @Override
    public boolean glAreTexturesResident(final int[] textures, final ByteBuffer residences) {
        return GL20.glAreTexturesResident(textures, residences);
    }

    @Override
    public void glClipPlane(int plane, final double[] equation) {
        GL20.glClipPlane(plane, equation);
    }

    @Override
    public void glColor3sv(final short[] v) {
        GL20.glColor3sv(v);
    }

    @Override
    public void glColor3iv(final int[] v) {
        GL20.glColor3iv(v);
    }

    @Override
    public void glColor3fv(final float[] v) {
        GL20.glColor3fv(v);
    }

    @Override
    public void glColor3dv(final double[] v) {
        GL20.glColor3dv(v);
    }

    @Override
    public void glColor3usv(final short[] v) {
        GL20.glColor3usv(v);
    }

    @Override
    public void glColor3uiv(final int[] v) {
        GL20.glColor3uiv(v);
    }

    @Override
    public void glColor4sv(final short[] v) {
        GL20.glColor4sv(v);
    }

    @Override
    public void glColor4iv(final int[] v) {
        GL20.glColor4iv(v);
    }

    @Override
    public void glColor4fv(final float[] v) {
        GL20.glColor4fv(v);
    }

    @Override
    public void glColor4dv(final double[] v) {
        GL20.glColor4dv(v);
    }

    @Override
    public void glColor4usv(final short[] v) {
        GL20.glColor4usv(v);
    }

    @Override
    public void glColor4uiv(final int[] v) {
        GL20.glColor4uiv(v);
    }

    @Override
    public void glDrawPixels(int width, int height, int format, int type, final short[] pixels) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glDrawPixels(int width, int height, int format, int type, final int[] pixels) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glDrawPixels(int width, int height, int format, int type, final float[] pixels) {
        GL20.glDrawPixels(width, height, format, type, pixels);
    }

    @Override
    public void glEvalCoord1fv(final float[] u) {
        GL20.glEvalCoord1fv(u);
    }

    @Override
    public void glEvalCoord1dv(final double[] u) {
        GL20.glEvalCoord1dv(u);
    }

    @Override
    public void glEvalCoord2fv(final float[] u) {
        GL20.glEvalCoord2fv(u);
    }

    @Override
    public void glEvalCoord2dv(final double[] u) {
        GL20.glEvalCoord2dv(u);
    }

    @Override
    public void glFeedbackBuffer(int type, final float[] buffer) {
        GL20.glFeedbackBuffer(type, buffer);
    }

    @Override
    public void glFogiv(int pname, final int[] params) {
        GL20.glFogiv(pname, params);
    }

    @Override
    public void glFogfv(int pname, final float[] params) {
        GL20.glFogfv(pname, params);
    }

    @Override
    public void glGenTextures(final int[] textures) {
        GL20.glGenTextures(textures);
    }

    @Override
    public void glDeleteTextures(final int[] textures) {
        GL20.glDeleteTextures(textures);
    }

    @Override
    public void glGetClipPlane(int plane, final double[] equation) {
        GL20.glGetClipPlane(plane, equation);
    }

    @Override
    public void glGetFloatv(int pname, final float[] params) {
        GL20.glGetFloatv(pname, params);
    }

    @Override
    public void glGetIntegerv(int pname, final int[] params) {
        GL20.glGetIntegerv(pname, params);
    }

    @Override
    public void glGetDoublev(int pname, final double[] params) {
        GL20.glGetDoublev(pname, params);
    }

    @Override
    public void glGetLightiv(int light, int pname, final int[] data) {
        GL20.glGetLightiv(light, pname, data);
    }

    @Override
    public void glGetLightfv(int light, int pname, final float[] data) {
        GL20.glGetLightfv(light, pname, data);
    }

    @Override
    public void glGetMapiv(int target, int query, final int[] data) {
        GL20.glGetMapiv(target, query, data);
    }

    @Override
    public void glGetMapfv(int target, int query, final float[] data) {
        GL20.glGetMapfv(target, query, data);
    }

    @Override
    public void glGetMapdv(int target, int query, final double[] data) {
        GL20.glGetMapdv(target, query, data);
    }

    @Override
    public void glGetMaterialiv(int face, int pname, final int[] data) {
        GL20.glGetMaterialiv(face, pname, data);
    }

    @Override
    public void glGetMaterialfv(int face, int pname, final float[] data) {
        GL20.glGetMaterialfv(face, pname, data);
    }

    @Override
    public void glGetPixelMapfv(int map, final float[] data) {
        GL20.glGetPixelMapfv(map, data);
    }

    @Override
    public void glGetPixelMapusv(int map, final short[] data) {
        GL20.glGetPixelMapusv(map, data);
    }

    @Override
    public void glGetPixelMapuiv(int map, final int[] data) {
        GL20.glGetPixelMapuiv(map, data);
    }

    @Override
    public void glGetTexEnviv(int env, int pname, final int[] data) {
        GL20.glGetTexEnviv(env, pname, data);
    }

    @Override
    public void glGetTexEnvfv(int env, int pname, final float[] data) {
        GL20.glGetTexEnvfv(env, pname, data);
    }

    @Override
    public void glGetTexGeniv(int coord, int pname, final int[] data) {
        GL20.glGetTexGeniv(coord, pname, data);
    }

    @Override
    public void glGetTexGenfv(int coord, int pname, final float[] data) {
        GL20.glGetTexGenfv(coord, pname, data);
    }

    @Override
    public void glGetTexGendv(int coord, int pname, final double[] data) {
        GL20.glGetTexGendv(coord, pname, data);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final short[] pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final int[] pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final float[] pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexImage(int tex, int level, int format, int type, final double[] pixels) {
        GL20.glGetTexImage(tex, level, format, type, pixels);
    }

    @Override
    public void glGetTexLevelParameteriv(int target, int level, int pname, final int[] params) {
        GL20.glGetTexLevelParameteriv(target, level, pname, params);
    }

    @Override
    public void glGetTexLevelParameterfv(int target, int level, int pname, final float[] params) {
        GL20.glGetTexLevelParameterfv(target, level, pname, params);
    }

    @Override
    public void glGetTexParameteriv(int target, int pname, final int[] params) {
        GL20.glGetTexParameteriv(target, pname, params);
    }

    @Override
    public void glGetTexParameterfv(int target, int pname, final float[] params) {
        GL20.glGetTexParameterfv(target, pname, params);
    }

    @Override
    public void glIndexiv(final int[] index) {
        GL20.glIndexiv(index);
    }

    @Override
    public void glIndexsv(final short[] index) {
        GL20.glIndexsv(index);
    }

    @Override
    public void glIndexfv(final float[] index) {
        GL20.glIndexfv(index);
    }

    @Override
    public void glIndexdv(final double[] index) {
        GL20.glIndexdv(index);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final short[] pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final int[] pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final float[] pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glInterleavedArrays(int format, int stride, final double[] pointer) {
        GL20.glInterleavedArrays(format, stride, pointer);
    }

    @Override
    public void glLightModeliv(int pname, final int[] params) {
        GL20.glLightModeliv(pname, params);
    }

    @Override
    public void glLightModelfv(int pname, final float[] params) {
        GL20.glLightModelfv(pname, params);
    }

    @Override
    public void glLightiv(int light, int pname, final int[] params) {
        GL20.glLightiv(light, pname, params);
    }

    @Override
    public void glLightfv(int light, int pname, final float[] params) {
        GL20.glLightfv(light, pname, params);
    }

    @Override
    public void glLoadMatrixf(final float[] m) {
        GL20.glLoadMatrixf(m);
    }

    @Override
    public void glLoadMatrixd(final double[] m) {
        GL20.glLoadMatrixd(m);
    }

    @Override
    public void glMap1f(
        int target,
        float u1,
        float u2,
        int stride,
        int order,
        final float[] points
    ) {
        GL20.glMap1f(target, u1, u2, stride, order, points);
    }

    @Override
    public void glMap1d(
        int target,
        double u1,
        double u2,
        int stride,
        int order,
        final double[] points
    ) {
        GL20.glMap1d(target, u1, u2, stride, order, points);
    }

    @Override
    public void glMap2f(
        int target,
        float u1,
        float u2,
        int ustride,
        int uorder,
        float v1,
        float v2,
        int vstride,
        int vorder,
        final float[] points
    ) {
        GL20.glMap2f(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
    }

    @Override
    public void glMap2d(
        int target,
        double u1,
        double u2,
        int ustride,
        int uorder,
        double v1,
        double v2,
        int vstride,
        int vorder,
        final double[] points
    ) {
        GL20.glMap2d(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
    }

    @Override
    public void glMaterialiv(int face, int pname, final int[] params) {
        GL20.glMaterialiv(face, pname, params);
    }

    @Override
    public void glMaterialfv(int face, int pname, final float[] params) {
        GL20.glMaterialfv(face, pname, params);
    }

    @Override
    public void glMultMatrixf(final float[] m) {
        GL20.glMultMatrixf(m);
    }

    @Override
    public void glMultMatrixd(final double[] m) {
        GL20.glMultMatrixd(m);
    }

    @Override
    public void glNormal3fv(final float[] v) {
        GL20.glNormal3fv(v);
    }

    @Override
    public void glNormal3sv(final short[] v) {
        GL20.glNormal3sv(v);
    }

    @Override
    public void glNormal3iv(final int[] v) {
        GL20.glNormal3iv(v);
    }

    @Override
    public void glNormal3dv(final double[] v) {
        GL20.glNormal3dv(v);
    }

    @Override
    public void glPixelMapfv(int map, final float[] values) {
        GL20.glPixelMapfv(map, values);
    }

    @Override
    public void glPixelMapusv(int map, final short[] values) {
        GL20.glPixelMapusv(map, values);
    }

    @Override
    public void glPixelMapuiv(int map, final int[] values) {
        GL20.glPixelMapuiv(map, values);
    }

    @Override
    public void glPrioritizeTextures(final int[] textures, final float[] priorities) {
        GL20.glPrioritizeTextures(textures, priorities);
    }

    @Override
    public void glRasterPos2iv(final int[] coords) {
        GL20.glRasterPos2iv(coords);
    }

    @Override
    public void glRasterPos2sv(final short[] coords) {
        GL20.glRasterPos2sv(coords);
    }

    @Override
    public void glRasterPos2fv(final float[] coords) {
        GL20.glRasterPos2fv(coords);
    }

    @Override
    public void glRasterPos2dv(final double[] coords) {
        GL20.glRasterPos2dv(coords);
    }

    @Override
    public void glRasterPos3iv(final int[] coords) {
        GL20.glRasterPos3iv(coords);
    }

    @Override
    public void glRasterPos3sv(final short[] coords) {
        GL20.glRasterPos3sv(coords);
    }

    @Override
    public void glRasterPos3fv(final float[] coords) {
        GL20.glRasterPos3fv(coords);
    }

    @Override
    public void glRasterPos3dv(final double[] coords) {
        GL20.glRasterPos3dv(coords);
    }

    @Override
    public void glRasterPos4iv(final int[] coords) {
        GL20.glRasterPos4iv(coords);
    }

    @Override
    public void glRasterPos4sv(final short[] coords) {
        GL20.glRasterPos4sv(coords);
    }

    @Override
    public void glRasterPos4fv(final float[] coords) {
        GL20.glRasterPos4fv(coords);
    }

    @Override
    public void glRasterPos4dv(final double[] coords) {
        GL20.glRasterPos4dv(coords);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glReadPixels(
        int x,
        int y,
        int width,
        int height,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glRectiv(final int[] v1, final int[] v2) {
        GL20.glRectiv(v1, v2);
    }

    @Override
    public void glRectsv(final short[] v1, final short[] v2) {
        GL20.glRectsv(v1, v2);
    }

    @Override
    public void glRectfv(final float[] v1, final float[] v2) {
        GL20.glRectfv(v1, v2);
    }

    @Override
    public void glRectdv(final double[] v1, final double[] v2) {
        GL20.glRectdv(v1, v2);
    }

    @Override
    public void glSelectBuffer(final int[] buffer) {
        GL20.glSelectBuffer(buffer);
    }

    @Override
    public void glTexCoord1fv(final float[] v) {
        GL20.glTexCoord1fv(v);
    }

    @Override
    public void glTexCoord1sv(final short[] v) {
        GL20.glTexCoord1sv(v);
    }

    @Override
    public void glTexCoord1iv(final int[] v) {
        GL20.glTexCoord1iv(v);
    }

    @Override
    public void glTexCoord1dv(final double[] v) {
        GL20.glTexCoord1dv(v);
    }

    @Override
    public void glTexCoord2fv(final float[] v) {
        GL20.glTexCoord2fv(v);
    }

    @Override
    public void glTexCoord2sv(final short[] v) {
        GL20.glTexCoord2sv(v);
    }

    @Override
    public void glTexCoord2iv(final int[] v) {
        GL20.glTexCoord2iv(v);
    }

    @Override
    public void glTexCoord2dv(final double[] v) {
        GL20.glTexCoord2dv(v);
    }

    @Override
    public void glTexCoord3fv(final float[] v) {
        GL20.glTexCoord3fv(v);
    }

    @Override
    public void glTexCoord3sv(final short[] v) {
        GL20.glTexCoord3sv(v);
    }

    @Override
    public void glTexCoord3iv(final int[] v) {
        GL20.glTexCoord3iv(v);
    }

    @Override
    public void glTexCoord3dv(final double[] v) {
        GL20.glTexCoord3dv(v);
    }

    @Override
    public void glTexCoord4fv(final float[] v) {
        GL20.glTexCoord4fv(v);
    }

    @Override
    public void glTexCoord4sv(final short[] v) {
        GL20.glTexCoord4sv(v);
    }

    @Override
    public void glTexCoord4iv(final int[] v) {
        GL20.glTexCoord4iv(v);
    }

    @Override
    public void glTexCoord4dv(final double[] v) {
        GL20.glTexCoord4dv(v);
    }

    @Override
    public void glTexEnviv(int target, int pname, final int[] params) {
        GL20.glTexEnviv(target, pname, params);
    }

    @Override
    public void glTexEnvfv(int target, int pname, final float[] params) {
        GL20.glTexEnvfv(target, pname, params);
    }

    @Override
    public void glTexGeniv(int coord, int pname, final int[] params) {
        GL20.glTexGeniv(coord, pname, params);
    }

    @Override
    public void glTexGenfv(int coord, int pname, final float[] params) {
        GL20.glTexGenfv(coord, pname, params);
    }

    @Override
    public void glTexGendv(int coord, int pname, final double[] params) {
        GL20.glTexGendv(coord, pname, params);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        final double[] pixels
    ) {
        GL20.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        final double[] pixels
    ) {
        GL20.glTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexParameteriv(int target, int pname, final int[] params) {
        GL20.glTexParameteriv(target, pname, params);
    }

    @Override
    public void glTexParameterfv(int target, int pname, final float[] params) {
        GL20.glTexParameterfv(target, pname, params);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int type,
        final double[] pixels
    ) {
        GL20.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        final double[] pixels
    ) {
        GL20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glVertex2fv(final float[] coords) {
        GL20.glVertex2fv(coords);
    }

    @Override
    public void glVertex2sv(final short[] coords) {
        GL20.glVertex2sv(coords);
    }

    @Override
    public void glVertex2iv(final int[] coords) {
        GL20.glVertex2iv(coords);
    }

    @Override
    public void glVertex2dv(final double[] coords) {
        GL20.glVertex2dv(coords);
    }

    @Override
    public void glVertex3fv(final float[] coords) {
        GL20.glVertex3fv(coords);
    }

    @Override
    public void glVertex3sv(final short[] coords) {
        GL20.glVertex3sv(coords);
    }

    @Override
    public void glVertex3iv(final int[] coords) {
        GL20.glVertex3iv(coords);
    }

    @Override
    public void glVertex3dv(final double[] coords) {
        GL20.glVertex3dv(coords);
    }

    @Override
    public void glVertex4fv(final float[] coords) {
        GL20.glVertex4fv(coords);
    }

    @Override
    public void glVertex4sv(final short[] coords) {
        GL20.glVertex4sv(coords);
    }

    @Override
    public void glVertex4iv(final int[] coords) {
        GL20.glVertex4iv(coords);
    }

    @Override
    public void glVertex4dv(final double[] coords) {
        GL20.glVertex4dv(coords);
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        long pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final DoubleBuffer pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final ByteBuffer pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        long pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final ShortBuffer pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final IntBuffer pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final FloatBuffer pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final DoubleBuffer pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glCopyTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int x,
        int y,
        int width,
        int height
    ) {
        GL20.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
    }

    @Override
    public void glDrawRangeElements(
        int mode,
        int start,
        int end,
        int count,
        int type,
        long indices
    ) {
        GL20.glDrawRangeElements(mode, start, end, count, type, indices);
    }

    @Override
    public void glDrawRangeElements(
        int mode,
        int start,
        int end,
        int type,
        final ByteBuffer indices
    ) {
        GL20.glDrawRangeElements(mode, start, end, type, indices);
    }

    @Override
    public void glDrawRangeElements(int mode, int start, int end, final ByteBuffer indices) {
        GL20.glDrawRangeElements(mode, start, end, indices);
    }

    @Override
    public void glDrawRangeElements(int mode, int start, int end, final ShortBuffer indices) {
        GL20.glDrawRangeElements(mode, start, end, indices);
    }

    @Override
    public void glDrawRangeElements(int mode, int start, int end, final IntBuffer indices) {
        GL20.glDrawRangeElements(mode, start, end, indices);
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int format,
        int type,
        final double[] pixels
    ) {
        GL20.glTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final short[] pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final int[] pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final float[] pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int type,
        final double[] pixels
    ) {
        GL20.glTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            type,
            pixels
        );
    }

    @Override
    public void glCompressedTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        int imageSize,
        long data
    ) {
        GL20.glCompressedTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            imageSize,
            data
        );
    }

    @Override
    public void glCompressedTexImage3D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int depth,
        int border,
        final ByteBuffer data
    ) {
        GL20.glCompressedTexImage3D(
            target,
            level,
            internalformat,
            width,
            height,
            depth,
            border,
            data
        );
    }

    @Override
    public void glCompressedTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int imageSize,
        long data
    ) {
        GL20.glCompressedTexImage2D(
            target,
            level,
            internalformat,
            width,
            height,
            border,
            imageSize,
            data
        );
    }

    @Override
    public void glCompressedTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        final ByteBuffer data
    ) {
        GL20.glCompressedTexImage2D(target, level, internalformat, width, height, border, data);
    }

    @Override
    public void glCompressedTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int imageSize,
        long data
    ) {
        GL20.glCompressedTexImage1D(target, level, internalformat, width, border, imageSize, data);
    }

    @Override
    public void glCompressedTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        final ByteBuffer data
    ) {
        GL20.glCompressedTexImage1D(target, level, internalformat, width, border, data);
    }

    @Override
    public void glCompressedTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        int imageSize,
        long data
    ) {
        GL20.glCompressedTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            imageSize,
            data
        );
    }

    @Override
    public void glCompressedTexSubImage3D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int zoffset,
        int width,
        int height,
        int depth,
        int format,
        final ByteBuffer data
    ) {
        GL20.glCompressedTexSubImage3D(
            target,
            level,
            xoffset,
            yoffset,
            zoffset,
            width,
            height,
            depth,
            format,
            data
        );
    }

    @Override
    public void glCompressedTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int imageSize,
        long data
    ) {
        GL20.glCompressedTexSubImage2D(
            target,
            level,
            xoffset,
            yoffset,
            width,
            height,
            format,
            imageSize,
            data
        );
    }

    @Override
    public void glCompressedTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        final ByteBuffer data
    ) {
        GL20.glCompressedTexSubImage2D(
            target,
            level,
            xoffset,
            yoffset,
            width,
            height,
            format,
            data
        );
    }

    @Override
    public void glCompressedTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        int imageSize,
        long data
    ) {
        GL20.glCompressedTexSubImage1D(target, level, xoffset, width, format, imageSize, data);
    }

    @Override
    public void glCompressedTexSubImage1D(
        int target,
        int level,
        int xoffset,
        int width,
        int format,
        final ByteBuffer data
    ) {
        GL20.glCompressedTexSubImage1D(target, level, xoffset, width, format, data);
    }

    @Override
    public void glGetCompressedTexImage(int target, int level, final ByteBuffer pixels) {
        GL20.glGetCompressedTexImage(target, level, pixels);
    }

    @Override
    public void glGetCompressedTexImage(int target, int level, long pixels) {
        GL20.glGetCompressedTexImage(target, level, pixels);
    }

    @Override
    public void glSampleCoverage(float value, boolean invert) {
        GL20.glSampleCoverage(value, invert);
    }

    @Override
    public void glActiveTexture(int texture) {
        GL20.glActiveTexture(texture);
    }

    @Override
    public void glClientActiveTexture(int texture) {
        GL20.glClientActiveTexture(texture);
    }

    @Override
    public void glMultiTexCoord1f(int texture, float s) {
        GL20.glMultiTexCoord1f(texture, s);
    }

    @Override
    public void glMultiTexCoord1s(int texture, short s) {
        GL20.glMultiTexCoord1s(texture, s);
    }

    @Override
    public void glMultiTexCoord1i(int texture, int s) {
        GL20.glMultiTexCoord1i(texture, s);
    }

    @Override
    public void glMultiTexCoord1d(int texture, double s) {
        GL20.glMultiTexCoord1d(texture, s);
    }

    @Override
    public void glMultiTexCoord1fv(int texture, final FloatBuffer v) {
        GL20.glMultiTexCoord1fv(texture, v);
    }

    @Override
    public void glMultiTexCoord1sv(int texture, final ShortBuffer v) {
        GL20.glMultiTexCoord1sv(texture, v);
    }

    @Override
    public void glMultiTexCoord1iv(int texture, final IntBuffer v) {
        GL20.glMultiTexCoord1iv(texture, v);
    }

    @Override
    public void glMultiTexCoord1dv(int texture, final DoubleBuffer v) {
        GL20.glMultiTexCoord1dv(texture, v);
    }

    @Override
    public void glMultiTexCoord2f(int texture, float s, float t) {
        GL20.glMultiTexCoord2f(texture, s, t);
    }

    @Override
    public void glMultiTexCoord2s(int texture, short s, short t) {
        GL20.glMultiTexCoord2s(texture, s, t);
    }

    @Override
    public void glMultiTexCoord2i(int texture, int s, int t) {
        GL20.glMultiTexCoord2i(texture, s, t);
    }

    @Override
    public void glMultiTexCoord2d(int texture, double s, double t) {
        GL20.glMultiTexCoord2d(texture, s, t);
    }

    @Override
    public void glMultiTexCoord2fv(int texture, final FloatBuffer v) {
        GL20.glMultiTexCoord2fv(texture, v);
    }

    @Override
    public void glMultiTexCoord2sv(int texture, final ShortBuffer v) {
        GL20.glMultiTexCoord2sv(texture, v);
    }

    @Override
    public void glMultiTexCoord2iv(int texture, final IntBuffer v) {
        GL20.glMultiTexCoord2iv(texture, v);
    }

    @Override
    public void glMultiTexCoord2dv(int texture, final DoubleBuffer v) {
        GL20.glMultiTexCoord2dv(texture, v);
    }

    @Override
    public void glMultiTexCoord3f(int texture, float s, float t, float r) {
        GL20.glMultiTexCoord3f(texture, s, t, r);
    }

    @Override
    public void glMultiTexCoord3s(int texture, short s, short t, short r) {
        GL20.glMultiTexCoord3s(texture, s, t, r);
    }

    @Override
    public void glMultiTexCoord3i(int texture, int s, int t, int r) {
        GL20.glMultiTexCoord3i(texture, s, t, r);
    }

    @Override
    public void glMultiTexCoord3d(int texture, double s, double t, double r) {
        GL20.glMultiTexCoord3d(texture, s, t, r);
    }

    @Override
    public void glMultiTexCoord3fv(int texture, final FloatBuffer v) {
        GL20.glMultiTexCoord3fv(texture, v);
    }

    @Override
    public void glMultiTexCoord3sv(int texture, final ShortBuffer v) {
        GL20.glMultiTexCoord3sv(texture, v);
    }

    @Override
    public void glMultiTexCoord3iv(int texture, final IntBuffer v) {
        GL20.glMultiTexCoord3iv(texture, v);
    }

    @Override
    public void glMultiTexCoord3dv(int texture, final DoubleBuffer v) {
        GL20.glMultiTexCoord3dv(texture, v);
    }

    @Override
    public void glMultiTexCoord4f(int texture, float s, float t, float r, float q) {
        GL20.glMultiTexCoord4f(texture, s, t, r, q);
    }

    @Override
    public void glMultiTexCoord4s(int texture, short s, short t, short r, short q) {
        GL20.glMultiTexCoord4s(texture, s, t, r, q);
    }

    @Override
    public void glMultiTexCoord4i(int texture, int s, int t, int r, int q) {
        GL20.glMultiTexCoord4i(texture, s, t, r, q);
    }

    @Override
    public void glMultiTexCoord4d(int texture, double s, double t, double r, double q) {
        GL20.glMultiTexCoord4d(texture, s, t, r, q);
    }

    @Override
    public void glMultiTexCoord4fv(int texture, final FloatBuffer v) {
        GL20.glMultiTexCoord4fv(texture, v);
    }

    @Override
    public void glMultiTexCoord4sv(int texture, final ShortBuffer v) {
        GL20.glMultiTexCoord4sv(texture, v);
    }

    @Override
    public void glMultiTexCoord4iv(int texture, final IntBuffer v) {
        GL20.glMultiTexCoord4iv(texture, v);
    }

    @Override
    public void glMultiTexCoord4dv(int texture, final DoubleBuffer v) {
        GL20.glMultiTexCoord4dv(texture, v);
    }

    @Override
    public void glLoadTransposeMatrixf(final FloatBuffer m) {
        GL20.glLoadTransposeMatrixf(m);
    }

    @Override
    public void glLoadTransposeMatrixd(final DoubleBuffer m) {
        GL20.glLoadTransposeMatrixd(m);
    }

    @Override
    public void glMultTransposeMatrixf(final FloatBuffer m) {
        GL20.glMultTransposeMatrixf(m);
    }

    @Override
    public void glMultTransposeMatrixd(final DoubleBuffer m) {
        GL20.glMultTransposeMatrixd(m);
    }

    @Override
    public void glMultiTexCoord1fv(int texture, final float[] v) {
        GL20.glMultiTexCoord1fv(texture, v);
    }

    @Override
    public void glMultiTexCoord1sv(int texture, final short[] v) {
        GL20.glMultiTexCoord1sv(texture, v);
    }

    @Override
    public void glMultiTexCoord1iv(int texture, final int[] v) {
        GL20.glMultiTexCoord1iv(texture, v);
    }

    @Override
    public void glMultiTexCoord1dv(int texture, final double[] v) {
        GL20.glMultiTexCoord1dv(texture, v);
    }

    @Override
    public void glMultiTexCoord2fv(int texture, final float[] v) {
        GL20.glMultiTexCoord2fv(texture, v);
    }

    @Override
    public void glMultiTexCoord2sv(int texture, final short[] v) {
        GL20.glMultiTexCoord2sv(texture, v);
    }

    @Override
    public void glMultiTexCoord2iv(int texture, final int[] v) {
        GL20.glMultiTexCoord2iv(texture, v);
    }

    @Override
    public void glMultiTexCoord2dv(int texture, final double[] v) {
        GL20.glMultiTexCoord2dv(texture, v);
    }

    @Override
    public void glMultiTexCoord3fv(int texture, final float[] v) {
        GL20.glMultiTexCoord3fv(texture, v);
    }

    @Override
    public void glMultiTexCoord3sv(int texture, final short[] v) {
        GL20.glMultiTexCoord3sv(texture, v);
    }

    @Override
    public void glMultiTexCoord3iv(int texture, final int[] v) {
        GL20.glMultiTexCoord3iv(texture, v);
    }

    @Override
    public void glMultiTexCoord3dv(int texture, final double[] v) {
        GL20.glMultiTexCoord3dv(texture, v);
    }

    @Override
    public void glMultiTexCoord4fv(int texture, final float[] v) {
        GL20.glMultiTexCoord4fv(texture, v);
    }

    @Override
    public void glMultiTexCoord4sv(int texture, final short[] v) {
        GL20.glMultiTexCoord4sv(texture, v);
    }

    @Override
    public void glMultiTexCoord4iv(int texture, final int[] v) {
        GL20.glMultiTexCoord4iv(texture, v);
    }

    @Override
    public void glMultiTexCoord4dv(int texture, final double[] v) {
        GL20.glMultiTexCoord4dv(texture, v);
    }

    @Override
    public void glLoadTransposeMatrixf(final float[] m) {
        GL20.glLoadTransposeMatrixf(m);
    }

    @Override
    public void glLoadTransposeMatrixd(final double[] m) {
        GL20.glLoadTransposeMatrixd(m);
    }

    @Override
    public void glMultTransposeMatrixf(final float[] m) {
        GL20.glMultTransposeMatrixf(m);
    }

    @Override
    public void glMultTransposeMatrixd(final double[] m) {
        GL20.glMultTransposeMatrixd(m);
    }

    @Override
    public void glBlendColor(float red, float green, float blue, float alpha) {
        GL20.glBlendColor(red, green, blue, alpha);
    }

    @Override
    public void glBlendEquation(int mode) {
        GL20.glBlendEquation(mode);
    }

    @Override
    public void glFogCoordf(float coord) {
        GL20.glFogCoordf(coord);
    }

    @Override
    public void glFogCoordd(double coord) {
        GL20.glFogCoordd(coord);
    }

    @Override
    public void glFogCoordfv(final FloatBuffer coord) {
        GL20.glFogCoordfv(coord);
    }

    @Override
    public void glFogCoorddv(final DoubleBuffer coord) {
        GL20.glFogCoorddv(coord);
    }

    @Override
    public void glFogCoordPointer(int type, int stride, final ByteBuffer pointer) {
        GL20.glFogCoordPointer(type, stride, pointer);
    }

    @Override
    public void glFogCoordPointer(int type, int stride, long pointer) {
        GL20.glFogCoordPointer(type, stride, pointer);
    }

    @Override
    public void glFogCoordPointer(int type, int stride, final ShortBuffer pointer) {
        GL20.glFogCoordPointer(type, stride, pointer);
    }

    @Override
    public void glFogCoordPointer(int type, int stride, final FloatBuffer pointer) {
        GL20.glFogCoordPointer(type, stride, pointer);
    }

    @Override
    public void glMultiDrawArrays(int mode, final IntBuffer first, final IntBuffer count) {
        GL20.glMultiDrawArrays(mode, first, count);
    }

    @Override
    public void glMultiDrawElements(
        int mode,
        final IntBuffer count,
        int type,
        final LongBuffer indices
    ) {

        PointerBuffer indicesBuf = BufferUtils.createPointerBuffer(indices.capacity());
        indicesBuf.put(indices);

        GL20.glMultiDrawElements(mode, count, type, indicesBuf);

        for (int i = 0; i < indices.capacity(); i++) {
            indices.put(i, indicesBuf.get(i));
        }
        indicesBuf.free();

    }

    @Override
    public void glPointParameterf(int pname, float param) {
        GL20.glPointParameterf(pname, param);
    }

    @Override
    public void glPointParameteri(int pname, int param) {
        GL20.glPointParameteri(pname, param);
    }

    @Override
    public void glPointParameterfv(int pname, final FloatBuffer params) {
        GL20.glPointParameterfv(pname, params);
    }

    @Override
    public void glPointParameteriv(int pname, final IntBuffer params) {
        GL20.glPointParameteriv(pname, params);
    }

    @Override
    public void glSecondaryColor3b(byte red, byte green, byte blue) {
        GL20.glSecondaryColor3b(red, green, blue);
    }

    @Override
    public void glSecondaryColor3s(short red, short green, short blue) {
        GL20.glSecondaryColor3s(red, green, blue);
    }

    @Override
    public void glSecondaryColor3i(int red, int green, int blue) {
        GL20.glSecondaryColor3i(red, green, blue);
    }

    @Override
    public void glSecondaryColor3f(float red, float green, float blue) {
        GL20.glSecondaryColor3f(red, green, blue);
    }

    @Override
    public void glSecondaryColor3d(double red, double green, double blue) {
        GL20.glSecondaryColor3d(red, green, blue);
    }

    @Override
    public void glSecondaryColor3ub(byte red, byte green, byte blue) {
        GL20.glSecondaryColor3ub(red, green, blue);
    }

    @Override
    public void glSecondaryColor3us(short red, short green, short blue) {
        GL20.glSecondaryColor3us(red, green, blue);
    }

    @Override
    public void glSecondaryColor3ui(int red, int green, int blue) {
        GL20.glSecondaryColor3ui(red, green, blue);
    }

    @Override
    public void glSecondaryColor3bv(final ByteBuffer v) {
        GL20.glSecondaryColor3bv(v);
    }

    @Override
    public void glSecondaryColor3sv(final ShortBuffer v) {
        GL20.glSecondaryColor3sv(v);
    }

    @Override
    public void glSecondaryColor3iv(final IntBuffer v) {
        GL20.glSecondaryColor3iv(v);
    }

    @Override
    public void glSecondaryColor3fv(final FloatBuffer v) {
        GL20.glSecondaryColor3fv(v);
    }

    @Override
    public void glSecondaryColor3dv(final DoubleBuffer v) {
        GL20.glSecondaryColor3dv(v);
    }

    @Override
    public void glSecondaryColor3ubv(final ByteBuffer v) {
        GL20.glSecondaryColor3ubv(v);
    }

    @Override
    public void glSecondaryColor3usv(final ShortBuffer v) {
        GL20.glSecondaryColor3usv(v);
    }

    @Override
    public void glSecondaryColor3uiv(final IntBuffer v) {
        GL20.glSecondaryColor3uiv(v);
    }

    @Override
    public void glSecondaryColorPointer(int size, int type, int stride, final ByteBuffer pointer) {
        GL20.glSecondaryColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glSecondaryColorPointer(int size, int type, int stride, long pointer) {
        GL20.glSecondaryColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glSecondaryColorPointer(int size, int type, int stride, final ShortBuffer pointer) {
        GL20.glSecondaryColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glSecondaryColorPointer(int size, int type, int stride, final IntBuffer pointer) {
        GL20.glSecondaryColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glSecondaryColorPointer(int size, int type, int stride, final FloatBuffer pointer) {
        GL20.glSecondaryColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glBlendFuncSeparate(
        int sfactorRGB,
        int dfactorRGB,
        int sfactorAlpha,
        int dfactorAlpha
    ) {
        GL20.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
    }

    @Override
    public void glWindowPos2i(int x, int y) {
        GL20.glWindowPos2i(x, y);
    }

    @Override
    public void glWindowPos2s(short x, short y) {
        GL20.glWindowPos2s(x, y);
    }

    @Override
    public void glWindowPos2f(float x, float y) {
        GL20.glWindowPos2f(x, y);
    }

    @Override
    public void glWindowPos2d(double x, double y) {
        GL20.glWindowPos2d(x, y);
    }

    @Override
    public void glWindowPos2iv(final IntBuffer p) {
        GL20.glWindowPos2iv(p);
    }

    @Override
    public void glWindowPos2sv(final ShortBuffer p) {
        GL20.glWindowPos2sv(p);
    }

    @Override
    public void glWindowPos2fv(final FloatBuffer p) {
        GL20.glWindowPos2fv(p);
    }

    @Override
    public void glWindowPos2dv(final DoubleBuffer p) {
        GL20.glWindowPos2dv(p);
    }

    @Override
    public void glWindowPos3i(int x, int y, int z) {
        GL20.glWindowPos3i(x, y, z);
    }

    @Override
    public void glWindowPos3s(short x, short y, short z) {
        GL20.glWindowPos3s(x, y, z);
    }

    @Override
    public void glWindowPos3f(float x, float y, float z) {
        GL20.glWindowPos3f(x, y, z);
    }

    @Override
    public void glWindowPos3d(double x, double y, double z) {
        GL20.glWindowPos3d(x, y, z);
    }

    @Override
    public void glWindowPos3iv(final IntBuffer p) {
        GL20.glWindowPos3iv(p);
    }

    @Override
    public void glWindowPos3sv(final ShortBuffer p) {
        GL20.glWindowPos3sv(p);
    }

    @Override
    public void glWindowPos3fv(final FloatBuffer p) {
        GL20.glWindowPos3fv(p);
    }

    @Override
    public void glWindowPos3dv(final DoubleBuffer p) {
        GL20.glWindowPos3dv(p);
    }

    @Override
    public void glFogCoordfv(final float[] coord) {
        GL20.glFogCoordfv(coord);
    }

    @Override
    public void glFogCoorddv(final double[] coord) {
        GL20.glFogCoorddv(coord);
    }

    @Override
    public void glMultiDrawArrays(int mode, final int[] first, final int[] count) {
        GL20.glMultiDrawArrays(mode, first, count);
    }

    @Override
    public void glMultiDrawElements(
        int mode,
        final int[] count,
        int type,
        final LongBuffer indices
    ) {

        PointerBuffer indicesBuf = BufferUtils.createPointerBuffer(indices.capacity());
        indicesBuf.put(indices);

        GL20.glMultiDrawElements(mode, count, type, indicesBuf);

        for (int i = 0; i < indices.capacity(); i++) {
            indices.put(i, indicesBuf.get(i));
        }
        indicesBuf.free();

    }

    @Override
    public void glPointParameterfv(int pname, final float[] params) {
        GL20.glPointParameterfv(pname, params);
    }

    @Override
    public void glPointParameteriv(int pname, final int[] params) {
        GL20.glPointParameteriv(pname, params);
    }

    @Override
    public void glSecondaryColor3sv(final short[] v) {
        GL20.glSecondaryColor3sv(v);
    }

    @Override
    public void glSecondaryColor3iv(final int[] v) {
        GL20.glSecondaryColor3iv(v);
    }

    @Override
    public void glSecondaryColor3fv(final float[] v) {
        GL20.glSecondaryColor3fv(v);
    }

    @Override
    public void glSecondaryColor3dv(final double[] v) {
        GL20.glSecondaryColor3dv(v);
    }

    @Override
    public void glSecondaryColor3usv(final short[] v) {
        GL20.glSecondaryColor3usv(v);
    }

    @Override
    public void glSecondaryColor3uiv(final int[] v) {
        GL20.glSecondaryColor3uiv(v);
    }

    @Override
    public void glWindowPos2iv(final int[] p) {
        GL20.glWindowPos2iv(p);
    }

    @Override
    public void glWindowPos2sv(final short[] p) {
        GL20.glWindowPos2sv(p);
    }

    @Override
    public void glWindowPos2fv(final float[] p) {
        GL20.glWindowPos2fv(p);
    }

    @Override
    public void glWindowPos2dv(final double[] p) {
        GL20.glWindowPos2dv(p);
    }

    @Override
    public void glWindowPos3iv(final int[] p) {
        GL20.glWindowPos3iv(p);
    }

    @Override
    public void glWindowPos3sv(final short[] p) {
        GL20.glWindowPos3sv(p);
    }

    @Override
    public void glWindowPos3fv(final float[] p) {
        GL20.glWindowPos3fv(p);
    }

    @Override
    public void glWindowPos3dv(final double[] p) {
        GL20.glWindowPos3dv(p);
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        GL20.glBindBuffer(target, buffer);
    }

    @Override
    public void glDeleteBuffers(final IntBuffer buffers) {
        GL20.glDeleteBuffers(buffers);
    }

    @Override
    public void glDeleteBuffers(int buffer) {
        GL20.glDeleteBuffers(buffer);
    }

    @Override
    public void glGenBuffers(final IntBuffer buffers) {
        GL20.glGenBuffers(buffers);
    }

    @Override
    public int glGenBuffers() {
        return GL20.glGenBuffers();
    }

    @Override
    public boolean glIsBuffer(int buffer) {
        return GL20.glIsBuffer(buffer);
    }

    @Override
    public void glBufferData(int target, long size, int usage) {
        GL20.glBufferData(target, size, usage);
    }

    @Override
    public void glBufferData(int target, final ByteBuffer data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final ShortBuffer data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final IntBuffer data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final LongBuffer data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final FloatBuffer data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final DoubleBuffer data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferSubData(int target, long offset, final ByteBuffer data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final ShortBuffer data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final IntBuffer data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final LongBuffer data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final FloatBuffer data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final DoubleBuffer data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final ByteBuffer data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final ShortBuffer data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final IntBuffer data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final LongBuffer data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final FloatBuffer data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final DoubleBuffer data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public final ByteBuffer glMapBuffer(int target, int access) {
        return GL20.glMapBuffer(target, access);
    }

    @Override
    public final ByteBuffer glMapBuffer(int target, int access, final ByteBuffer oldBuffer) {
        return GL20.glMapBuffer(target, access, oldBuffer);
    }

    @Override
    public final ByteBuffer glMapBuffer(
        int target,
        int access,
        long length,
        final ByteBuffer oldBuffer
    ) {
        return GL20.glMapBuffer(target, access, length, oldBuffer);
    }

    @Override
    public boolean glUnmapBuffer(int target) {
        return GL20.glUnmapBuffer(target);
    }

    @Override
    public void glGetBufferParameteriv(int target, int pname, final IntBuffer params) {
        GL20.glGetBufferParameteriv(target, pname, params);
    }

    @Override
    public int glGetBufferParameteri(int target, int pname) {
        return GL20.glGetBufferParameteri(target, pname);
    }

    @Override
    public void glGetBufferPointerv(int target, int pname, final LongBuffer params) {

        PointerBuffer paramsBuf = BufferUtils.createPointerBuffer(params.capacity());
        paramsBuf.put(params);

        GL20.glGetBufferPointerv(target, pname, paramsBuf);

        for (int i = 0; i < params.capacity(); i++) {
            params.put(i, paramsBuf.get(i));
        }
        paramsBuf.free();

    }

    @Override
    public long glGetBufferPointer(int target, int pname) {
        return GL20.glGetBufferPointer(target, pname);
    }

    @Override
    public void glGenQueries(final IntBuffer ids) {
        GL20.glGenQueries(ids);
    }

    @Override
    public int glGenQueries() {
        return GL20.glGenQueries();
    }

    @Override
    public void glDeleteQueries(final IntBuffer ids) {
        GL20.glDeleteQueries(ids);
    }

    @Override
    public void glDeleteQueries(int id) {
        GL20.glDeleteQueries(id);
    }

    @Override
    public boolean glIsQuery(int id) {
        return GL20.glIsQuery(id);
    }

    @Override
    public void glBeginQuery(int target, int id) {
        GL20.glBeginQuery(target, id);
    }

    @Override
    public void glEndQuery(int target) {
        GL20.glEndQuery(target);
    }

    @Override
    public void glGetQueryiv(int target, int pname, final IntBuffer params) {
        GL20.glGetQueryiv(target, pname, params);
    }

    @Override
    public int glGetQueryi(int target, int pname) {
        return GL20.glGetQueryi(target, pname);
    }

    @Override
    public void glGetQueryObjectiv(int id, int pname, final IntBuffer params) {
        GL20.glGetQueryObjectiv(id, pname, params);
    }

    @Override
    public void glGetQueryObjectiv(int id, int pname, long params) {
        GL20.glGetQueryObjectiv(id, pname, params);
    }

    @Override
    public int glGetQueryObjecti(int id, int pname) {
        return GL20.glGetQueryObjecti(id, pname);
    }

    @Override
    public void glGetQueryObjectuiv(int id, int pname, final IntBuffer params) {
        GL20.glGetQueryObjectuiv(id, pname, params);
    }

    @Override
    public void glGetQueryObjectuiv(int id, int pname, long params) {
        GL20.glGetQueryObjectuiv(id, pname, params);
    }

    @Override
    public int glGetQueryObjectui(int id, int pname) {
        return GL20.glGetQueryObjectui(id, pname);
    }

    @Override
    public void glDeleteBuffers(final int[] buffers) {
        GL20.glDeleteBuffers(buffers);
    }

    @Override
    public void glGenBuffers(final int[] buffers) {
        GL20.glGenBuffers(buffers);
    }

    @Override
    public void glBufferData(int target, final short[] data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final int[] data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final long[] data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final float[] data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, final double[] data, int usage) {
        GL20.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferSubData(int target, long offset, final short[] data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final int[] data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final long[] data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final float[] data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, long offset, final double[] data) {
        GL20.glBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final short[] data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final int[] data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final long[] data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final float[] data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferSubData(int target, long offset, final double[] data) {
        GL20.glGetBufferSubData(target, offset, data);
    }

    @Override
    public void glGetBufferParameteriv(int target, int pname, final int[] params) {
        GL20.glGetBufferParameteriv(target, pname, params);
    }

    @Override
    public void glGenQueries(final int[] ids) {
        GL20.glGenQueries(ids);
    }

    @Override
    public void glDeleteQueries(final int[] ids) {
        GL20.glDeleteQueries(ids);
    }

    @Override
    public void glGetQueryiv(int target, int pname, final int[] params) {
        GL20.glGetQueryiv(target, pname, params);
    }

    @Override
    public void glGetQueryObjectiv(int id, int pname, final int[] params) {
        GL20.glGetQueryObjectiv(id, pname, params);
    }

    @Override
    public void glGetQueryObjectuiv(int id, int pname, final int[] params) {
        GL20.glGetQueryObjectuiv(id, pname, params);
    }

    @Override
    public int glCreateProgram() {
        return GL20.glCreateProgram();
    }

    @Override
    public void glDeleteProgram(int program) {
        GL20.glDeleteProgram(program);
    }

    @Override
    public boolean glIsProgram(int program) {
        return GL20.glIsProgram(program);
    }

    @Override
    public int glCreateShader(int type) {
        return GL20.glCreateShader(type);
    }

    @Override
    public void glDeleteShader(int shader) {
        GL20.glDeleteShader(shader);
    }

    @Override
    public boolean glIsShader(int shader) {
        return GL20.glIsShader(shader);
    }

    @Override
    public void glAttachShader(int program, int shader) {
        GL20.glAttachShader(program, shader);
    }

    @Override
    public void glDetachShader(int program, int shader) {
        GL20.glDetachShader(program, shader);
    }

    @Override
    public void glShaderSource(int shader, final LongBuffer strings, final IntBuffer length) {

        PointerBuffer stringBuf = BufferUtils.createPointerBuffer(strings.capacity());
        stringBuf.put(strings);

        GL20.glShaderSource(shader, stringBuf, length);

        for (int i = 0; i < strings.capacity(); i++) {
            strings.put(i, stringBuf.get(i));
        }
        stringBuf.free();

    }

    @Override
    public void glShaderSource(int shader, final CharSequence... strings) {
        GL20.glShaderSource(shader, strings);
    }

    @Override
    public void glShaderSource(int shader, final CharSequence string) {
        GL20.glShaderSource(shader, string);
    }

    @Override
    public void glCompileShader(int shader) {
        GL20.glCompileShader(shader);
    }

    @Override
    public void glLinkProgram(int program) {
        GL20.glLinkProgram(program);
    }

    @Override
    public void glUseProgram(int program) {
        GL20.glUseProgram(program);
    }

    @Override
    public void glValidateProgram(int program) {
        GL20.glValidateProgram(program);
    }

    @Override
    public void glUniform1f(int location, float v0) {
        GL20.glUniform1f(location, v0);
    }

    @Override
    public void glUniform2f(int location, float v0, float v1) {
        GL20.glUniform2f(location, v0, v1);
    }

    @Override
    public void glUniform3f(int location, float v0, float v1, float v2) {
        GL20.glUniform3f(location, v0, v1, v2);
    }

    @Override
    public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        GL20.glUniform4f(location, v0, v1, v2, v3);
    }

    @Override
    public void glUniform1i(int location, int v0) {
        GL20.glUniform1i(location, v0);
    }

    @Override
    public void glUniform2i(int location, int v0, int v1) {
        GL20.glUniform2i(location, v0, v1);
    }

    @Override
    public void glUniform3i(int location, int v0, int v1, int v2) {
        GL20.glUniform3i(location, v0, v1, v2);
    }

    @Override
    public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        GL20.glUniform4i(location, v0, v1, v2, v3);
    }

    @Override
    public void glUniform1fv(int location, final FloatBuffer value) {
        GL20.glUniform1fv(location, value);
    }

    @Override
    public void glUniform2fv(int location, final FloatBuffer value) {
        GL20.glUniform2fv(location, value);
    }

    @Override
    public void glUniform3fv(int location, final FloatBuffer value) {
        GL20.glUniform3fv(location, value);
    }

    @Override
    public void glUniform4fv(int location, final FloatBuffer value) {
        GL20.glUniform4fv(location, value);
    }

    @Override
    public void glUniform1iv(int location, final IntBuffer value) {
        GL20.glUniform1iv(location, value);
    }

    @Override
    public void glUniform2iv(int location, final IntBuffer value) {
        GL20.glUniform2iv(location, value);
    }

    @Override
    public void glUniform3iv(int location, final IntBuffer value) {
        GL20.glUniform3iv(location, value);
    }

    @Override
    public void glUniform4iv(int location, final IntBuffer value) {
        GL20.glUniform4iv(location, value);
    }

    @Override
    public void glUniformMatrix2fv(int location, boolean transpose, final FloatBuffer value) {
        GL20.glUniformMatrix2fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3fv(int location, boolean transpose, final FloatBuffer value) {
        GL20.glUniformMatrix3fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4fv(int location, boolean transpose, final FloatBuffer value) {
        GL20.glUniformMatrix4fv(location, transpose, value);
    }

    @Override
    public void glGetShaderiv(int shader, int pname, final IntBuffer params) {
        GL20.glGetShaderiv(shader, pname, params);
    }

    @Override
    public int glGetShaderi(int shader, int pname) {
        return GL20.glGetShaderi(shader, pname);
    }

    @Override
    public void glGetProgramiv(int program, int pname, final IntBuffer params) {
        GL20.glGetProgramiv(program, pname, params);
    }

    @Override
    public int glGetProgrami(int program, int pname) {
        return GL20.glGetProgrami(program, pname);
    }

    @Override
    public void glGetShaderInfoLog(int shader, final IntBuffer length, final ByteBuffer infoLog) {
        GL20.glGetShaderInfoLog(shader, length, infoLog);
    }

    @Override
    public String glGetShaderInfoLog(int shader, int maxLength) {
        return GL20.glGetShaderInfoLog(shader, maxLength);
    }

    @Override
    public String glGetShaderInfoLog(int shader) {
        return GL20.glGetShaderInfoLog(shader);
    }

    @Override
    public void glGetProgramInfoLog(int program, final IntBuffer length, final ByteBuffer infoLog) {
        GL20.glGetProgramInfoLog(program, length, infoLog);
    }

    @Override
    public String glGetProgramInfoLog(int program, int maxLength) {
        return GL20.glGetProgramInfoLog(program, maxLength);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        return GL20.glGetProgramInfoLog(program);
    }

    @Override
    public void glGetAttachedShaders(int program, final IntBuffer count, final IntBuffer shaders) {
        GL20.glGetAttachedShaders(program, count, shaders);
    }

    @Override
    public int glGetUniformLocation(int program, final ByteBuffer name) {
        return GL20.glGetUniformLocation(program, name);
    }

    @Override
    public int glGetUniformLocation(int program, final CharSequence name) {
        return GL20.glGetUniformLocation(program, name);
    }

    @Override
    public void glGetActiveUniform(
        int program,
        int index,
        final IntBuffer length,
        final IntBuffer size,
        final IntBuffer type,
        final ByteBuffer name
    ) {
        GL20.glGetActiveUniform(program, index, length, size, type, name);
    }

    @Override
    public String glGetActiveUniform(
        int program,
        int index,
        int maxLength,
        final IntBuffer size,
        final IntBuffer type
    ) {
        return GL20.glGetActiveUniform(program, index, maxLength, size, type);
    }

    @Override
    public String glGetActiveUniform(
        int program,
        int index,
        final IntBuffer size,
        final IntBuffer type
    ) {
        return GL20.glGetActiveUniform(program, index, size, type);
    }

    @Override
    public void glGetUniformfv(int program, int location, final FloatBuffer params) {
        GL20.glGetUniformfv(program, location, params);
    }

    @Override
    public float glGetUniformf(int program, int location) {
        return GL20.glGetUniformf(program, location);
    }

    @Override
    public void glGetUniformiv(int program, int location, final IntBuffer params) {
        GL20.glGetUniformiv(program, location, params);
    }

    @Override
    public int glGetUniformi(int program, int location) {
        return GL20.glGetUniformi(program, location);
    }

    @Override
    public void glGetShaderSource(int shader, final IntBuffer length, final ByteBuffer source) {
        GL20.glGetShaderSource(shader, length, source);
    }

    @Override
    public String glGetShaderSource(int shader, int maxLength) {
        return GL20.glGetShaderSource(shader, maxLength);
    }

    @Override
    public String glGetShaderSource(int shader) {
        return GL20.glGetShaderSource(shader);
    }

    @Override
    public void glVertexAttrib1f(int index, float v0) {
        GL20.glVertexAttrib1f(index, v0);
    }

    @Override
    public void glVertexAttrib1s(int index, short v0) {
        GL20.glVertexAttrib1s(index, v0);
    }

    @Override
    public void glVertexAttrib1d(int index, double v0) {
        GL20.glVertexAttrib1d(index, v0);
    }

    @Override
    public void glVertexAttrib2f(int index, float v0, float v1) {
        GL20.glVertexAttrib2f(index, v0, v1);
    }

    @Override
    public void glVertexAttrib2s(int index, short v0, short v1) {
        GL20.glVertexAttrib2s(index, v0, v1);
    }

    @Override
    public void glVertexAttrib2d(int index, double v0, double v1) {
        GL20.glVertexAttrib2d(index, v0, v1);
    }

    @Override
    public void glVertexAttrib3f(int index, float v0, float v1, float v2) {
        GL20.glVertexAttrib3f(index, v0, v1, v2);
    }

    @Override
    public void glVertexAttrib3s(int index, short v0, short v1, short v2) {
        GL20.glVertexAttrib3s(index, v0, v1, v2);
    }

    @Override
    public void glVertexAttrib3d(int index, double v0, double v1, double v2) {
        GL20.glVertexAttrib3d(index, v0, v1, v2);
    }

    @Override
    public void glVertexAttrib4f(int index, float v0, float v1, float v2, float v3) {
        GL20.glVertexAttrib4f(index, v0, v1, v2, v3);
    }

    @Override
    public void glVertexAttrib4s(int index, short v0, short v1, short v2, short v3) {
        GL20.glVertexAttrib4s(index, v0, v1, v2, v3);
    }

    @Override
    public void glVertexAttrib4d(int index, double v0, double v1, double v2, double v3) {
        GL20.glVertexAttrib4d(index, v0, v1, v2, v3);
    }

    @Override
    public void glVertexAttrib4Nub(int index, byte x, byte y, byte z, byte w) {
        GL20.glVertexAttrib4Nub(index, x, y, z, w);
    }

    @Override
    public void glVertexAttrib1fv(int index, final FloatBuffer v) {
        GL20.glVertexAttrib1fv(index, v);
    }

    @Override
    public void glVertexAttrib1sv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib1sv(index, v);
    }

    @Override
    public void glVertexAttrib1dv(int index, final DoubleBuffer v) {
        GL20.glVertexAttrib1dv(index, v);
    }

    @Override
    public void glVertexAttrib2fv(int index, final FloatBuffer v) {
        GL20.glVertexAttrib2fv(index, v);
    }

    @Override
    public void glVertexAttrib2sv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib2sv(index, v);
    }

    @Override
    public void glVertexAttrib2dv(int index, final DoubleBuffer v) {
        GL20.glVertexAttrib2dv(index, v);
    }

    @Override
    public void glVertexAttrib3fv(int index, final FloatBuffer v) {
        GL20.glVertexAttrib3fv(index, v);
    }

    @Override
    public void glVertexAttrib3sv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib3sv(index, v);
    }

    @Override
    public void glVertexAttrib3dv(int index, final DoubleBuffer v) {
        GL20.glVertexAttrib3dv(index, v);
    }

    @Override
    public void glVertexAttrib4fv(int index, final FloatBuffer v) {
        GL20.glVertexAttrib4fv(index, v);
    }

    @Override
    public void glVertexAttrib4sv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib4sv(index, v);
    }

    @Override
    public void glVertexAttrib4dv(int index, final DoubleBuffer v) {
        GL20.glVertexAttrib4dv(index, v);
    }

    @Override
    public void glVertexAttrib4iv(int index, final IntBuffer v) {
        GL20.glVertexAttrib4iv(index, v);
    }

    @Override
    public void glVertexAttrib4bv(int index, final ByteBuffer v) {
        GL20.glVertexAttrib4bv(index, v);
    }

    @Override
    public void glVertexAttrib4ubv(int index, final ByteBuffer v) {
        GL20.glVertexAttrib4ubv(index, v);
    }

    @Override
    public void glVertexAttrib4usv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib4usv(index, v);
    }

    @Override
    public void glVertexAttrib4uiv(int index, final IntBuffer v) {
        GL20.glVertexAttrib4uiv(index, v);
    }

    @Override
    public void glVertexAttrib4Nbv(int index, final ByteBuffer v) {
        GL20.glVertexAttrib4Nbv(index, v);
    }

    @Override
    public void glVertexAttrib4Nsv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib4Nsv(index, v);
    }

    @Override
    public void glVertexAttrib4Niv(int index, final IntBuffer v) {
        GL20.glVertexAttrib4Niv(index, v);
    }

    @Override
    public void glVertexAttrib4Nubv(int index, final ByteBuffer v) {
        GL20.glVertexAttrib4Nubv(index, v);
    }

    @Override
    public void glVertexAttrib4Nusv(int index, final ShortBuffer v) {
        GL20.glVertexAttrib4Nusv(index, v);
    }

    @Override
    public void glVertexAttrib4Nuiv(int index, final IntBuffer v) {
        GL20.glVertexAttrib4Nuiv(index, v);
    }

    @Override
    public void glVertexAttribPointer(
        int index,
        int size,
        int type,
        boolean normalized,
        int stride,
        final ByteBuffer pointer
    ) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    @Override
    public void glVertexAttribPointer(
        int index,
        int size,
        int type,
        boolean normalized,
        int stride,
        long pointer
    ) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    @Override
    public void glVertexAttribPointer(
        int index,
        int size,
        int type,
        boolean normalized,
        int stride,
        final ShortBuffer pointer
    ) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    @Override
    public void glVertexAttribPointer(
        int index,
        int size,
        int type,
        boolean normalized,
        int stride,
        final IntBuffer pointer
    ) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    @Override
    public void glVertexAttribPointer(
        int index,
        int size,
        int type,
        boolean normalized,
        int stride,
        final FloatBuffer pointer
    ) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        GL20.glEnableVertexAttribArray(index);
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        GL20.glDisableVertexAttribArray(index);
    }

    @Override
    public void glBindAttribLocation(int program, int index, final ByteBuffer name) {
        GL20.glBindAttribLocation(program, index, name);
    }

    @Override
    public void glBindAttribLocation(int program, int index, final CharSequence name) {
        GL20.glBindAttribLocation(program, index, name);
    }

    @Override
    public void glGetActiveAttrib(
        int program,
        int index,
        final IntBuffer length,
        final IntBuffer size,
        final IntBuffer type,
        final ByteBuffer name
    ) {
        GL20.glGetActiveAttrib(program, index, length, size, type, name);
    }

    @Override
    public String glGetActiveAttrib(
        int program,
        int index,
        int maxLength,
        final IntBuffer size,
        final IntBuffer type
    ) {
        return GL20.glGetActiveAttrib(program, index, maxLength, size, type);
    }

    @Override
    public String glGetActiveAttrib(
        int program,
        int index,
        final IntBuffer size,
        final IntBuffer type
    ) {
        return GL20.glGetActiveAttrib(program, index, size, type);
    }

    @Override
    public int glGetAttribLocation(int program, final ByteBuffer name) {
        return GL20.glGetAttribLocation(program, name);
    }

    @Override
    public int glGetAttribLocation(int program, final CharSequence name) {
        return GL20.glGetAttribLocation(program, name);
    }

    @Override
    public void glGetVertexAttribiv(int index, int pname, final IntBuffer params) {
        GL20.glGetVertexAttribiv(index, pname, params);
    }

    @Override
    public int glGetVertexAttribi(int index, int pname) {
        return GL20.glGetVertexAttribi(index, pname);
    }

    @Override
    public void glGetVertexAttribfv(int index, int pname, final FloatBuffer params) {
        GL20.glGetVertexAttribfv(index, pname, params);
    }

    @Override
    public void glGetVertexAttribdv(int index, int pname, final DoubleBuffer params) {
        GL20.glGetVertexAttribdv(index, pname, params);
    }

    @Override
    public void glGetVertexAttribPointerv(int index, int pname, final LongBuffer pointer) {

        PointerBuffer pointerBuf = BufferUtils.createPointerBuffer(pointer.capacity());
        pointerBuf.put(pointer);

        GL20.glGetVertexAttribPointerv(index, pname, pointerBuf);

        for (int i = 0; i < pointer.capacity(); i++) {
            pointer.put(i, pointerBuf.get(i));
        }
        pointerBuf.free();

    }

    @Override
    public long glGetVertexAttribPointer(int index, int pname) {
        return GL20.glGetVertexAttribPointer(index, pname);
    }

    @Override
    public void glDrawBuffers(final IntBuffer bufs) {
        GL20.glDrawBuffers(bufs);
    }

    @Override
    public void glDrawBuffers(int buf) {
        GL20.glDrawBuffers(buf);
    }

    @Override
    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
    }

    @Override
    public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
        GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
    }

    @Override
    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        GL20.glStencilFuncSeparate(face, func, ref, mask);
    }

    @Override
    public void glStencilMaskSeparate(int face, int mask) {
        GL20.glStencilMaskSeparate(face, mask);
    }

    @Override
    public void glShaderSource(int shader, final LongBuffer strings, final int[] length) {
        PointerBuffer stringBuf = BufferUtils.createPointerBuffer(strings.capacity());
        stringBuf.put(strings);

        GL20.glShaderSource(shader, stringBuf, length);

        for (int i = 0; i < strings.capacity(); i++) {
            strings.put(i, stringBuf.get(i));
        }
        stringBuf.free();
    }

    @Override
    public void glUniform1fv(int location, final float[] value) {
        GL20.glUniform1fv(location, value);
    }

    @Override
    public void glUniform2fv(int location, final float[] value) {
        GL20.glUniform2fv(location, value);
    }

    @Override
    public void glUniform3fv(int location, final float[] value) {
        GL20.glUniform3fv(location, value);
    }

    @Override
    public void glUniform4fv(int location, final float[] value) {
        GL20.glUniform4fv(location, value);
    }

    @Override
    public void glUniform1iv(int location, final int[] value) {
        GL20.glUniform1iv(location, value);
    }

    @Override
    public void glUniform2iv(int location, final int[] value) {
        GL20.glUniform2iv(location, value);
    }

    @Override
    public void glUniform3iv(int location, final int[] value) {
        GL20.glUniform3iv(location, value);
    }

    @Override
    public void glUniform4iv(int location, final int[] value) {
        GL20.glUniform4iv(location, value);
    }

    @Override
    public void glUniformMatrix2fv(int location, boolean transpose, final float[] value) {
        GL20.glUniformMatrix2fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3fv(int location, boolean transpose, final float[] value) {
        GL20.glUniformMatrix3fv(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4fv(int location, boolean transpose, final float[] value) {
        GL20.glUniformMatrix4fv(location, transpose, value);
    }

    @Override
    public void glGetShaderiv(int shader, int pname, final int[] params) {
        GL20.glGetShaderiv(shader, pname, params);
    }

    @Override
    public void glGetProgramiv(int program, int pname, final int[] params) {
        GL20.glGetProgramiv(program, pname, params);
    }

    @Override
    public void glGetShaderInfoLog(int shader, final int[] length, final ByteBuffer infoLog) {
        GL20.glGetShaderInfoLog(shader, length, infoLog);
    }

    @Override
    public void glGetProgramInfoLog(int program, final int[] length, final ByteBuffer infoLog) {
        GL20.glGetProgramInfoLog(program, length, infoLog);
    }

    @Override
    public void glGetAttachedShaders(int program, final int[] count, final int[] shaders) {
        GL20.glGetAttachedShaders(program, count, shaders);
    }

    @Override
    public void glGetActiveUniform(
        int program,
        int index,
        final int[] length,
        final int[] size,
        final int[] type,
        final ByteBuffer name
    ) {
        GL20.glGetActiveUniform(program, index, length, size, type, name);
    }

    @Override
    public void glGetUniformfv(int program, int location, final float[] params) {
        GL20.glGetUniformfv(program, location, params);
    }

    @Override
    public void glGetUniformiv(int program, int location, final int[] params) {
        GL20.glGetUniformiv(program, location, params);
    }

    @Override
    public void glGetShaderSource(int shader, final int[] length, final ByteBuffer source) {
        GL20.glGetShaderSource(shader, length, source);
    }

    @Override
    public void glVertexAttrib1fv(int index, final float[] v) {
        GL20.glVertexAttrib1fv(index, v);
    }

    @Override
    public void glVertexAttrib1sv(int index, final short[] v) {
        GL20.glVertexAttrib1sv(index, v);
    }

    @Override
    public void glVertexAttrib1dv(int index, final double[] v) {
        GL20.glVertexAttrib1dv(index, v);
    }

    @Override
    public void glVertexAttrib2fv(int index, final float[] v) {
        GL20.glVertexAttrib2fv(index, v);
    }

    @Override
    public void glVertexAttrib2sv(int index, final short[] v) {
        GL20.glVertexAttrib2sv(index, v);
    }

    @Override
    public void glVertexAttrib2dv(int index, final double[] v) {
        GL20.glVertexAttrib2dv(index, v);
    }

    @Override
    public void glVertexAttrib3fv(int index, final float[] v) {
        GL20.glVertexAttrib3fv(index, v);
    }

    @Override
    public void glVertexAttrib3sv(int index, final short[] v) {
        GL20.glVertexAttrib3sv(index, v);
    }

    @Override
    public void glVertexAttrib3dv(int index, final double[] v) {
        GL20.glVertexAttrib3dv(index, v);
    }

    @Override
    public void glVertexAttrib4fv(int index, final float[] v) {
        GL20.glVertexAttrib4fv(index, v);
    }

    @Override
    public void glVertexAttrib4sv(int index, final short[] v) {
        GL20.glVertexAttrib4sv(index, v);
    }

    @Override
    public void glVertexAttrib4dv(int index, final double[] v) {
        GL20.glVertexAttrib4dv(index, v);
    }

    @Override
    public void glVertexAttrib4iv(int index, final int[] v) {
        GL20.glVertexAttrib4iv(index, v);
    }

    @Override
    public void glVertexAttrib4usv(int index, final short[] v) {
        GL20.glVertexAttrib4usv(index, v);
    }

    @Override
    public void glVertexAttrib4uiv(int index, final int[] v) {
        GL20.glVertexAttrib4uiv(index, v);
    }

    @Override
    public void glVertexAttrib4Nsv(int index, final short[] v) {
        GL20.glVertexAttrib4Nsv(index, v);
    }

    @Override
    public void glVertexAttrib4Niv(int index, final int[] v) {
        GL20.glVertexAttrib4Niv(index, v);
    }

    @Override
    public void glVertexAttrib4Nusv(int index, final short[] v) {
        GL20.glVertexAttrib4Nusv(index, v);
    }

    @Override
    public void glVertexAttrib4Nuiv(int index, final int[] v) {
        GL20.glVertexAttrib4Nuiv(index, v);
    }

    @Override
    public void glGetActiveAttrib(
        int program,
        int index,
        final int[] length,
        final int[] size,
        final int[] type,
        final ByteBuffer name
    ) {
        GL20.glGetActiveAttrib(program, index, length, size, type, name);
    }

    @Override
    public void glGetVertexAttribiv(int index, int pname, final int[] params) {
        GL20.glGetVertexAttribiv(index, pname, params);
    }

    @Override
    public void glGetVertexAttribfv(int index, int pname, final float[] params) {
        GL20.glGetVertexAttribfv(index, pname, params);
    }

    @Override
    public void glGetVertexAttribdv(int index, int pname, final double[] params) {
        GL20.glGetVertexAttribdv(index, pname, params);
    }

    @Override
    public void glDrawBuffers(final int[] bufs) {
        GL20.glDrawBuffers(bufs);
    }


}
