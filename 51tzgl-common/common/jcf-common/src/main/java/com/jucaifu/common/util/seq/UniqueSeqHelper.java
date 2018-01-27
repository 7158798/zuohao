package com.jucaifu.common.util.seq;

import java.util.HashMap;

/**
 * UniqueSeqHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/15.
 */
public final class UniqueSeqHelper {


    private static final HashMap<String, UniqueSeq> sUniqueSeqMap = new HashMap<String, UniqueSeq>();

    private static final Object lock = new Object();

    /**
     * Gets global unique seq.
     *
     * @return the global unique seq
     */
    public static final UniqueSeq getGlobalUniqueSeq() {
        synchronized (lock) {
            return UniqueSeq.getGlobalUniqueSeq();
        }
    }

    /**
     * Gets unique seq. 如果不存在缓存自动创建
     *
     * @param tag the tag
     * @return the unique seq
     */
    public static final UniqueSeq getUniqueSeq(String tag) {
        synchronized (lock) {
            UniqueSeq uniqueSeq = sUniqueSeqMap.get(tag);
            if (uniqueSeq == null) {
                if (tag != null) {
                    uniqueSeq = new UniqueSeq(tag);
                    sUniqueSeqMap.put(tag, uniqueSeq);
                }
            }
            return uniqueSeq;
        }
    }

    /**
     * Cache unique seq.
     *
     * @param uniqueSeq the unique seq
     */
    public static final void cacheUniqueSeq(UniqueSeq uniqueSeq) {
        synchronized (lock) {
            if (uniqueSeq != null && uniqueSeq.getTag() != null) {
                sUniqueSeqMap.put(uniqueSeq.getTag(), uniqueSeq);
            }
        }
    }

}
