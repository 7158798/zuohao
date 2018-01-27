package com.szzc.api.app.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yong on 15-12-4.
 */
public class CheckMobileVersionUtil {

    private static final int VERSION_LENGTH = 3;

    public static int checkAppVersion(String versionMobile, String versionActive) {

        int updateType = 0;

        if (StringUtils.equals(versionActive, versionMobile)) {
            return updateType;
        }

        String[] server = versionActive.split("\\.");
        String[] mobile = versionMobile.split("\\.");

        if (server.length != VERSION_LENGTH || mobile.length != VERSION_LENGTH) {
            return updateType;
        }
        for (int i = 0; i < VERSION_LENGTH; i++) {
            int oldNum = Integer.parseInt(mobile[i].trim());
            int newNum = Integer.parseInt(server[i].trim());
            if (oldNum < newNum && i < VERSION_LENGTH - 1) {
                updateType = 2;
                break;
            }
            if (oldNum < newNum && i == VERSION_LENGTH - 1) {
                updateType = 1;
                break;
            } else if (oldNum > newNum) {
                break;
            }
        }
        return updateType;
    }
}
