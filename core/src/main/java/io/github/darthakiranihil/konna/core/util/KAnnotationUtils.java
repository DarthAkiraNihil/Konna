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

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Class that provides different useful annotation utils.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KAnnotationUtils {

    private static
    final int CLASS_EXT_LENGTH = 6;

    private KAnnotationUtils() { }

    /**
     * Searches for classes with specific annotation in given package.
     * @param packageName Package to search in
     * @param annotation Annotation that class must have
     * @return List of annotated classes
     * @throws ClassNotFoundException If it somehow fails to find a class in a package
     * @throws IOException If resources failed to read
     */
    public static List<Class<?>> findAnnotatedClasses(
        final String packageName,
        final Class<? extends Annotation> annotation
    ) throws ClassNotFoundException, IOException {

        List<Class<?>> annotatedClasses = new ArrayList<>();
        String path = packageName.replace('.', '/');

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            if (!directory.exists()) {
                continue;
            }

            File[] files = directory.listFiles();
            if (files == null) {
                continue;
            }

            for (File file : files) {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }

                String classFilename = file.getName().substring(
                    0, file.getName().length() - CLASS_EXT_LENGTH
                );

                String className = packageName + "." + classFilename;
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(annotation)) {
                    annotatedClasses.add(clazz);
                }

            }

        }
        return annotatedClasses;
    }

}
