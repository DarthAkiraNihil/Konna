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

package io.github.darthakiranihil.konna.struct.math;

import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Representation of a 4D vector, which coordinates are represented with floats.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KVector4f {

    private final float x;
    private final float y;
    private final float z;
    private final float w;

    private KVector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    private static int hash(float x, float y, float z, float w) {
        int result = 1;
        result = 31 * result + Float.floatToIntBits(x);
        result = 33 * result + Float.floatToIntBits(y);
        result = 37 * result + Float.floatToIntBits(z);
        result = 41 * result + Float.floatToIntBits(w);
        return result;
    }

    // todo: soft references
    private static final Map<Integer, KVector4f> INSTANCES;

    /**
     * Zero vector - (0,0,0,0).
     */
    public static final KVector4f ZERO = new KVector4f(0.0f, 0.0f, 0.0f, 0.0f);
    /**
     * Unit vector - (1,1,1,1).
     */
    public static final KVector4f ONE = new KVector4f(1.0f, 1.0f, 1.0f, 1.0f);

    static {
        INSTANCES = new ConcurrentHashMap<>();

        INSTANCES.put(ZERO.hashCode(), ZERO);
        INSTANCES.put(ONE.hashCode(), ONE);
    }

    static KVector4f create(float x, float y, float z, float w) {
        int hash = KVector4f.hash(x, y, z, w);
        if (!INSTANCES.containsKey(hash)) {
            INSTANCES.put(hash, new KVector4f(x, y, z, w));
        }

        KVector4f instance = INSTANCES.get(hash);
        return instance.x == x && instance.y == y && instance.z == z && instance.w == w
            ? instance
            : new KVector4f(x, y, z, w);
    }

    /**
     * @return X coordinate
     */
    public float x() {
        return this.x;
    }

    /**
     * @return Y coordinate
     */
    public float y() {
        return this.y;
    }

    /**
     * @return Z coordinate
     */
    public float z() {
        return this.z;
    }

    /**
     * @return W coordinate
     */
    public float w() {
        return this.w;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        KVector4f kVector4f = (KVector4f) object;
        return
                Float.compare(this.x, kVector4f.x) == 0
            &&  Float.compare(this.y, kVector4f.y) == 0
            &&  Float.compare(this.z, kVector4f.z) == 0
            &&  Float.compare(this.w, kVector4f.w) == 0;
    }

    // fixme: maybe this thing is not good, if needed - rework hash algorithm (as well as for other
    //        vector types)
    @Override
    public int hashCode() {
        return KVector4f.hash(this.x, this.y, this.z, this.w);
    }

    @Override
    public String toString() {
        return
                "KVector4f{"
            +   "x=" + this.x
            +   ", y=" + this.y
            +   ", z=" + this.z
            +   ", w=" + this.w + '}';
    }
}
