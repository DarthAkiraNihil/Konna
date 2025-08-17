package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;

/**
 * Interface that provides methods to serialize different objects into json values,
 * presented by KJsonValue
 * @see io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonSerializer
 */
public interface KJsonSerializer {

    /**
     * Serializes an object into a json value
     * @param object Object to serialize
     * @param clazz Class of the serialized object
     * @return Serialized object into KJsonValue
     * @param <T> Generic parameter of type of the serialized object
     * @throws KJsonSerializationException If it fails to serialize the object
     */
    <T> KJsonValue serialize(T object, Class<? extends T> clazz) throws KJsonSerializationException;

}
