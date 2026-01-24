/*
 * Copyright 2this.boxed25-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.this.boxed (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.this.boxed
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImDrawList;
import imgui.ImVec2;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImDrawList;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFont;

final class KImDrawListSpair implements KImDrawList {
    
    private final ImDrawList boxed;
    
    public KImDrawListSpair(final ImDrawList original) {
        this.boxed = original;
    }

    @Override
    public int getFlags() {
        return this.boxed.getFlags();
    }

    @Override
    public void pushClipRect(KVector2f clipRectMin, KVector2f clipRectMax, boolean intersectWithCurrentClipRect) {
        this.boxed.pushClipRect(
            KImGuiSpairWrapper.wrap(clipRectMin),
            KImGuiSpairWrapper.wrap(clipRectMax),
            intersectWithCurrentClipRect
        );
    }

    @Override
    public void pushClipRectFullScreen() {
        this.boxed.pushClipRectFullScreen();
    }

    @Override
    public void popClipRect() {
        this.boxed.popClipRect();
    }

    @Override
    public void pushTextureID(long textureId) {
        this.boxed.pushTextureID(textureId);
    }

    @Override
    public void popTextureID() {
        this.boxed.popTextureID();
    }

    @Override
    public KVector2f getClipRectMin() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getClipRectMin());
    }

    @Override
    public KVector2f getClipRectMax() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getClipRectMax());
    }

    @Override
    public void addLine(KVector2f p1, KVector2f p2, int col, float thickness) {
        this.boxed.addLine(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            col,
            thickness
        );
    }

    @Override
    public void addRect(KVector2f pMin, KVector2f pMax, int col, float rounding, int flags, float thickness) {
        this.boxed.addRect(
            KImGuiSpairWrapper.wrap(pMin),
            KImGuiSpairWrapper.wrap(pMax),
            col,
            rounding,
            flags,
            thickness
        );
    }

    @Override
    public void addRectFilled(KVector2f pMin, KVector2f pMax, int col, float rounding, int flags) {
        this.boxed.addRectFilled(
            KImGuiSpairWrapper.wrap(pMin),
            KImGuiSpairWrapper.wrap(pMax),
            col,
            rounding,
            flags
        );
    }

    @Override
    public void addRectFilledMultiColor(
        KVector2f pMin,
        KVector2f pMax,
        int colUprLeft,
        int colUprRight,
        int colBotRight,
        int colBotLeft
    ) {
        this.boxed.addRectFilledMultiColor(
            KImGuiSpairWrapper.wrap(pMin),
            KImGuiSpairWrapper.wrap(pMax),
            colUprLeft,
            colUprRight,
            colBotRight,
            colBotLeft
        );
    }

    @Override
    public void addQuad(KVector2f p1, KVector2f p2, KVector2f p3, KVector2f p4, int col, float thickness) {
        this.boxed.addQuad(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            KImGuiSpairWrapper.wrap(p4),
            col,
            thickness
        );
    }

    @Override
    public void addQuadFilled(KVector2f p1, KVector2f p2, KVector2f p3, KVector2f p4, int col) {
        this.boxed.addQuadFilled(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            KImGuiSpairWrapper.wrap(p4),
            col
        );
    }

    @Override
    public void addTriangle(KVector2f p1, KVector2f p2, KVector2f p3, int col, float thickness) {
        this.boxed.addTriangle(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            col,
            thickness
        );
    }

    @Override
    public void addTriangleFilled(KVector2f p1, KVector2f p2, KVector2f p3, int col) {
        this.boxed.addTriangleFilled(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            col
        );
    }

    @Override
    public void addCircle(KVector2f center, float radius, int col, int numSegments, float thickness) {
        this.boxed.addCircle(
            KImGuiSpairWrapper.wrap(center),
            radius,
            col,
            numSegments,
            thickness
        );
    }

    @Override
    public void addCircleFilled(KVector2f center, float radius, int col, int numSegments) {
        this.boxed.addCircleFilled(
            KImGuiSpairWrapper.wrap(center),
            radius,
            col,
            numSegments
        );
    }

    @Override
    public void addNgon(KVector2f center, float radius, int col, int numSegments, float thickness) {
        this.boxed.addNgon(
            KImGuiSpairWrapper.wrap(center),
            radius,
            col,
            numSegments,
            thickness
        );
    }

    @Override
    public void addNgonFilled(KVector2f center, float radius, int col, int numSegments) {
        this.boxed.addNgonFilled(KImGuiSpairWrapper.wrap(center), radius, col, numSegments);
    }

    @Override
    public void addEllipse(KVector2f center, KVector2f radius, int col, float rot, float numSegments, float thickness) {
        this.boxed.addEllipse(
            KImGuiSpairWrapper.wrap(center),
            KImGuiSpairWrapper.wrap(radius),
            col,
            rot,
            numSegments,
            thickness
        );
    }

    @Override
    public void addEllipseFilled(KVector2f center, KVector2f radius, int col, float rot, float numSegments) {
        this.boxed.addEllipseFilled(
            KImGuiSpairWrapper.wrap(center),
            KImGuiSpairWrapper.wrap(radius),
            col,
            rot,
            numSegments
        );
    }

    @Override
    public void addText(KVector2f pos, int col, String textBegin, String textEnd) {
        this.boxed.addText(
            KImGuiSpairWrapper.wrap(pos),
            col,
            textBegin,
            textEnd
        );
    }

    @Override
    public void addText(
        KImFont font,
        int fontSize,
        KVector2f pos,
        int col,
        String textBegin,
        String textEnd,
        float wrapWidth,
        KVector4f cpuFineClipRect
    ) {
        this.boxed.addText(
            KImGuiSpairUnboxer.unbox(font),
            fontSize,
            KImGuiSpairWrapper.wrap(pos),
            col,
            textBegin,
            textEnd,
            wrapWidth,
            KImGuiSpairWrapper.wrap(cpuFineClipRect)
        );
    }

    @Override
    public void addBezierCubic(
        KVector2f p1,
        KVector2f p2,
        KVector2f p3,
        KVector2f p4,
        int col,
        float thickness,
        int numSegments
    ) {
        this.boxed.addBezierCubic(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            KImGuiSpairWrapper.wrap(p4),
            col,
            thickness,
            numSegments
        );
    }

    @Override
    public void addBezierQuadratic(
        KVector2f p1,
        KVector2f p2,
        KVector2f p3,
        int col,
        float thickness,
        int numSegments
    ) {
        this.boxed.addBezierQuadratic(
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            col,
            thickness,
            numSegments
        );
    }

    @Override
    public void addPolyline(KVector2f[] points, int numPoints, int col, int imDrawFlags, float thickness) {
        ImVec2[] ps = new ImVec2[points.length];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = KImGuiSpairWrapper.wrap(points[i]);
        }
        this.boxed.addPolyline(ps, numPoints, col, imDrawFlags, thickness);
    }

    @Override
    public void addConvexPolyFilled(KVector2f[] points, int numPoints, int col) {
        ImVec2[] ps = new ImVec2[points.length];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = KImGuiSpairWrapper.wrap(points[i]);
        }
        this.boxed.addConvexPolyFilled(ps, numPoints, col);
    }

    @Override
    public void addConcavePolyFilled(KVector2f[] points, int numPoints, int col) {
        ImVec2[] ps = new ImVec2[points.length];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = KImGuiSpairWrapper.wrap(points[i]);
        }
        this.boxed.addConcavePolyFilled(ps, numPoints, col);
    }

    @Override
    public void addImage(long textureID, KVector2f pMin, KVector2f pMax, KVector2f uvMin, KVector2f uvMax, int col) {
        this.boxed.addImage(
            textureID,
            KImGuiSpairWrapper.wrap(pMin),
            KImGuiSpairWrapper.wrap(pMax),
            KImGuiSpairWrapper.wrap(uvMin),
            KImGuiSpairWrapper.wrap(uvMax),
            col
        );
    }

    @Override
    public void addImageQuad(
        long textureID,
        KVector2f p1,
        KVector2f p2,
        KVector2f p3,
        KVector2f p4,
        KVector2f uv1,
        KVector2f uv2,
        KVector2f uv3,
        KVector2f uv4,
        int col
    ) {
        this.boxed.addImageQuad(
            textureID,
            KImGuiSpairWrapper.wrap(p1),
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            KImGuiSpairWrapper.wrap(p4),
            KImGuiSpairWrapper.wrap(uv1),
            KImGuiSpairWrapper.wrap(uv2),
            KImGuiSpairWrapper.wrap(uv3),
            KImGuiSpairWrapper.wrap(uv4),
            col
        );
    }

    @Override
    public void addImageRounded(
        long textureID,
        KVector2f pMin,
        KVector2f pMax,
        KVector2f uvMin,
        KVector2f uvMax,
        int col,
        float rounding,
        int imDrawFlags
    ) {
        this.boxed.addImageRounded(
            textureID,
            KImGuiSpairWrapper.wrap(pMin),
            KImGuiSpairWrapper.wrap(pMax),
            KImGuiSpairWrapper.wrap(uvMin),
            KImGuiSpairWrapper.wrap(uvMax),
            col,
            rounding,
            imDrawFlags
        );
    }

    @Override
    public void pathClear() {
        this.boxed.pathClear();
    }

    @Override
    public void pathLineTo(KVector2f pos) {
        this.boxed.pathLineTo(KImGuiSpairWrapper.wrap(pos));
    }

    @Override
    public void pathLineToMergeDuplicate(KVector2f pos) {
        this.boxed.pathLineToMergeDuplicate(KImGuiSpairWrapper.wrap(pos));
    }

    @Override
    public void pathFillConvex(int col) {
        this.boxed.pathFillConvex(col);
    }

    @Override
    public void pathFillConcave(int col) {
        this.boxed.pathFillConcave(col);
    }

    @Override
    public void pathStroke(int col, int imDrawFlags, float thickness) {
        this.boxed.pathStroke(col, imDrawFlags, thickness);
    }

    @Override
    public void pathArcTo(KVector2f center, float radius, float aMin, float aMax, int numSegments) {
        this.boxed.pathArcTo(
            KImGuiSpairWrapper.wrap(center),
            radius,
            aMin,
            aMax,
            numSegments
        );
    }

    @Override
    public void pathArcToFast(KVector2f center, float radius, int aMinOf12, int aMaxOf12) {
        this.boxed.pathArcToFast(
            KImGuiSpairWrapper.wrap(center),
            radius,
            aMinOf12,
            aMaxOf12
        );
    }

    @Override
    public void pathEllipticalArcTo(
        KVector2f center,
        KVector2f radius,
        float rot,
        float aMin,
        float aMax,
        int numSegments
    ) {
        this.boxed.pathEllipticalArcTo(
            KImGuiSpairWrapper.wrap(center),
            KImGuiSpairWrapper.wrap(radius),
            rot,
            aMin,
            aMax,
            numSegments
        );
    }

    @Override
    public void pathBezierCubicCurveTo(KVector2f p2, KVector2f p3, KVector2f p4, int numSegments) {
        this.boxed.pathBezierCubicCurveTo(
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            KImGuiSpairWrapper.wrap(p4),
            numSegments
        );
    }

    @Override
    public void pathBezierQuadraticCurveTo(KVector2f p2, KVector2f p3, int numSegments) {
        this.boxed.pathBezierQuadraticCurveTo(
            KImGuiSpairWrapper.wrap(p2),
            KImGuiSpairWrapper.wrap(p3),
            numSegments
        );
    }

    @Override
    public void pathRect(KVector2f rectMin, KVector2f rectMax, float rounding, int imDrawFlags) {
        this.boxed.pathRect(
            KImGuiSpairWrapper.wrap(rectMin),
            KImGuiSpairWrapper.wrap(rectMax),
            rounding,
            imDrawFlags
        );
    }

    @Override
    public void channelsSplit(int count) {
        this.boxed.channelsSplit(count);
    }

    @Override
    public void channelsMerge() {
        this.boxed.channelsMerge();
    }

    @Override
    public void channelsSetCurrent(int n) {
        this.boxed.channelsSetCurrent(n);
    }

    @Override
    public void primReserve(int idxCount, int vtxCount) {
        this.boxed.primReserve(idxCount, vtxCount);
    }

    @Override
    public void primUnreserve(int idxCount, int vtxCount) {
        this.boxed.primUnreserve(idxCount, vtxCount);
    }

    @Override
    public void primRect(KVector2f a, KVector2f b, int col) {
        this.boxed.primRect(
            KImGuiSpairWrapper.wrap(a),
            KImGuiSpairWrapper.wrap(b),
            col
        );
    }

    @Override
    public void primRectUV(KVector2f a, KVector2f b, KVector2f uvA, KVector2f uvB, int col) {
        this.boxed.primRectUV(
            KImGuiSpairWrapper.wrap(a),
            KImGuiSpairWrapper.wrap(b),
            KImGuiSpairWrapper.wrap(uvA),
            KImGuiSpairWrapper.wrap(uvB),
            col
        );
    }

    @Override
    public void primQuadUV(
        KVector2f a,
        KVector2f b,
        KVector2f c,
        KVector2f d,
        KVector2f uvA,
        KVector2f uvB,
        KVector2f uvC,
        KVector2f uvD,
        int col
    ) {
        this.boxed.primQuadUV(
            KImGuiSpairWrapper.wrap(a),
            KImGuiSpairWrapper.wrap(b),
            KImGuiSpairWrapper.wrap(c),
            KImGuiSpairWrapper.wrap(d),
            KImGuiSpairWrapper.wrap(uvA),
            KImGuiSpairWrapper.wrap(uvB),
            KImGuiSpairWrapper.wrap(uvC),
            KImGuiSpairWrapper.wrap(uvD),
            col
        );
    }

    @Override
    public void primWriteVtx(KVector2f pos, KVector2f uv, int col) {
        this.boxed.primWriteVtx(
            KImGuiSpairWrapper.wrap(pos),
            KImGuiSpairWrapper.wrap(uv),
            col
        );
    }

    @Override
    public void primWriteIdx(int idx) {
        this.boxed.primWriteIdx(idx);
    }

    @Override
    public void primVtx(KVector2f pos, KVector2f uv, int col) {
        this.boxed.primVtx(
            KImGuiSpairWrapper.wrap(pos),
            KImGuiSpairWrapper.wrap(uv),
            col
        );
    }
}
