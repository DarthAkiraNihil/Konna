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

package io.github.darthakiranihil.konna.graphics.image;

import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.Map;

public final class KTextureSliceSet {

    private final KTexture baseTexture;
    private final Map<String, KTextureSliceData> slices;

    public KTextureSliceSet(
        final KTexture baseTexture,
        final Map<String, KTextureSliceData> slices
    ) {

        this.baseTexture = baseTexture;
        this.slices = slices;

    }

    public KRenderableTexture getTexture(final String sliceName) {
        return this.getTexture(sliceName, 0);
    }

    public KRenderableTexture getTexture(final String sliceName, int unit) {
        this.validateSliceIsPresented(sliceName);

        KTextureSliceData sliceData = this.slices.get(sliceName);

        return new KRenderableTexture(
            sliceData.uv(),
            sliceData.xy(),
            sliceData.colors(),
            this.baseTexture,
            unit
        );
    }

    public KRenderableTexture getTexture(final String sliceName, final KVector2i[] xy) {
        return this.getTexture(sliceName, xy, 0);
    }

    public KRenderableTexture getTexture(final String sliceName, final KVector2i[] xy, int unit) {
        this.validateSliceIsPresented(sliceName);

        KTextureSliceData sliceData = this.slices.get(sliceName);

        return new KRenderableTexture(
            sliceData.uv(),
            xy,
            sliceData.colors(),
            this.baseTexture,
            unit
        );
    }

    private void validateSliceIsPresented(final String sliceName) {
        if (this.slices.containsKey(sliceName)) {
            return;
        }

        throw new KInvalidArgumentException(
            String.format(
                "Texture slice with name %s not found",
                sliceName
            )
        );
    }


}
