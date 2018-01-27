package com.jucaifu.common.util;

import java.io.UnsupportedEncodingException;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.model.UserPo;

/**
 * TestUrlHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/11.
 */
public class TestUrlHelper extends BaseTest {

    @Override
    public void doTest() {

//        String jsonStr = "{\"appCode\": \"jucaifu\", \"deviceType\": \"android\", \"version\": \"0.1.1\",\"size\": \"2\", \"page\":\" 1\"}";
        String jsonStr = "{\"uuid\":\"uuid-0\",\"name\":\"name-0\"}";
        LOG.d(this, jsonStr);
        try {
            LOG.d(this, UrlHelper.encode(jsonStr));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        UserPo userPo = new UserPo();
        userPo.setName("scofieldcai");
        String encodeQueryJson = null;
        try {
            encodeQueryJson = UrlHelper.encodeObj(userPo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOG.d(this, encodeQueryJson);
    }


}
