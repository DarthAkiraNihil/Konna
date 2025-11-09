package io.github.darthakiranihil.konna.libfrontend.glfw;

@FunctionalInterface
public interface KGlfwAllocateFunction {

    void invoke(long size, long user);

}
