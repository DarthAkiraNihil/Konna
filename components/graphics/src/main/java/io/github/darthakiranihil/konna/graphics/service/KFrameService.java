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

package io.github.darthakiranihil.konna.graphics.service;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.KFrame;
import org.jspecify.annotations.Nullable;

@KSingleton
@KComponentServiceMetaInfo(
    name = "FrameService"
)
public class KFrameService extends KObject {

    public static final KSimpleEvent RENDER_REQUIRED = new KSimpleEvent("render_required");

    private static final String WATCHER_THREAD_NAME = "frame_watcher";

    private final KFrame frame;
    private @Nullable Thread frameWatcherThread;

    public KFrameService(@KInject final KFrame frame) {
        super("Graphics.FrameService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));
        this.frame = frame;

        this.frameWatcherThread = new Thread(this::watch);
        this.frameWatcherThread.setName(WATCHER_THREAD_NAME);
        this.frameWatcherThread.start();

    }

    private void watch() {

        KSystemLogger.info("FrameWatcher", "Frame watcher thread has been started");
        this.frame.assignToCurrentContext();
        this.frame.show();

        while (!this.frame.shouldClose()) {

            RENDER_REQUIRED.invokeSync();
            this.frame.swapBuffers();
            this.frame.pollEvents();

        }

        this.frame.terminate();
        this.frameWatcherThread = null;

        KSystemLogger.info("FrameWatcher", "Frame watcher thread has been stopped");

    }


}
