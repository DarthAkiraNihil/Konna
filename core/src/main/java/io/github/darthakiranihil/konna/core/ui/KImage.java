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

package io.github.darthakiranihil.konna.core.ui;

import io.github.darthakiranihil.konna.core.engine.except.KIOException;
import io.github.darthakiranihil.konna.core.util.KCopyable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public record KImage(
    BufferedImage ref
) implements KCopyable<KImage> {

    public static KImage fromFile(final String filename) throws KIOException {
        try {
            return new KImage(ImageIO.read(new File(filename)));
        } catch (IOException e) {
            throw new KIOException(e.getMessage());
        }
    }

    @Override
    public KImage copy() {
        WritableRaster data = ((WritableRaster) this.ref.getData())
            .createWritableTranslatedChild(0, 0);

        var colorModel = this.ref.getColorModel();
        var isAlphaPremultiplied = this.ref.isAlphaPremultiplied();

        BufferedImage cloned = new BufferedImage(colorModel, data, isAlphaPremultiplied, null);
        return new KImage(cloned);
    }

    public KImage applyShaders(final KShader[] shaders) {
        KImage copy = this.copy();
        for (KShader shader: shaders) {
            copy = shader.apply(copy);
        }
        return copy;
    }

}
