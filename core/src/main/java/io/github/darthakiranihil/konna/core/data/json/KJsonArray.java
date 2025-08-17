package io.github.darthakiranihil.konna.core.data.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the type of list-like field elements (the generic type).
 * It is required as Java uses type erasure on generics so it is impossible
 * to get list generic parameter at runtime.
 * Other fields are not affected by this annotation, at least in the standard implementation of deserializer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KJsonArray {
    /**
     * Returns the type of list elements
     * @return The actual type
     */
    Class<?> elementType();
}
