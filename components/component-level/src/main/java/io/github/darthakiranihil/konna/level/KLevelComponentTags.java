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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

/**
 * Utility class that provides tags, specific for Level component's objects.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLevelComponentTags extends KUninstantiable {

    private KLevelComponentTags() {
        super();
    }

    /**
     * Indicates that the object is a map sector.
     */
    public static final KTag SECTOR = new KTag("sector");
    /**
     * Indicates that the object is a level.
     */
    public static final KTag LEVEL = new KTag("level");
    /**
     * Indicates that the object is an autonomous entity controller.
     */
    public static final KTag CONTROLLER = new KTag("controller");

}
