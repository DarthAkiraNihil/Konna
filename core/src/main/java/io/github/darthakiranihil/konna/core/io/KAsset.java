package io.github.darthakiranihil.konna.core.io;

/**
 * Representation of asset data, that is used by engine components to build
 * concrete asset objects (like textures, entity objects etc.).
 * @param assetId Global asset id
 * @param type Internal asset type (inside application, that is defined by its developer)
 * @param typeAlias Type alias (asset type inside its loader component)
 * @param definition Asset definition
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KAsset(
    String assetId,
    KAssetType type,
    String typeAlias,
    KAssetDefinition definition
) {

}

