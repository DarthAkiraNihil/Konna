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

package io.github.darthakiranihil.konna.level.layer;

import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import org.jspecify.annotations.Nullable;

public class KPassabilityLayer
    extends KAbstractSizedLayer<KPassabilityLayerTool>
    implements KObjectLevelLayer<KPassabilityState, KPassabilityLayerTool> {

    private static final class Tool implements KPassabilityLayerTool {

        private final KPassabilityLayer self;

        public Tool(KPassabilityLayer self) {
            this.self = self;
        }

        @Override
        public void setState(int x, int y, KPassabilityState state) {

            if (
                    x < 0
                ||  x >= this.self.size.width()
                ||  y < 0
                ||  y >= this.self.size.height()
            ) {
                return;
            }

            this.self.states[y][x] = state;
        }

        @Override
        public void setImpassableIfVoid(int x, int y) {
            if (this.getOnPosition(x, y) == KPassabilityState.VOID) {
                this.setState(x, y, KPassabilityState.IMPASSABLE);
            }
        }

        @Override
        public void digPassableRectangle(final KVector2i topLeft, final KSize size) {

            // todo: size check
            int finalX = topLeft.x() + size.width() + 1;
            int finalY = topLeft.y() + size.height() + 1;

            int startX = topLeft.x() - 1;
            int startY = topLeft.y() - 1;


            for (int y = startY; y < finalY; y++) {
                for (int x = startX; x < finalX; x++) {

                    if (y == startY || y == finalY - 1) {
                        this.setImpassableIfVoid(x, y);
                    } else {
                        if (x == startX || x == finalX - 1) {
                            this.setImpassableIfVoid(x, y);
                        } else {
                            this.setState(x, y, KPassabilityState.PASSABLE);
                        }
                    }

                }
            }

        }

        @Override
        public void digStraightPassableLine(
            final KVector2i start,
            int length,
            final KVector2i direction
        ) {
            if (
                    !direction.equals(KVector2i.DOWN)
                &&  !direction.equals(KVector2i.UP)
                &&  !direction.equals(KVector2i.LEFT)
                &&  !direction.equals(KVector2i.RIGHT)
            ) {
                throw new KInvalidArgumentException(
                    String.format("Invalid direction: %s", direction)
                );
            }

            KVector2i position = start;
            for (int i = 0; i < length; i++) {
                int currentX = position.x();
                int currentY = position.y();

                this.setState(currentX, currentY, KPassabilityState.PASSABLE);
                if (direction.equals(KVector2i.DOWN) || direction.equals(KVector2i.UP)) {
                    this.setImpassableIfVoid(currentX - 1, currentY);
                    this.setImpassableIfVoid(currentX + 1, currentY);
                } else {
                    this.setImpassableIfVoid(currentX, currentY - 1);
                    this.setImpassableIfVoid(currentX, currentY + 1);
                }

                position = position.add(direction);
            }

        }

        @Override
        public KPassabilityState getOnPosition(int x, int y) {
            if (x < 0 || x >= this.self.size.width() || y < 0 || y >= this.self.size.height()) {
                return KPassabilityState.VOID;
            }

            return this.self.states[y][x];
        }
    }

    private final KPassabilityState[][] states;
    private final KPassabilityLayerTool tool;

    public KPassabilityLayer(final KSize size) {
        super(size);
        this.states = new KPassabilityState[size.height()][size.width()];
        for (int y = 0; y < size.height(); y++) {
            for (int x = 0; x < size.width(); x++) {
                this.states[y][x] = KPassabilityState.VOID;
            }
        }

        this.tool = new Tool(this);
    }

    @Override
    public KPassabilityState getOnPosition(int x, int y) {
        return this.states[y][x];
    }

    @Override
    public KPassabilityLayerTool getTool() {
        return this.tool;
    }
}
