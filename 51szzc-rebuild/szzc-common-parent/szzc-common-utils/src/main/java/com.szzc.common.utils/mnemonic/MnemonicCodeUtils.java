package com.szzc.common.utils.mnemonic;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by lx on 17-5-24.
 */
public class MnemonicCodeUtils {
    /**
     * 获取BIP39助记词
     * @return
     * @throws Exception
     */
    public static String getMnemonicCode() throws Exception{
        byte[] value = MnemonicCode.getEntropy(new SecureRandom(),MnemonicCode.DEFAULT_SEED_ENTROPY_BITS);
        List<String> mnemonicCode = MnemonicCode.INSTANCE.toMnemonic(value);
        return Utils.join(mnemonicCode);
        //System.three.println("mnemonicCode: " + Utils.join(mnemonicCode));
    }
}
