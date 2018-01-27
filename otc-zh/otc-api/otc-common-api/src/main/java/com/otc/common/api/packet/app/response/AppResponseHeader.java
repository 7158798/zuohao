package com.otc.common.api.packet.app.response;

/**
 * The type App response header.
 */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)

/**
 * AppResponseHeader
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public class AppResponseHeader {

    private int seqNo;
    private long reqTimeStamp;
    private long respTimeStamp;

    /**
     * Sets seq no.
     *
     * @param seqNo the seq no
     */
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * Sets req time stamp.
     *
     * @param reqTimeStamp the req time stamp
     */
    public void setReqTimeStamp(long reqTimeStamp) {
        this.reqTimeStamp = reqTimeStamp;
    }

    /**
     * Sets resp time stamp.
     *
     * @param respTimeStamp the resp time stamp
     */
    public void setRespTimeStamp(long respTimeStamp) {
        this.respTimeStamp = respTimeStamp;
    }


    /**
     * Gets seq no.
     *
     * @return the seq no
     */
    public int getSeqNo() {
        return seqNo;
    }

    /**
     * Gets req time stamp.
     *
     * @return the req time stamp
     */
    public long getReqTimeStamp() {
        return reqTimeStamp;
    }

    /**
     * Gets resp time stamp.
     *
     * @return the resp time stamp
     */
    public long getRespTimeStamp() {
        return respTimeStamp;
    }

}
