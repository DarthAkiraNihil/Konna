package io.github.darthakiranihil.konna.core.graphics.text;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.awt.*;

public class KFont extends KObject {

    private static final int DEFAULT_SIZE = 10;

    private final String name;
    private final Font raw;

    public KFont(final String name) {
        super(
            String.format("font_%s", name), KStructUtils.setOfTags(
                KTag.DefaultTags.GRAPHICS,
                KTag.DefaultTags.ASSET
            )
        );
        this.name = name;
        Font base = new Font(name, Font.PLAIN, DEFAULT_SIZE);
        this.raw = base.deriveFont(KFontStyle.DEFAULT_STYLE.raw());
    }

    public KFont(final String name, final KFontStyle style) {
        super(
            String.format("font_%s", name), KStructUtils.setOfTags(
                KTag.DefaultTags.GRAPHICS,
                KTag.DefaultTags.ASSET
            )
        );

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
