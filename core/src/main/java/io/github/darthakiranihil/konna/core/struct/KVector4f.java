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

import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
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
        int hash = Objects.hash(x, y, z, w);
        if (!INSTANCES.containsKey(hash)) {
            INSTANCES.put(hash, new KVector4f(x, y, z, w));
        }

        return INSTANCES.get(hash);
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
    public boolean equals(@Nullable Object object) {
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        KVector4f kVector4f = (KVector4f) object;
        return
                Float.compare(x, kVector4f.x) == 0
            &&  Float.compare(y, kVector4f.y) == 0
            &&  Float.compare(z, kVector4f.z) == 0
            &&  Float.compare(w, kVector4f.w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "KVector4f{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}';
    }
}
