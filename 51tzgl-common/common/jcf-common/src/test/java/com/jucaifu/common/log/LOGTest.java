package com.jucaifu.common.log;

import com.base.BaseTest;
import com.jucaifu.common.configs.ApiVersionManager;
import com.jucaifu.common.constants.ApiType;
import com.jucaifu.common.util.model.UserPo;
import org.junit.Test;

/**
 * LOG Tester.
 *
 * @author scofieldcai-dev
 * @version 1.0
 * @since 十一月 14, 2015
 */
public class LOGTest extends BaseTest {

    @Override
    public void doTest() {
        UserPo user = new UserPo();
        user.setName("scofieldcai");
        user.setPwd("123");

        LOG.dStart(this, "user");
        LOG.d(this, user);
        LOG.i(this, user);
        LOG.w(this, user);
        LOG.e(this, user, new Exception("测试异常"));
        LOG.dEnd(this, "user");

    }


    @Test
    public void testVersion() {

        boolean validateResult = false;

//        String requestUri = "/jcf-api-web/web054/product/view/%7B%22productId%22:%22fa61518b-8f61-4eff-b443-065980ae104e%22%7D";
        String requestUri = "/jcf-api-web/web/product/view/%7B%22productId%22:%22fa61518b-8f61-4eff-b443-065980ae104e%22%7D";

        String version = null;
        try {
            String[] paths = requestUri.split("\\/");

            for (String p : paths) {
                LOG.d(this, p);
            }
            String currentVersion = paths[2];
            LOG.d(this, currentVersion);
            version = currentVersion.substring(ApiType.WEB.length());
            if (version==null){
                LOG.d(this,"version==null");
            }else{
                LOG.d(this,"version==:"+version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        validateResult = ApiVersionManager.validateApiVersion(version);


        LOG.d(this, "版本验证结果:" + validateResult);
    }
}
