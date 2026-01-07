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

package io.github.darthakiranihil.konna.core.io;

/**
 * Interface of simple collection of assets of concrete type. It is supposed to
 * be used in components for handling internal asset types. However, it is useful
 * in tunnels to transform assets from one type to another. This interface should not be
 * used as is, only its implementations.
 *
 * @param <T> Asset class type
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KAssetCollection<T> {

    /**
     * Returns built asset class object by its asset id.
     * @param assetId Asset id of building object
     * @return Built asset object of concrete type
     */
    T getAsset(String assetId);

}
