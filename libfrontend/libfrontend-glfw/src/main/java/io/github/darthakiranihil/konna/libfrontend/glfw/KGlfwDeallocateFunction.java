package io.github.darthakiranihil.konna.libfrontend.glfw;

@FunctionalInterface
public interface KGlfwDeallocateFunction {

    void invoke(long block, long user);

}
