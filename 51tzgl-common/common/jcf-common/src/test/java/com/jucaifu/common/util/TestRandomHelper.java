package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.random.RandomHelper;
import com.jucaifu.common.util.random.RandomType;

/**
 * TestRandomHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/28.
 */
public class TestRandomHelper extends BaseTest {

    @Override
    public void doTest() {


        try {
            LOG.d(this, RandomType.ALL_CHAR.getRandomSource());
            LOG.d(this, RandomHelper.generateRandom(RandomType.ALL_CHAR, 6));
            LOG.d(this, RandomType.ALL_LETTER_CHAR.getRandomSource());
            LOG.d(this, RandomHelper.generateRandom(RandomType.ALL_LETTER_CHAR, 6));
            LOG.d(this, RandomType.ALL_NUMBER_CHAR.getRandomSource());
            LOG.d(this, RandomHelper.generateRandom(RandomType.ALL_NUMBER_CHAR, 6));
            String randomSource = "scofieldcai";
            LOG.d(this, RandomHelper.generateRandom(randomSource, 6));
            randomSource = "sc";
//            LOG.d(this,RandomHelper.generateRandom(randomSource,6));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
//            String randomSource = "sc";
//            LOG.d(this,RandomHelper.generateRandom(randomSource,6));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
