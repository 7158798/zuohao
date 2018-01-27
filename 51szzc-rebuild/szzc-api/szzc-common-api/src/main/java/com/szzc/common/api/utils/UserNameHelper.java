package com.szzc.common.api.utils;

import com.jucaifu.common.constants.StringPool;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lx on 16-11-30.
 */
public class UserNameHelper {

    /**
     * 得到用户昵称
     */
    public static String getNiceName(String mobileNumber, String nickName) {
        String nick = StringPool.BLANK;
        if (StringUtils.isNotBlank(nickName) ) {
            if (StringUtils.isNotEmpty(mobileNumber) && nickName.equals(mobileNumber) ) {
                nick = StringUtils.overlay(nickName, "****", 0, 7);
            } else {
                nick = nickName;
            }
        }
        return nick;
    }
}
