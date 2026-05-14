package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KJsonAssetLoader;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.entity.asset.KEntityMetadataCollection;
import io.github.darthakiranihil.konna.entity.except.KEntityException;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KStandardEntityFactoryNegativeTests extends KStandardTestClass {

    private final KEntityFactory factory;

    public KStandardEntityFactoryNegativeTests() {
        var constructor = KReflectionUtils.getConstructor(
            KAppContainer.useGenerated(),
            KApplicationFeatures.class,
            KSystemFeatures.class
        );

        Assertions.assertNotNull(constructor);
        KEngineModule engineModule = KEngineModule.create(
            KReflectionUtils.newInstance(
                constructor,
                new KStandardApplicationFeatures(Map.of()),
                new KSystemFeatures()
            )
        );

        var assetLoader = new KJsonAssetLoader(
            engineModule.resourceLoader(),
            new KStandardJsonParser(new KStandardJsonTokenizer()),
            new String[] { "classpath:assets/" },
            new KAssetTypedef[][]{ KEntityComponent.getAssetTypedefs2() }
        );

        var metadataCollection = new KEntityMetadataCollection(
            assetLoader
        );

        this.factory = new KStandardEntityFactory(
            metadataCollection,
            engineModule.activator(),
            this.jsonDeserializer,
            engineModule.objectRegistry()
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
