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

package io.github.darthakiranihil.konna.test;

import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameLock;
import io.github.darthakiranihil.konna.core.io.control.KInputProcessor;
import io.github.darthakiranihil.konna.core.struct.KSize;
import org.jetbrains.annotations.TestOnly;

/**
 * Implementation of {@link KFrame} to be used only in tests.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@TestOnly
public final class KTestFrame implements KFrame {

    private boolean shouldClose;

    public KTestFrame() {

        this.shouldClose = false;

    }

    @Override
    public void setTitle(final String newTitle) {

    }

    @Override
    public long handle() {
        return 0;
    }

    @Override
    public KSize getSize() {
        return KSize.squared(0);
    }

    @Override
    public void setSize(final KSize newSize) {

    }

    @Override
    public void setResizable(boolean state) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void terminate() {

    }

    @Override
    public boolean shouldClose() {
        return this.shouldClose;
    }

    @Override
    public void setShouldClose(boolean flag) {
        this.shouldClose = flag;
    }

    @Override
    public void swapBuffers() {

    }

    @Override
    public void pollEvents() {

    }

    @Override
    public void addLock(final KFrameLock lock) {

    }

    @Override
    public void removeLock(final KFrameLock lock) {

    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public KInputProcessor getInputProcessor() {
        return new KTestInputProcessor();
    }
}
