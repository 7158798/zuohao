package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.security.Base64Helper;
import org.apache.commons.codec.DecoderException;

/**
 * TestBase64Helper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/13.
 */
public class TestBase64Helper extends BaseTest {

    @Override
    public void doTest() {

        String src = "sss";

        String encodeStr = Base64Helper.encode2Str(src);
        LOG.d(this, "encodeStr:" + encodeStr);
        String decodeStr = Base64Helper.decode2Str(encodeStr);
        LOG.d(this, "decode2Str:" + decodeStr);

        String encodeHexStr = Base64Helper.encode2HexStr(src);
        LOG.d(this, "encode2HexStr:" + encodeHexStr);
        String decodeHexStr = null;
        try {
            decodeHexStr = Base64Helper.decodeHexStr2Str(encodeHexStr);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        LOG.d(this, "decodeHexStr2Str:" + decodeHexStr);

    }
}
