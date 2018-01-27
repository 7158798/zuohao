package com.jucaifu.common.util;

import java.util.ArrayList;
import java.util.List;

import com.base.BaseTest;
import com.jucaifu.common.enums.EnumDeviceType;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.model.UserPo;
import org.junit.Test;

/**
 * Created by scofieldcai-dev on 15/8/25.
 */
public class TestJsonHeloper extends BaseTest {

    @Override
    public void doTest() {

        ArrayList<UserPo> userList = new ArrayList<UserPo>();

        UserPo user = new UserPo();
        user.setName("scofieldcai");
        user.setPwd("123");
        String jsonStr = JsonHelper.obj2JsonStr(user);
        System.out.println("jsonStr=" + jsonStr);

        UserPo po = JsonHelper.jsonStr2Obj(jsonStr, UserPo.class);
        System.out.println("name=" + po.getName());

        LOG.d(po, po);


        userList.add(user);
        userList.add(new UserPo());
        String jsonArrayStr = JsonHelper.obj2JsonStr(userList);
        LOG.d(this, jsonArrayStr);

        List<UserPo> list = JsonHelper.jsonArrayStr2List(jsonArrayStr, UserPo.class);

        LOG.d(this, list);
    }


    @Test
    public void testArray() {
        List<String> list = new ArrayList<>();

        list.add("11@126.com");
        list.add("22@126.com");

        LOG.d(this, list);
        String jsonStr = JsonHelper.obj2JsonStr(list);

        list = JsonHelper.jsonArrayStr2List(jsonStr, String.class);
        LOG.d(this,list);

    }

    @Test
    public void testDeviceType(){
        LOG.d(this,EnumDeviceType.getByCode("1"));
        LOG.d(this,EnumDeviceType.getByCode("2"));
        LOG.d(this,EnumDeviceType.getByCode("-1"));
    }
}
