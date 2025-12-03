package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.util.KRawDataContainer;

public record KAsset (
    String assetId,
    KAssetType type,
    String typeAlias,
    KAssetDefinition definition,
    KResource raw
) implements KRawDataContainer<KResource>{

}

