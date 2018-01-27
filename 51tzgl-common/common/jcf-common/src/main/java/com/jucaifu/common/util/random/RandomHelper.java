package com.jucaifu.common.util.random;

import java.util.Random;

import com.jucaifu.common.constants.CommonConstant;

/**
 * RandomHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/28.
 */
public final class RandomHelper implements CommonConstant {


    /**
     * Generate random.
     *
     * @param randomSource       the random source
     * @param outputRandomLength the output random length
     * @return the string
     * @throws Exception the exception
     */
    public static final String generateRandom(String randomSource, int outputRandomLength) throws Exception {

        if (randomSource == null || randomSource.length() < outputRandomLength) {
            throw new Exception("RandomHelper.generateRandom 中产生随机数的randomSource长度小于输出的随机数长度");
        }

        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < outputRandomLength; i++) {
            sb.append(randomSource.charAt(random.nextInt(randomSource.length())));
        }

        return sb.toString();
    }


    /**
     * Generate random.
     *
     * @param randomType         the random type
     * @param outputRandomLength the output random length
     * @return the string
     * @throws Exception the exception
     */
    public static final String generateRandom(RandomType randomType, int outputRandomLength) throws Exception {
        if (randomType == null) {
            return null;
        }

        return generateRandom(randomType.getRandomSource(), outputRandomLength);
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串 string
     */
    public static final String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

}
