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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.service.KRenderService;
import io.github.darthakiranihil.konna.graphics.type.*;

/**
 * <p>
 *     Konna Graphics component, used for rendering object on the screen.
 * </p>
 * <p>
 *     <h2>Provided endpoints</h2>
 *     <ul>
 *         <li>
 *             <p>
 *                 <i>Graphics.RenderService.render</i> - renders on object
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code object} -
 *                         {@link io.github.darthakiranihil.konna.graphics.render.KRenderable} -
 *                         object to render
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Graphics.RenderService.bulkRender</i> - renders array of objects
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code objects} -
 *                         {@link io.github.darthakiranihil.konna.graphics.render.KRenderable}[] -
 *                         array of objects to render
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *         <li>
 *             <p>
 *                 <i>Graphics.RenderService.bulkAddToRender</i> - adds array of objects to render
 *                 (does not erase list of current rendered objects)
 *             </p>
 *             <p>
 *                 Message schema:
 *                 <ul>
 *                     <li>
 *                         {@code objects} -
 *                         {@link io.github.darthakiranihil.konna.graphics.render.KRenderable}[] -
 *                         array of objects to render
 *                     </li>
 *                 </ul>
 *             </p>
 *         </li>
 *     </ul>
 * </p>
 * <p>
 *     Produced messages: none
 * </p>
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
public class KGraphicsComponent extends KComponent {

    /**
     * Component's config.
     */
    protected final KGraphicsComponentConfig config;

    /**
     * Constructs this component.
     * @param ctx Engine context
     * @param renderService Render service instance
     * @param config Component's config
     */
    public KGraphicsComponent(
        final KEngineContext ctx,
        final KRenderService renderService,
        final KGraphicsComponentConfig config
    ) {
        super("Graphics", ctx, new KService[]{renderService});
        this.config = config;
    }

    @Override
    public KAssetTypedef[] getAssetTypedefs() {
        return new KAssetTypedef[] {
            new KShaderTypedef(),
            new KShaderProgramTypedef(),
            new KTextureTypedef(),
            new KTiledFontTypedef(),
            new KTextureSliceSetTypedef(),
            new KRenderableTextureTypedef(),
        };
    }

    @Override
    public void postInit() {

        KRenderFrontend rf = this.engineModule.createObject(KRenderFrontend.class);
        rf.initialize();

    }
}
