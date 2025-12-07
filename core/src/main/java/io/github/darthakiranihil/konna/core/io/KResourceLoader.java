package io.github.darthakiranihil.konna.core.io;

public interface KResourceLoader {

    void addProtocol(KProtocol protocol);
    KResource loadResource(String path);
    KResource loadResource(String path, KProtocol protocol);

}
