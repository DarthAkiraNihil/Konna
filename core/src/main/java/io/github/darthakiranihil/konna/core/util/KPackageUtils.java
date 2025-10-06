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

import io.github.darthakiranihil.konna.core.object.KUninstantiable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that provides different useful package utils.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KPackageUtils extends KUninstantiable {

    private static List<String> indexedPackageNames;
    private static List<Package> packageIndex;

    private KPackageUtils() {
        super();
    }

    static {
        KPackageUtils.packageIndex = Arrays
            .stream(Package.getPackages())
            .filter((p) -> {
                System.out.println(p);
                return p.isAnnotationPresent(KIndexedPackage.class);
            })
            .toList();

        System.out.println(KPackageUtils.packageIndex);

        KPackageUtils.indexedPackageNames =  KPackageUtils.packageIndex
            .stream()
            .map(Package::getName)
            .toList();
    }

    /**
     * Returns list of package names that are used in the application.
     * Does not return packages, that are likely system:
     * their name starts with java, jdk, sun or com.sun.
     * @return List of package names used in the application excluding java packages
     */
    public static List<String> getIndexedPackagesNames() {
        return Collections.unmodifiableList(KPackageUtils.indexedPackageNames);
    }

    /**
     * Returns list of packages that are used in the application.
     * Does not return packages, that are likely system:
     * their name starts with java, jdk, sun or com.sun.
     * @return List of packages used in the application excluding java packages
     */
    public static List<Package> getPackageIndex() {
        return Collections.unmodifiableList(KPackageUtils.packageIndex);
    }

}
