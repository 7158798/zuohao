package com.szzc.common.utils.lbc.parse;

/**
 * Created by lx on 17-6-20.
 */
public class Transaction {


    /**
     * took : 1
     * timed_out : false
     */

    private int took;
    private Boolean timed_out;
    private Hits hits;

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public boolean isTimed_out() {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }
}
