package io.github.darthakiranihil.konna.core.io;

import org.jspecify.annotations.Nullable;

/**
 * Representation of a protocol - the way with which
 * a resource should be loaded by its path.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KProtocol {

    /**
     * Resolves a resource by its path.
     * @param path Path to the resource
     * @return Loaded resource object or null if the resource is not found
     */
    @Nullable KResource resolve(String path);

}
