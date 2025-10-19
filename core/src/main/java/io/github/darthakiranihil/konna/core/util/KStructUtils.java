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

package io.github.darthakiranihil.konna.core.util;

import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides different useful methods, connected with data structures.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KStructUtils extends KUninstantiable {

    private KStructUtils() {
        super();
    }

    /**
     * Puts given tag array into a set and returns it.
     * @param tags List of tags
     * @return Set of given tags
     */
    public static Set<KTag> setOfTags(final KTag... tags) {
        return new HashSet<>(List.of(tags));
    }

}
