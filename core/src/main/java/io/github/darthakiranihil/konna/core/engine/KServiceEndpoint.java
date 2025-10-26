package io.github.darthakiranihil.konna.core.engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signalizes that a method is a service endpoint.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KServiceEndpoint {
    /**
     * Route of the endpoint.
     * It does not include name of the service to endpoint belongs to, same
     * for engine component
     * @return Route of the endpoint.
     */
    String route();

    /**
     * Class that is used to convert a message to array of objects
     * representing called endpoint args. If not specified, a message
     * will be converted to an empty array.
     * @return Class of message to endpoint args converter
     */
    Class<? extends KMessageToEndpointConverter>
        converter() default KMessageToEndpointConverter.NoConverter.class;
}
