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

package io.github.darthakiranihil.konna.core.ui;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.struct.KSize;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class KFrame extends Frame implements KeyListener, WindowListener {

    private final KCanvas canvas;

    public KFrame(@KInject final KCanvas canvas, final String title, final KSize size) {

        super(title);
        this.canvas = canvas;
        this.addKeyListener(this);
        this.addWindowListener(this);
        this.setSize(size.width(), size.height());
        this.add(this.canvas);

    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void keyPressed(final KeyEvent e) {

    }

    @Override
    public void keyReleased(final KeyEvent e) {

    }

    @Override
    public void windowOpened(final WindowEvent e) {

    }

    @Override
    public void windowClosing(final WindowEvent e) {
        this.dispose();
    }

    @Override
    public void windowClosed(final WindowEvent e) {

    }

    @Override
    public void windowIconified(final WindowEvent e) {

    }

    @Override
    public void windowDeiconified(final WindowEvent e) {

    }

    @Override
    public void windowActivated(final WindowEvent e) {

    }

    @Override
    public void windowDeactivated(final WindowEvent e) {

    }
}
