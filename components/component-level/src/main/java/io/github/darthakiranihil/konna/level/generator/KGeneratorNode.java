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

package io.github.darthakiranihil.konna.level.generator;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;

import java.util.Random;

/**
 * Interface for an elementary functional unit in {@link KLevelGenerator}
 * that takes input parameters and a {@link Random} instance to return some data in output
 * to be transported to other nodes.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KGeneratorNode {

    /**
     * Runs processing for this node.
     * @param params Input parameters for processing
     * @param rnd {@link Random} instance created in this generation session
     * @return Processing result to be handled by other nodes
     */
    KUniversalMap process(KUniversalMap params, Random rnd);

}
