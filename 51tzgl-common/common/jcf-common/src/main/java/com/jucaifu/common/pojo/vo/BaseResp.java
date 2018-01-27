package com.jucaifu.common.pojo.vo;

import java.io.Serializable;

/**
 * BaseResp
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/30.
 */
public class BaseResp  implements Serializable,ISeqNo {

    /**
     * The Seq no.
     */
    protected int seqNo;

    /**
     * Gets seq no.
     *
     * @return the seq no
     */
    @Override
    public int getSeqNo() {
        return seqNo;
    }

    /**
     * Sets seq no.
     *
     * @param seqNo the seq no
     */
    @Override
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

}
