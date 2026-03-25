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

/**
 * Container class that holds information how to slice a base texture into
 * subtextures, each of that has a name, uvs, xys and colors, assigned to subtexture's vertices.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KTextureSliceSet {

    private final KTexture baseTexture;
    private final Map<String, KTextureSliceData> slices;

    /**
     * Standard constructor.
     * @param baseTexture Base texture, which is being sliced
     * @param slices Texture slices (name-slice data)
     */
    public KTextureSliceSet(
        final KTexture baseTexture,
        final Map<String, KTextureSliceData> slices
    ) {

        this.baseTexture = baseTexture;
        this.slices = slices;

    }

    /**
     * Attempts to return a renderable texture on specific unit by its name.
     * @param sliceName Name of the slice
     * @param unit Return texture unit
     * @return Renderable texture, representing the slice
     * @throws KInvalidArgumentException if the slice is not presented in the set
     */
    public KRenderableTexture getTexture(final String sliceName, int unit) {
        this.validateSliceIsPresented(sliceName);

        KTextureSliceData sliceData = this.slices.get(sliceName);

        return new KRenderableTexture(
            sliceName,
            sliceData.uv(),
            sliceData.xy(),
            sliceData.colors(),
            this.baseTexture,
            unit
        );
    }

    /**
     * Attempts to return a renderable texture on specific unit by its name, but with additional
     * shift of texture vertices (affecting its xys).
     * @param sliceName Name of the slice
     * @param topLeftCorner Coordinate of top-left corner of the result texture
     * @param unit Return texture unit
     * @return Renderable texture, representing the slice
     * @throws KInvalidArgumentException if the slice is not presented in the set
     */
    public KRenderableTexture getTexture(
        final String sliceName,
        final KVector2i topLeftCorner,
        int unit
    ) {
        this.validateSliceIsPresented(sliceName);

        KTextureSliceData sliceData = this.slices.get(sliceName);

        KVector2i[] xy = sliceData.xy();
        KVector2i[] newXy = new KVector2i[xy.length];
        for (int i = 0; i < xy.length; i++) {
            newXy[i] = xy[i].add(topLeftCorner);
        }

        return new KRenderableTexture(
            sliceName,
            sliceData.uv(),
            newXy,
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
