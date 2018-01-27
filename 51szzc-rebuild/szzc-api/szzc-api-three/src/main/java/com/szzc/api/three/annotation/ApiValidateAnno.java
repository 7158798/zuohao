package com.szzc.api.three.annotation;

import java.lang.annotation.*;

/**
 * Created by lx on 17-7-23.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiValidateAnno {

    boolean skip() default true;

    int time() default 1;
}
