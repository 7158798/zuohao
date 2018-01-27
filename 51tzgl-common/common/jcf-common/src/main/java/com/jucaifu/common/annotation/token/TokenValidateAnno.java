package com.jucaifu.common.annotation.token;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TokenValidateAnno
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/09.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenValidateAnno {

    boolean skip() default false;
}
