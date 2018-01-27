package com.szzc.common.utils.lbc.parse;

import java.util.List;

/**
 * Created by lx on 17-6-20.
 */
public class Hits {


    /**
     * total : 1
     * max_score : 1
     */

    private int total;
    private int max_score;
    private List<HitsBean> hits;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMax_score() {
        return max_score;
    }

    public void setMax_score(int max_score) {
        this.max_score = max_score;
    }

    public List<HitsBean> getHits() {
        return hits;
    }

    public void setHits(List<HitsBean> hits) {
        this.hits = hits;
    }
}
