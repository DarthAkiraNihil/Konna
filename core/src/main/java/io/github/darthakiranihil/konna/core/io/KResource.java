package io.github.darthakiranihil.konna.core.io;

import java.io.Closeable;
import java.nio.channels.ReadableByteChannel;

public interface KResource extends Closeable {

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
        public void close() {

        }
    }

    String name();
    String path();
    boolean exists();
    boolean isOpen();
    ReadableByteChannel channel();

}
