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

package io.github.darthakiranihil.konna.core.graphics;

import java.util.Arrays;
import java.util.List;

public record KVertexAttribute(
    int location,
    String name,
    int length
) {

    public static final String ATTR_COLOR = "a_color";
    public static final String ATTR_POSITION = "a_position";
    public static final String ATTR_TEX_COORD = "a_tex_coord";

    public static final List<KVertexAttribute> DEFAULT_ATTRIBUTES = Arrays.asList(
        new KVertexAttribute(0, ATTR_POSITION, 2),
        new KVertexAttribute(1, ATTR_COLOR, 4),
        new KVertexAttribute(2, ATTR_TEX_COORD, 2)
    );

}
