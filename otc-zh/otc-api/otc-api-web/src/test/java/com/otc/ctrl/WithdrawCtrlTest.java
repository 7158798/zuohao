package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.virtual.WithdrawCtrl;
import com.otc.api.web.ctrl.virtual.request.FeesReq;
import com.otc.api.web.ctrl.virtual.request.WithdrawAddReq;
import com.otc.api.web.ctrl.wallet.request.VirtualWalletReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by lx on 17-4-25.
 */
public class WithdrawCtrlTest extends BaseSpringTest {

    @Autowired
    private WithdrawCtrl withdrawCtrl;

    @Test
    public void testGetWalletInfo(){
        VirtualWalletReq req = new VirtualWalletReq();
        req.setCoinId(1l);
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        LOG.d(this,req);
        String str = JsonHelper.obj2JsonStr(req);
        WebApiResponse webApiResponse = withdrawCtrl.init(str);
        LOG.d(this, webApiResponse);
    }

    @Test
    public void testAddWithdraw(){
        WithdrawAddReq req = new WithdrawAddReq();
        req.setAmount("0.0035");
        req.setFees("0.0");
        req.setAddress("提现地址2号");
        req.setCoinId(1l);
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        WebApiResponse response = withdrawCtrl.addWithdraw(req);
        LOG.d(this,response);
    }

    @Test
    public void testGetFees(){
        FeesReq req = new FeesReq();
        req.setAmount("0.001");
        req.setId(1l);
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        WebApiResponse response = withdrawCtrl.getFees(req);
        LOG.d(this,response);
    }

    @Override
    public void doTest() {

    }
}
