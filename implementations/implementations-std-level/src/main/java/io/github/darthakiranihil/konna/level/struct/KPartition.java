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

package io.github.darthakiranihil.konna.level.struct;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.Collections;
import java.util.List;

public final class KPartition {

    private final KVector2i topLeft;
    private final KVector2i bottomRight;
    private final KSize size;
    private final KVector2i center;
    private final List<KPartition> subpartitions;

    public KPartition(
        final KVector2i topLeft,
        final KSize size,
        final List<KPartition> subpartitions
    ) {
        this.topLeft = topLeft;
        this.size = size;
        this.subpartitions = Collections.unmodifiableList(subpartitions);
        this.center = new KVector2i(
            topLeft.x() + (int) (size.width() / 2.0f),
            topLeft.y() + (int) (size.height() / 2.0f)
        );
        this.bottomRight = new KVector2i(
            topLeft.x() + size.width(),
            topLeft.y() + size.height()
        );

    }

    public KVector2i getTopLeft() {
        return this.topLeft;
    }

    public KSize getSize() {
        return this.size;
    }

    public KVector2i getCenter() {
        return this.center;
    }

    public KVector2i getBottomRight() {
        return bottomRight;
    }

    public List<KPartition> getSubpartitions() {
        return this.subpartitions;
    }

}
