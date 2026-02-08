package io.github.darthakiranihil.konna.core.io;

/**
 * Representation of a resource loader that purpose is obvious.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KResourceLoader {

    /**
     * Adds a new protocol in order for resource loader to support.
     * @param protocol Protocol to support
     */
    void addProtocol(KProtocol protocol);

    /**
     * Loads a resource by its path.
     * @param path Path to the resource
     * @return Loaded resource or {@link KResource.Empty} if it is not found
     */
    KResource loadResource(String path);
    /**
     * Loads a resource by its path and explicitly passed protocol.
     * @param path Path to the resource
     * @param protocol Protocol to use to resolve resource
     * @return Loaded resource or {@link KResource.Empty} if it is not found
     */
    KResource loadResource(String path, KProtocol protocol);

    KResource[] loadResources(String path);
    KResource[] loadResources(String path, KProtocol protocol);
    KResource[] loadResources(String path, boolean recursive);
    KResource[] loadResources(String path, KProtocol protocol, boolean recursive);

}
