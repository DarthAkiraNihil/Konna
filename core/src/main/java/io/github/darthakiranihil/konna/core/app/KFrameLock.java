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

package io.github.darthakiranihil.konna.core.app;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

@KPoolable(
    initialPoolSize = KFrameLock.MAX_LOCKS
)
public final class KFrameLock extends KObject {

    public static final int MAX_LOCKS = 8;
    private @Nullable KFrame frame;

    private KFrameLock() {
        super(
            "frame_lock",
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM, KTag.DefaultTags.STD)
        );

        this.frame = null;
    }

    @KOnPoolableObjectObtain
    private void lock(@KInject final KFrame frame) {
        this.frame = frame;
        frame.addLock(this);
    }

    @KOnPoolableObjectRelease
    private void unlock() {
        if (this.frame == null) {
            return;
        }

        this.frame.removeLock(this);
    }

}
