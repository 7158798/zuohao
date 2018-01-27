package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.virtual.VirtualCtrl;
import com.otc.api.web.ctrl.virtual.request.VirtualRecordReq;
import com.otc.api.web.ctrl.wallet.WalletCtrl;
import com.otc.api.web.ctrl.wallet.request.VirtualWalletReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualCtrlTest extends BaseSpringTest {

    @Autowired
    private VirtualCtrl virtualCtrl;
    @Autowired
    private WalletCtrl walletCtrl;

    @Override
    public void doTest() {

    }

    @Test
    public void testGetRecordList(){
        VirtualRecordReq req = new VirtualRecordReq();
        req.setPageFilter(1, 10);
        req.setCoinId(1l);
        req.setType("00");
        String token = CacheHelper.buildTestToken("68");
        req.setToken(token);
        LOG.d(this,req);
        String str = JsonHelper.obj2JsonStr(req);
        WebApiResponse webApiResponse = virtualCtrl.getRecordList(str);
        LOG.d(this,webApiResponse);
    }

    @Test
    public void testGetCoinList(){
        WebApiResponse webApiResponse = virtualCtrl.getCoinList();
        LOG.d(this,webApiResponse);
    }

    @Test
    public void testGetWalletList(){
        WebApiBaseReq req = new WebApiBaseReq();
        String str = JsonHelper.obj2JsonStr(req);
        LOG.d(this,req);
        WebApiResponse webApiResponse =walletCtrl.getWalletList(str);
        LOG.d(this,webApiResponse);
    }

    @Test
    public void testGetWalletInfo(){
        VirtualWalletReq req = new VirtualWalletReq();
        req.setCoinId(1l);
        String str = JsonHelper.obj2JsonStr(req);
        WebApiResponse webApiResponse =walletCtrl.getWalletInfo(str);
        LOG.d(this,webApiResponse);
    }
}
