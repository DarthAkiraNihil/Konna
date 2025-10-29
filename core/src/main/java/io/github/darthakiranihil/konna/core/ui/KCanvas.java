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

package io.github.darthakiranihil.konna.core.ui;

import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;

import java.awt.*;

@KExcludeFromGeneratedCoverageReport
@KSingleton(immortal = true)
public class KCanvas extends Canvas {

    private Graphics2D graphics;

    public KCanvas() {
        super();
    }

    public void postInit() {
        this.graphics = (Graphics2D) this.getGraphics();
    }

    public void drawRectangle(final KRectangle rect) {
        if (this.graphics == null) {
            System.out.println("GRAPHICS ARE NULL GETTING NEW");
            this.graphics = (Graphics2D) this.getGraphics();
        }

        final Color originalColor = this.graphics.getColor();

        if (rect.fillColor() != null) {
            this.graphics.setColor(rect.fillColor().raw());
            this.graphics.fillRect(rect.x(), rect.y(), rect.width(), rect.height());
        }

        if (rect.outlineColor() != null) {
            this.graphics.setColor(rect.outlineColor().raw());
        } else {
            this.graphics.setColor(originalColor);
        }

        this.graphics.drawRect(rect.x(), rect.y(), rect.width(), rect.height());
        this.graphics.setColor(originalColor);
        System.out.println("I've drawn a rectangle");
    }
}
