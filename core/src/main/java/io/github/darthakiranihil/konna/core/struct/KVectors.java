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

package io.github.darthakiranihil.konna.core.struct;

import io.github.darthakiranihil.konna.core.object.KUninstantiable;

public final class KVectors extends KUninstantiable {

    private KVectors() {
        super();
    }

    public static KVector2i new2i(int x, int y) {
        return KVector2i.create(x, y);
    }

    public static KVector2f new2f(float x, float y) {
        return KVector2f.create(x, y);
    }

    public static KVector4f new4f(float x, float y, float z, float w) {
        return KVector4f.create(x, y, z, w);
    }

}
