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

package io.github.darthakiranihil.konna.core.io.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;

import java.util.List;

public class KJsonAssetDefinition implements KAssetDefinition {

    private final KJsonValue value;

    public KJsonAssetDefinition(final KJsonValue value, final KJsonValidator schema) {
        if (value.getType() != KJsonValueType.OBJECT) {
            throw new KAssetDefinitionError(
                "Cannot create asset definition: provided json value is not an object"
            );
        }

        schema.validate(value);
        this.value = value;
    }

    private KJsonAssetDefinition(final KJsonValue value) {
        if (value.getType() != KJsonValueType.OBJECT) {
            throw new KAssetDefinitionError(
                "Cannot create asset definition: provided json value is not an object"
            );
        }

        this.value = value;
    }

    @Override
    public int getInt(String property) {
        return value.getProperty(property).getInt();
    }

    @Override
    public float getFloat(String property) {
        return value.getProperty(property).getFloat();
    }

    @Override
    public boolean getBoolean(String property) {
        return value.getProperty(property).getBoolean();
    }

    @Override
    public String getString(String property) {
        return value.getProperty(property).getString();
    }

    @Override
    public KAssetDefinition getSubdefinition(String property) {
        return new KJsonAssetDefinition(value.getProperty(property));
    }

    @Override
    public int[] getIntArray(String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getInt();
        }

        return array;
    }

    @Override
    public float[] getFloatArray(String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getFloat();
        }

        return array;
    }

    @Override
    public boolean[] getBooleanArray(String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        boolean[] array = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getBoolean();
        }

        return array;
    }

    @Override
    public String[] getStringArray(String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getString();
        }

        return array;
    }

    @Override
    public KAssetDefinition[] getSubdefinitionArray(String property) {
        List<KJsonValue> list = this.value.getProperty(property).getList();
        KAssetDefinition[] array = new KAssetDefinition[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = new KJsonAssetDefinition(list.get(i));
        }

        return array;
    }
}
