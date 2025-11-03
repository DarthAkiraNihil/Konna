package io.github.darthakiranihil.konna.core.graphics;

import java.awt.*;

public interface K2dRenderEngine {
    void render(KRectangle rect);
    void render(KRectangle rect, KTransform transform);
}
