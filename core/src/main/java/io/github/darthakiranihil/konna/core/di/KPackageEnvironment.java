package io.github.darthakiranihil.konna.core.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets a specific environment for a package. This annotation's data should
 * be used by {@link KContainerResolver}
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface KPackageEnvironment {
    /**
     * Name of the package environment.
     * @return Name of the package environment
     */
    String name();
}
