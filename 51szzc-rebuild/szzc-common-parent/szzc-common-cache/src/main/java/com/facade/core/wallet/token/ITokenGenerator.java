package com.facade.core.wallet.token;

/**
 * ITokenGenerator
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/6.
 */
public interface ITokenGenerator {

    /**
     * Key string.
     *
     * @return the string
     */
    default String key() {
        return "zuohao-jucaifu";
    }

    /**
     * Decrypt string.
     *
     * @param str the str
     * @return the string
     */
    String decrypt(String str);

    /**
     * Encrypt string.
     *
     * @param str the str
     * @return the string
     */
    String encrypt(String str);

    /**
     * Generator token.
     *
     * @param pramaters the pramaters
     * @return the string
     */
    default String generatorToken(String... pramaters) {

        String token = null;

        if (pramaters != null) {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < pramaters.length; i++) {
                sb.append(pramaters[i] + "-");
            }

            sb.append(System.currentTimeMillis());

            token = encrypt(sb.toString());
        }

        return token;

    }

    /**
     * Generator user token.
     *
     * @param str the str
     * @return the string
     */
    default String generatorUserToken(String str) {

        return encrypt(str);
    }

}
