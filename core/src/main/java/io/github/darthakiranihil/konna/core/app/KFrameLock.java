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
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectObtain;
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectRelease;
import io.github.darthakiranihil.konna.core.object.KPoolable;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

/**
 * Utility object that prevent frame updating when the lock is active.
 * This object is pollable and its count is limited to {@link KFrameLock#MAX_LOCKS},
 * since its usage requires maximum caution, as it may highly reduce application's
 * performance. May be useful for tasks that are required to be finished before the next
 * frame update.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KPoolable(
    initialPoolSize = KFrameLock.MAX_LOCKS
)
public final class KFrameLock extends KObject {

    /**
     * Max number of active frame locks.
     */
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
    private void lock(@KInject final KFrame lockedFrame) {
        this.frame = lockedFrame;
        lockedFrame.addLock(this);
    }

    @KOnPoolableObjectRelease
    private void unlock() {
        if (this.frame == null) {
            return;
        }

        this.frame.removeLock(this);
    }

}
