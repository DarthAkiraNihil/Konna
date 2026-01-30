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

package io.github.darthakiranihil.konna.graphics;

/**
 * Interface representation of a transformable object that
 * can be rotated, scaled and translated in render space.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("UnusedReturnValue")
public interface KTransformable {

    /**
     * Returns the transform of this object.
     * @return Transform of this object
     */
    KTransform getTransform();

    /**
     * Sets a specific transform to this object.
     * @param newTransform New transform
     */
    void setTransform(KTransform newTransform);

}
