package org.specialistSteak.dataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a method as incomplete.
 */
@Retention(java.lang.annotation.RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Incomplete {
    String key() default "Method is incomplete";
}
