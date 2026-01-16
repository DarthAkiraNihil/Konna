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

package io.github.darthakiranihil.konna.core.util.std;

import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KCache;
import io.github.darthakiranihil.konna.core.util.KDisposer;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class KHashMapBasedCache extends KObject implements KCache {

    private static class CacheRecord {

        private long maxTtl;
        private long ttl;
        private final Object obj;
        private final Class<?> clazz;
        private final @Nullable Runnable disposer;

        public <T> CacheRecord(
            long maxTtl,
            T obj,
            Class<T> clazz,
            @Nullable KDisposer<T> disposer
        ) {
            this.maxTtl = maxTtl;
            this.ttl = maxTtl;
            this.obj = obj;
            this.clazz = clazz;
            if (disposer != null) {
                this.disposer = () -> disposer.dispose((T) this.obj);
            } else {
                this.disposer = null;
            }
        }

        public void decreaseTtl(long value) {
            this.ttl -= value;
        }

        public void refresh() {
            this.ttl = maxTtl;
        }

        public void onEviction() {
            if (this.disposer == null) {
                return;
            }

            this.disposer.run();
        }
    }

    private static final int DEFAULT_TTL = 60;

    private @Nullable Thread cleaner;
    private volatile boolean running;

    private final Map<String, CacheRecord> cache;

    public KHashMapBasedCache() {
        super("cache", KStructUtils.setOfTags(
            KTag.DefaultTags.STD,
            KTag.DefaultTags.SYSTEM
        ));

        this.cache = new ConcurrentHashMap<>();

        this.cleaner = new Thread(this::clean);
        this.cleaner.setName("cache_cleaner");
        this.cleaner.start();
    }

    @Override
    public <T> void putToCache(String key, T obj, long ttl) {
        this.cache.put(
            key,
            new CacheRecord(
                ttl,
                obj,
                (Class<T>) obj.getClass(),
                null
            )
        );
    }

    @Override
    public <T> void putToCache(String key, T obj) {
        this.cache.put(
            key,
            new CacheRecord(
                DEFAULT_TTL,
                obj,
                (Class<T>) obj.getClass(),
                null
            )
        );
    }

    @Override
    public <T> void putToCache(String key, T obj, KDisposer<T> disposer, long ttl) {
        this.cache.put(
            key,
            new CacheRecord(
                ttl,
                obj,
                (Class<T>) obj.getClass(),
                disposer
            )
        );
    }

    @Override
    public <T> void putToCache(String key, T obj, KDisposer<T> disposer) {
        this.cache.put(
            key,
            new CacheRecord(
                DEFAULT_TTL,
                obj,
                (Class<T>) obj.getClass(),
                disposer
            )
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @Nullable T getFromCache(String key, Class<T> clazz) {
        if (!this.cache.containsKey(key)) {
            return null;
        }

        var record = this.cache.get(key);
        if (record == null || record.clazz != clazz) {
            return null;
        }
        record.refresh();

        return (T) record.obj;
    }

    @Override
    public void evictFromCache(String key) {
        var record = this.cache.get(key);
        if (record != null) {
            this.cache.remove(key);
            record.onEviction();
        }
    }

    @Override
    public void clearCache() {
        Set<String> keys = this.cache.keySet();
        for (String key: keys) {
            this.evictFromCache(key);
        }
    }

    private void clean() {

        this.running = true;
        long deltaTime = 1;
        while (this.running) {

            Instant beginTime = Instant.now();

            Set<String> keys = this.cache.keySet();
            for (String key: keys) {

                var record = this.cache.get(key);
                if (record == null) {
                    continue;
                }

                if (record.ttl <= 0 && record.maxTtl != KCache.TTL_EVERLASTING) {
                    this.evictFromCache(key);
                } else {
                    record.decreaseTtl(deltaTime);
                }
            }


            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                KSystemLogger.warning("cache_cleaner", e);
            }
            deltaTime = Duration.between(beginTime, Instant.now()).getSeconds();
        }

    }

    @Override
    public void setTtl(String key, long ttl) {
        var record = this.cache.get(key);
        if (record == null) {
            return;
        }

        record.maxTtl = ttl;
        record.ttl = ttl;
    }

    @Override
    public void close() {
        this.running = false;
        this.cleaner = null;
    }
}
