package com.szzc.common.utils.lbc.parse;

/**
 * Created by lx on 17-6-20.
 */
public class HitsBean {


    /**
     * _index : lbry-transactions
     * _type : transactions
     * _id : 95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd
     * _score : 1
     */
    private String index;
    private String type;
    private String id;
    private int score;
    private Source source;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
