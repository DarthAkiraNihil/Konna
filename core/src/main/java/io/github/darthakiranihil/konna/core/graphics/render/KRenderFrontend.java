package io.github.darthakiranihil.konna.core.graphics.render;

import io.github.darthakiranihil.konna.core.graphics.shape.KPolygon;
import io.github.darthakiranihil.konna.core.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.core.struct.KSize;

public interface KRenderFrontend {

    void setViewportSize(KSize size);
    void render(KPolygon polygon);
    void render(KRectangle rectangle);

}
