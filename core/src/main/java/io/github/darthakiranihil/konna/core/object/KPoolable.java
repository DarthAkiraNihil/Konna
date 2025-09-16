package io.github.darthakiranihil.konna.core.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KPoolable {

    int initialPoolSize();
    boolean weak() default false;

}
