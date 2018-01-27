package com.otc.api.web.ctrl.wallet;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiWallet;
import com.otc.api.web.ctrl.wallet.request.VirtualWalletReq;
import com.otc.api.web.ctrl.wallet.response.VirtualWalletResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-19.
 */
@RestController
public class WalletCtrl extends BaseWebCtrl implements WebApiWallet {


    @RequestMapping(value = LIST_WALLET_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getWalletList(@PathVariable String queryJson){
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        VirtualWalletCond cond = new VirtualWalletCond();
        cond.setUserId(userId);
        List<VirtualWallet> list = otc.virtualWalletWebFacade.queryListVirtualWallet(cond);
        List<VirtualWalletResp> respList = new ArrayList<>();
        VirtualWalletResp resp;
        Map<Long,VirtualCoin> coinMap = otc.virtualCoinWebFacade.getVirtualCoin();
        if (coinMap != null && coinMap.size() > 0){
            for (VirtualWallet wallet : list){
                resp = new VirtualWalletResp();
                resp.setFrozen(wallet.getFrozen().toString());
                resp.setTotal(wallet.getTotal().toString());
                BigDecimal all = wallet.getFrozen().add(wallet.getTotal());
                resp.setAll(all.toString());
                VirtualCoin coin = coinMap.get(wallet.getCoinId());
                if (coin == null)
                    continue;
                resp.setName(coin.getName());
                resp.setShortName(coin.getShortName());
                respList.add(resp);
            }
        }
        LOG.d(this,respList);
        return buildWebApiResponse(SUCCESS,respList);
    }

    @RequestMapping(value = INFO_WALLET_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getWalletInfo(@PathVariable String queryJson){

        VirtualWalletReq req = JsonHelper.jsonStr2Obj(queryJson, VirtualWalletReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        if (req.getCoinId() == null){
            throw new WebBizException("虚拟币ID不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        VirtualWalletCond cond = new VirtualWalletCond();
        cond.setUserId(userId);
        cond.setCoinId(req.getCoinId());
        VirtualWallet wallet = otc.virtualWalletWebFacade.queryVirtualWallet(cond);
        VirtualWalletResp resp = new VirtualWalletResp();
        if (wallet != null){
            resp.setFrozen(wallet.getFrozen().toString());
            resp.setTotal(wallet.getTotal().toString());
            BigDecimal all = wallet.getFrozen().add(wallet.getTotal());
            resp.setAll(all.toString());
        }
        LOG.d(this,resp);
        return buildWebApiResponse(SUCCESS,resp);
    }
}
