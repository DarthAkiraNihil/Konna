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

package io.github.darthakiranihil.konna.core.io.std.resource;

import io.github.darthakiranihil.konna.core.io.KResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class KClasspathResource implements KResource {

    private final String path;
    private final String name;
    private final ReadableByteChannel chan;

    public KClasspathResource(final String path, final String name, final InputStream stream) {
        this.path = path;
        this.name = name;
        if (stream != null) {
            this.chan = Channels.newChannel(stream);
        } else {
            this.chan = null;
        }
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean exists() {
        return this.chan != null;
    }

    @Override
    public boolean isOpen() {
        return this.exists() && this.chan.isOpen();
    }

    @Override
    public ReadableByteChannel channel() {
        return this.chan;
    }

    @Override
    public void close() throws IOException {
        this.chan.close();
    }
}
