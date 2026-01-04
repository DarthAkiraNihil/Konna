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

import io.github.darthakiranihil.konna.core.input.KKeyListener;
import io.github.darthakiranihil.konna.core.struct.KSize;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class TestFrame implements KFrame {

    @Override
    public void setTitle(String newTitle) {

    }

    @Override
    public KSize getSize() {
        return new KSize(0, 0);
    }

    @Override
    public void setSize(KSize newSize) {

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
        return false;
    }

    @Override
    public void setShouldClose(boolean flag) {

    }

    @Override
    public void swapBuffers() {

    }

    @Override
    public void pollEvents() {

    }

    @Override
    public void addKeyListener(KKeyListener listener) {

    }
}
