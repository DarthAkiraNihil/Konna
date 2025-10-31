package io.github.darthakiranihil.konna.core.ui.text;

import java.awt.*;

public class KFont {

    private static final int DEFAULT_SIZE = 10;

    private final String name;
    private final Font raw;

    public KFont(final String name) {
        this(name, KFontStyle.DEFAULT_STYLE);
    }

    public KFont(final String name, final KFontStyle style) {
        this.name = name;
        Font base = new Font(name, Font.PLAIN, DEFAULT_SIZE);
        this.raw = base.deriveFont(style.raw());
    }

    public String name() {
        return this.name;
    }

    public Font raw() {
        return this.raw;
    }

}
