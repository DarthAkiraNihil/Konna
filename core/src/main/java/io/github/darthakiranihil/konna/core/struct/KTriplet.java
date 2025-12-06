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

package io.github.darthakiranihil.konna.core.struct;

/**
 * Represents an immutable group of three values of different types like a tuple.
 * @param first The first element of the triplet
 * @param second The second element of the triplet
 * @param third The third element of the triplet
 * @param <F> Type of the first element
 * @param <S> Type of the second element
 * @param <T> Type of the third element
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public record KTriplet<F, S, T>(F first, S second, T third) {

}
