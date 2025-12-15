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

package io.github.darthakiranihil.konna.core.app;

import java.util.List;

/**
 * Interface for an argument parser which purpose is
 * to take args passed to {@code main} method, validate them
 * and build corresponding application features, that may be references by
 * any part of Konna system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KArgumentParser {

    /**
     * Parses application arguments and builds application features.
     * @param args Args, passed to {@code main} method
     * @param options Parsing options
     * @return Built application features
     */
    KApplicationFeatures parse(String[] args, List<KApplicationArgument> options);

}
