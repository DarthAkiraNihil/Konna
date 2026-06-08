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
 * Representation of a 2D vector, which coordinates are represented with floats.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KVector2f {

    private final float x;
    private final float y;

    private KVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private static int hash(float x, float y) {
        int result = 1;
        result = 31 * result + Float.floatToIntBits(x);
        result = 33 * result + Float.floatToIntBits(y);
        return result;
    }

    // todo: soft references
    private static final Map<Integer, KVector2f> INSTANCES;

    /**
     * Zero vector - (0,0).
     */
    public static final KVector2f ZERO = new KVector2f(0.0f, 0.0f);
    /**
     * Unit vector - (1,1).
     */
    public static final KVector2f ONE = new KVector2f(1.0f, 1.0f);

    static {
        INSTANCES = new ConcurrentHashMap<>();

        INSTANCES.put(ZERO.hashCode(), ZERO);
        INSTANCES.put(ONE.hashCode(), ONE);
    }


    static KVector2f create(float x, float y) {
        int hash = KVector2f.hash(x, y);
        if (!INSTANCES.containsKey(hash)) {
            INSTANCES.put(hash, new KVector2f(x, y));
        }

        KVector2f instance = INSTANCES.get(hash);
        return instance.x == x && instance.y == y
            ? instance
            : new KVector2f(x, y);

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

    @Override
    public boolean equals(@Nullable final Object object) {
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        KVector2f kVector2f = (KVector2f) object;
        return
                Float.compare(this.x, kVector2f.x) == 0
            &&  Float.compare(this.y, kVector2f.y) == 0;
    }

    @Override
    public int hashCode() {
        return KVector2f.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "KVector2f{" + "x=" + this.x + ", y=" + this.y + '}';
    }
}
