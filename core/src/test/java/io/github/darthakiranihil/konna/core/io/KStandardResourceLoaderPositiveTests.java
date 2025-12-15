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

import io.github.darthakiranihil.konna.core.io.std.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.std.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.io.std.resource.KClasspathResource;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class KStandardResourceLoaderPositiveTests extends KStandardTestClass {

    private final KResourceLoader resourceLoader;

    public KStandardResourceLoaderPositiveTests() {
        this.resourceLoader = new KStandardResourceLoader(new ArrayList<>());
        this.resourceLoader.addProtocol(new KClasspathProtocol(
            ClassLoader.getSystemClassLoader()
        ));
    }

    @Test
    public void testLoadExistingResource() {

        try (KResource res = this.resourceLoader.loadResource("classpath:test_config.json")) {

            Assertions.assertEquals(KClasspathResource.class, res.getClass());
            Assertions.assertEquals("test_config.json", res.name());
            Assertions.assertEquals("test_config.json", res.path());
            Assertions.assertTrue(res.exists());
            Assertions.assertNotNull(res.bytes());
            Assertions.assertNotNull(res.stream());
            Assertions.assertNotNull(res.channel());
            Assertions.assertNotNull(res.string());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testLoadExistingResourceWithExplicitProtocolPassing() {

        try (KResource res = this.resourceLoader.loadResource(
                "classpath:test_config.json",
                new KClasspathProtocol(ClassLoader.getSystemClassLoader())
            )
        ) {

            Assertions.assertEquals(KClasspathResource.class, res.getClass());
            Assertions.assertEquals("test_config.json", res.name());
            Assertions.assertEquals("test_config.json", res.path());
            Assertions.assertTrue(res.exists());
            Assertions.assertNotNull(res.bytes());
            Assertions.assertNotNull(res.stream());
            Assertions.assertNotNull(res.channel());
            Assertions.assertNotNull(res.string());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testLoadNonExistingResource() {

        try (KResource res = this.resourceLoader.loadResource("classpath:test_config1.json")) {

            Assertions.assertEquals(KResource.Empty.class, res.getClass());
            Assertions.assertEquals("test_config1.json", res.name());
            Assertions.assertEquals("classpath:test_config1.json", res.path());
            Assertions.assertFalse(res.exists());
            Assertions.assertEquals(0, res.bytes().length);
            Assertions.assertNull(res.stream());
            Assertions.assertNull(res.channel());
            Assertions.assertEquals("", res.string());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testLoadNonExistingResourceWithExplicitProtocolPassing() {

        try (KResource res = this.resourceLoader.loadResource(
            "classpath:test_config1.json",
                new KClasspathProtocol(ClassLoader.getSystemClassLoader())
            )
        ) {

            Assertions.assertEquals(KResource.Empty.class, res.getClass());
            Assertions.assertEquals("test_config1.json", res.name());
            Assertions.assertEquals("classpath:test_config1.json", res.path());
            Assertions.assertFalse(res.exists());
            Assertions.assertEquals(0, res.bytes().length);
            Assertions.assertNull(res.stream());
            Assertions.assertNull(res.channel());
            Assertions.assertEquals("", res.string());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
