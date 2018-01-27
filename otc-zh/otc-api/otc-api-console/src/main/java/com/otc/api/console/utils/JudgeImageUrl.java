package com.otc.api.console.utils;

import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.dfs.FastDFSHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yong on 16-2-1.
 */
public class JudgeImageUrl {

    public static String getImageUrl(String imgUrl) {
        String result = StringPool.BLANK;
        if (StringUtils.isNotBlank(imgUrl)) {
            if (!imgUrl.contains("http")) {
                result = FastDFSHelper.getFullPath(imgUrl);
            } else {
                result = imgUrl;
            }
        }
        return result;
    }
}
