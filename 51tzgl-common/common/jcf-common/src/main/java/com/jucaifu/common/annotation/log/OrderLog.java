package com.jucaifu.common.annotation.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OrderLog
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/10.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderLog {

    String module() default "订单模块";

    String method() default "";

    String description() default "";
}
