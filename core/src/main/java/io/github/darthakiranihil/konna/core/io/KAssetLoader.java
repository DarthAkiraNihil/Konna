package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;

public interface KAssetLoader {

    KAsset loadAsset(String assetId, String typeAlias);
    void addAssetTypeAlias(String typeAlias, KJsonValidator schema);

}
