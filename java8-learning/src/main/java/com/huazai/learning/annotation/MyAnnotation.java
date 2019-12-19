package com.huazai.learning.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author pyh
 * @email pyh@efala.com
 * @date 2019/12/19 16:43:36
 */
@Repeatable(value = MyAnnotations.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface MyAnnotation {
    String value() default "China";
}
