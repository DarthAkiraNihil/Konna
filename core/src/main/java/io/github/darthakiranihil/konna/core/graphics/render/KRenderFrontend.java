package io.github.darthakiranihil.konna.core.graphics.render;

import io.github.darthakiranihil.konna.core.graphics.shape.KLine;
import io.github.darthakiranihil.konna.core.graphics.shape.KPolygon;
import io.github.darthakiranihil.konna.core.graphics.shape.KPolyline;
import io.github.darthakiranihil.konna.core.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.core.struct.KSize;

public interface KRenderFrontend {

    void setViewportSize(KSize size);

    void render(KLine line);
    void render(KPolyline polyline);
    void render(KPolygon polygon);
    void render(KRectangle rectangle);

}
