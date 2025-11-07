package main.java.io.github.darthakiranihil.konna.libfrontend.glfw;

public interface KGlfwAllocator {

    KGlfwAllocateFunction allocate();
    KGlfwReallocateFunction reallocate();
    KGlfwDeallocateFunction deallocate();
    long user();

}
