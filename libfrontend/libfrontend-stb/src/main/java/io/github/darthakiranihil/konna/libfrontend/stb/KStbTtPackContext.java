package io.github.darthakiranihil.konna.libfrontend.stb;

import java.nio.ByteBuffer;

public interface KStbTtPackContext {

    long user_allocator_context();
    KStbRpContext pack_info();
    int width();
    int height();
    int stride_in_bytes();
    int padding();
    boolean skip_missing();
    int h_oversample();
    int v_oversample();
    ByteBuffer pixels(int capacity);
    KStbRpNode[] nodes(int capacity);

}
