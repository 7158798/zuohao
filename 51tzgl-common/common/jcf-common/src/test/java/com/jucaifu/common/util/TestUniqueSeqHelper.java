package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.seq.UniqueSeq;
import com.jucaifu.common.util.seq.UniqueSeqHelper;

/**
 * TestUniqueSeqHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/15.
 */
public class TestUniqueSeqHelper extends BaseTest {

    @Override
    public void doTest() {
        UniqueSeq uniqueSeq = UniqueSeqHelper.getGlobalUniqueSeq();
        LOG.d(this, uniqueSeq.getNowUniqueSeq());
        for (int i = 1; i < 20; i++) {
            LOG.d(this, "Tag-GlobalUniqueSeq:" + uniqueSeq.getNewUniqueSeq());
        }

        String bjsys_tag = "bjsys_tag";
        UniqueSeq uniqueSeq2 = UniqueSeqHelper.getUniqueSeq(bjsys_tag);
        LOG.d(this, uniqueSeq2.getNowUniqueSeq());
        for (int i = 1; i < 20; i++) {
            LOG.d(this, "Tag-bjsys:" + uniqueSeq2.getNewUniqueSeq());
        }

    }
}
