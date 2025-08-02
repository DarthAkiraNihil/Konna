package io.github.darthakiranihil.konna.core.data.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides serializer a custom name for serialized field key in a json value.
 * Also used by deserializer to recognize the required field if its json name differs from class-declared
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KJsonCustomName {
    /**
     * Returns the custom name of the field
     * @return The actual name
     */
    String name();
}
