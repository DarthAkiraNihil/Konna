package io.github.darthakiranihil.konna.core.graphics.frontend.glfw;

public interface KGlfwAllocator {

    KGlfwAllocateFunction allocate();
    KGlfwReallocateFunction reallocate();
    KGlfwDeallocateFunction deallocate();
    long user();

}
