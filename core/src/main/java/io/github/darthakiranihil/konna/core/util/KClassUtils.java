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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.darthakiranihil.konna.core.except.KClassNotFoundException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class that provides different useful class utils.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KClassUtils extends KUninstantiable {

    private KClassUtils() {
        super();
    }

    /**
     * Returns a list of annotated classes from the class index.
     * @param index Index
     * @param annotation Annotation to look for
     * @return List of classes with specified annotation
     */
    public static List<Class<?>> getAnnotatedClasses(
        final KIndex index,
        final Class<? extends Annotation> annotation
    ) {
        return index
            .getClassIndex()
            .stream()
            .filter((c) -> c.isAnnotationPresent(annotation))
            .toList();
    }

    /**
     * Returns a list of real classes in specific packages. A real class is not abstract,
     * an interface, an annotation, a record or an enum.
     * @param packages Packages where to look for.
     * @return List of real classes.
     */
    public static List<Class<?>> getRealClassesInPackages(final Set<String> packages) {
        try (ScanResult scanResult = new ClassGraph()
            .enableAllInfo()
            .acceptPackages(packages.toArray(new String[0]))
            .scan()
        ) {
            List<Class<?>> classes = new ArrayList<>();
            scanResult
                .getAllClasses()
                .stream()
                .filter((c) -> !(
                    c.isInterface()
                        ||  c.isAbstract()
                        ||  c.isAnnotation()
                        ||  c.isRecord()
                        ||  c.isEnum()
                ))
                .forEach((c) -> classes.add(c.loadClass()));
            return classes;
        }
    }

    /**
     * Convenience method to get class by its name without forced
     * checked exception catching.
     * @param name Name of the class
     * @return Class object by passed name
     * @throws KException if class is not found
     * @since 0.4.0
     */
    public static Class<?> getForName(final @Nullable String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new KClassNotFoundException(e.getMessage());
        }
    }

}
