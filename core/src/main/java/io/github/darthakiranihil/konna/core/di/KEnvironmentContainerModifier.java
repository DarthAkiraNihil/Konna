package io.github.darthakiranihil.konna.core.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signalizes that this type of caller class is allowed to modify the resolved
 * environment container, so {@link KContainerResolver} should not return
 * an immutable container.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KEnvironmentContainerModifier {
}
