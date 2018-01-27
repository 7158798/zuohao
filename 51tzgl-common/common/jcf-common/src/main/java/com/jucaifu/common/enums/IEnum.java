package com.jucaifu.common.enums;

/**
 * IEnum
 *
 * @param <ValueType> the type parameter
 * @author scofieldcai  Created by scofieldcai-dev on 15/8/31.
 */
public interface IEnum<ValueType> {

    /**
     * Gets value.
     *
     * @return the value
     */
    ValueType getValue();

    /**
     * Gets desc.
     *
     * @return the desc
     */
    String getDesc();

}
