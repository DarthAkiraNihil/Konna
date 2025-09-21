package io.github.darthakiranihil.konna.core.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks class as a singleton so {@link KActivator} should create only one
 * instance of it.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
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

    /**
     * Flag that indicates if singleton is immortal, so it cannot be deleted with {@link KActivator}
     * and only application termination can destroy immortal objects. Has priority above
     * weak flag, so if both weak and immortal are true, weak flag will be ignored.
     * @return Flag of object immortality
     */
    boolean immortal() default false;
}
