package io.github.darthakiranihil.konna.core.io;

import java.net.URI;
import java.nio.channels.ReadableByteChannel;

public interface KResource {

    String name();
    boolean exists();
    boolean isOpen();
    URI uri();
    ReadableByteChannel channel();

}
