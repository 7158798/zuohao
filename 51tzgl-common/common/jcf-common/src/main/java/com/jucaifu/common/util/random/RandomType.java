package com.jucaifu.common.util.random;

import com.jucaifu.common.constants.CommonConstant;

/**
 * RandomType
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/28.
 */
public enum RandomType {

    /**
     * The ALL_CHAR.
     */
    ALL_CHAR(CommonConstant.ALL_CHAR, "产生仅仅包含数字，大小写字母的随机码"),
    /**
     * The ALL_LETTER_CHAR.
     */
    ALL_LETTER_CHAR(CommonConstant.ALL_LETTER_CHAR, "产生仅仅包含大小写字母的随机码"),
    /**
     * The ALL_NUMBER_CHAR.
     */
    ALL_NUMBER_CHAR(CommonConstant.ALL_NUMBER_CHAR, "产生仅仅包含数字的随机码"),;

    /**
     * The Random source.
     */
    private String randomSource;

    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Random type.
     *
     * @param randomSource the random source
     * @param desc         the desc
     */
    RandomType(String randomSource, String desc) {
        this.randomSource = randomSource;
        this.desc = desc;
    }

    public String getRandomSource() {
        return randomSource;
    }

    public String getDesc() {
        return desc;
    }
}
