package io.github.darthakiranihil.konna.core.io;

import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Representation of asset data, that is used by engine components to build
 * concrete asset objects (like textures, entity objects etc.).
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KAsset implements KAssetDefinition {
    
    private final String id;
    private final String type;
    private final KAssetDefinition definition;

    /**
     * Constructs a new asset with provided definition.
     * @param id Asset id
     * @param type Real asset type assigned during its loading
     * @param definition Definition of this asset to be boxed
     *
     * @since 0.6.0
     */
    public KAsset(final String id, final String type, final KAssetDefinition definition) {
        this.id = id;
        this.type = type;
        this.definition = definition;
    }

    /**
     * @return ID of this asset
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return Asset type assigned to this instance
     */
    public String getType() {
        return this.type;
    }

    @Override
    public List<String> getProperties() {
        return this.definition.getProperties();
    }

    @Override
    public int getInt(final String property) {
        return this.definition.getInt(property);
    }

    @Override
    public float getFloat(final String property) {
        return this.definition.getFloat(property);
    }

    @Override
    public boolean getBoolean(final String property) {
        return this.definition.getBoolean(property);
    }

    @Override
    public @Nullable String getString(final String property) {
        return this.definition.getString(property);
    }

    @Override
    public KAssetDefinition getSubdefinition(final String property) {
        return this.definition.getSubdefinition(property);
    }

    @Override
    public <T extends Enum<T>> T getEnum(final String property, final Class<T> enumClass) {
        return this.definition.getEnum(property, enumClass);
    }

    @Override
    public Class<?> getClassObject(final String property) {
        return this.definition.getClassObject(property);
    }

    @Override
    public <T> Class<? extends T> getClassObject(
        final String property,
        final Class<T> targetClass
    ) {
        return this.definition.getClassObject(property, targetClass);
    }

    @Override
    public @Nullable Object getObject(final String property) {
        return this.definition.getObject(property);
    }

    @Override
    public int[] getIntArray(final String property) {
        return this.definition.getIntArray(property);
    }

    @Override
    public float[] getFloatArray(final String property) {
        return this.definition.getFloatArray(property);
    }

    @Override
    public boolean[] getBooleanArray(final String property) {
        return this.definition.getBooleanArray(property);
    }

    @Override
    public String @Nullable [] getStringArray(final String property) {
        return this.definition.getStringArray(property);
    }

    @Override
    public KAssetDefinition[] getSubdefinitionArray(final String property) {
        return this.definition.getSubdefinitionArray(property);
    }

    @Override
    public Class<?>[] getClassObjectArray(final String property) {
        return this.definition.getClassObjectArray(property);
    }

    @Override
    public <T> Class<? extends T>[] getClassObjectArray(
        final String property,
        final Class<T> targetClass
    ) {
        return this.definition.getClassObjectArray(property, targetClass);
    }

    @Override
    public @Nullable Object[] getObjectArray(final String property) {
        return this.definition.getObjectArray(property);
    }

    @Override
    public boolean hasInt(final String property) {
        return this.definition.hasInt(property);
    }

    @Override
    public boolean hasFloat(final String property) {
        return this.definition.hasFloat(property);
    }

    @Override
    public boolean hasBoolean(final String property) {
        return this.definition.hasBoolean(property);
    }

    @Override
    public boolean hasString(final String property) {
        return this.definition.hasString(property);
    }

    @Override
    public boolean hasSubdefinition(final String property) {
        return this.definition.hasSubdefinition(property);
    }

    @Override
    public <T extends Enum<T>> boolean hasEnum(final String property, final Class<T> enumClass) {
        return this.definition.hasEnum(property, enumClass);
    }

    @Override
    public boolean hasClassObject(final String property) {
        return this.definition.hasClassObject(property);
    }

    @Override
    public <T> boolean hasClassObject(final String property, final Class<T> targetClass) {
        return this.definition.hasClassObject(property, targetClass);
    }

    @Override
    public boolean hasObject(final String property) {
        return this.definition.hasObject(property);
    }

    @Override
    public boolean hasIntArray(final String property) {
        return this.definition.hasIntArray(property);
    }

    @Override
    public boolean hasFloatArray(final String property) {
        return this.definition.hasFloatArray(property);
    }

    @Override
    public boolean hasBooleanArray(final String property) {
        return this.definition.hasBooleanArray(property);
    }

    @Override
    public boolean hasStringArray(final String property) {
        return this.definition.hasStringArray(property);
    }

    @Override
    public boolean hasSubdefinitionArray(final String property) {
        return this.definition.hasSubdefinitionArray(property);
    }

    @Override
    public boolean hasClassObjectArray(final String property) {
        return this.definition.hasClassObjectArray(property);
    }

    @Override
    public <T> boolean hasClassObjectArray(final String property, final Class<T> targetClass) {
        return this.definition.hasClassObjectArray(property, targetClass);
    }

    @Override
    public boolean hasObjectArray(final String property) {
        return this.definition.hasObjectArray(property);
    }
}

