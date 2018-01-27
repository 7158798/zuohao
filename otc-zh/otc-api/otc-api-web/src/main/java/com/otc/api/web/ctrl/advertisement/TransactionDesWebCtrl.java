package com.otc.api.web.ctrl.advertisement;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiAdvertisement;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.advertisement.pojo.po.TransactionDes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zygong on 17-6-26.
 */
@RestController
public class TransactionDesWebCtrl extends BaseWebCtrl implements WebApiAdvertisement {

    /**
     * 交易设置
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = DETAIL_TRANSACTIONDESC_WEB_API,method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson){
        LOG.dStart(this, "获取首页广告列表");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null || req.getId() == null){
            throw new WebBizException("请求参数不能为空");
        }


        TransactionDes transactionDes = otc.transactionDesWebFacade.getByTypeAndId(null, req.getId().intValue());
        LOG.dEnd(this, "获取首页广告列表");

        return buildWebApiResponse(SUCCESS, transactionDes);
    }
}
