package com.facade.core.wallet.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yangyy on 16-1-29.
 */
public class WebCacheObj<T> implements Serializable {

    private ArrayList<T> list = new ArrayList<>();

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
