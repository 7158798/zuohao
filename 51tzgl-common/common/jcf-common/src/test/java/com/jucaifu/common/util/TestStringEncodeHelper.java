package com.jucaifu.common.util;

import com.jucaifu.common.log.LOG;
import org.junit.Test;

/**
 * Created by scofieldcai-dev on 15/8/28.
 */
public class TestStringEncodeHelper {

    @Test
    public void testStringEncodeConvert() {

        String str = "测试-1234567890";
        LOG.d(this, "原始 :" + str);

        String str_ISO88591 = StringEncodeHelper.convert(str, StringEncodeHelper.EncodeConvertType.UTF8_TO_ISO88591);
        LOG.d(this, "str_ISO88591 :" + str_ISO88591);

        String str_utf8 = StringEncodeHelper.convert(str_ISO88591, StringEncodeHelper.EncodeConvertType.ISO88591_TO_UTF8);
        LOG.d(this, "str_utf8 :" + str_utf8);

    }
}
