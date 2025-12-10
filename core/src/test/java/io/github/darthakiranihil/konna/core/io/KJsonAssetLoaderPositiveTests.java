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
import io.github.darthakiranihil.konna.core.io.std.KJsonAssetLoader;
import io.github.darthakiranihil.konna.core.io.std.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.std.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KJsonAssetLoaderPositiveTests extends KStandardTestClass {

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
    public void testLoadAsset() {

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
            assetLoader.addAssetTypeAlias("alias_1", new Alias1Schema());

            KAsset asset = assetLoader.loadAsset("type_1.asset_1", "alias_1");

            Assertions.assertEquals("type_1.asset_1", asset.assetId());
            Assertions.assertEquals("alias_1", asset.typeAlias());
            Assertions.assertEquals("type_1", asset.type().name());

            KAssetDefinition def = asset.definition();

            Assertions.assertEquals(1, def.getInt("int_property"));
            Assertions.assertEquals(1.0f, def.getFloat("float_property"));
            Assertions.assertFalse(def.getBoolean("boolean_property"));
            Assertions.assertEquals("1234", def.getString("string_property"));

            KAssetDefinition subdef = def.getSubdefinition("subdef_property");
            Assertions.assertEquals("string", subdef.getString("prop_1"));

            int[] intArrayCheck = new int[] {1, 2, 3};
            float[] floatArrayCheck = new float[] {1.0f, 2.0f, 3.0f};
            boolean[] booleanArrayCheck = new boolean[] {true, true, true};
            String[] stringArrayCheck = new String[] {"1234", "1235", "9999"};

            int[] intArray = def.getIntArray("int_array_property");
            float[] floatArray = def.getFloatArray("float_array_property");
            boolean[] booleanArray = def.getBooleanArray("boolean_array_property");
            String[] stringArray = def.getStringArray("string_array_property");

            for (int i = 0; i < 3; i++) {
                Assertions.assertEquals(intArrayCheck[i], intArray[i]);
                Assertions.assertEquals(floatArrayCheck[i], floatArray[i]);
                Assertions.assertEquals(booleanArrayCheck[i], booleanArray[i]);
                Assertions.assertEquals(stringArrayCheck[i], stringArray[i]);
            }

            KAssetDefinition[] subdefArray = def.getSubdefinitionArray("subdef_array_property");
            for (var subdefEl: subdefArray) {
                Assertions.assertEquals("string", subdefEl.getString("prop_1"));
            }

        } catch (Throwable e) {
            Assertions.fail(e);
        }


    }

    @Test
    public void testAddAssetTypeAliasThatIsNotRegistered() {

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
            assetLoader.addAssetTypeAlias("alias_16", new Alias1Schema());
        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testAddNewAsset() {

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
            assetLoader.addAssetTypeAlias("alias_1", new Alias1Schema());

            KJsonValue addedDef = this.jsonParser.parse(
                "{\n"
                    + "        \"alias_1\": {\n"
                    + "            \"int_property\": 1,\n"
                    + "            \"float_property\": 1.0,\n"
                    + "            \"boolean_property\": false,\n"
                    + "            \"string_property\": \"1234\",\n"
                    + "            \"subdef_property\": {\n"
                    + "                \"prop_1\": \"string\"\n"
                    + "            },\n"
                    + "            \"int_array_property\": [\n"
                    + "                1,\n"
                    + "                2,\n"
                    + "                3\n"
                    + "            ],\n"
                    + "            \"float_array_property\": [\n"
                    + "                1.0,\n"
                    + "                2.0,\n"
                    + "                3.0\n"
                    + "            ],\n"
                    + "            \"boolean_array_property\": [\n"
                    + "                true,\n" + "                true,\n"
                    + "                true\n" + "            ],\n"
                    + "            \"string_array_property\": [\n"
                    + "                \"1234\",\n"
                    + "                \"1235\",\n"
                    + "                \"9999\"\n"
                    + "            ],\n"
                    + "            \"subdef_array_property\": [\n"
                    + "                {\n"
                    + "                    \"prop_1\": \"string\"\n"
                    + "                },\n"
                    + "                {\n"
                    + "                    \"prop_1\": \"string\"\n"
                    + "                }\n"
                    + "            ]\n"
                    + "        }\n"
                    + "    }"
            );

            assetLoader.addNewAsset("type_1.asset_2", "type_1", addedDef);
            assetLoader.addNewAsset("type_1.asset_3", "type_1", addedDef);

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
