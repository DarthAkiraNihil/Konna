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

package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.object.KUninstantiable;

/**
 * Class that provides different useful resource utils.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KResourceUtils extends KUninstantiable {

    private KResourceUtils() {
        super();
    }

    /**
     * Returns filename from path of the file.
     * @param path Path to the file.
     * @return Filename path from the path
     */
    public static String getFilename(final String path) {
        String[] tokens = path.split("[\\\\|/:]");
        return tokens[tokens.length - 1];
    }

}
