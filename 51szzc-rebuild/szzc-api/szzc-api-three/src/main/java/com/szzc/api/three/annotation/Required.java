package com.szzc.api.three.annotation;

import java.lang.annotation.*;

/**
 * Created by lx on 17-7-23.
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Required {

    boolean value() default true;
}
