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
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@KSingleton(immortal = true)
public final class KClassGraphClasspathSearchEngine
    extends KObject
    implements KClasspathSearchEngine {

    static {
        ClassGraph.CIRCUMVENT_ENCAPSULATION = ClassGraph.CircumventEncapsulationMethod.JVM_DRIVER;
    }

    public KClassGraphClasspathSearchEngine() {
        super(
            "ClassGraphClasspathSearchEngine",
            KStructUtils.setOfTags(KTag.DefaultTags.STD, KTag.DefaultTags.SYSTEM)
        );
    }

    private static final class Query
        implements KClasspathSearchQuery, ClassInfoList.ClassInfoFilter {

        private static final int INITIAL_LIST_PARAMS_CAPACITY = 4;

        private final List<String> searchPackages;
        private final List<Class<? extends Annotation>> filteredAnnotations;
        private final List<Class<?>> implementingInterfaces;

        public Query() {
            this.searchPackages = new ArrayList<>(INITIAL_LIST_PARAMS_CAPACITY);
            this.filteredAnnotations = new ArrayList<>(INITIAL_LIST_PARAMS_CAPACITY);
            this.implementingInterfaces = new ArrayList<>(INITIAL_LIST_PARAMS_CAPACITY);
        }

        @Override
        public KClasspathSearchQuery inPackages(final String... packages) {
            this.searchPackages.addAll(List.of(packages));
            return this;
        }

        @Override
        public KClasspathSearchQuery withAnnotation(final Class<? extends Annotation> annotation) {
            this.filteredAnnotations.add(annotation);
            return this;
        }

        @Override
        public KClasspathSearchQuery implementsInterface(final Class<?> interfaceClass) {
            if (!interfaceClass.isInterface()) {
                throw new KInvalidArgumentException(
                    String.format(
                        "%s in not an interface",
                        interfaceClass
                    )
                );
            }

            this.implementingInterfaces.add(interfaceClass);
            return this;
        }

        @Override
        public KClasspathSearchResult execute() {

            String[] searchPackagesArray = this.searchPackages.toArray(new String[0]);

            ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo() // so far
                .acceptPackages(searchPackagesArray)
                .scan();

            List<KClassInfo> classes = new ArrayList<>(scanResult.getAllClasses().size());

            classes.addAll(
                scanResult
                    .getAllClasses()
                    .filter(this)
                    .stream()
                    .map(ClassGraphClassInfo::new)
                    .toList()
            );

            return new SearchResult(scanResult, classes);
        }

        @Override
        public boolean accept(final ClassInfo classInfo) {

            for (var interfaceClass: this.implementingInterfaces) {
                if (!classInfo.implementsInterface(interfaceClass)) {
                    return false;
                }
            }

            for (var annotation: this.filteredAnnotations) {
                if (!classInfo.hasAnnotation(annotation)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static final class ClassGraphClassInfo implements KClassInfo {

        private final ClassInfo source;

        public ClassGraphClassInfo(final ClassInfo source) {
            this.source = source;
        }

        @Override
        public String getSimpleName() {
            return this.source.getSimpleName();
        }

        @Override
        public String getCanonicalName() {
            return this.source.getName();
        }

        @Override
        public String getPackageName() {
            return this.source.getPackageName();
        }

        @Override
        public Class<?> load() {
            return this.source.loadClass();
        }

    }

    private static final class SearchResult implements KClasspathSearchResult {

        private final ScanResult parent;
        private final List<KClassInfo> classes;
        private @Nullable List<Class<?>> loaded;

        public SearchResult(
            final ScanResult parent,
            final List<KClassInfo> classes
        ) {
            this.parent = parent;
            this.classes = classes;
        }

        @Override
        public List<KClassInfo> getClasses() {
            return this.classes;
        }

        @Override
        public List<Class<?>> loadClasses() {

            if (this.loaded != null) {
                return this.loaded;
            }

            this.loaded = new ArrayList<>(this.classes.size());
            this.classes.forEach(x -> this.loaded.add(x.load()));
            return this.loaded;

        }

        @Override
        public void close() {
            this.parent.close();
        }
    }

    @Override
    public KClasspathSearchQuery query() {
        return new Query();
    }

    @Override
    public KClasspathSearchQuery queryGenerated() {
        return new Query().inPackages(KClasspathSearchEngine.GENERATED_CLASSES_PACKAGE);
    }

}
