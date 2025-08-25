package io.github.darthakiranihil.konna.core.engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides meta information about engine component.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KComponentMetaInfo {
    // String name();

    /**
     * Returns config filename of the component. The config is read from java resources
     * @return Filename of config file
     */
    String configFilename();

    /**
     * Returns full package path where component's services will be looked for.
     * @return Package name of component services
     */
    String servicesPackage();
}
