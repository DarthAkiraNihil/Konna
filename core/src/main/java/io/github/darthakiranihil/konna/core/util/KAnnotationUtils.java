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
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Class that provides different useful annotation utils.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KAnnotationUtils extends KUninstantiable {

    private KAnnotationUtils() {
        super();
    }

    /**
     * Searches for classes with specific annotation in the provided index.
     * @param index Index to look for classes in
     * @param annotation Annotation that class must have
     * @return List of annotated classes
     */
    public static List<Class<?>> findAnnotatedClasses(
        final KIndex index,
        final Class<? extends Annotation> annotation
    ) {

        return KAnnotationUtils.findAnnotatedClasses(
            index.getPackageIndex().stream().map(Package::getName).toList(),
            annotation
        );

    }

    /**
     * Searches for classes with specific annotation in given package.
     * @param index Index to look for classes in
     * @param packageName Package to search in
     * @param annotation Annotation that class must have
     * @return List of annotated classes
     */
    public static List<Class<?>> findAnnotatedClasses(
        final KIndex index,
        final String packageName,
        final Class<? extends Annotation> annotation
    ) {

        return KAnnotationUtils.findAnnotatedClasses(
            List.of(packageName),
            annotation
        );

    }

    /**
     * Searches for classes with specific annotation in specified package list.
     * @param annotation Annotation that class must have
     * @param packages List of packages to look classes for
     * @return List of annotated classes
     */
    public static List<Class<?>> findAnnotatedClasses(
        final List<String> packages,
        final Class<? extends Annotation> annotation
    ) {

        List<Class<?>> annotatedClasses = new LinkedList<>();

        try (ScanResult scanResult =
            new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(packages.toArray(new String[0]))
                .scan()
        ) {

            scanResult
                .getAllClasses()
                .stream()
                .filter((x) -> x.hasAnnotation(annotation.getName()))
                .toList()
                .forEach((x) -> annotatedClasses.add(x.loadClass())
                );

            return annotatedClasses;

        }

    }

}
