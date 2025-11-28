package io.github.darthakiranihil.konna.core.graphics;

import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public interface KTransformable {

    void rotate(double theta);
    void rotate(double theta, KVector2i pivot);

    void scale(KVector2d factor);

    KVector2d getScaling();
    void setScaling(KVector2d scale);

    void translate(KVector2i value);

}
