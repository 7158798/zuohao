package com.otc.api.web.ctrl.virtual;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiVirtual;
import com.otc.api.web.ctrl.virtual.request.VirtualRecordReq;
import com.otc.api.web.ctrl.virtual.response.VirtualCoinResp;
import com.otc.api.web.ctrl.virtual.response.VirtualRecordResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by lx on 17-4-19.
 */
@RestController
public class VirtualCtrl extends BaseWebCtrl implements WebApiVirtual {

    @RequestMapping(value = lIST_RECORD_VIRTUAL_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getRecordList(@PathVariable String queryJson){
        LOG.dStart(this, "获取虚拟币记录开始");
        LOG.d(this,queryJson);
        VirtualRecordReq req = JsonHelper.jsonStr2Obj(queryJson, VirtualRecordReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        if (req.getCoinId() == null){
            throw new WebBizException("币种id不能为空");
        }
        if (StringUtils.isEmpty(req.getType())){
            throw new WebBizException("请求类型不能为空");
        }
        if (VirtualRecordType.getTypeByCode(req.getType()) == null){
            throw new WebBizException("请求类型不正确");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        VirtualRecordVo vo = new VirtualRecordVo();
        vo.setType(req.getType());
        vo.setUserId(userId);
        vo.setCoinId(req.getCoinId());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());
        vo = otc.virtualRecordWebFacade.queryByConditionPage(vo);
        List<VirtualRecord> list = vo.fatchTransferList();

        List<VirtualRecordResp> respList = new ArrayList<>();
        VirtualRecordResp resp;
        for (VirtualRecord record : list){
            resp = new VirtualRecordResp();
            resp.copy(record);
            respList.add(resp);
        }
        LOG.d(this, respList);
        LOG.dEnd(this,"获取虚拟币记录结束");
        return buildWebApiPageResponse(vo,respList);
    }

    @RequestMapping(value = LIST_COIN_VIRTUAL_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getCoinList(){
        LOG.dStart(this,"获取可用的币种");
        Map<Long,VirtualCoin> coinMap = otc.virtualCoinWebFacade.getVirtualCoin();
        List<VirtualCoinResp> respList = new ArrayList<>();
        VirtualCoinResp resp;
        if (coinMap != null){
            for (Map.Entry<Long,VirtualCoin> entry : coinMap.entrySet()){
                VirtualCoin temp = entry.getValue();
                resp = new VirtualCoinResp();
                resp.setShortName(temp.getShortName());
                resp.setName(temp.getName());
                resp.setCoinId(temp.getId());
                resp.setIconUrl(temp.getIconUrl());
                resp.setIconUrl2(temp.getIconUrl2());
                respList.add(resp);
            }
        }
        LOG.d(this,respList);
        LOG.dEnd(this,"获取可用的币种");
        return buildWebApiResponse(SUCCESS,respList);
    }
}
