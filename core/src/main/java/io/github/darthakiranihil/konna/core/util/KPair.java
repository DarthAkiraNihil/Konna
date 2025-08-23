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

/**
 * Represents an immutable pair of values of different types like a tuple.
 * @param first The first element of the pair
 * @param second The second element of the pair
 * @param <F> Type of the first pair
 * @param <S> Type of the second pair
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public record KPair<F, S>(F first, S second) {

}
