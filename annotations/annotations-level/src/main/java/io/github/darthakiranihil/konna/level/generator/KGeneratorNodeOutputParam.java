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

package io.github.darthakiranihil.konna.level.generator;

import java.lang.annotation.*;

/**
 * <p>
 *     Annotation that specifies output parameter for a generator node.
 * </p>
 * <p>
 *     Usually it should be handled by annotation processor that generates
 *     corresponding validator that checks output params structure according to provided output
 *     params description.
 * </p>
 * <p>
 *     So far it is not required to be exact in {@code process} method of a generator node,
 *     though applying to other methods of other classes is senseless.
 * </p>
 * <p>
 *     Each node must specify at least one output parameter
 *     in order to be used in level generators.
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Repeatable(KGeneratorNodeOutputParams.class)
public @interface KGeneratorNodeOutputParam {

    /**
     * @return Parameter name
     */
    String name();

    /**
     * @return Parameter type
     */
    Class<?> type();

}
