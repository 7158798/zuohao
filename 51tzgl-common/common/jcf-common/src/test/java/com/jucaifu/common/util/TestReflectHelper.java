package com.jucaifu.common.util;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.model.DogPo;
import com.jucaifu.common.util.model.UserPo;
import org.junit.Test;

/**
 * Created by scofieldcai-dev on 15/8/27.
 */
public class TestReflectHelper {

    @Test
    public void testReflect() {


        try {
            //方便查看所有接口的Api地址
//            ReflectHelper.printAllStaticFieldValue(this, ApiBasePathConstant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserPo user = new UserPo();
        user.setName("jack");
        user.setPwd("123");
        user.setUuid(UUIDHelper.getUUID());
        user.setId(1L);

        LOG.d(this, "user");
        LOG.d(this, user);

        DogPo dog = new DogPo();
        try {
            ReflectHelper.injectAttrFromSrcObj(user,dog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(this, "dog");
        LOG.d(this, dog);

//        Field filed = ReflectHelper.getFieldByFieldName(user,"name");
//        LOG.d(this,filed);
//
        String value;
//        try {
//            value = (String)filed.get(user);
//            LOG.d(this,value);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();value
//        }


        try {
            value = ReflectHelper.getValueByFieldName(user, "name");
            LOG.d(this, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            ReflectHelper.setValueByFieldName(user, "name", "sc");
            LOG.d(this, user);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
