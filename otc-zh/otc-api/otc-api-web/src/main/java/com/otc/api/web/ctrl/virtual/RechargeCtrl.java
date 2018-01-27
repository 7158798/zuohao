package com.otc.api.web.ctrl.virtual;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiRecharge;
import com.otc.api.web.ctrl.user.request.UserAddressReq;
import com.otc.api.web.ctrl.user.response.UserAddressResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 广告
 * Created by lx on 17-4-19.
 */
@RestController
public class RechargeCtrl extends BaseWebCtrl implements WebApiRecharge{


    /**
     * 用户保存广告
     * @param queryJson
     * @return
     */
    @RequestMapping(value = ADDRESS_RECHARGE_WEB_API,method = RequestMethod.GET)
    public WebApiResponse rechargeAddress(@PathVariable String queryJson){
        LOG.dStart(this, "获取用户充值地址");
        UserAddressReq req = JsonHelper.jsonStr2Obj(queryJson, UserAddressReq.class);
        if (req == null) {
            throw new WebBizException("请求参数不能为空");
        }
        if (req.getCoinId() == null){
            throw new WebBizException("虚拟币类型ID不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        UserAddressCond cond = new UserAddressCond();
        cond.setUserId(userId);
        cond.setCoinId(req.getCoinId());
        UserAddress userAddress = otc.userAddressWebFacade.queryUserAddress(cond);
        if (userAddress == null){
            // 还没有分配地址,进行地址分配
            userAddress = otc.userAddressWebFacade.bindUserAddress(userId,req.getCoinId());
        }
        //提现说明
        Map<Long, VirtualCoin> allVirtualCoin = otc.virtualCoinWebFacade.getAllVirtualCoin();
        VirtualCoin virtualCoin = allVirtualCoin.get(req.getCoinId());
        UserAddressResp resp = new UserAddressResp();
        resp.setAddress(userAddress.getAddress());
        resp.setRechargeDes(virtualCoin != null ? virtualCoin.getRechargeDes() : "");
        LOG.d(this,resp);
        LOG.dEnd(this,"获取用户充值地址");
        return buildWebApiResponse(SUCCESS,resp);
    }

}
