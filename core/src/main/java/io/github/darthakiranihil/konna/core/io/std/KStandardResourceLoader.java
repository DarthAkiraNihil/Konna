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

package io.github.darthakiranihil.konna.core.io.std;

import io.github.darthakiranihil.konna.core.io.KProtocol;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;
import io.github.darthakiranihil.konna.core.io.KResourceUtils;

import java.util.List;

/**
 * Standard implementation of {@link KResourceLoader}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KStandardResourceLoader implements KResourceLoader {

    private final List<KProtocol> protocols;

    /**
     * Standard constructor.
     * @param protocols List of initial protocols
     */
    public KStandardResourceLoader(final List<KProtocol> protocols) {
        this.protocols = protocols;
    }

    @Override
    public void addProtocol(final KProtocol protocol) {
        this.protocols.add(protocol);
    }

    @Override
    public KResource loadResource(final String path) {
        for (KProtocol protocol: protocols) {
            KResource resolved = protocol.resolve(path);
            if (resolved != null) {
                return resolved;
            }
        }

        return new KResource.Empty(path, KResourceUtils.getFilename(path));
    }

    @Override
    public KResource loadResource(final String path, final KProtocol protocol) {
        KResource resolved = protocol.resolve(path);
        if (resolved == null) {
            return new KResource.Empty(path, KResourceUtils.getFilename(path));
        }
        return resolved;
    }

    @Override
    public KResource[] loadResources(final String path) {
        return this.loadResources(path, true);
    }

    @Override
    public KResource[] loadResources(final String path, final KProtocol protocol) {
        return this.loadResources(path, protocol, true);
    }

    @Override
    public KResource[] loadResources(final String path, boolean recursive) {
        for (KProtocol protocol: this.protocols) {
            KResource[] resolved = protocol.resolveMany(path, recursive);
            if (resolved.length > 0) {
                return resolved;
            }
        }

        return new KResource[0];
    }

    @Override
    public KResource[] loadResources(
        final String path,
        final KProtocol protocol,
        boolean recursive
    ) {
        return protocol.resolveMany(path, recursive);
    }
}
