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

import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.io.resource.KClasspathResource;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KStandardResourceLoaderPositiveTests extends KStandardTestClass {

    private final KResourceLoader resourceLoader;

    public KStandardResourceLoaderPositiveTests() {
        this.resourceLoader = new KStandardResourceLoader(new ArrayList<>());
        this.resourceLoader.addProtocol(new KClasspathProtocol(
            Thread.currentThread().getContextClassLoader()
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

    @Test
    public void testLoadManyResourcesNonRecursive() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:amogus/", false);
        Assertions.assertEquals(4, resources.length);
        List<String> names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("bootstrap1.json"));
        Assertions.assertTrue(names.contains("invalid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("test_config1.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_11.json"));

    }

    @Test
    public void testLoadManyResourcesRecursive() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:amogus/", true);
        Assertions.assertEquals(5, resources.length);
        List<String> names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("bootstrap1.json"));
        Assertions.assertTrue(names.contains("invalid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("test_config1.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("ioa"));

        for (KResource resource : resources) {
            try {
                resource.close();
            } catch (IOException e) {
                Assertions.fail(e);
            }
        }

        resources = this.resourceLoader.loadResources("classpath:amogus/");
        Assertions.assertEquals(5, resources.length);
        names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("bootstrap1.json"));
        Assertions.assertTrue(names.contains("invalid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("test_config1.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("ioa"));

        for (KResource resource : resources) {
            try {
                resource.close();
            } catch (IOException e) {
                Assertions.fail(e);
            }
        }

    }

    @Test
    public void testLoadManyResourcesFromRootNonRecursive() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:", false);
        Assertions.assertEquals(5, resources.length);
        List<String> names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("invalid_assets_type_1.json"));
        Assertions.assertTrue(names.contains("test_config.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_1.json"));

    }

    @Test
    public void testLoadManyResourcesNonRecursiveWithExplicitProtocolPassing() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:amogus/", new KClasspathProtocol(ClassLoader.getSystemClassLoader()), false);
        Assertions.assertEquals(4, resources.length);
        List<String> names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("bootstrap1.json"));
        Assertions.assertTrue(names.contains("invalid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("test_config1.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_11.json"));

    }

    @Test
    public void testLoadManyResourcesRecursiveWithExplicitProtocolPassing() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:amogus/", new KClasspathProtocol(ClassLoader.getSystemClassLoader()), true);
        Assertions.assertEquals(5, resources.length);
        List<String> names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("bootstrap1.json"));
        Assertions.assertTrue(names.contains("invalid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("test_config1.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("ioa"));

        for (KResource resource : resources) {
            try {
                resource.close();
            } catch (IOException e) {
                Assertions.fail(e);
            }
        }

        resources = this.resourceLoader.loadResources("classpath:amogus/", new KClasspathProtocol(ClassLoader.getSystemClassLoader()));
        Assertions.assertEquals(5, resources.length);
        names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();

        Assertions.assertTrue(names.contains("bootstrap1.json"));
        Assertions.assertTrue(names.contains("invalid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("test_config1.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_11.json"));
        Assertions.assertTrue(names.contains("ioa"));

        for (KResource resource : resources) {
            try {
                resource.close();
            } catch (IOException e) {
                Assertions.fail(e);
            }
        }

    }

    @Test
    public void testLoadManyResourcesFromRootNonRecursiveWithExplicitProtocolPassing() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:", new KClasspathProtocol(ClassLoader.getSystemClassLoader()), false);
        Assertions.assertEquals(5, resources.length);
        List<String> names = Arrays
            .stream(resources)
            .map(KResource::name)
            .toList();
        
        Assertions.assertTrue(names.contains("invalid_assets_type_1.json"));
        Assertions.assertTrue(names.contains("test_config.json"));
        Assertions.assertTrue(names.contains("valid_assets_type_1.json"));

    }

    @Test
    public void testLoadManyResourcesButProtocolDidNotFoundThem() {

        KResource[] resources = this.resourceLoader.loadResources("classpath:biber/");
        Assertions.assertEquals(0, resources.length);

    }
}
