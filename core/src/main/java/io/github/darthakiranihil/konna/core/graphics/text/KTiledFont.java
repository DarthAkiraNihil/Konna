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

package io.github.darthakiranihil.konna.core.graphics.text;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.graphics.shape.KImage;
import io.github.darthakiranihil.konna.core.graphics.KShader;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

public abstract class KTiledFont extends KObject {

    private final String name;
    protected final KSize charSize;
    protected final KImage face;

    public KTiledFont(final String name, final KSize charSize, final KImage face, final KShader[] shaders) {
        super(
            String.format("tiled_font_%s", name), KStructUtils.setOfTags(
                KTag.DefaultTags.GRAPHICS,
                KTag.DefaultTags.ASSET
            )
        );
        this.name = name;
        this.charSize = charSize;
        this.face = face.applyShaders(shaders);
    }

    public KTiledFont(final String name, final KSize charSize, final KImage face) {
        super(
            String.format("tiled_font_%s", name), KStructUtils.setOfTags(
                KTag.DefaultTags.GRAPHICS,
                KTag.DefaultTags.ASSET
            )
        );
        this.name = name;
        this.charSize = charSize;
        this.face = face;
    }

    public String name() {
        return this.name;
    }

    public abstract KImage resolveChar(char ch);

}
