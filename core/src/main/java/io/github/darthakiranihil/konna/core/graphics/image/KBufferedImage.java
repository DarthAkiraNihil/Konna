package io.github.darthakiranihil.konna.core.graphics.image;

import java.nio.ByteBuffer;

public record KBufferedImage(
    int width,
    int height,
    ByteBuffer pixels
) {
}
