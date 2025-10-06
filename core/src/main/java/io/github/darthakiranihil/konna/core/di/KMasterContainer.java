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

import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;
import io.github.darthakiranihil.konna.core.util.KPackageUtils;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;

public final class KMasterContainer extends KContainer {

    private static final KContainer CONTAINER = new KContainer();

    static {

        List<String> packages = KPackageUtils.getAllPackageNames();
        List<Class<?>> classes;
        try {
            classes = KAnnotationUtils.findAnnotatedClasses(null, packages);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (var clazz: classes) {
            if (
                    clazz.isInterface()
                ||  Modifier.isAbstract(clazz.getModifiers())
                ||  clazz.isRecord()
                ||  clazz.isAnnotation()
                ||  clazz.isEnum()
            ) {
                continue;
            }

            KMasterContainer.CONTAINER.add(clazz);
        }

    }

    public static KContainer getMaster() {
        return KMasterContainer.CONTAINER;
    }



}
