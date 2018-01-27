package com.szzc.job.bean;

import java.util.List;

/**
 * Created by lx on 17-6-29.
 */
public class Source {
    // 币种名称
    private String shortName;
    //
    private List<String> other;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getOther() {
        return other;
    }

    public void setOther(List<String> other) {
        this.other = other;
    }
}
