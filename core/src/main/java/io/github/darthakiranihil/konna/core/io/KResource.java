package io.github.darthakiranihil.konna.core.io;

import java.io.Closeable;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/**
 * Interface for a resource descriptor that abstracts from the actual file.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KResource extends Closeable {

    /**
     * Implementation of an empty resource that likely does not exist.
     * Should be returned by {@link KResourceLoader#loadResource(String)} and
     * {@link KResourceLoader#loadResource(String, KProtocol)} if resource
     * with given path is not found.
     */
    final class Empty implements KResource {

        private final String name;
        private final String path;

        public Empty(
            final String path,
            final String name
        ) {
            this.name = name;
            this.path = path;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public String path() {
            return this.path;
        }

        @Override
        public boolean exists() {
            return false;
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public ReadableByteChannel channel() {
            return null;
        }

        @Override
        public InputStream stream() {
            return null;
        }

        @Override
        public String string() {
            return "";
        }

        @Override
        public byte[] bytes() {
            return new byte[0];
        }

        @Override
        public void close() {

        }
    }

    /**
     * Returns name of the resource (typically the last part of its path).
     * @return Name of the resource
     */
    String name();

    /**
     * Returns string representation of resource's path (that is usually
     * the same as passed to a resource loader).
     * @return Path to the resource
     */
    String path();

    /**
     * Returns flag of resource existence.
     * @return {@code true} if the resource exists for real, else {@code false}
     */
    boolean exists();

    /**
     * Returns indication if the resource is open (like its stream is open etc.).
     * @return {@code true} if the resource is open, else {@code false}
     */
    boolean isOpen();

    /**
     * Returns resource's content as a readable byte channel.
     * @return Readable byte channel of resource's content
     */
    ReadableByteChannel channel();
    /**
     * Returns resource's content as an input stream.
     * @return Input stream of resource's content
     */
    InputStream stream();
    /**
     * Returns resource's content as a string.
     * @return Resource's content as a string
     */
    String string();
    /**
     * Returns resource's content as byte array.
     * @return Byte array of resource's content
     */
    byte[] bytes();

}
