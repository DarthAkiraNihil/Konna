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

package io.github.darthakiranihil.konna.core.di;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KIndex;
import io.github.darthakiranihil.konna.core.util.KPair;

import java.util.*;

/**
 * A base class for a container resolver - an essential class, that is designed
 * to return a {@link KContainer} depending on the environment of the caller class.
 *
 * @since 0.2.0
 * @author Dartk Akira Nihil
 *
 * @see KPackageEnvironment
 * @see KEnvironmentContainerModifier
 */
public abstract class KContainerResolver extends KObject {

    /**
     * System index.
     */
    protected KIndex index;

    /**
     * Base constructor.
     * @param index Built system index (must contain complete package and class list)
     */
    public KContainerResolver(final KIndex index) {
        super("container_resolver", new HashSet<>(List.of(KTag.DefaultTags.SYSTEM)));
        this.index = index;
    }

    /**
     * Returns the container according to the caller class. Container resolution
     * depends on implementation.
     * @return Container the for caller class
     */
    public abstract KContainer resolve();

    /**
     * Returns list of environment data, containing name of an environment and list of its packages.
     * @return Data of created environments.
     */
    public abstract List<KPair<String, List<String>>> getEnvironments();

}
