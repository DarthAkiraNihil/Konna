package io.github.darthakiranihil.konna.core.graphics.render;

import io.github.darthakiranihil.konna.core.graphics.image.KTexture;
import io.github.darthakiranihil.konna.core.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.core.graphics.shape.*;
import io.github.darthakiranihil.konna.core.struct.KSize;

public interface KRenderFrontend {

    void setViewportSize(KSize size);
    void setActiveShader(KShaderProgram shader);
    void disableActiveShader();


    void render(KLine line);
    void render(KPolyline polyline);
    void render(KPolygon polygon);
    void render(KRectangle rectangle);
    void render(KOval oval);
    void render(KCircle circle);
    void render(KArc arc);
    void render(KTexture texture);

}
