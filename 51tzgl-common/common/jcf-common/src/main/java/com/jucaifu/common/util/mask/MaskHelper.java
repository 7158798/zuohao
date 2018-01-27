package com.jucaifu.common.util.mask;

import com.jucaifu.common.constants.StringPool;
import org.apache.commons.lang3.StringUtils;

/**
 * MaskHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/25.
 */
public class MaskHelper {

    private static String maskCardNo(String cardNo) {

        if (cardNo == null || cardNo.trim().length() <= 8) {
            return cardNo;
        }
        cardNo = cardNo.trim();
        int length = cardNo.length();

        String firstFourNo = cardNo.substring(0, 4);
        String lastFourNo = cardNo.substring(length - 4);

        StringBuffer mask = new StringBuffer("");
        mask.append(firstFourNo);

        for (int i = 0; i < length - 8; i++) {
            mask.append("*");
        }
        mask.append(lastFourNo);

        return mask.toString();
    }

    /**
     * Mask phone no.
     *
     * @param phoneNo the phone no
     * @return the string
     */
    public static String maskPhoneNo(String phoneNo) {

        if (phoneNo == null || phoneNo.trim().length() != 11) {
            return phoneNo;
        }

        return new StringBuilder().append(phoneNo.substring(0, 3)).append("****").append(phoneNo.substring(phoneNo.length() - 4)).toString();

    }

    /**
     * Mask bank card no.
     *
     * @param bankCardNo the bank card no
     * @return the string
     */
    public static String maskBankCardNo(String bankCardNo) {

        return maskCardNo(bankCardNo);
    }

    /**
     * Mask id card no.
     *
     * @param bankCardNo the bank card no
     * @return the string
     */
    public static String maskIdCardNo(String bankCardNo) {

        return maskCardNo(bankCardNo);
    }


    /**
     * Mask no.
     *
     * @param srcNo the src no
     * @param type  the type
     * @return the string
     */
    public static String maskNo(String srcNo, EnumMaskType type) {

        String maskNo = null;

        switch (type) {

            case BANKCARD_NO: {
                maskNo = maskBankCardNo(srcNo);
                break;
            }
            case IDCARD_NO: {
                maskNo = maskIdCardNo(srcNo);
                break;
            }
            case PHONE_NO: {
                maskNo = maskPhoneNo(srcNo);
                break;
            }
            default: {
                break;
            }
        }

        return maskNo;
    }

    public static String maskRealName(String realName) {
        String name = StringPool.BLANK;
        if(StringUtils.isBlank(realName)){
            return name;
        }
        int length = realName.length();
        if(length > 3) {
            length = 3;
        }
        StringBuffer arterisk = new StringBuffer(realName.substring(0, 1));
        for (int i = 0; i < length - 1; i++) {
            arterisk.append("*");
        }
        name = arterisk.toString();
        return name;
    }

}
