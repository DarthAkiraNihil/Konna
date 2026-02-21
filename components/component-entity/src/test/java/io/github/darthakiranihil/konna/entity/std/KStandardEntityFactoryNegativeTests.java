package io.github.darthakiranihil.konna.entity.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.std.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.entity.KEntityFactory;
import io.github.darthakiranihil.konna.entity.asset.KEntityMetadataCollection;
import io.github.darthakiranihil.konna.entity.except.KEntityException;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KStandardEntityFactoryNegativeTests extends KStandardTestClass {

    private final KEntityFactory factory;

    public KStandardEntityFactoryNegativeTests() {
        var assetLoader = new KJsonSubtypeBasedAssetLoader(
            KStandardTestClass.context,
            Map.of("entities", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KEntityMetadataTypedef.ENTITY_METADATA_ASSET_TYPE },
                new String[] {"classpath:assets/entities.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );
        assetLoader.addAssetTypedef(new KEntityMetadataTypedef());

        var metadataCollection = new KEntityMetadataCollection(
            assetLoader
        );

        this.factory = new KStandardEntityFactory(
            metadataCollection,
            KStandardTestClass.context,
            this.jsonDeserializer
        );
    }

    @Test
    public void testCreateEntityButDeserializationFailed() {

        Assertions.assertThrows(
            KEntityException.class,
            () -> this.factory.createEntity(
                "e2",
                "Typpi2",
                KJsonValue.fromMap(
                    Map.of(
                        TestEntityDataComponent.class.getCanonicalName(),
                        new KJsonValue(KJsonValueType.NULL, null)
                    )
                )
            )
        );

    }
}
