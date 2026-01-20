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

package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;

public interface KImDrawList {
    
    int getFlags();
    
    void pushClipRect(KVector2f clipRectMin, KVector2f clipRectMax, boolean intersectWithCurrentClipRect);
    void pushClipRectFullScreen();
    void popClipRect();
    void pushTextureID(long textureId);
    void popTextureID();
    KVector2f getClipRectMin();
    KVector2f getClipRectMax();
        
    void addLine(KVector2f p1, KVector2f p2, int col, float thickness);
    void addRect(KVector2f pMin, KVector2f pMax, int col, float rounding, int flags, float thickness);
    void addRectFilled(KVector2f pMin, KVector2f pMax, int col, float rounding, int flags);
    void addRectFilledMultiColor(KVector2f pMin, KVector2f pMax, int colUprLeft, int colUprRight, int colBotRight, int colBotLeft);
    void addQuad(KVector2f p1, KVector2f p2, KVector2f p3, KVector2f p4, int col, float thickness);
    void addQuadFilled(KVector2f p1, KVector2f p2, KVector2f p3, KVector2f p4, int col);
    void addTriangle(KVector2f p1, KVector2f p2, KVector2f p3, int col, float thickness);
    void addTriangleFilled(KVector2f p1, KVector2f p2, KVector2f p3, int col);
    void addCircle(KVector2f center, float radius, int col, int numSegments, float thickness);
    void addCircleFilled(KVector2f center, float radius, int col, int numSegments);
    void addNgon(KVector2f center, float radius, int col, int num_segments, float thickness);
    void addNgonFilled(KVector2f center, float radius, int col, int num_segments);
    void addEllipse(KVector2f center, KVector2f radius, int col, float rot, float numSegments, float thickness);
    void addEllipseFilled(KVector2f center, KVector2f radius, int col, float rot, float numSegments);
    void addText(KVector2f pos, int col, String textBegin, String textEnd);
    void addText(ImFont font, int fontSize, KVector2f pos, int col, String textBegin, String textEnd, float wrapWidth, KVector4f cpuFineClipRect);
    void addBezierCubic(KVector2f p1, KVector2f p2, KVector2f p3, KVector2f p4, int col, float thickness, int numSegments);
    void addBezierQuadratic(KVector2f p1, KVector2f p2, KVector2f p3, int col, float thickness, int numSegments);
    void addPolyline(KVector2f[] points, int numPoints, int col, int imDrawFlags, float thickness);
    void addConvexPolyFilled(KVector2f[] points, int numPoints, int col);
    void addConcavePolyFilled(KVector2f[] points, int numPoints, int col);
    void addImage(long textureID, KVector2f pMin, KVector2f pMax, KVector2f uvMin, KVector2f uvMax, int col);
    void addImageQuad(long textureID, KVector2f p1, KVector2f p2, KVector2f p3, KVector2f p4, KVector2f uv1, KVector2f uv2, KVector2f uv3, KVector2f uv4, int col);
    void addImageRounded(long textureID, KVector2f pMin, KVector2f pMax, KVector2f uvMin, KVector2f uvMax, int col, float rounding, int imDrawFlags);


    void pathClear();
    void pathLineTo(KVector2f pos);
    void pathLineToMergeDuplicate(KVector2f pos);
    void pathFillConvex(int col);
    void pathFillConcave(int col);
    void pathStroke(int col, int imDrawFlags, float thickness);
    void pathArcTo(KVector2f center, float radius, float aMin, float aMax, int numSegments);
    void pathArcToFast(KVector2f center, float radius, int aMinOf12, int aMaxOf12);
    void pathEllipticalArcTo(KVector2f center, KVector2f radius, float rot, float aMin, float aMax, int numSegments);
    void pathBezierCubicCurveTo(KVector2f p2, KVector2f p3, KVector2f p4, int numSegments);
    void pathBezierQuadraticCurveTo(KVector2f p2, KVector2f p3, int numSegments);
    void pathRect(KVector2f rectMin, KVector2f rectMax, float rounding, int imDrawFlags);

    void channelsSplit(int count);
    void channelsMerge();
    void channelsSetCurrent(int n);

    void primReserve(int idxCount, int vtxCount);
    void primUnreserve(int idxCount, int vtxCount);
    void primRect(KVector2f a, KVector2f b, int col);
    void primRectUV(KVector2f a, KVector2f b, KVector2f uvA, KVector2f uvB, int col);
    void primQuadUV(KVector2f a, KVector2f b, KVector2f c, KVector2f d, KVector2f uvA, KVector2f uvB, KVector2f uvC, KVector2f uvD, int col);
    void primWriteVtx(KVector2f pos, KVector2f uv, int col);
    void primWriteIdx(int idx);
    void primVtx(KVector2f pos, KVector2f uv, int col);

}
