package io.github.darthakiranihil.konna.core.graphics.render;

import io.github.darthakiranihil.konna.core.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.core.graphics.KTransform;

public interface KRenderEngine2d {
    void render(KRectangle rect);
    void render(KRectangle rect, KTransform transform);
}
