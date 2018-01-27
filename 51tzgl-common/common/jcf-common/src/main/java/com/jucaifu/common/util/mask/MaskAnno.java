package com.jucaifu.common.util.mask;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MaskAnno
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/21.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaskAnno {

    EnumMaskType maskType();
}
