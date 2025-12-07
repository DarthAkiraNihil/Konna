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
import io.github.darthakiranihil.konna.core.io.std.resource.KClasspathResource;

import java.io.InputStream;

public final class KClasspathProtocol implements KProtocol {

    private static final String PREFIX = "classpath:";

    private final ClassLoader classLoader;

    public KClasspathProtocol(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public KResource resolve(String path) {
        if (!path.startsWith(PREFIX)) {
            return null;
        }

        String realPath = path.substring(PREFIX.length());
        String name = KResourceUtils.getFilename(path);
        InputStream is = this.classLoader.getResourceAsStream(realPath);

        return new KClasspathResource(
            realPath,
            name,
            is
        );
    }
}
