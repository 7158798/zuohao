package com.jucaifu.common.network;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * TestHttpClientHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/20.
 */
public class TestHttpClientHelper extends BaseTest {

    @Override
    public void doTest() {

        String url = "http://www.baidu.com/";

        String result = HttpClientHelper.sendGetRequest(url,"sss",false);
        LOG.d(this, result);

        RequestObject reqObj = new RequestObject();
        reqObj.setHostUrl(url);
        reqObj.setMethodUrl("");
        reqObj.setValues(null);
        String postResult = HttpClientHelper.sendPostFormRequest(reqObj,false);
        LOG.d(this, postResult);
    }

}
