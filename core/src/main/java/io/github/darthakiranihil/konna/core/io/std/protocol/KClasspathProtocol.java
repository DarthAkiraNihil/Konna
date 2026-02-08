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

package io.github.darthakiranihil.konna.core.io.std.protocol;

import io.github.darthakiranihil.konna.core.io.KProtocol;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.KResourceUtils;
import io.github.darthakiranihil.konna.core.io.except.KIoException;
import io.github.darthakiranihil.konna.core.io.std.resource.KClasspathResource;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link KProtocol} that searches for resources
 * inside application's classpath (the path must start with classpath:).
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KClasspathProtocol implements KProtocol {

    private static final String PREFIX = "classpath:";

    private final ClassLoader classLoader;

    /**
     * Standard constructor.
     * @param classLoader Classloader to load resources from
     */
    public KClasspathProtocol(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public @Nullable KResource resolve(final String path) {
        if (!path.startsWith(PREFIX)) {
            return null;
        }

        String realPath = path.substring(PREFIX.length());
        String name = KResourceUtils.getFilename(realPath);
        InputStream is = this.classLoader.getResourceAsStream(realPath);

        if (is == null) {
            return null;
        }

        return new KClasspathResource(
            realPath,
            name,
            is
        );
    }

    @Override
    public KResource[] resolveMany(String path, boolean recursive) {

        if (!path.startsWith(PREFIX)) {
            return new KResource[0];
        }

        String realPath = path.substring(PREFIX.length());
        URL resource = this.classLoader.getResource(path);
        if (resource == null) {
            return new KResource[0];
        }

        try {
            URI uri = resource.toURI();
            Path dirPath;
            FileSystem fileSystem = null;

            try {
                dirPath = Paths.get(uri);
            } catch (FileSystemNotFoundException e) {
                fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                dirPath = fileSystem.getPath("/");
                if (!realPath.isEmpty()) {
                    for (String part : realPath.split("/")) {
                        dirPath = dirPath.resolve(part);
                    }
                }
            }

            try (Stream<Path> paths = recursive ? Files.walk(dirPath) : Files.list(dirPath)) {
                return paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(p -> new KClasspathResource(
                        p,
                        KResourceUtils.getFilename(p),
                        Objects.requireNonNull(
                            this.classLoader.getResourceAsStream(p)
                        )
                    ))
                    .toList()
                    .toArray(new KResource[0]);

            } finally {
                if (fileSystem != null) {
                    fileSystem.close();
                }
            }

        } catch (URISyntaxException | IOException e) {
            throw new KIoException(e.getMessage());
        }
    }
}
