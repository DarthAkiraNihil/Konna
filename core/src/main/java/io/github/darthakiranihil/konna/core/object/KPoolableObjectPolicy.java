package io.github.darthakiranihil.konna.core.object;

public interface KPoolableObjectPolicy<T extends KObject> {

    T createInstance();
    void onObtain(T object, Object... args);
    void onRelease(T object);

}
