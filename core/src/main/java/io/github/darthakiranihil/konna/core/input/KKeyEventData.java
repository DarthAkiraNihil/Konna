package io.github.darthakiranihil.konna.core.input;

public record KKeyEventData(
    KKey key,
    boolean withShift,
    boolean withAlt,
    boolean withSuper,
    boolean withCapsLock,
    boolean withNumLock
) {
}
