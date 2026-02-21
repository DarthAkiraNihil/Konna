/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.graphics.text;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.*;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderProgram;
import io.github.darthakiranihil.konna.graphics.text.KSquaredAsciiTiledFontFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KTiledTextPositiveTests extends KStandardTestClass {

    private final KTexture tex;
    private final KTiledFont font;

    public KTiledTextPositiveTests() {

        this.tex = new KTexture(
            new KImage(
                new byte[0],
                0,
                0
            ),
            new TestShaderProgram(),
            KTextureFiltering.MIPMAP,
            KTextureFiltering.LINEAR,
            KTextureWrapping.REPEAT,
            KTextureWrapping.REPEAT
        );

        this.font = new KTiledFont(
            "test_font",
            tex,
            new KSquaredAsciiTiledFontFormat(),
            KSize.squared(16)
        );

    }

    @Test
    public void testCreateTiledTextStandard() {

        String string = "ABOBA\nABOBA";

        KTiledText text = new KTiledText(
            string,
            KVector2i.ZERO,
            this.font,
            KColor.WHITE
        );

        Assertions.assertEquals(string, text.getText());
        Assertions.assertEquals(this.font, text.getFont());
        Assertions.assertEquals(KColor.WHITE, text.getColor());
        Assertions.assertEquals(0, text.getUnit());
        Assertions.assertFalse(text.isIgnoreNewline());
        Assertions.assertNull(text.getShader());

        KRenderableTexture rendered = text.getRendered();

        Assertions.assertEquals(4 * (string.length() - 1), rendered.uv().length);
        Assertions.assertEquals(4 * (string.length() - 1), rendered.xy().length);
        Assertions.assertEquals(4 * (string.length() - 1), rendered.colors().length);
        Assertions.assertEquals(this.tex, rendered.texture());

    }

    @Test
    public void testCreateTiledTextStandardWithIgnoreNewLine() {

        String string = "ABOBA\nABOBA";

        KTiledText text = new KTiledText(
            string,
            KVector2i.ZERO,
            this.font,
            KColor.WHITE
        );
        text.setIgnoreNewline(true);

        Assertions.assertEquals(string, text.getText());
        Assertions.assertEquals(this.font, text.getFont());
        Assertions.assertEquals(KColor.WHITE, text.getColor());
        Assertions.assertEquals(0, text.getUnit());
        Assertions.assertTrue(text.isIgnoreNewline());
        Assertions.assertNull(text.getShader());

        KRenderableTexture rendered = text.getRendered();

        Assertions.assertEquals(4 * string.length(), rendered.uv().length);
        Assertions.assertEquals(4 * string.length(), rendered.xy().length);
        Assertions.assertEquals(4 * string.length(), rendered.colors().length);
        Assertions.assertEquals(this.tex, rendered.texture());

    }

    @Test
    public void testCreateTiledTextEmptyText() {

        KTiledText text = new KTiledText(
            KVector2i.ZERO,
            this.font,
            KColor.WHITE
        );

        Assertions.assertEquals("", text.getText());
        Assertions.assertEquals(this.font, text.getFont());
        Assertions.assertEquals(KColor.WHITE, text.getColor());
        Assertions.assertEquals(0, text.getUnit());
        Assertions.assertFalse(text.isIgnoreNewline());
        Assertions.assertNull(text.getShader());

        KRenderableTexture rendered = text.getRendered();

        Assertions.assertEquals(0, rendered.uv().length);
        Assertions.assertEquals(0, rendered.xy().length);
        Assertions.assertEquals(0, rendered.colors().length);
        Assertions.assertEquals(this.tex, rendered.texture());

    }

    @Test
    public void testCreateTiledTextStandardWithShader() {

        String string = "ABOBA\nABOBA";

        KTiledText text = new KTiledText(
            string,
            KVector2i.ZERO,
            this.font,
            KColor.WHITE,
            new TestShaderProgram()
        );

        Assertions.assertEquals(string, text.getText());
        Assertions.assertEquals(this.font, text.getFont());
        Assertions.assertEquals(KColor.WHITE, text.getColor());
        Assertions.assertEquals(0, text.getUnit());
        Assertions.assertFalse(text.isIgnoreNewline());
        Assertions.assertNotNull(text.getShader());

        KRenderableTexture rendered = text.getRendered();

        Assertions.assertEquals(4 * (string.length() - 1), rendered.uv().length);
        Assertions.assertEquals(4 * (string.length() - 1), rendered.xy().length);
        Assertions.assertEquals(4 * (string.length() - 1), rendered.colors().length);
        Assertions.assertEquals(this.tex, rendered.texture());

    }

    @Test
    public void testCreateTiledTextEmptyTextAndShader() {

        KTiledText text = new KTiledText(
            KVector2i.ZERO,
            this.font,
            KColor.WHITE,
            new TestShaderProgram()
        );

        Assertions.assertEquals("", text.getText());
        Assertions.assertEquals(this.font, text.getFont());
        Assertions.assertEquals(KColor.WHITE, text.getColor());
        Assertions.assertEquals(0, text.getUnit());
        Assertions.assertFalse(text.isIgnoreNewline());
        Assertions.assertNotNull(text.getShader());

        KRenderableTexture rendered = text.getRendered();

        Assertions.assertEquals(0, rendered.uv().length);
        Assertions.assertEquals(0, rendered.xy().length);
        Assertions.assertEquals(0, rendered.colors().length);
        Assertions.assertEquals(this.tex, rendered.texture());

    }

    @Test
    public void testSetters() {

        KTiledText text = new KTiledText(
            "ABOBA",
            KVector2i.ZERO,
            this.font,
            KColor.WHITE
        );

        text.setText("ABIBA");
        Assertions.assertEquals("ABIBA", text.getText());
        text.setUnit(1);
        Assertions.assertEquals(1, text.getUnit());
        text.setFont(this.font);
        Assertions.assertEquals(this.font, text.getFont());
        text.setColor(KColor.RED);
        Assertions.assertEquals(KColor.RED, text.getColor());

        KRenderableTexture rtex = text.getRendered();
        Assertions.assertEquals(rtex, text.getRendered());

    }


}
