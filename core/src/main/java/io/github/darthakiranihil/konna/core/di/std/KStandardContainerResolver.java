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

package io.github.darthakiranihil.konna.core.di.std;

import io.github.darthakiranihil.konna.core.di.*;
import io.github.darthakiranihil.konna.core.except.KUnknownException;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.core.util.*;

import java.util.*;

/**
 * Standard implementation of {@link KContainerResolver}.
 * It actively uses {@link KPackageEnvironment} data to define package environments, that are
 * resolved depending on the caller class. It requires a built package and class index to
 * create a package-to-environment mapping.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KStandardContainerResolver extends KObject implements KContainerResolver {

    private static final int CLASS_BEFORE_ACTIVATOR = 3;

    private static final class PackageEnvRecord {

        private static int unnamedEnvironmentsCreated = 0;

        private String name;
        private String parentEnvironmentName;
        private final String packageName;
        private final int packageNameLength;
        private boolean isDefiningEnvironment;

        PackageEnvRecord(
            final String name,
            final String packageName,
            boolean isDefiningEnvironment
        ) {
            this.name = name;
            this.packageName = packageName;
            this.packageNameLength = packageName.split("\\.").length;
            this.isDefiningEnvironment = isDefiningEnvironment;

            this.parentEnvironmentName = "";
        }
    }

    private final KIndex index;
    private final Map<String, KContainer> env2container;
    private final Map<String, String> package2env;

    /**
     * Standard constructor.
     * On initialization, the resolver collects all indexed packages and creates corresponding
     * internal environment records for them. After that it processes packages that define
     * an environment, then it continues with processing empty environments, the resolver
     * define a new environment for each subpackage of the top-level packages. Then it
     * groups all records by environment name and builds container according to group result.
     * @param index Built system index (must contain complete package and class list)
     */
    public KStandardContainerResolver(final KIndex index) {
        super("container_resolver", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.index = index;
        this.addTag(KTag.DefaultTags.STD);

        this.env2container = new HashMap<>();
        this.package2env = new HashMap<>();

        List<KTriplet<String, String, Boolean>>
            indexedPackages = this.collectIndexedPackages();

        List<PackageEnvRecord>
            records = this.createEnvRecords(indexedPackages);

        this.processEnvironmentDefiningPackages(records);
        this.processEmptyEnvironments(records);

        Map<String, KPair<Set<String>, String>>
            groupedEnvironments = this.groupEnvironmentRecords(records);
        Map<String, KContainer> containers = this.buildContainers(groupedEnvironments);

        this.env2container.putAll(containers);
        groupedEnvironments.forEach(
            (k, v) -> v.first().forEach((p) -> this.package2env.put(p, k))
        );
    }

    /**
     * Returns the container according to the caller class. Container resolution
     * is connected with the package environment of the package of the caller class.
     * If caller class cannot be got, the root container is returned. If the caller
     * class is an instance of {@link KActivator}, the container will be resolved for
     * the caller class of the activator.
     * If the caller class does not have {@link KEnvironmentContainerModifier},
     * an {@link KImmutableContainer} will be returned instead of regular {@link KContainer}.
     *
     * @return Container the for caller class
     */
    @Override
    public KContainer resolve() {
        var stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length == 0) {
            return this.env2container.get("");
        }

        try {
            Class<?> callerClass = Class.forName(stackTrace[2].getClassName());
            if (callerClass == KActivator.class) {
                callerClass = Class.forName(
                    stackTrace[KStandardContainerResolver.CLASS_BEFORE_ACTIVATOR].getClassName()
                );
            }

            KContainer retrieved = this.env2container.get(
                this.package2env.get(
                    callerClass
                        .getPackage()
                        .getName()
                )
            );
            if (retrieved == null) {
                retrieved = this.env2container.get("");
            }

            if (!callerClass.isAnnotationPresent(KEnvironmentContainerModifier.class)) {
                return new KImmutableContainer(retrieved);
            }

            return retrieved;
        } catch (Throwable e) {
            throw new KUnknownException(e);
        }
    }

    @Override
    public List<KPair<String, List<String>>> getEnvironments() {
        return this
            .env2container
            .keySet()
            .stream()
            .map(
                container -> new KPair<>(
                    container,
                    this
                        .package2env
                        .entrySet()
                        .stream()
                        .filter(
                            (p) -> p
                                .getValue()
                                .equals(container)
                        )
                        .map(Map.Entry::getKey)
                        .toList()
                )
            )
            .toList();
    }

    private List<KTriplet<String, String, Boolean>> collectIndexedPackages() {
        return this
            .index
            .getPackageIndex()
            .stream()
            .map((x) -> {
                if (!x.isAnnotationPresent(KPackageEnvironment.class)) {
                    return new KTriplet<>(x.getName(), "", false);
                }

                var meta = x.getAnnotation(KPackageEnvironment.class);
                return new KTriplet<>(x.getName(), meta.name(), true);
            })
            .toList();
    }

    private List<PackageEnvRecord> createEnvRecords(
        final List<KTriplet<String, String, Boolean>> indexedPackages
    ) {
        List<PackageEnvRecord> records = new ArrayList<>(indexedPackages.size());
        for (var indexedPackage: indexedPackages) {
            records.add(
                new PackageEnvRecord(
                    indexedPackage.second(),
                    indexedPackage.first(),
                    indexedPackage.third()
                )
            );
        }
        return records;
    }

    private void processEnvironmentDefiningPackages(
        final List<PackageEnvRecord> records
    ) {
        var environmentDefiningPackages = records
            .stream()
            .filter((x) -> !x.name.isEmpty())
            .sorted(Comparator.comparingInt(x -> x.packageNameLength))
            .map((x) -> new PackageEnvRecord(x.name, x.packageName, x.isDefiningEnvironment))
            .toList();

        for (PackageEnvRecord environmentDefiningPackage: environmentDefiningPackages) {
            records.forEach((r) -> {
                if (
                    r.packageName.equals(environmentDefiningPackage.packageName)
                        ||  !r.packageName.startsWith(environmentDefiningPackage.packageName)
                ) {
                    return;
                }

                if (r.isDefiningEnvironment) {
                    r.parentEnvironmentName = environmentDefiningPackage.name;
                    return;
                }

                r.name = environmentDefiningPackage.name;
            });
        }
    }

    @KExcludeFromGeneratedCoverageReport
    private void processEmptyEnvironments(
        final List<PackageEnvRecord> records
    ) {
        var minimumUnnamedEnvPackageLength = records
            .stream()
            .filter((x) -> x.name.isEmpty())
            .map((x) -> x.packageNameLength)
            .min(Comparator.comparingInt((x) -> x));

        while (minimumUnnamedEnvPackageLength.isPresent()) {
            var minLength = minimumUnnamedEnvPackageLength.get();

            var emptyPackages = records
                .stream()
                .filter((x) -> x.name.isEmpty() && x.packageNameLength == minLength)
                .toList();

            for (PackageEnvRecord emptyPackage: emptyPackages) {
                emptyPackage.name = String.format(
                    "env@%d",
                    PackageEnvRecord.unnamedEnvironmentsCreated++
                );
                emptyPackage.isDefiningEnvironment = true;
                records.forEach((r) -> {
                    if (
                        r.packageName.equals(emptyPackage.packageName)
                            ||  !r.packageName.startsWith(emptyPackage.packageName)
                    ) {
                        return;
                    }

                    if (r.isDefiningEnvironment) {
                        r.parentEnvironmentName = emptyPackage.name;
                        return;
                    }

                    r.name = emptyPackage.name;
                });
            }

            minimumUnnamedEnvPackageLength = records
                .stream()
                .filter((x) -> x.name.isEmpty())
                .map((x) -> x.packageNameLength)
                .min(Comparator.comparingInt((x) -> x));
        }
    }

    private Map<String, KPair<Set<String>, String>> groupEnvironmentRecords(
        final List<PackageEnvRecord> records
    ) {
        Map<String, KPair<Set<String>, String>> groupResult = new HashMap<>();
        for (var record: records) {
            if (groupResult.containsKey(record.name)) {
                var value = groupResult.get(record.name);
                value.first().add(record.packageName);
                continue;
            }

            Set<String> packages = new HashSet<>();
            packages.add(record.packageName);
            groupResult.put(
                record.name, new KPair<>(packages, record.parentEnvironmentName)
            );
        }
        return groupResult;
    }

    private Map<String, KContainer> buildContainers(
        final Map<String, KPair<Set<String>, String>> environments
    ) {
        Map<String, KContainer> containers = new HashMap<>(environments.size());
        KContainer root = new KContainer("root_container");
        containers.put("", root);

        environments
            .entrySet()
            .stream()
            .filter((e) -> e.getValue().second().isEmpty())
            .forEach((e) -> {
                String containerName = String.format("container_of_environment_%s", e.getKey());
                KContainer container = new KContainer(root, containerName);
                KClassUtils
                    .getRealClassesInPackages(e.getValue().first())
                    .forEach(container::add);
                containers.put(e.getKey(), container);
            });

        environments
            .entrySet()
            .stream()
            .filter((e) -> !e.getValue().second().isEmpty())
            .forEach((e) -> {
                String containerName = String.format("container_of_environment_%s", e.getKey());
                KContainer parent = containers.get(e.getValue().second());
                KContainer container = new KContainer(parent, containerName);
                KClassUtils
                    .getRealClassesInPackages(e.getValue().first())
                    .forEach(container::add);
                containers.put(e.getKey(), container);
            });

        return containers;
    }

}
