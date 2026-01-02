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

package io.github.darthakiranihil.konna.graphics.internal;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.graphics.except.KInvalidRenderableClassException;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.*;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@KSingleton
public final class KRenderableObjectTypeMapping extends KObject {

    private static final Map<String, Class<? extends KRenderable>> DEFAULT_TYPES = Map.of(
        "arc", KArc.class,
        "circle", KCircle.class,
        "line", KLine.class,
        "oval", KOval.class,
        "polygon", KPolygon.class,
        "polyline", KPolyline.class,
        "rectangle", KRectangle.class
    );

    private final Map<String, Class<? extends KRenderable>> mapping;

    public KRenderableObjectTypeMapping() {
        this.mapping = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public void addMapping(final String type, final String typeClassName) {
        try {
            Class<?> clazz = Class.forName(typeClassName);
            if (!KRenderable.class.isAssignableFrom(clazz)) {
                throw new KInvalidRenderableClassException(
                    String.format(
                            "Class for renderable object type %s is not renderable: %s. "
                        +   "Check if it implements KRenderable interface.",
                        type,
                        typeClassName
                    )
                );
            }
            this.mapping.put(type, (Class<? extends KRenderable>) clazz);
        } catch (ClassNotFoundException e) {
            throw new KInvalidRenderableClassException(
                String.format(
                    "Class not found for renderable object type %s: %s",
                    type,
                    typeClassName
                )
            );
        }
    }

    public @Nullable Class<? extends KRenderable> getMapping(final String type) {
        Class<? extends KRenderable> typeClass = DEFAULT_TYPES.get(type);
        if (typeClass != null) {
            return typeClass;
        }
        return this.mapping.get(type);
    }

}
