package com.jucaifu.common.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ****************************************
 *
 * @author : scofieldandroid@gmail.com
 * @ClassName : DataValidateRuleAnno
 * @date : 2013-7-1 上午11:17:01
 * @Description: 注入数据校验规则
 ****************************************/
@Retention(RUNTIME)
@Target({ElementType.FIELD})
public @interface DataValidateRuleAnno {

    /**
     * Validate type.
     *
     * @return the validate type
     */
    ValidateType validateType() default ValidateType.NOT_EMPTY;

    /**
     * 自定义的校验规则
     *
     * @return the string
     */
    String validateRuleRegex() default "";

    /**
     * 校验为空的错误信息
     *
     * @return the string
     */
    String emptyMSG() default "";

    /**
     * 校验不合法的错误信息
     *
     * @return the string
     */
    String errorMSG() default "";

    /**
     * Need validate when empty.
     *
     * @return the boolean
     */
    boolean needValidateWhenEmpty() default true;

}
