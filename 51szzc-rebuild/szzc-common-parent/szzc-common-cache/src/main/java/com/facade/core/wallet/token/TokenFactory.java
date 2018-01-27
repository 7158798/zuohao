package com.facade.core.wallet.token;

import com.jucaifu.common.util.UUIDHelper;

/**
 * TokenFactory
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/6.
 */
public class TokenFactory {

    private static final UserTokenGenerator sUserTokenGenerator = new UserTokenGenerator();

    private TokenFactory() {
    }

    public static ITokenGenerator getUserTokenGenerator() {
        return sUserTokenGenerator;
    }

    public static String generatorUserToken() {
        return sUserTokenGenerator.generatorUserToken(UUIDHelper.get32UUID());
    }


}
