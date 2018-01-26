package com.ruizton.main.aspect;

import java.lang.annotation.*;

/**
 * SysLog
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/10.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String code() default "";

    String method() default "";

    String description() default "";
}
