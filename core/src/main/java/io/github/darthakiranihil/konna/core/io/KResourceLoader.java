package io.github.darthakiranihil.konna.core.io;

public interface KResourceLoader {

    KResource load(String path);
    KResource load(String path, KProtocol protocol);

}
