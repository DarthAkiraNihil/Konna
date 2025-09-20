package io.github.darthakiranihil.konna.core.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks class as a singleton so {@link KActivator} should create only one
 * instance of it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KSingleton {
    /**
     * Flag that specifies if a singleton should be stored in {@link KActivator}
     * using a weak reference (if {@code true}) or not. Defaults to {@code false}
     * @return Flag of weak reference usage
     */
    boolean weak() default false;
    boolean immortal() default false;
}
