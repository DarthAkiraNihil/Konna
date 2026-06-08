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

/**
 * Provides different useful utils connected with buffers.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KBufferUtils extends KUninstantiable {

    private KBufferUtils() {
        super();
    }

    /**
     * Allocates a direct byte buffer with desired capacity and native byte order.
     * @param capacity Capacity of the buffer in elements count
     * @return Allocated direct byte buffer
     */
    public static ByteBuffer createByteBuffer(int capacity) {
        return ByteBuffer
            .allocateDirect(capacity * Byte.BYTES)
            .order(ByteOrder.nativeOrder());
    }

    /**
     * Allocates a direct int buffer with desired capacity and native byte order.
     * @param capacity Capacity of the buffer in elements count
     * @return Allocated direct int buffer
     */
    public static IntBuffer createIntBuffer(int capacity) {
        return ByteBuffer
            .allocateDirect(capacity * Integer.BYTES)
            .order(ByteOrder.nativeOrder())
            .asIntBuffer();
    }

    /**
     * Allocates a direct float buffer with desired capacity and native byte order.
     * @param capacity Capacity of the buffer in elements count
     * @return Allocated direct float buffer
     */
    public static FloatBuffer createFloatBuffer(int capacity) {
        return ByteBuffer
            .allocateDirect(capacity * Float.BYTES)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
    }

    /**
     * Creates a direct byte buffer from an array.
     * @param array Array to create the buffer from
     * @return Allocated direct byte buffer, containing all array values
     */
    public static ByteBuffer wrapByteArrayToBuffer(final byte[] array) {
        ByteBuffer wrapper = ByteBuffer.allocateDirect(array.length);
        wrapper.put(array);
        return wrapper;
    }

}
