package com.jucaifu.common.util.seq;

/**
 * UniqueSeq
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/15.
 */
public class UniqueSeq {

    private static int START_UNIQUE_SEQ = 1;

    private String tag;

    private int uniqueSeq = START_UNIQUE_SEQ;

    /**
     * Instantiates a new Unique seq.
     *
     * @param tag the tag
     */
    public UniqueSeq(String tag) {
        this.tag = tag;
    }

    /**
     * Gets new unique seq.
     *
     * @return the new unique seq
     */
    public int getNewUniqueSeq() {

        uniqueSeq++;
        uniqueSeq &= 0x7FFFFFFF;

        if (uniqueSeq == 0) {
            uniqueSeq = START_UNIQUE_SEQ;
        }

        return uniqueSeq;
    }


    /**
     * Gets now unique seq.
     *
     * @return the now unique seq
     */
    public int getNowUniqueSeq() {
        return uniqueSeq;
    }

    /**
     * Gets unique seq.
     *
     * @return the unique seq
     */
    public int getUniqueSeq() {
        return uniqueSeq;
    }

    /**
     * Gets tag.
     *
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    ////////////////////////////////////////////////////////////
    ////管理全局的UniqueSeq
    ////////////////////////////////////////////////////////////
    private static UniqueSeq sInstance = null;

    private static String TAG_GLOBAL_UNIQUE_SEQ = "tag_global_unique_seq";

    /**
     * Gets global unique seq.
     *
     * @return the global unique seq
     */
    public static UniqueSeq getGlobalUniqueSeq() {

        if (sInstance == null) {
            synchronized (UniqueSeq.class) {
                if (sInstance == null) {
                    sInstance = new UniqueSeq(TAG_GLOBAL_UNIQUE_SEQ);
                }
            }
        }

        return sInstance;
    }

}
