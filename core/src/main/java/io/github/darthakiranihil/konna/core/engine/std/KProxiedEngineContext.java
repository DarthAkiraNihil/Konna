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

package io.github.darthakiranihil.konna.core.engine.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.di.KEnvironmentContainerModifier;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.util.KIndex;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of {@link KEngineContext} that requires ready instances of all
 * context classes in order to construct the context. It is basically just a proxy
 * to all context services such as resource loader, message system etc.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
@KEnvironmentContainerModifier
public final class KProxiedEngineContext extends KObject implements KEngineContext {

    private final KActivator activator;
    private final KContainerResolver containerResolver;
    private final KIndex index;
    private final KObjectRegistry objectRegistry;
    private final KQueueBasedEventSystem eventSystem;
    private final KQueueBasedMessageSystem messageSystem;
    private final KResourceLoader resourceLoader;
    private final KAssetLoader assetLoader;

    /**
     * Standard constructor.
     * @param activator Activator of the context
     * @param containerResolver Container resolver of the context
     * @param index Index of the context
     * @param objectRegistry Object registry of the context
     * @param eventSystem Event system of the context
     * @param messageSystem Message system of the context
     * @param resourceLoader Resource loader of the context
     * @param assetLoader Asset loader of the context
     */
    public KProxiedEngineContext(
        final KActivator activator,
        final KContainerResolver containerResolver,
        final KIndex index,
        final KObjectRegistry objectRegistry,
        final KQueueBasedEventSystem eventSystem,
        final KQueueBasedMessageSystem messageSystem,
        final KResourceLoader resourceLoader,
        final KAssetLoader assetLoader
        ) {
        super("context", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM, KTag.DefaultTags.STD));
        this.activator = activator;
        this.containerResolver = containerResolver;
        this.index = index;
        this.objectRegistry = objectRegistry;
        this.eventSystem = eventSystem;
        this.messageSystem = messageSystem;
        this.resourceLoader = resourceLoader;
        this.assetLoader = assetLoader;
    }

    @Override
    public KContainer resolveContainer() {
        return this.containerResolver.resolveContainer();
    }

    @Override
    public List<KPair<String, List<String>>> getEnvironments() {
        return this.containerResolver.getEnvironments();
    }

    @Override
    public KAsset loadAsset(final String assetId, final String typeAlias) {
        return this.assetLoader.loadAsset(assetId, typeAlias);
    }

    @Override
    public void addAssetTypeAlias(final String typeAlias, final KJsonValidator schema) {
        this.assetLoader.addAssetTypeAlias(typeAlias, schema);
    }

    @Override
    public void addNewAsset(
        final String assetId,
        final String internalType,
        final KJsonValue rawDefinition
    ) {
        this.assetLoader.addNewAsset(assetId, internalType, rawDefinition);
    }

    @Override
    public void addProtocol(final KProtocol protocol) {
        this.resourceLoader.addProtocol(protocol);
    }

    @Override
    public KResource loadResource(final String path) {
        return this.resourceLoader.loadResource(path);
    }

    @Override
    public KResource loadResource(final String path, final KProtocol protocol) {
        return this.resourceLoader.loadResource(path, protocol);
    }

    @Override
    public <T> void registerEvent(final KEvent<T> event) {
        this.eventSystem.registerEvent(event);
    }

    @Override
    public void registerEvent(final KSimpleEvent event) {
        this.eventSystem.registerEvent(event);
    }

    @Override
    public @Nullable <T> KEvent<T> getEvent(final String eventName) {
        return this.eventSystem.getEvent(eventName);
    }

    @Override
    public @Nullable KSimpleEvent getSimpleEvent(final String eventName) {
        return this.eventSystem.getSimpleEvent(eventName);
    }

    @Override
    public void deliverMessage(final KMessage message) {
        this.messageSystem.deliverMessage(message);
    }

    @Override
    public void deliverMessageSync(final KMessage message) {
        this.messageSystem.deliverMessageSync(message);
    }

    @Override
    public KMessageSystem addMessageRoute(
        final String messageId,
        final String destinationEndpoint,
        final List<Class<? extends KTunnel>> tunnels
    ) {
        return this.messageSystem.addMessageRoute(messageId, destinationEndpoint, tunnels);
    }

    @Override
    public KMessageSystem addMessageRoute(
        final String messageId,
        final String destinationEndpoint
    ) {
        return this.messageSystem.addMessageRoute(messageId, destinationEndpoint);
    }

    @Override
    public void registerComponent(final KComponent component) {
        this.messageSystem.registerComponent(component);
    }

    @Override
    public KContainer newContainer() {
        return this.activator.newContainer();
    }

    @Override
    public <T> T createObject(
        final Class<? extends T> clazz,
        final KContainer container,
        final Object... nonInjectedArgs
    ) {
        return this.activator.createObject(clazz, container, nonInjectedArgs);
    }

    @Override
    public <T> T createObject(final Class<? extends T> clazz, final Object... nonInjectedArgs) {
        return this.activator.createObject(clazz, nonInjectedArgs);
    }

    @Override
    public <T> void deleteObject(final T object) {
        this.activator.deleteObject(object);
    }

    @Override
    public void pushObjectToRegistry(
        final KObject obj,
        final KObjectInstantiationType instantiationType
    ) {
        this.objectRegistry.pushObjectToRegistry(obj, instantiationType);
    }

    @Override
    public void removeObjectFromRegistry(final UUID objectId) {
        this.objectRegistry.removeObjectFromRegistry(objectId);
    }

    @Override
    public Set<KObjectRegistryRecord> listObjects() {
        return this.objectRegistry.listObjects();
    }

    @Override
    public List<Class<?>> getClassIndex() {
        return this.index.getClassIndex();
    }

    @Override
    public List<Package> getPackageIndex() {
        return this.index.getPackageIndex();
    }

    @Override
    public void handleShutdown() {
        this.messageSystem.stopPolling();
        this.eventSystem.stopPolling();
    }
}
