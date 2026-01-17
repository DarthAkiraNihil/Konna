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

import org.jspecify.annotations.Nullable;

import java.io.Closeable;

/**
 * Simple cache interface that does not depend on actual cache
 * framework, used in a project.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KCache extends Closeable {

    /**
     * Constant for TTL that does not expire
     * (so a cache record put with this TTL won't be removed).
     */
    long TTL_EVERLASTING = -1;

    /**
     * Puts object to cache.
     * @param key Cache key
     * @param obj Object to put to the cache
     * @param ttl Key's TTL
     * @param <T> Type of cached object
     */
    <T> void putToCache(String key, T obj, long ttl);

    /**
     * Puts object to cache with default TTL (depends on implementation).
     * @param key Cache key
     * @param obj Object to put to the cache
     * @param <T> Type of cached object
     */
    <T> void putToCache(String key, T obj);

    /**
     * Puts object to cache with a disposer, using on key eviction.
     * @param key Cache key
     * @param obj Object to put to the cache
     * @param disposer Disposer for this key on eviction (used on value)
     * @param ttl Key's TTL
     * @param <T> Type of cached object
     */
    <T> void putToCache(String key, T obj, KDisposer<T> disposer, long ttl);

    /**
     * Puts object to cache with a disposer, using on key eviction
     * and default TTL (depends on implementation).
     * @param key Cache key
     * @param obj Object to put to the cache
     * @param disposer Disposer for this key on eviction (used on value)
     * @param <T> Type of cached object
     */
    <T> void putToCache(String key, T obj, KDisposer<T> disposer);

    /**
     * Gets object from cache.
     * @param key Cache key of retrieved object
     * @param clazz Class of retrieved object
     * @return Cached object or {@code null} if the key does not exist or
     *         passed class does not match stored class
     * @param <T> Type of retrieved object
     */
    <T> @Nullable T getFromCache(String key, Class<T> clazz);

    /**
     * @param key Key to check
     * @return Whether the key is presented in the cache or not
     */
    boolean hasKey(String key);

    /**
     * Removes the key from cache. If it does not exist, nothing happens.
     * @param key Key to evict
     */
    void evictFromCache(String key);

    /**
     * Clears all cache records.
     */
    void clearCache();

    /**
     * Sets TTL for specific key. If it does not exist, nothing happens
     * @param key Key to set TTL for
     * @param ttl New TTL
     */
    void setTtl(String key, long ttl);

}
