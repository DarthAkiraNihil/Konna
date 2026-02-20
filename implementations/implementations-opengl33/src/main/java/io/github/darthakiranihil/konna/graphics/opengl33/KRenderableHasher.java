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

package io.github.darthakiranihil.konna.graphics.opengl33;

import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.annotation.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.*;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Objects;

@ApiStatus.Internal
@KExcludeFromGeneratedCoverageReport
final class KRenderableHasher extends KUninstantiable {

    public static int hash(final KArc arc) {
        return Objects.hash(
            arc.center(),
            arc.size(),
            arc.startAngle(),
            arc.arcAngle()
        );
    }

    public static int hash(final KLine line) {
        return Objects.hash(line.start(), line.end());
    }

    public static int hash(final KOval oval) {
        return Objects.hash(oval.center(), oval.size());
    }

    public static int hash(final KPolygon polygon) {
        return Objects.hash(Arrays.hashCode(polygon.points()));
    }

    public static int hash(final KPolyline polyline) {
        return Objects.hash(Arrays.hashCode(polyline.points()));
    }

    public static int hash(final KRenderableTexture texture) {
        return Objects.hash(
            Arrays.hashCode(texture.uv()),
            Arrays.hashCode(texture.xy()),
            texture.texture()
        );
    }

    public static int hash(final KRenderable renderable) {
        return switch (renderable) {
            case KArc a -> hash(a);
            case KLine l -> hash(l);
            case KOval o -> hash(o);
            case KPolygon p -> hash(p);
            case KPolyline p -> hash(p);
            case KRenderableTexture tex -> hash(tex);
            default -> Objects.hash(renderable);
        };
    }

}
