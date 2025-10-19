package io.github.darthakiranihil.konna.core.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks constructor as to be called when an object of a class is created
 * with {@link io.github.darthakiranihil.konna.core.object.KActivator}.
 * It's not required, though, because it there is no constructor with such
 * annotation, the first one will be invoked.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface KInjectedConstructor {
}
