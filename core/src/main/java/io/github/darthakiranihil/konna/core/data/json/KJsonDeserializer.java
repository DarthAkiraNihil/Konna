package io.github.darthakiranihil.konna.core.data.json;

public interface KJsonDeserializer {

    <T> T deserialize(KJsonValue value, Class<?> clazz);

}
