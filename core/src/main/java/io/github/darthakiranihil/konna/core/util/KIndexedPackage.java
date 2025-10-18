package io.github.darthakiranihil.konna.core.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signalizes that this package should be included in the package index.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 *
 * @see KIndex
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface KIndexedPackage {
}
