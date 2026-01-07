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

package io.github.darthakiranihil.konna.graphics.render;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shape.*;

/**
 * Interface for a render frontend that renders object using implementation-defined
 * tools and classes.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KRenderFrontend {

    /**
     * Additionally initializes the frontend. Should not have effect when called twice.
     */
    void initialize();

    /**
     * Renders a line.
     * @param line Line object to render
     */
    void render(KLine line);
    /**
     * Renders a polyline.
     * @param polyline Polyline object to render
     */
    void render(KPolyline polyline);
    /**
     * Renders a polygon.
     * @param polygon Polygon object to render
     */
    void render(KPolygon polygon);
    /**
     * Renders a rectangle.
     * @param rectangle Rectangle object to render
     */
    void render(KRectangle rectangle);
    /**
     * Renders an oval.
     * @param oval Oval object to render
     */
    void render(KOval oval);
    /**
     * Renders a circle.
     * @param circle Circle object to render
     */
    void render(KCircle circle);
    /**
     * Renders an arc.
     * @param arc Arc object to render
     */
    void render(KArc arc);

    /**
     * Render a texture.
     * @param texture Texture object to render
     */
    void render(KRenderableTexture texture);

    /**
     * Clears the viewport.
     */
    void clear();

    /**
     * Sets viewport size for this render frontend. Usually it is required
     * if internal rendering process relies on normalized coordinates ((-1,1) range)
     * instead of screen coordinates, so after setting the size 0 will represent "-1" coordinate
     * and corresponding size value will represent "1" coordinate.
     * @param size Size of the viewport
     */
    void setViewportSize(KSize size);

    /**
     * Sets a shader for this render frontend as active.
     * @param shader Activated shader
     */
    void setActiveShader(KShaderProgram shader);

    /**
     * Disables currently active shader of this render frontend.
     */
    void disableActiveShader();

}
