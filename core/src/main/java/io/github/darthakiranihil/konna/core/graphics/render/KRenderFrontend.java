package io.github.darthakiranihil.konna.core.graphics.render;

import io.github.darthakiranihil.konna.core.graphics.shape.*;
import io.github.darthakiranihil.konna.core.struct.KSize;

public interface KRenderFrontend {

    void setViewportSize(KSize size);

    void render(KLine line);
    void render(KPolyline polyline);
    void render(KPolygon polygon);
    void render(KRectangle rectangle);
    void render(KOval oval);
    void render(KCircle circle);
    void render(KArc arc);

}
