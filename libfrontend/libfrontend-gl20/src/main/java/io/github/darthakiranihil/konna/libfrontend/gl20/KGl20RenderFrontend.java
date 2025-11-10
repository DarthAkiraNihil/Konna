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

package io.github.darthakiranihil.konna.libfrontend.gl20;

import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.core.object.KObject;

public class KGl20RenderFrontend extends KObject implements KRenderFrontend {

    private final KGl20 gl;

    public KGl20RenderFrontend(KGl20 gl) {
        this.gl = gl;
    }

    @Override
    public void render(KRectangle rectangle) {

    }
}
