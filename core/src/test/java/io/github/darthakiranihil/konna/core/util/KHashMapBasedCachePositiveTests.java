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

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.core.util.std.KHashMapBasedCache;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class KHashMapBasedCachePositiveTests extends KStandardTestClass {

    private static final class Disposer implements KDisposer<@NonNull String> {

        private String val;

        @Override
        public void dispose(String obj) {
            this.val = obj;
        }

    }

    @Test
    public void testPutToCacheBasic() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba", 5);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );
            TimeUnit.SECONDS.sleep(6);
            Assertions.assertNull(
                c.getFromCache("aboba", String.class)
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testPutToCacheDefaultTtl() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba");
            TimeUnit.SECONDS.sleep(20);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );
            TimeUnit.SECONDS.sleep(61);
            Assertions.assertNull(
                c.getFromCache("aboba", String.class)
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testPutToCacheBasicWithDisposer() {

        try {

            KCache c = new KHashMapBasedCache();
            Disposer d = new Disposer();

            c.putToCache("aboba", "Abiba", d, 5);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );
            TimeUnit.SECONDS.sleep(7);
            Assertions.assertNull(
                c.getFromCache("aboba", String.class)
            );
            Assertions.assertEquals(
                "Abiba",
                d.val
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testPutToCacheWithDisposerAndDefaultTtl() {

        try {

            KCache c = new KHashMapBasedCache();
            Disposer d = new Disposer();

            c.putToCache("aboba", "Abiba", d);
            TimeUnit.SECONDS.sleep(20);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );
            TimeUnit.SECONDS.sleep(61);
            Assertions.assertNull(
                c.getFromCache("aboba", String.class)
            );
            Assertions.assertEquals(
                "Abiba",
                d.val
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testPutToCacheEverlasting() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba", KCache.TTL_EVERLASTING);
            TimeUnit.SECONDS.sleep(10);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );
            TimeUnit.SECONDS.sleep(20);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testHasKey() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba", 30);
            TimeUnit.SECONDS.sleep(20);
            Assertions.assertTrue(c.hasKey("aboba"));
            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testGetKeyMissingOrIncorrectClass() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba", 20);
            Assertions.assertNull(c.getFromCache("abiba", String.class));
            Assertions.assertNull(c.getFromCache("aboba", Integer.class));
            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testClearCache() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba", KCache.TTL_EVERLASTING);
            c.clearCache();
            Assertions.assertNull(
                c.getFromCache("aboba", String.class)
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSetTtl() {

        try {

            KCache c = new KHashMapBasedCache();

            c.putToCache("aboba", "Abiba", 5);
            Assertions.assertEquals(
                "Abiba",
                c.getFromCache("aboba", String.class)
            );
            c.setTtl("aboba", 3);
            TimeUnit.SECONDS.sleep(4);
            Assertions.assertNull(
                c.getFromCache("aboba", String.class)
            );

            c.close();

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
