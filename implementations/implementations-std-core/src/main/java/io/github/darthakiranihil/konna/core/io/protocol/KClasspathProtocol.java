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

package io.github.darthakiranihil.konna.core.io.protocol;

import io.github.darthakiranihil.konna.core.io.KProtocol;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.KResourceUtils;
import io.github.darthakiranihil.konna.core.io.resource.KClasspathResource;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
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
    public KResource[] resolveMany(final String path, boolean recursive) {

        if (!path.startsWith(PREFIX)) {
            return new KResource[0];
        }

        String realPath = path.substring(PREFIX.length());

        try {
            Enumeration<URL> matches = this.classLoader.getResources(realPath);
            List<KResource> resources = new LinkedList<>();

            while (matches.hasMoreElements()) {
                URL url = matches.nextElement();
                resources.addAll(this.findInsidePath(realPath, url, recursive));
            }

            return resources.toArray(new KResource[0]);

        } catch (IOException | FileSystemNotFoundException | URISyntaxException e) {
            KSystemLogger.warning("ClasspathProtocol", e);
            return new KResource[0];
        }
    }

    private List<KClasspathResource> findInsidePath(
        final String realPath,
        final URL url,
        boolean recursive
    )
        throws IOException, FileSystemNotFoundException, URISyntaxException {

        URI uri = url.toURI();
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
                .filter(x -> !x.endsWith(".class"))
                .map(dirPath::relativize)
                .map(Path::toString)
                .map(p -> {
                    String fullPath = realPath + p;
                    return new KClasspathResource(
                        fullPath,
                        KResourceUtils.getFilename(p),
                        Objects.requireNonNull(
                            this.classLoader.getResourceAsStream(fullPath)
                        )
                    );
                })
                .toList();

        } finally {
            if (fileSystem != null) {
                fileSystem.close();
            }
        }

    }
}
