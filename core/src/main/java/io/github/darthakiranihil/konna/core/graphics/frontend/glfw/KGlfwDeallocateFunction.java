package io.github.darthakiranihil.konna.core.graphics.frontend.glfw;

@FunctionalInterface
public interface KGlfwDeallocateFunction {

    void invoke(long block, long user);

}
