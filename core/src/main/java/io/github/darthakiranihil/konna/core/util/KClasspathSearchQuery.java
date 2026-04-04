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

import java.lang.annotation.Annotation;

/**
 * Interface providing a fluent interface for constructing classpath
 * search queries.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KClasspathSearchQuery {

    /**
     * Adds a filter to find classes in specified packages.
     * @param packages Packages to find classes in
     * @return This query (for method chaining)
     */
    KClasspathSearchQuery inPackages(String... packages);

    /**
     * Adds a filter to find classes that contain specified annotation.
     * @param annotation Annotation to be contained in found classes
     * @return This query (for method chaining)
     */
    KClasspathSearchQuery withAnnotation(Class<? extends Annotation> annotation);

    /**
     * Adds a filter to find classes that implement specific interface.
     * @param interfaceClass Interface to be implemented by found classes
     * @return This query (for method chaining)
     */
    KClasspathSearchQuery implementsInterface(Class<?> interfaceClass);

    /**
     * Executes search.
     * @return Search result
     */
    KClasspathSearchResult execute();

}
