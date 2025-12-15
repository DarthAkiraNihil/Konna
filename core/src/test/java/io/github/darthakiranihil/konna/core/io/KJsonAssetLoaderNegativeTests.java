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

import io.github.darthakiranihil.konna.core.data.json.KJsonPropertyValidationInfo;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonArrayValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.io.std.KJsonAssetLoader;
import io.github.darthakiranihil.konna.core.io.std.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.std.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public class KJsonAssetLoaderNegativeTests extends KStandardTestClass {

    private static final class Alias1Schema implements KJsonValidator {

        private final KJsonValidator validator;

        public Alias1Schema() {

            var builder = new KJsonPropertyValidationInfo.Builder();

            this.validator = new KJsonObjectValidator(
                builder
                    .withName("int_property")
                    .withExpectedType(KJsonValueType.NUMBER_INT)
                    .build(),
                builder
                    .withName("float_property")
                    .withExpectedType(KJsonValueType.NUMBER_FLOAT)
                    .build(),
                builder
                    .withName("boolean_property")
                    .withExpectedType(KJsonValueType.BOOLEAN)
                    .build(),
                builder
                    .withName("string_property")
                    .withExpectedType(KJsonValueType.STRING)
                    .build(),
                builder
                    .withName("subdef_property")
                    .withExpectedType(KJsonValueType.OBJECT)
                    .build(),
                builder
                    .withName("int_array_property")
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(new KJsonArrayValidator(KJsonValueType.NUMBER_INT))
                    .build(),
                builder
                    .withName("float_array_property")
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(new KJsonArrayValidator(KJsonValueType.NUMBER_FLOAT))
                    .build(),
                builder
                    .withName("boolean_array_property")
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(new KJsonArrayValidator(KJsonValueType.BOOLEAN))
                    .build(),
                builder
                    .withName("string_array_property")
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(new KJsonArrayValidator(KJsonValueType.STRING))
                    .build(),
                builder
                    .withName("subdef_array_property")
                    .withExpectedType(KJsonValueType.ARRAY)
                    .withValidator(new KJsonArrayValidator(KJsonValueType.OBJECT))
                    .build()
            );

        }

        @Override
        public void validate(KJsonValue value) {
            this.validator.validate(value);
        }
    }


    @Test
    public void testLoadAssetUnknownTypeAlias() {
        Map<String, KJsonAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:valid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {
            assetLoader.addAssetTypeAlias("alias_1", new KJsonAssetLoaderNegativeTests.Alias1Schema());

            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> assetLoader.loadAsset("type_1.asset_1", "alias_2")
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAddAssetTypeAliasInvalidAsset() {

        Map<String, KJsonAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:invalid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {

            Assertions.assertThrows(
                KAssetLoadingException.class,
                () -> assetLoader.addAssetTypeAlias("alias_1", new KJsonAssetLoaderNegativeTests.Alias1Schema())
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testAddAssetValidationFailed() {

        Map<String, KJsonAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:valid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {
            assetLoader.addAssetTypeAlias("alias_1", new KJsonAssetLoaderNegativeTests.Alias1Schema());

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
                () -> assetLoader.addNewAsset("type_1.asset_2", "type_1", addedDef)
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testAddAssetUnknownInternalType() {

        Map<String, KJsonAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:valid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {
            assetLoader.addAssetTypeAlias("alias_1", new KJsonAssetLoaderNegativeTests.Alias1Schema());

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
                () -> assetLoader.addNewAsset("type_1.asset_2", "type_2", addedDef)
            );

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
