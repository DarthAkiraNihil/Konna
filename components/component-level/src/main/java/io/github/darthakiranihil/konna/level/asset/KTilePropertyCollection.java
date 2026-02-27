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
import io.github.darthakiranihil.konna.level.property.factory.KTilePropertyFactory;
import io.github.darthakiranihil.konna.level.property.factory.*;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;

import java.util.Objects;

/**
 * Collection of tile properties info assets of type
 * {@link KTilePropertyTypedef#TILE_PROPERTY_ASSET_TYPE}.
 * Usually should not be used by hand as its assets are used
 * in {@link KTileCollection} to deserialize additional properties.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KTilePropertyCollection implements KAssetCollection<KTilePropertyFactory<?>> {

    private final KAssetLoader assetLoader;
    private final KActivator activator;

    private final KIntPropertyFactory intPropertyFactory;
    private final KIntArrayPropertyFactory intArrayPropertyFactory;
    private final KFloatPropertyFactory floatPropertyFactory;
    private final KFloatArrayPropertyFactory floatArrayPropertyFactory;
    private final KBooleanPropertyFactory booleanPropertyFactory;
    private final KBooleanArrayPropertyFactory booleanArrayPropertyFactory;
    private final KStringPropertyFactory stringPropertyFactory;
    private final KStringArrayPropertyFactory stringArrayPropertyFactory;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     * @param activator Activator to create property factories
     */
    public KTilePropertyCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KActivator activator
    ) {

        this.assetLoader = assetLoader;
        this.activator = activator;

        this.intPropertyFactory = new KIntPropertyFactory();
        this.intArrayPropertyFactory = new KIntArrayPropertyFactory();
        this.floatPropertyFactory = new KFloatPropertyFactory();
        this.floatArrayPropertyFactory = new KFloatArrayPropertyFactory();
        this.booleanPropertyFactory = new KBooleanPropertyFactory();
        this.booleanArrayPropertyFactory = new KBooleanArrayPropertyFactory();
        this.stringPropertyFactory = new KStringPropertyFactory();
        this.stringArrayPropertyFactory = new KStringArrayPropertyFactory();
    }

    @Override
    public KTilePropertyFactory<?> getAsset(final String assetId) {

        KAsset asset = this.assetLoader.loadAsset(
            assetId, KTilePropertyTypedef.TILE_PROPERTY_ASSET_TYPE
        );
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
        }

        boolean isArray = type.endsWith("[]");
        String factoryClassName = String.format(
            "konna.generated.level.props.%s$$%s",
            isArray ? type.substring(0, type.lastIndexOf('[')) : type,
            isArray ? "ArrayFactory" : "Factory"
        );

        if (isArray) {
            return this.activator.createObject(
                (Class<? extends KObjectArrayPropertyFactory<?>>)
                    KClassUtils.getForName(factoryClassName)
            );
        } else {
            return this.activator.createObject(
                (Class<? extends KObjectPropertyFactory<?>>)
                    KClassUtils.getForName(factoryClassName)
            );
        }

    }

}
