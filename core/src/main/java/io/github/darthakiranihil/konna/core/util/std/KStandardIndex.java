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

package io.github.darthakiranihil.konna.core.util.std;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.PackageInfo;
import io.github.classgraph.ScanResult;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KIndex;
import io.github.darthakiranihil.konna.core.util.KIndexedPackage;
import io.github.darthakiranihil.konna.core.util.KStructUtils;

import java.util.*;

/**
 * Standard implementation of {@link KIndex}. It indexes only packages and classes from
 * them that provide {@link KIndexedPackage} annotation.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KStandardIndex extends KObject implements KIndex {

    private final List<Class<?>> classIndex;
    private final List<Package> packageIndex;

    public KStandardIndex() {
        super("index", KStructUtils.setOfTags(KTag.DefaultTags.STD, KTag.DefaultTags.SYSTEM));

        this.classIndex = new ArrayList<>();
        this.packageIndex = new ArrayList<>();

        List<PackageInfo> explicitIndexed;
        try (
            ScanResult scanResult = new ClassGraph().enableAllInfo().scan()
        ) {
            explicitIndexed = scanResult
                .getPackageInfo()
                .stream()
                .filter((x) -> x.hasAnnotation(KIndexedPackage.class))
                .toList();
        }

        try (ScanResult scanResult =
            new ClassGraph()
                .enableAllInfo()
                .acceptPackages(
                    explicitIndexed
                        .stream()
                        .map(PackageInfo::getName)
                        .toList()
                        .toArray(new String[0])
                )
                .scan()
        ) {

            scanResult
                .getAllClasses()
                .forEach((x) -> this.classIndex.add(x.loadClass()));

        }

        String[] explicitIndexedPackageNames = explicitIndexed
            .stream()
            .map(PackageInfo::getName)
            .toList()
            .toArray(new String[0]);

        try (
            ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(explicitIndexedPackageNames)
                .scan()
        ) {

            List<String> foundPackages = scanResult
                .getPackageInfo()
                .stream()
                .map(PackageInfo::getName)
                .toList();

            this.packageIndex.addAll(
                this.classIndex
                    .stream()
                    .map(Class::getPackage)
                    .distinct()
                    .filter((x) -> foundPackages.contains(x.getName()))
                    .toList()
            );
        }
    }

    @Override
    public List<Class<?>> getClassIndex() {
        return Collections.unmodifiableList(this.classIndex);
    }

    @Override
    public List<Package> getPackageIndex() {
        return Collections.unmodifiableList(this.packageIndex);
    }
}
