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

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.io.KJsonAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public class KJsonSubtypeBasedAssetLoaderPositiveTests extends KStandardTestClass {

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
    public void testLoadAsset() {

        Map<String, KJsonSubtypeBasedAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:valid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonSubtypeBasedAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {
            assetLoader.addAssetTypedef(new Alias1Typedef());

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
            Assertions.assertNotNull(stringArray);

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
    public void testAddAssetTypeDefinitionThatIsNotRegistered() {

        Map<String, KJsonSubtypeBasedAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:valid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonSubtypeBasedAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {
            assetLoader.addAssetTypedef(new KAssetTypedef() {
                @Override
                public String getName() {
                    return "alias_16";
                }

                @Override
                public KAssetDefinitionRule getRule() {
                    return KCompositeAssetDefinitionRuleBuilder
                        .create().build();
                }
            });

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testAddNewAsset() {

        Map<String, KJsonSubtypeBasedAssetLoader.AssetTypeData> assetTypeData = new HashMap<>();
        assetTypeData.put("type_1", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
            new String[] {"alias_1"}, new String[] {"classpath:valid_assets_type_1.json"}
        ));
        KAssetLoader assetLoader = new KJsonSubtypeBasedAssetLoader(
            new KStandardResourceLoader(
                List.of(new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                ))
            ),
            assetTypeData,
            this.jsonParser
        );

        try {
            assetLoader.addAssetTypedef(new Alias1Typedef());

            KJsonValue addedDef = this.jsonParser.parse("""
                {
                        "alias_1": {
                            "int_property": 1,
                            "float_property": 1.0,
                            "boolean_property": false,
                            "string_property": "1234",
                            "subdef_property": {
                                "prop_1": "string"
                            },
                            "int_array_property": [
                                1,
                                2,
                                3
                            ],
                            "float_array_property": [
                                1.0,
                                2.0,
                                3.0
                            ],
                            "boolean_array_property": [
                                true,
                                true,
                                true
                            ],
                            "string_array_property": [
                                "1234",
                                "1235",
                                "9999"
                            ],
                            "subdef_array_property": [
                                {
                                    "prop_1": "string"
                                },
                                {
                                    "prop_1": "string"
                                }
                            ]
                        }
                    }"""
            );

            assetLoader.addNewAsset("type_1.asset_2", "type_1", new KJsonAssetDefinition(addedDef, v -> {}));
            assetLoader.addNewAsset("type_1.asset_3", "type_1", new KJsonAssetDefinition(addedDef, v -> {}));

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
