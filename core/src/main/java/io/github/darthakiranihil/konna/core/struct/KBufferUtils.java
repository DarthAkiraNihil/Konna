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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class KBufferUtils extends KUninstantiable {

    private KBufferUtils() {
        super();
    }

    public static IntBuffer createIntBuffer(int capacity) {
        return ByteBuffer
            .allocateDirect(capacity * Integer.SIZE)
            .order(ByteOrder.nativeOrder())
            .asIntBuffer();
    }

    public static FloatBuffer createFloatBuffer(int capacity) {
        return ByteBuffer
            .allocateDirect(capacity * Float.SIZE)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
    }

}
