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

package io.github.darthakiranihil.konna.core.di;

import io.github.darthakiranihil.konna.core.except.KUnknownException;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.core.util.KPair;
import io.github.darthakiranihil.konna.core.util.KTriplet;
import io.github.darthakiranihil.konna.core.util.index.KPackageIndex;

import java.util.*;

/*
получаем список пакетов
из них выбираем те, что определяют среду
затем из них смотрим, не определяются ли подсреды
собираем дерево сред
для каждого дерева проходимся по всем классам в пакете и подпакетах, игнорируя подсреды,
собираем примитивные зависимости
после проходимся по дереву из корня и формируем для каждого узла контейнер
не забываем проиндексировать, чтобы потом получать среду для класса максимально быстро

 */
public final class KMasterContainer extends KUninstantiable {

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



    private static final Map<String, KContainer> ENV_2_CONTAINER = new HashMap<>();
    private static final Map<String, String> PACKAGE_2_ENV = new HashMap<>();

    static {
        List<KTriplet<String, String, Boolean>>
            indexedPackages = KMasterContainer.collectIndexedPackages();

        List<PackageEnvRecord>
            records = KMasterContainer.createEnvRecords(indexedPackages);

        KMasterContainer.processEnvironmentDefiningPackages(records);
        KMasterContainer.processEmptyEnvironments(records);

        Map<String, KPair<Set<String>, String>>
            groupedEnvironments = KMasterContainer.groupEnvironmentRecords(records);
        Map<String, KContainer> containers = KMasterContainer.buildContainers(groupedEnvironments);

        KMasterContainer.ENV_2_CONTAINER.putAll(containers);
        groupedEnvironments.forEach(
            (k, v) -> v.first().forEach((p) -> KMasterContainer.PACKAGE_2_ENV.put(p, k))
        );
    }

    public static KContainer getMaster() {
        var stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length == 0) {
            return KMasterContainer.ENV_2_CONTAINER.get("");
        }

        try {
            Class<?> callerClass = Class.forName(stackTrace[2].getClassName());
            if (callerClass == KActivator.class) {
                callerClass = Class.forName(
                    stackTrace[KMasterContainer.CLASS_BEFORE_ACTIVATOR].getClassName()
                );
            }

            KContainer retrieved = KMasterContainer.ENV_2_CONTAINER.get(
                KMasterContainer.PACKAGE_2_ENV.get(
                    callerClass
                        .getPackage()
                        .getName()
                )
            );
            if (retrieved == null) {
                retrieved = KMasterContainer.ENV_2_CONTAINER.get("");
            }

            if (!callerClass.isAnnotationPresent(KMasterContainerModifier.class)) {
                return new KImmutableContainer(retrieved);
            }

            return retrieved;
        } catch (Throwable e) {
            throw new KUnknownException(e);
        }
    }

    private KMasterContainer() {
        super();
    }

    private static List<KTriplet<String, String, Boolean>> collectIndexedPackages() {
        return KPackageIndex
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

    private static List<PackageEnvRecord> createEnvRecords(
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

    private static void processEnvironmentDefiningPackages(
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

    private static void processEmptyEnvironments(
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
                emptyPackage.name = String.format("env@%d", PackageEnvRecord.unnamedEnvironmentsCreated++);
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

    private static Map<String, KPair<Set<String>, String>> groupEnvironmentRecords(
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

    private static Map<String, KContainer> buildContainers(
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
