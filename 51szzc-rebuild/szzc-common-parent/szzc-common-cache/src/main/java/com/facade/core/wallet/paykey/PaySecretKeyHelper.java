package com.facade.core.wallet.paykey;

import com.jucaifu.common.configs.TimeOutConfigConstant;
import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.security.MD5Helper;
import com.jucaifu.common.util.UUIDHelper;
import com.facade.core.wallet.cache.CacheHelper;

/**
 * PaySecretKeyHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/8.
 */
public class PaySecretKeyHelper {

    private static final String SUCCESS = "success";

    /**
     * 5秒内多次提交:算重复提交
     */
    private static final Integer RESUBMIT_TIMES = TimeConstant.ONE_SECOND_UNIT_SECONDS * 5;

    private static String generatorPaySecret(String uid) {
        String key = MD5Helper.encodeMD5Hex(uid + ":" + UUIDHelper.get32UUID());
        return key;
    }

    /**
     * Save pay secret key.
     *
     * @param uid the uid
     * @return the boolean
     */
    public static String savePaySecretKey(String uid) {
        String key = generatorPaySecret(uid);
        boolean result = CacheHelper.saveObj(key, key, TimeOutConfigConstant.STEP_DEPEND_TOKEN_TIMEOUT);
        LOG.d("", "savePaySecretKey-result:" + result);
        return key;
    }

    /**
     * Validate pay secret key.
     *
     * @param key the key
     * @return the boolean
     */
    public static PaySecretKeyValidateResult validatePaySecretKey(String key) {

        PaySecretKeyValidateResult validateResult = PaySecretKeyValidateResult.INVALID;

        if (key != null) {

            String value = CacheHelper.getObj(key);

            if (value == null) {

                validateResult = PaySecretKeyValidateResult.INVALID;

            } else {
                if (value.equals(key)) {
                    //成功-更新状态
                    validateResult = PaySecretKeyValidateResult.SUCCESS;
                    CacheHelper.saveObj(key, SUCCESS, RESUBMIT_TIMES);
                } else {
                    //重复提交
                    validateResult = PaySecretKeyValidateResult.RESUBMIT;
                }
            }
        }

        return validateResult;
    }

}
