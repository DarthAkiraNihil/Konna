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

package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.entity.asset.KEntityMetadataCollection;
import io.github.darthakiranihil.konna.entity.except.KEntityException;

import java.util.*;

/**
 * Standard implementation of {@link KEntityFactory} that is able to create
 * entities with recursive resolving of all derived properties from parent types.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@KSingleton
public class KStandardEntityFactory extends KObject implements KEntityFactory {

    private final KEntityMetadataCollection metadataCollection;
    private final KActivator activator;
    private final KJsonDeserializer deserializer;

    /**
     * Standard constructor.
     * @param metadataCollection Entity metadata collection (to resolve types)
     * @param activator Activator to create Entity components
     * @param deserializer JSON deserializer to restore data components with
     *                     provided data
     */
    public KStandardEntityFactory(
        @KInject final KEntityMetadataCollection metadataCollection,
        @KInject final KActivator activator,
        @KInject final KJsonDeserializer deserializer
    ) {
        super("std_entity_factory", KStructUtils.setOfTags(KTag.DefaultTags.STD));

        this.metadataCollection = metadataCollection;
        this.activator = activator;
        this.deserializer = deserializer;
    }

    @Override
    public KEntity createEntity(final String name, final String type) {
        KEntityMetadata metadata = this.metadataCollection.getAsset(type);

        Set<Class<? extends KEntityDataComponent>> dataComponents
            = new HashSet<>(List.of(metadata.dataComponents()));
        Queue<String> extensions = new LinkedList<>(List.of(metadata.dataExtensionList()));

        while (!extensions.isEmpty()) {
            String extension = extensions.poll();
            KEntityMetadata superType = this.metadataCollection.getAsset(extension);
            dataComponents.addAll(List.of(superType.dataComponents()));
            extensions.addAll(List.of(superType.dataExtensionList()));
        }

        List<KEntityDataComponent> createdComponents = new LinkedList<>();
        for (var dataComponent: dataComponents) {
            createdComponents.add(
                this.activator.createObject(dataComponent)
            );
        }

        KEntity entity = new KEntity(name, type, createdComponents);
        List<KEntityBehaviour> behaviours = this.createBehaviours(metadata, entity);
        for (var behaviour: behaviours) {
            entity.addBehaviour(behaviour);
        }

        return entity;
    }

    /**
     * Creates an entity by its type and data.
     * The data must be a JSON objects, whose keys are canonical names of used
     * data component classes and values are JSON-represented data components instances
     * of its class.
     * @param name Name of created entity
     * @param type Entity type
     * @param data Entity data
     * @return Created entity instance with provided data
     */
    @Override
    public KEntity createEntity(
        final String name,
        final String type,
        final KJsonValue data
    ) {
        KEntityMetadata metadata = this.metadataCollection.getAsset(type);

        Set<Class<? extends KEntityDataComponent>> dataComponents =
                new HashSet<>(List.of(metadata.dataComponents()));
        Queue<String> extensions = new LinkedList<>(List.of(metadata.dataExtensionList()));

        while (!extensions.isEmpty()) {
            String extension = extensions.poll();
            KEntityMetadata superType = this.metadataCollection.getAsset(extension);
            dataComponents.addAll(List.of(superType.dataComponents()));
            extensions.addAll(List.of(superType.dataExtensionList()));
        }

        List<KEntityDataComponent> createdComponents = new LinkedList<>();
        for (var dataComponent: dataComponents) {
            KEntityDataComponent deserialized = this.deserializer.deserialize(
                data.getProperty(dataComponent.getCanonicalName()),
                dataComponent
            );

            if (deserialized == null) {
                throw new KEntityException(
                    String.format(
                        "Could not create entity %s: deserialization for data component %s failed",
                        type,
                        dataComponent.getCanonicalName()
                    )
                );
            }

            createdComponents.add(
                deserialized
            );
        }

        KEntity entity = new KEntity(name, type, createdComponents);
        List<KEntityBehaviour> behaviours = this.createBehaviours(metadata, entity);
        for (var behaviour: behaviours) {
            entity.addBehaviour(behaviour);
        }

        return entity;
    }

    private List<KEntityBehaviour> createBehaviours(
        final KEntityMetadata metadata,
        final KEntity createdEntity
    ) {
        Set<Class<? extends KEntityBehaviour>> behaviours
            = new HashSet<>(List.of(metadata.behaviours()));
        Queue<String> extensions = new LinkedList<>(List.of(metadata.behaviourExtensionList()));

        while (!extensions.isEmpty()) {
            String extension = extensions.poll();
            KEntityMetadata superType = this.metadataCollection.getAsset(extension);
            behaviours.addAll(List.of(superType.behaviours()));
            extensions.addAll(List.of(superType.behaviourExtensionList()));
        }

        List<KEntityBehaviour> createdBehaviours = new LinkedList<>();
        for (var behaviour: behaviours) {
            createdBehaviours.add(
                this.activator.createObject(behaviour, createdEntity)
            );
        }

        return createdBehaviours;
    }
}
