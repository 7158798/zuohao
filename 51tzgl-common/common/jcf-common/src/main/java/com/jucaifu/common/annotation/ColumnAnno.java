package com.jucaifu.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ColumnAnno
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/30.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnAnno {
    String value() default "";
}
