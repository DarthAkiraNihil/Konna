package io.github.darthakiranihil.konna.libfrontend.glfw;

@FunctionalInterface
public interface KGlfwErrorCallback {

    void invoke(int error, long description);

}
