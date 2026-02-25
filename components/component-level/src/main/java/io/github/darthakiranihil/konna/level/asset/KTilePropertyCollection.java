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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.level.KObjectTileProperty;
import io.github.darthakiranihil.konna.level.KObjectTilePropertyFactory;
import io.github.darthakiranihil.konna.level.KTilePropertyFactory;
import io.github.darthakiranihil.konna.level.property.factory.*;
import io.github.darthakiranihil.konna.level.property.*;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;

import java.util.Objects;

public final class KTilePropertyCollection implements KAssetCollection<KTilePropertyFactory<?>> {

    private final KAssetLoader assetLoader;
    private final KActivator activator;

    private final KIntPropertyFactory intPropertyFactory;
    private final KIntArrayTilePropertyFactory intArrayPropertyFactory;
    private final KFloatTilePropertyFactory floatPropertyFactory;
    private final KFloatArrayTilePropertyFactory floatArrayPropertyFactory;
    private final KBooleanTilePropertyFactory booleanPropertyFactory;
    private final KBooleanArrayTilePropertyFactory booleanArrayPropertyFactory;
    private final KStringTilePropertyFactory stringPropertyFactory;
    private final KStringArrayTilePropertyFactory stringArrayPropertyFactory;

    public KTilePropertyCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KActivator activator
    ) {

        this.assetLoader = assetLoader;
        this.activator = activator;

        this.intPropertyFactory = new KIntPropertyFactory();
        this.intArrayPropertyFactory = new KIntArrayTilePropertyFactory();
        this.floatPropertyFactory = new KFloatTilePropertyFactory();
        this.floatArrayPropertyFactory = new KFloatArrayTilePropertyFactory();
        this.booleanPropertyFactory = new KBooleanTilePropertyFactory();
        this.booleanArrayPropertyFactory = new KBooleanArrayTilePropertyFactory();
        this.stringPropertyFactory = new KStringTilePropertyFactory();
        this.stringArrayPropertyFactory = new KStringArrayTilePropertyFactory();
    }

    @Override
    public KTilePropertyFactory<?> getAsset(String assetId) {

        KAsset asset = this.assetLoader.loadAsset(assetId, KTilePropertyTypedef.TILE_PROPERTY_ASSET_TYPE);
        KAssetDefinition definition = asset.definition();

        String type = Objects.requireNonNull(definition.getString("property_type"));
        return this.getFactory(type);

    }

    @SuppressWarnings("unchecked")
    private KTilePropertyFactory<?> getFactory(final String type) {

        switch (type) {
            case "int": {
                return this.intPropertyFactory;
            }
            case "int[]": {
                return this.intArrayPropertyFactory;
            }
            case "float": {
                return this.floatPropertyFactory;
            }
            case "float[]": {
                return this.floatArrayPropertyFactory;
            }
            case "bool": {
                return this.booleanPropertyFactory;
            }
            case "bool[]": {
                return this.booleanArrayPropertyFactory;
            }
            case "string": {
                return this.stringPropertyFactory;
            }
            case "string[]": {
                return this.stringArrayPropertyFactory;
            }
        };

        var factoryClass = (Class<? extends KObjectTilePropertyFactory<?>>) KClassUtils.getForName(
            String.format(
                "konna.generated.level.props.%s$$Factory",
                type
            )
        );

        return this.activator.createObject(factoryClass);

    }

}
