package io.github.darthakiranihil.konna.core.graphics.text;

import io.github.darthakiranihil.konna.core.graphics.shape.KColor;

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public record KFontStyle(
    int size,
    boolean italic,
    KFontWeight weight,
    KColor foregroundColor,
    KColor backgroundColor,
    KFontUnderline underline,
    boolean strikethrough,
    KFontSuperscriptOption superscriptOption,
    KFontWidth width
) {

    public static final KFontStyle DEFAULT_STYLE = new KFontStyle(10);

    public KFontStyle(
        int size,
        boolean italic,
        final KFontWeight weight,
        final KColor foregroundColor,
        final KColor backgroundColor,
        final KFontUnderline underline,
        boolean strikethrough,
        final KFontSuperscriptOption superscriptOption
    ) {
        this(size, italic, weight, foregroundColor, backgroundColor, underline, strikethrough, superscriptOption, KFontWidth.REGULAR);
    }

    public KFontStyle(
        int size,
        boolean italic,
        final KFontWeight weight,
        final KColor foregroundColor,
        final KColor backgroundColor,
        final KFontUnderline underline,
        boolean strikethrough
    ) {
        this(size, italic, weight, foregroundColor, backgroundColor, underline, strikethrough, KFontSuperscriptOption.DISABLED);
    }

    public KFontStyle(
        int size,
        boolean italic,
        final KFontWeight weight,
        final KColor foregroundColor,
        final KColor backgroundColor,
        final KFontUnderline underline
    ) {
        this(size, italic, weight, foregroundColor, backgroundColor, underline, false);
    }

    public KFontStyle(
        int size,
        boolean italic,
        final KFontWeight weight,
        final KColor foregroundColor,
        final KColor backgroundColor
    ) {
        this(size, italic, weight, foregroundColor, backgroundColor, KFontUnderline.DISABLED);
    }

    public KFontStyle(
        int size,
        boolean italic,
        final KFontWeight weight,
        final KColor foregroundColor
    ) {
        this(size, italic, weight, foregroundColor, null);
    }

    public KFontStyle(
        int size,
        boolean italic,
        final KFontWeight weight
    ) {
        this(size, italic, weight, null);
    }

    public KFontStyle(
        int size,
        boolean italic
    ) {
        this(size, italic, KFontWeight.REGULAR);
    }

    public KFontStyle(
        int size
    ) {
        this(size, false);
    }

    public Map<TextAttribute, Object> raw() {

        Map<TextAttribute, Object> attributes = new HashMap<>();

        attributes.put(TextAttribute.SIZE, this.size);
        if (this.italic) {
            attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        }

        switch (this.weight) {
            case BOLD -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            case DEMIBOLD -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);
            case DEMILIGHT -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMILIGHT);
            case EXTRA_LIGHT -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRA_LIGHT);
            case EXTRABOLD -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRABOLD);
            case HEAVY -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_HEAVY);
            case LIGHT -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_LIGHT);
            case MEDIUM -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM);
            case REGULAR -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
            case SEMIBOLD -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
            case ULTRABOLD -> attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_ULTRABOLD);
        }

        attributes.put(TextAttribute.FOREGROUND, this.foregroundColor.raw());
        attributes.put(TextAttribute.BACKGROUND, this.backgroundColor.raw());

        switch (this.underline) {
            case DEFAULT -> attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            case LOW_DASHED -> attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DASHED);
            case LOW_DOTTED -> attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
            case LOW_GRAY -> attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_GRAY);
            case LOW_ONE_PIXEL -> attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
            case LOW_TWO_PIXEL -> attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL);
        }

        if (this.strikethrough) {
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        }

        switch (this.superscriptOption) {
            case SUB -> attributes.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
            case SUPER -> attributes.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
        }

        return attributes;
    }

}
