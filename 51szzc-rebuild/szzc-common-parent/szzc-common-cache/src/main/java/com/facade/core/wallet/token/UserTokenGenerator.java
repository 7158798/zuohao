package com.facade.core.wallet.token;

import com.jucaifu.common.security.MD5Helper;

/**
 * UserTokenGenerator
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/6.
 */
class UserTokenGenerator implements ITokenGenerator {

    @Override
    public String decrypt(String str) {
        String content = str;
        return content;
    }

    @Override
    public String encrypt(String str) {
        String content;
        try {
            content = MD5Helper.encodeMD5Hex(str);
        } catch (Exception e) {
            throw new TokenGeneratorException("生成用户token异常", e);
        }
        return content;
    }
}
