/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */
package edu.hm.wgabler.limmer.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @author Andrea Limmer, limmer@hm.edu
 * @since 27/03/2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RenderMe {

    String with() default "";

}
