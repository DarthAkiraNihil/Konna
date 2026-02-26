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
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.util.KCache;
import io.github.darthakiranihil.konna.level.*;
import io.github.darthakiranihil.konna.level.property.factory.KIntPropertyFactory;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;

import java.util.HashMap;
import java.util.Map;

public final class KTileCollection implements KAssetCollection<KTileInfo> {

    private static final int TILE_TTL = 300;
    private final KAssetLoader assetLoader;
    private final KCache tileCache;

    private final KTilePropertyCollection propsCollection;


    public KTileCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KCache cache,
        @KInject final KTilePropertyCollection propsCollection
    ) {
        this.assetLoader = assetLoader;
        this.tileCache = cache;
        this.propsCollection = propsCollection;
    }

    @Override
    public KTileInfo getAsset(String assetId) {
        if (this.tileCache.hasKey(assetId)) {
            KTileInfo obj = this.tileCache.getFromCache(assetId, KTileInfo.class);
            if (obj != null) {
                return obj;
            }
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, KTileTypedef.TILE_ASSET_TYPE);
        KAssetDefinition tileDefinition = asset.definition();
        int tileId = tileDefinition.getInt("tile_id");
        int transparency = tileDefinition.getInt("transparency");
        boolean passable = tileDefinition.getBoolean("passable");

        KAssetDefinition props = tileDefinition.getSubdefinition("properties");
        Map<String, KTileProperty> readProps = this.readProps(props);


        KTileInfo tile = new KTileInfo(
            tileId,
            passable,
            transparency,
            readProps
        );

        this.tileCache.putToCache(assetId, tile, TILE_TTL);

        return tile;
    }

    private Map<String, KTileProperty> readProps(
        final KAssetDefinition props
    ) {
        Map<String, KTileProperty> readProps = new HashMap<>();
        for (var prop: props.getProperties()) {

            var factory = this.propsCollection.getAsset(prop);
            switch (factory) {
                case KIntPropertyFactory ipf: {
                    readProps.put(prop, ipf.create(props.getInt(prop)));
                    break;
                }
                case KObjectTilePropertyFactory<?> opf: {
                    readProps.put(prop, opf.create(props.getSubdefinition(prop)));
                    break;
                }
                case KObjectArrayTilePropertyFactory<?> oapf: {
                    readProps.put(prop, oapf.create(props.getSubdefinitionArray(prop)));
                    break;
                }
                default: {
                    throw new KAssetLoadingException(
                        String.format(
                            "Unknown tile property factory: %s",
                            factory
                        )
                    );
                }
            }
        }

        return readProps;
    }
}
