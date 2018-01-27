package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.virtual.RechargeCtrl;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import com.otc.api.web.ctrl.user.request.UserAddressReq;
import com.otc.core.cache.UserCacheManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lx on 17-4-19.
 */
public class RechargeCtrlTest extends BaseSpringTest {

    @Autowired
    private RechargeCtrl rechargeCtrl;

    @Test
    public void testGetRecordList(){
       /* UserAddressReq req = new UserAddressReq();
        req.setCoinId(1l);
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        LOG.d(this,req);
        String str = JsonHelper.obj2JsonStr(req);
        WebApiResponse response = rechargeCtrl.rechargeAddress(str);
        LOG.d(this,response);*/
        Long userId = UserCacheManager.getUidWithToken("90243a70dc7c493089b49838636091ba001");
        System.out.println(userId);
    }

    @Override
    public void doTest() {

    }
}
