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

import java.io.Closeable;
import java.util.List;

/**
 * Resource interface for classpath search results.
 */
public interface KClasspathSearchResult extends Closeable {

    /**
     * @return List of class infos of all found classes
     */
    List<KClassInfo> getClasses();

    /**
     * Loads all classes that have been found. Alternatively you can call
     * {@link KClasspathSearchResult#getClasses()} and call {@link KClassInfo#load()}
     * on all elements. Should be used only if you really need loaded classes, not
     * just their information.
     * @return List of all found loaded classes
     */
    List<Class<?>> loadClasses();

    /**
     * Cleans all temporal resources, that might be used by search engine
     * when looking for classes.
     */
    @Override
    void close();
}
