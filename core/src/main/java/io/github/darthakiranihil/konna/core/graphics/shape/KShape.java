package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.graphics.KPositioned;
import io.github.darthakiranihil.konna.core.graphics.KRenderable;
import io.github.darthakiranihil.konna.core.graphics.KTransform;
import io.github.darthakiranihil.konna.core.graphics.KTransformable;

public interface KShape extends
    KRenderable,
    KTransformable {
    KTransform getTransform();
}
