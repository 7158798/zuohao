package com.facade.core.wallet.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List类型，通用存储
 * Created by luwei on 17-5-16.
 */
public class GeneralListCache<T> implements Serializable {

    private List<T> list = new ArrayList<T>();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
