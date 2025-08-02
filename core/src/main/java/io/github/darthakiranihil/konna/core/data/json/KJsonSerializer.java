package io.github.darthakiranihil.konna.core.data.json;

public interface KJsonSerializer {

    <T> KJsonValue serialize(T object, Class<? extends T> clazz);

}
