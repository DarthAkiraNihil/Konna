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
import io.github.darthakiranihil.konna.core.util.KCache;
import io.github.darthakiranihil.konna.level.entity.state.KMapEntityState;
import io.github.darthakiranihil.konna.level.entity.state.KMapEntityStateMachineMetadata;
import io.github.darthakiranihil.konna.level.entity.state.KMapEntityStateTransition;
import io.github.darthakiranihil.konna.level.type.KMapEntityStateMachineMetadataTypedef;

import java.util.*;

public final class KMapEntityStateMachineMetadataCollection
    implements KAssetCollection<KMapEntityStateMachineMetadata> {

    private static final int METADATA_TTL = 60;

    private final KAssetLoader assetLoader;
    private final KCache metadataCache;

    public KMapEntityStateMachineMetadataCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KCache cache
    ) {
        this.assetLoader = assetLoader;
        this.metadataCache = cache;
    }

    @Override
    public KMapEntityStateMachineMetadata getAsset(String assetId) {
        if (this.metadataCache.hasKey(assetId)) {
            KMapEntityStateMachineMetadata obj =
                this.metadataCache.getFromCache(assetId, KMapEntityStateMachineMetadata.class);
            if (obj != null) {
                return obj;
            }
        }

        KAsset asset = this.assetLoader.loadAsset(
            assetId,
            KMapEntityStateMachineMetadataTypedef.STATE_MACHINE_METADATA_TYPEDEF
        );
        KAssetDefinition definition = asset.definition();

        KAssetDefinition states = definition.getSubdefinition("states");
        KAssetDefinition[] transitions = definition.getSubdefinitionArray("transitions");

        Map<String, Class<? extends KMapEntityState>> statesMetadata = new HashMap<>();
        List<KMapEntityStateMachineMetadata.TransitionMetadata>
            transitionMetadata = new ArrayList<>(transitions.length);

        List<String> stateNodes = states.getProperties();
        for (var stateNode: stateNodes) {
            statesMetadata.put(
                stateNode,
                states.getClassObject(stateNode, KMapEntityState.class)
            );
        }

        for (var transition: transitions) {
            transitionMetadata.add(
                new KMapEntityStateMachineMetadata.TransitionMetadata(
                    Objects.requireNonNull(transition.getString("from")),
                    Objects.requireNonNull(transition.getString("to")),
                    transition.getClassObject("class", KMapEntityStateTransition.class)
                )
            );
        }

        KMapEntityStateMachineMetadata metadata = new KMapEntityStateMachineMetadata(
            statesMetadata,
            transitionMetadata
        );

        this.metadataCache.putToCache(assetId, metadata, METADATA_TTL);
        return metadata;

    }
}
