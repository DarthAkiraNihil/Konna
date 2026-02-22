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

package io.github.darthakiranihil.konna.core.io.resource;

import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.except.KResourceException;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Implementation of {@link KResource} that represents a classpath resource.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@NullUnmarked
public class KClasspathResource implements KResource {

    private final String path;
    private final String name;

    private final ReadableByteChannel chan;
    private final InputStream stream;

    private String cachedStringContent;
    private byte[] cachedByteContent;

    /**
     * Standard constructor.
     * @param path Path to the resource
     * @param name Name of the resource
     * @param stream Input stream of the resource that is likely to be
     *               provided by {@link ClassLoader#getResourceAsStream(String)}
     */
    public KClasspathResource(
        final String path,
        final String name,
        @Nullable final InputStream stream
    ) {
        this.path = path;
        this.name = name;
        this.stream = stream;
        if (stream != null) {
            this.chan = Channels.newChannel(stream);
        } else {
            this.chan = null;
        }

        this.cachedStringContent = null;
        this.cachedByteContent = null;
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
    public @Nullable ReadableByteChannel channel() {
        return this.chan;
    }

    @Override
    public @Nullable InputStream stream() {
        return this.stream;
    }

    @Override
    public String string() {
        if (this.cachedStringContent != null) {
            return this.cachedStringContent;
        }

        BufferedReader br = new BufferedReader(
            Channels.newReader(this.chan, StandardCharsets.UTF_8)
        );
        this.cachedStringContent = br
            .lines()
            .onClose(() -> {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new KResourceException(e);
                }
            })
            .collect(Collectors.joining("\n"));
        return this.cachedStringContent;
    }

    @Override
    public byte[] bytes() {
        if (this.cachedByteContent != null) {
            return this.cachedByteContent;
        }

        try {
            this.cachedByteContent = this.stream.readAllBytes();
            return this.cachedByteContent;
        } catch (IOException e) {
            throw new KResourceException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.chan.close();
        this.stream.close();
    }
}
