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

import io.github.darthakiranihil.konna.libfrontend.stb.KStbTtBakedChar;
import io.github.darthakiranihil.konna.libfrontend.stb.KStbTtFontInfo;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryUtil;

final class KStbTrueTypeWrapper {

    public static STBTTFontinfo wrap(final KStbTtFontInfo original) {
        return STBTTFontinfo.create(original.handle());
    }

    public static STBTTBakedChar wrap(final KStbTtBakedChar original) {

        var internal = STBTTBakedChar.create();
        long address = internal.address();
        MemoryUtil.memPutShort(address + STBTTBakedChar.X0, original.x0());
        MemoryUtil.memPutShort(address + STBTTBakedChar.X1, original.x1());
        MemoryUtil.memPutShort(address + STBTTBakedChar.Y0, original.y0());
        MemoryUtil.memPutShort(address + STBTTBakedChar.Y1, original.y1());
        MemoryUtil.memPutFloat(address + STBTTBakedChar.XOFF, original.xoff());
        MemoryUtil.memPutFloat(address + STBTTBakedChar.XADVANCE, original.xadvance());
        return internal;

    }

}
