package io.github.darthakiranihil.konna.core.graphics.frontend.glfw;

@FunctionalInterface
public interface KGlfwAllocateFunction {

    void invoke(long size, long user);

}
