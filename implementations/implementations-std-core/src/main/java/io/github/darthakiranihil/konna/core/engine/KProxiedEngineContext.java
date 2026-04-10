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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.app.KFrameTaskDescription;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.di.*;
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KDisposer;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Implementation of {@link KEngineContext} that requires ready instances of all
 * context classes in order to construct the context. It is basically just a proxy
 * to all context services such as resource loader, message system etc.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
@KContainerModifier
public final class KProxiedEngineContext extends KObject implements KEngineContext {

    private final KActivator activator;
    private final KActivator2 activator2;
    private final KContainerAccessor containerResolver;
    private final KObjectRegistry objectRegistry;
    private final KQueueBasedEventSystem eventSystem;
    private final KQueueBasedMessageSystem messageSystem;
    private final KResourceLoader resourceLoader;
    private final KAssetLoader assetLoader;
    private final KFrameTaskScheduler frameTaskScheduler;
    private final @Nullable KDisposer<KEngineContext> disposer;

    /**
     * Standard constructor.
     * @param activator Activator of the context
     * @param containerAccessor Container accessor of the context
     * @param objectRegistry Object registry of the context
     * @param eventSystem Event system of the context
     * @param messageSystem Message system of the context
     * @param resourceLoader Resource loader of the context
     * @param assetLoader Asset loader of the context
     * @param frameTaskScheduler Frame task scheduler of the context
     * @param disposer Additional disposer for this context when it shuts down
     */
    public KProxiedEngineContext(
        final KActivator activator,
        final KActivator2 activator2,
        final KContainerAccessor containerAccessor,
        final KObjectRegistry objectRegistry,
        final KQueueBasedEventSystem eventSystem,
        final KQueueBasedMessageSystem messageSystem,
        final KResourceLoader resourceLoader,
        final KAssetLoader assetLoader,
        final KFrameTaskScheduler frameTaskScheduler,
        final @Nullable KDisposer<KEngineContext> disposer
    ) {
        super("context", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM, KTag.DefaultTags.STD));
        this.activator = activator;
        this.activator2 = activator2;
        this.containerResolver = containerAccessor;
        this.objectRegistry = objectRegistry;
        this.eventSystem = eventSystem;
        this.messageSystem = messageSystem;
        this.resourceLoader = resourceLoader;
        this.assetLoader = assetLoader;
        this.frameTaskScheduler = frameTaskScheduler;
        this.disposer = disposer;
    }

    /**
     * Standard constructor, but without disposer.
     * @param activator Activator of the context
     * @param containerAccessor Container accessor of the context
     * @param objectRegistry Object registry of the context
     * @param eventSystem Event system of the context
     * @param messageSystem Message system of the context
     * @param resourceLoader Resource loader of the context
     * @param assetLoader Asset loader of the context
     * @param frameTaskScheduler Frame task scheduler of the context
     */
    public KProxiedEngineContext(
        final KActivator activator,
        final KActivator2 activator2,
        final KContainerAccessor containerAccessor,
        final KObjectRegistry objectRegistry,
        final KQueueBasedEventSystem eventSystem,
        final KQueueBasedMessageSystem messageSystem,
        final KResourceLoader resourceLoader,
        final KAssetLoader assetLoader,
        final KFrameTaskScheduler frameTaskScheduler
    ) {
        this(
            activator,
            activator2,
            containerAccessor,
            objectRegistry,
            eventSystem,
            messageSystem,
            resourceLoader,
            assetLoader,
            frameTaskScheduler,
            null
        );
    }

    @Override
    public KContainer getContainer() {
        return this.containerResolver.getContainer();
    }

    @Override
    public KAsset loadAsset(final String assetId, final String typeAlias) {
        return this.assetLoader.loadAsset(assetId, typeAlias);
    }

    @Override
    public void addAssetTypedef(final KAssetTypedef typedef) {
        this.assetLoader.addAssetTypedef(typedef);
    }

    @Override
    public void addNewAsset(
        final String assetId,
        final String internalType,
        final KAssetDefinition rawDefinition
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
    public KResource[] loadResources(final String path) {
        return this.resourceLoader.loadResources(path);
    }

    @Override
    public KResource[] loadResources(final String path, final KProtocol protocol) {
        return this.resourceLoader.loadResources(path, protocol);
    }

    @Override
    public KResource[] loadResources(final String path, boolean recursive) {
        return this.resourceLoader.loadResources(path, recursive);
    }

    @Override
    public KResource[] loadResources(
        final String path,
        final KProtocol protocol,
        boolean recursive
    ) {
        return this.resourceLoader.loadResources(path, protocol, recursive);
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
    public <T> KEventInvoker<T> getEventInvoker(final String eventName) {
        return this.eventSystem.getEventInvoker(eventName);
    }

    @Override
    public KSimpleEventInvoker getSimpleEventInvoker(final String eventName) {
        return this.eventSystem.getSimpleEventInvoker(eventName);
    }

    @Override
    public <T> KEventSubscriber<T> getEventSubscriber(final String eventName) {
        return this.eventSystem.getEventSubscriber(eventName);
    }

    @Override
    public KSimpleEventSubscriber getSimpleEventSubscriber(final String eventName) {
        return this.eventSystem.getSimpleEventSubscriber(eventName);
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
    public KMessageSystem addMessageRoute(
        final String messageId,
        final Consumer<KMessage> destination
    ) {
        return this.messageSystem.addMessageRoute(messageId, destination);
    }

    @Override
    public KMessageSystem addMessageRoute(
        final String messageId,
        final Consumer<KMessage> destination,
        final List<Class<? extends KTunnel>> tunnels
    ) {
        return this.messageSystem.addMessageRoute(messageId, destination, tunnels);
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
    public <T> T createObject(final Class<? extends T> clazz) {
        return this.activator2.createObject(clazz);
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
    public void scheduleTask(
        final KFrameTaskDescription description,
        final Runnable task
    ) {
        this.frameTaskScheduler.scheduleTask(description, task);
    }

    @Override
    public void handleShutdown() {
        this.messageSystem.stopPolling();
        this.eventSystem.stopPolling();
        if (this.disposer != null) {
            this.disposer.dispose(this);
        }
    }
}
