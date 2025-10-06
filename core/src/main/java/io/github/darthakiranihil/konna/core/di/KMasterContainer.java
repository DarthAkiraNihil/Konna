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
public final class KMasterContainer {

    private static final KContainer CONTAINER = new KContainer();

    public static KContainer getMaster() {
        List<String> packages = KPackageUtils.getAllPackageNames();
        List<Class<?>> classes;
        try {
            classes = KAnnotationUtils.findAnnotatedClasses(packages, null);
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

        return KMasterContainer.CONTAINER;
    }

    private KMasterContainer() {

    }



}
