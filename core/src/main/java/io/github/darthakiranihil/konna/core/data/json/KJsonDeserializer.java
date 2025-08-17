package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;

/**
 * Interface that provides methods to deserialize json values into objects
 * @see io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonDeserializer
 */
public interface KJsonDeserializer {

    /**
     * Deserializes a json value into a new object of passed type.
     * @param value Json value to deserialize
     * @param clazz Class of destination object
     * @return Deserialized object
     * @param <T> Generic type of deserialized object
     * @throws KJsonSerializationException If it fails to deserialize, mostly because of attempting to deserialize object with structure that differs from json value
     */
    <T> T deserialize(KJsonValue value, Class<?> clazz) throws KJsonSerializationException;

}
