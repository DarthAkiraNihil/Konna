package io.github.darthakiranihil.konna.core.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that a method parameter value should be obtained
 * from nonResolvedArgs list instead of creating through
 * {@link io.github.darthakiranihil.konna.core.object.KActivator}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface KNonResolved {
}
