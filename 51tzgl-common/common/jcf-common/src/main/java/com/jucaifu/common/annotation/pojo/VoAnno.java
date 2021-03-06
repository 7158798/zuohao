package com.jucaifu.common.annotation.pojo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VoAnno
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/27.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VoAnno {
    String value() default "";
}
