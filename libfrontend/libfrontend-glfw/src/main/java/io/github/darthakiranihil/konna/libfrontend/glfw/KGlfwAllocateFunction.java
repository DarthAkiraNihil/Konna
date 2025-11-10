package io.github.darthakiranihil.konna.libfrontend.glfw;

@FunctionalInterface
public interface KGlfwAllocateFunction {

    long invoke(long size, long user);

}
