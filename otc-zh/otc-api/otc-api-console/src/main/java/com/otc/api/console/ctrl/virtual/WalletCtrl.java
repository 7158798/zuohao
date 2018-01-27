package com.otc.api.console.ctrl.virtual;

import com.jucaifu.common.log.LOG;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiWallet;
import com.otc.api.console.ctrl.virtual.request.RecordReq;
import com.otc.api.console.ctrl.virtual.request.WalletReq;
import com.otc.api.console.ctrl.virtual.response.RecordListResp;
import com.otc.api.console.ctrl.virtual.response.WalletListResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.poex.VirtualRecordEx;
import com.otc.facade.virtual.pojo.poex.VirtualWalletEx;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import com.otc.facade.virtual.pojo.vo.VirtualWalletVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-5-2.
 */
@RestController
public class WalletCtrl extends BaseConsoleCtrl implements ConsoleApiWallet {

    /**
     * Get wallet list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_WALLET_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getWalletList(@PathVariable String queryJson){
        LOG.dStart(this, "用户钱包列表");
        WalletReq req = encodeJsonStr2Obj(queryJson,WalletReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        VirtualWalletVo vo = new VirtualWalletVo();
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setCoinId(req.getCoinId());
        vo.setCondition(req.getCondition());

        vo = otc.virtualWalletConsoleFacade.queryConsoleByConditionPage(vo);
        List<VirtualWalletEx> list = vo.fatchTransferList();
        List<WalletListResp> respList = new ArrayList<>();
        WalletListResp resp;
        for (VirtualWalletEx walletEx : list){
            resp = new WalletListResp();
            resp.copy(walletEx);
            respList.add(resp);
        }
        LOG.d(this,respList);

        LOG.dEnd(this, "用户钱包列表");
        return buildWebApiPageResponse(vo,respList);
    }

}
