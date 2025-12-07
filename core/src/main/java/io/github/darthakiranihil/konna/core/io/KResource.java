package io.github.darthakiranihil.konna.core.io;

import java.io.Closeable;
import java.io.InputStream;
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

    String name();
    String path();
    boolean exists();
    boolean isOpen();
    ReadableByteChannel channel();
    InputStream stream();
    String string();
    byte[] bytes();

}
