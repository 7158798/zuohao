package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.security.DESedeHelper;
import org.apache.commons.codec.binary.Hex;

/**
 * TestDESedeHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/29.
 */
public class TestDESedeHelper extends BaseTest {

    @Override
    public void doTest() {

        try {
            String src = "你是最棒的";
            byte[] bytesKey = DESedeHelper.generateBytesKey();
            String hexStrKey = Hex.encodeHexString(bytesKey);

            byte[] encryptData = DESedeHelper.encrypt(bytesKey, src.getBytes());
            String encryptStr = Hex.encodeHexString(encryptData);
            LOG.d(this, encryptStr);
            byte[] decryptData = DESedeHelper.decrypt(bytesKey, Hex.decodeHex(encryptStr.toCharArray()));
            LOG.d(this, new String(decryptData));

            encryptStr = DESedeHelper.encrypt(hexStrKey, src);
            LOG.d(this, encryptStr);
            LOG.d(this, DESedeHelper.decrypt(hexStrKey, encryptStr));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
