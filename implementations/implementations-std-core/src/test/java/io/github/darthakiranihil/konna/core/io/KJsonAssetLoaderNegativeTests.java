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

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@NullMarked
public class KJsonAssetLoaderNegativeTests extends KStandardTestClass {

    private static final class Alias1Typedef implements KAssetTypedef {

        @Override
        public String getName() {
            return "alias_1";
        }

        @Override
        public KAssetDefinitionRule getRule() {
            return KCompositeAssetDefinitionRuleBuilder
                .create()
                .withInt("int_property")
                .withFloat("float_property")
                .withBoolean("boolean_property")
                .withString("string_property")
                .withSubdefinition("subdef_property")
                .withIntArray("int_array_property")
                .withFloatArray("float_array_property")
                .withBooleanArray("boolean_array_property")
                .withStringArray("string_array_property")
                .withSubdefinitionArray("subdef_array_property")
                .build();
        }
    }


    @Test
    public void testLoadAssetUnknownTypeAlias() {
        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            this.jsonParser,
            new String[] { "classpath:new_assets/" },
            new KAssetTypedef[][]{
                new KAssetTypedef[] { new KJsonAssetLoaderNegativeTests.Alias1Typedef() }
            }
        );

        try {
            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> assetLoader.loadAsset("type_1.asset_1", "alias_2")
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAddAssetValidationFailed() {

        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            this.jsonParser,
            new String[] { "classpath:new_assets/" },
            new KAssetTypedef[][]{
                new KAssetTypedef[] { new KJsonAssetLoaderNegativeTests.Alias1Typedef() }
            }
        );

        try {
            KJsonValue addedDef = this.jsonParser.parse("""
                {
                        "alias_1": {
                            "int1_property": 1,
                            "float1_property": 1.0,
                            "boolean1_property": false,
                            "string1_property": "1234",
                            "subdef1_property": {
                                "prop_1": "string"
                            },
                        }
                    }"""
            );

            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> assetLoader.addNewAsset(new KAsset("type_1.asset_2", "type_1", new KJsonAssetDefinition(addedDef, v -> {})))
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testAddAssetUnknownInternalType() {

        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            this.jsonParser,
            new String[] { "classpath:new_assets/" },
            new KAssetTypedef[][]{
                new KAssetTypedef[] { new KJsonAssetLoaderNegativeTests.Alias1Typedef() }
            }
        );

        try {

            KJsonValue addedDef = this.jsonParser.parse("""
                {
                        "alias_1": {
                            "int1_property": 1,
                            "float1_property": 1.0,
                            "boolean1_property": false,
                            "string1_property": "1234",
                            "subdef1_property": {
                                "prop_1": "string"
                            },
                        }
                    }"""
            );

            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> assetLoader.addNewAsset(new KAsset("type_1.asset_2", "type_2", new KJsonAssetDefinition(addedDef, (v) -> {})))
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testLoadWithNonUnique() {



        try {

            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> new KJsonAssetLoader(
                    new KStandardResourceLoader(
                        List.of(new KClasspathProtocol(
                            ClassLoader.getSystemClassLoader()
                        ))
                    ),
                    this.jsonParser,
                    new String[] { "classpath:assets_with_non_unique/" },
                    new KAssetTypedef[][]{
                        new KAssetTypedef[] { new KJsonAssetLoaderNegativeTests.Alias1Typedef() }
                    }
                )
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testLoadWithUnknownId() {

        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            this.jsonParser,
            new String[] { "classpath:new_assets/" },
            new KAssetTypedef[][]{
                new KAssetTypedef[] { new KJsonAssetLoaderNegativeTests.Alias1Typedef() }
            }
        );

        try {

            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> assetLoader.loadAsset("aaa", "alias_1")
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
