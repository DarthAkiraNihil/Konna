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

package io.github.darthakiranihil.konna.core.object;

/**
 * <p>
 *     Interface for objects that can be deleted somehow, or, more correct,
 *     can be set to the state in that an object can be claimed by the GC.
 * </p>
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KDeletable {

    /**
     * <p>
     *     "Deletes" this object
     * </p>
     * <p>
     *     In this exact context, "deleting" an object does not actually perform it, but prepares
     *     object to be claimed by a GC. For example, if the object holds native resources,
     *     provides a strong reference to other objects and the like.
     * </p>
     * <p>
     *     An implementation of this method should work correctly if it's called <i>twice</i>.
     *     The common handling of this situation is literally doing nothing.
     * </p>
     * <p>
     *     Actually, it is not restricted to used "deleted" object, but it's not recommended,
     *     because deleted object is usually in an invalid state. Implementation may forbid calling
     *     all deleted object methods by throwing
     *     {@link io.github.darthakiranihil.konna.core.except.KIllegalStateException} or something
     *     like that.
     * </p>
     */
    default void delete() {

    }

}
