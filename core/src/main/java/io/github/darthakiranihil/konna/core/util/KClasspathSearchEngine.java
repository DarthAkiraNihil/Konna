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

/**
 * Interface that provides methods for searching classes inside classpath.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KClasspathSearchEngine {

    /**
     * Name of package prefix, where all generated classes should be located.
     * It's not required, though.
     */
    String GENERATED_CLASSES_PACKAGE = "konna.generated";

    /**
     * Creates a general search query.
     * @return New search query
     */
    KClasspathSearchQuery query();

    /**
     * Creates a search query to find classes in
     * {@link KClasspathSearchEngine#GENERATED_CLASSES_PACKAGE}.
     * All queries constructed with this method will search classes only in generated
     * classes package and the only (if others are not passes explicitly)
     * @return New search query
     */
    KClasspathSearchQuery queryGenerated();

}
