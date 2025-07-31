package io.github.darthakiranihil.konna.core.data.json;

public interface KJsonSerializer {

    <T> KJsonValue serialize(Object object, Class<T> clazz);

}
