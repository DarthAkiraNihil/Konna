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

package io.github.darthakiranihil.konna.graphics.service;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;

@KSingleton
@KComponentServiceMetaInfo(
    name = "RenderService"
)
public class KRenderService extends KObject {

    private final KRenderFrontend renderFrontend;

    public KRenderService(@KInject KRenderFrontend renderFrontend) {
        super("Graphics.RenderService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));
        this.renderFrontend = renderFrontend;
    }

    @KServiceEndpoint(
        route = "render",
        converter = KInternals.MessageToRenderableConverter.class
    )
    public void render(KRenderable renderable) {
        renderable.render(this.renderFrontend);
    }

}
