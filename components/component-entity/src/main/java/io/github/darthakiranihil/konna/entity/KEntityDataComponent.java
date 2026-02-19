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

package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.except.KUnsupportedOperationException;

import java.lang.reflect.Proxy;

/**
 * Simple marking interface, used to show that this class contains
 * some data, suitable to be used as entity data.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public interface KEntityDataComponent extends Cloneable {

    KEntityDataComponent clone();
    default KEntityDataComponent readonlyClone() {

        KEntityDataComponent cloned = this.clone();

        return (KEntityDataComponent) Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(),
            new Class[] { KEntityDataComponent.class },
            (proxy, method, args) -> {
                if (method.getName().startsWith("set")) {
                    throw new KUnsupportedOperationException(
                        "This entity data component is read-only"
                    );
                }

                return method.invoke(cloned, args);
            }
        );

    }

}
