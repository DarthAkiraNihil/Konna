package io.github.darthakiranihil.konna.core.graphics.frontend.glfw;

@FunctionalInterface
public interface KGlfwErrorCallback {

    void invoke(int error, String description);

}
