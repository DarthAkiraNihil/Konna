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
import java.util.Collections;
import java.util.List;

public final class KClassUtils extends KUninstantiable {

    private static final List<Class<?>> CLASS_INDEX = new ArrayList<>();

    private KClassUtils() {
        super();
    }

    static {

        try (ScanResult scanResult =
            new ClassGraph()
                .enableAllInfo()
                .acceptPackages(KPackageUtils.getIndexedPackagesNames().toArray(new String[0]))
                .scan()
        ) {

            scanResult
                .getAllClasses()
                .forEach((x) -> {
                    System.out.println(x);
                    KClassUtils.CLASS_INDEX.add(x.loadClass());
                });

        }

    }

    public static List<Class<?>> getClassIndex() {
        return Collections.unmodifiableList(KClassUtils.CLASS_INDEX);
    }

    public static List<Class<?>> getAnnotatedClasses(final Class<? extends Annotation> annotation) {
        return KClassUtils
            .CLASS_INDEX
            .stream()
            .filter((c) -> c.isAnnotationPresent(annotation))
            .toList();
    }

}
