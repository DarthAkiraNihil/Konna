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

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import java.util.Queue;

@KExcludeFromGeneratedCoverageReport
@KSingleton(immortal = true)
public class KCanvas extends Canvas implements K2dRenderEngine, ComponentListener {

    private Graphics2D graphics;

    public final KEvent<KSize> canvasResized;

    private record RenderObject(
        KRenderable r,
        KTransform t
    ) {

    }

    private final Queue<KRenderable> dq;

    public KCanvas() {
        super();
        this.dq = new LinkedList<>();
        this.addComponentListener(this);
        this.canvasResized = new KEvent<>("canvas_resized");
    }

//    @Override
//    public void paint(Graphics g) {
////        System.out.println(g.getClipBounds());
////        var gg = (Graphics2D) g;
////        for (var r: this.dq) {
////            r.render(this);
////        }
//    }

    public void postInit() {
        this.graphics = (Graphics2D) this.getGraphics();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("RESIZE");
        System.out.println(e);
        var c = e.getComponent();
        this.canvasResized.invokeSync(new KSize(c.getWidth(), c.getHeight()));
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void render(KRectangle rect) {
        this.graphics = (Graphics2D) this.getGraphics();
        final Color originalColor = graphics.getColor();

        if (rect.fillColor() != null) {
            graphics.setColor(rect.fillColor().raw());
            graphics.fillRect(rect.x(), rect.y(), rect.width(), rect.height());
        }

        if (rect.outlineColor() != null) {
            graphics.setColor(rect.outlineColor().raw());
        } else {
            graphics.setColor(originalColor);
        }

        graphics.drawRect(rect.x(), rect.y(), rect.width(), rect.height());
        graphics.setColor(originalColor);
    }

    @Override
    public void render(KRectangle rect, KTransform transform) {
        this.graphics = (Graphics2D) this.getGraphics();
        var tr = this.graphics.getTransform();
        this.graphics.translate(transform.translation().x(), transform.translation().y());
        if (transform.isRotationPivoted()) {
            this.graphics.rotate(transform.rotation(), transform.rotationPivot().x(), transform.rotationPivot().y());
        } else {
            this.graphics.rotate(transform.rotation());
        }
        this.graphics.scale(transform.scaling().x(), transform.scaling().y());
        this.render(rect);
        this.graphics.setTransform(tr);
    }

    public void add(KRenderable r) {
        if (!this.dq.contains(r)) this.dq.add(r);
    }

    // public void render(final KRectangle rect) {
//        if (this.graphics == null) {
//            System.out.println("GRAPHICS ARE NULL GETTING NEW");
//            this.graphics = (Graphics2D) this.getGraphics();
//        }
//

        // if (!this.dq.contains(rect)) this.dq.add(rect);
        // System.out.println("I've drawn a rectangle");
    // }
}
