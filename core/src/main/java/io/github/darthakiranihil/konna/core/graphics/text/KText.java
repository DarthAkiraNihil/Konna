package io.github.darthakiranihil.konna.core.graphics.text;

import io.github.darthakiranihil.konna.core.struct.KVector2i;

public record KText(
    int x,
    int y,
    String text,
    KFont font
) {

    public KText(final KVector2i coordinates, final String text, final KFont font) {
        this(coordinates.x(), coordinates.y(), text, font);
    }

}
