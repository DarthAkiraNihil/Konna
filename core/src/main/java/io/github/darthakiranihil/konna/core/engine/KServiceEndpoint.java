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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signalizes that a method is a service endpoint.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KServiceEndpoint {
    /**
     * Route of the endpoint.
     * It does not include name of the service to endpoint belongs to, same
     * for engine component
     * @return Route of the endpoint.
     */
    String route();

    /**
     * Class that is used to convert a message to array of objects
     * representing called endpoint args. If not specified, a message
     * will be converted to an empty array.
     * @return Class of message to endpoint args converter
     */
    Class<? extends KMessageToEndpointConverter>
        converter() default KMessageToEndpointConverter.NoConverter.class;
}
