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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for Konna engine component.
 *
 * @since 0.2.0
 * @author DarthAkiraNihil
 */
public abstract class KComponent {

    /**
     * List of component services.
     */
    protected final List<Object> services;

    public KComponent(
        final String servicesPackage,
        final KJsonValue config
    ) throws KComponentLoadingException {


        List<Class<?>> serviceClasses;
        try {
            serviceClasses = KAnnotationUtils.findAnnotatedClasses(
                servicesPackage, KComponentService.class
            );
        } catch (ClassNotFoundException | IOException e) {
            throw new KComponentLoadingException(e);
        }

        List<Object> instantiatedServices = new ArrayList<>();
        try {
            for (var serviceClass: serviceClasses) {
                var serviceConstructor = serviceClass.getConstructor();
                instantiatedServices.add(
                    serviceConstructor.newInstance()
                );
            }
        } catch (
                NoSuchMethodException
            |   InstantiationException
            |   IllegalAccessException
            |   InvocationTargetException e
        ) {
            throw new KComponentLoadingException(e);
        }

        this.services = instantiatedServices;
        this.applyConfig(config);
    }

    protected abstract void applyConfig(KJsonValue config);
}
