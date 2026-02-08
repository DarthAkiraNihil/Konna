package io.github.darthakiranihil.konna.core.io;

/**
 * Interface for a utility class which purpose it to
 * load assets by its id and type alias according to data
 * that a component requires to build an actual asset.
 * Also provides special methods to add new asset types
 * (for initialization) and adding new assets (potentially
 * useful for mod loaders).
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KAssetLoader {

    /**
     * Loads an asset by its id and type alias.
     * @param assetId Global asset id
     * @param typeAlias Type alias (passed by requested component)
     * @return Loaded asset with data of passed type alias
     */
    KAsset loadAsset(String assetId, String typeAlias);

    /**
     * Adds a new asset type definition to this loader.
     * @param typedef Asset type definition
     */
    void addAssetTypedef(KAssetTypedef typedef);

    /**
     * Add a new asset so that could be loaded by other components at request.
     * @param assetId Global asset id
     * @param internalType Type alias used in this application
     * @param rawDefinition Full asset definition
     */
    void addNewAsset(String assetId, String internalType, KAssetDefinition rawDefinition);

}
