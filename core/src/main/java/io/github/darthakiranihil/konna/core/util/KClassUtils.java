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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class KClassUtils extends KUninstantiable {

    private KClassUtils() {
        super();
    }

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

}
