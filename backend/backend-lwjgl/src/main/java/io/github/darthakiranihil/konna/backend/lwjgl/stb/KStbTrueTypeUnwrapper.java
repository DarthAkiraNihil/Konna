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

package io.github.darthakiranihil.konna.backend.lwjgl.stb;

import io.github.darthakiranihil.konna.libfrontend.stb.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryUtil;

final class KStbTrueTypeUnwrapper {

    public static KStbTtFontInfo wrap(final STBTTFontinfo original) {
        return original::address;
    }

    public static KStbTtBakedChar wrap(final STBTTBakedChar original) {
        return new KStbTtBakedChar() {
            @Override
            public short x0() {
                return original.x0();
            }

            @Override
            public short y0() {
                return original.y0();
            }

            @Override
            public short x1() {
                return original.x1();
            }

            @Override
            public short y1() {
                return original.y1();
            }

            @Override
            public float xoff() {
                return original.xoff();
            }

            @Override
            public float yoff() {
                return original.yoff();
            }

            @Override
            public float xadvance() {
                return original.xadvance();
            }
        };
    }

    public static KStbTtAlignedQuad wrap(final STBTTAlignedQuad original) {
        return new KStbTtAlignedQuad() {
            @Override
            public float x0() {
                return original.x0();
            }

            @Override
            public float y0() {
                return original.y0();
            }

            @Override
            public float s0() {
                return original.s0();
            }

            @Override
            public float t0() {
                return original.t0();
            }

            @Override
            public float x1() {
                return original.x1();
            }

            @Override
            public float y1() {
                return original.y1();
            }

            @Override
            public float s1() {
                return original.s1();
            }

            @Override
            public float t1() {
                return original.t1();
            }

            @Override
            public void set(float x0, float y0, float s0, float t0, float x1, float y1, float s1, float t1) {
                long address = original.address();
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.X0, x0);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.Y0, y0);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.S0, s0);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.T0, t0);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.X1, x1);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.Y1, y1);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.S1, s1);
                MemoryUtil.memPutFloat(address + STBTTAlignedQuad.T1, t1);
            }
        };
    }

}
