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

import io.github.darthakiranihil.konna.core.engine.except.KEndpointRoutingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class KServiceEntry {

    private final Object service;
    private final Map<String, Method> endpoints;

    public KServiceEntry(final Object service, final Map<String, Method> endpoints) {
        this.service = service;
        this.endpoints = endpoints;
    }

    public void callEndpoint(final String route, final Object... args) throws KEndpointRoutingException {
        if (!this.endpoints.containsKey(route)) {
            throw new KEndpointRoutingException(
                String.format(
                    "Cannot call non-existent endpoint: %s",
                    route
                )
            );
        }

        try {
            this.endpoints.get(route).invoke(this.service, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
