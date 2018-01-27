package com.jucaifu.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.BaseTest;
import com.jucaifu.common.enums.EnumSort;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.sort.SortHelper;
import com.jucaifu.common.util.sort.SortItem;

/**
 * TestSortHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/10.
 */
public class TestSortHelper extends BaseTest {


    @Override
    public void doTest() {

        HashMap map = new HashMap();
        map.put("name", "1");
        map.put("age", "10");

        HashMap map1 = new HashMap();
        map1.put("name", "1");
        map1.put("age", "13");

        Map map2 = new HashMap();
        map2.put("name", "2");
        map2.put("age", "12");

        Map map3 = new HashMap();
        map3.put("name", "3");
        map3.put("age", "11");

        Map map4 = new HashMap();
        map4.put("name", "3");
        map4.put("age", "15");


        List<Map> list = new ArrayList<Map>();
        list.add(map3);
        list.add(map1);
        list.add(map2);
        list.add(map4);
        list.add(map);


        List<Map> resultList = SortHelper.sort(list, "age", EnumSort.ASC);
        LOG.d(this, resultList);

        SortItem sortItem1 = new SortItem("name", EnumSort.ASC);
        SortItem sortItem2 = new SortItem("age", EnumSort.DESC);
        resultList = SortHelper.sort(list, sortItem1, sortItem2);

        LOG.d(this, resultList);

    }

}
