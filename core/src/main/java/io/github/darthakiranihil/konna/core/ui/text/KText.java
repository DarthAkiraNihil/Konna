package io.github.darthakiranihil.konna.core.ui.text;

import io.github.darthakiranihil.konna.core.struct.KIntVector2d;

public record KText(
    int x,
    int y,
    String text,
    KFont font
) {

    public KText(final KIntVector2d coordinates, final String text, final KFont font) {
        this(coordinates.x(), coordinates.y(), text, font);
    }

}
