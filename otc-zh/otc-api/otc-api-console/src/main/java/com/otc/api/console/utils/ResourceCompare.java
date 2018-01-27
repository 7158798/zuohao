package com.otc.api.console.utils;


import com.otc.facade.sys.pojo.po.Resource;

import java.util.Comparator;

/**
 * Created by zhaiyz on 15-11-3.
 */
public class ResourceCompare implements Comparator<Resource> {

    @Override
    public int compare(Resource o1, Resource o2) {
        if (o1.getOrderNo() < o2.getOrderNo()) {
            return -1;
        } else if (o1.getOrderNo() == o2.getOrderNo()) {
            return 0;
        } else {
            return 1;
        }
    }
}
