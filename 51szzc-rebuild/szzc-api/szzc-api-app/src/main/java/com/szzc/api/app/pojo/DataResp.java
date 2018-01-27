package com.szzc.api.app.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyy on 15-12-8.
 */
public class DataResp<T> {

    private List<T> data = new ArrayList<>();

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
