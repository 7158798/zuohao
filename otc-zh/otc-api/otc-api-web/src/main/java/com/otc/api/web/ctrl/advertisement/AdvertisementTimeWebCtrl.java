package com.otc.api.web.ctrl.advertisement;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiAdvertisement;
import com.otc.api.web.ctrl.advertisement.request.AdvertisementTimeSaveReq;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.advertisement.pojo.po.AdvertisementTime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 广告时间设置
 * Created by zygong on 17-4-26.
 */
@RestController
public class AdvertisementTimeWebCtrl extends BaseWebCtrl implements WebApiAdvertisement {

    /**
     * 获取广告时间设置详情
     * @param queryJson
     * @return
     */
    @RequestMapping(value = DETAIL_ADVERTISEMENTTIME_WEB_API,method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson){
        LOG.dStart(this, "获取广告时间设置详情");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }

        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        if(userId == null){
            throw new WebBizException("请先登录");
        }
        Long id = req.getId();
        List<AdvertisementTime> detailList = otc.advertisementTimeWebFacade.getByUserId(userId);

        LOG.dEnd(this, "获取广告时间设置详情");
        return buildWebApiResponse(SUCCESS, detailList);
    }

    /**
     * 保存用户广告时间设置
     * @param req
     * @return
     */
    @RequestMapping(value = SAVEORUPDATE_ADVERTISEMENTTIME_WEB_API,method = RequestMethod.POST)
    public WebApiResponse saveOrUpdate(@RequestBody AdvertisementTimeSaveReq req){
        LOG.dStart(this, "保存用户广告时间设置");
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        if(userId == null){
            throw new WebBizException("请先登录");
        }

        AdvertisementTime advertisementTime = new AdvertisementTime();
            advertisementTime.setStatus(req.getStatus());
            advertisementTime.setType(req.getType());
            advertisementTime.setEndTime1(req.getEndTime1());
            advertisementTime.setEndTime2(req.getEndTime2());
            advertisementTime.setStartTime1(req.getStartTime1());
            advertisementTime.setStartTime2(req.getStartTime2());
            advertisementTime.setId(req.getId());
            advertisementTime.setUserId(userId);

        boolean saveResult = otc.advertisementTimeWebFacade.saveOrUpdate(advertisementTime);
        LOG.dEnd(this, "保存用户广告时间设置");
        if(saveResult){
            return buildWebApiResponse(SUCCESS, "保存成功");
        }else{
            return buildWebApiResponse(FAILURE, "保存失败");
        }
    }

    /**
     * 删除用户广告时间设置
     * @param queryJson
     * @return
     */
    @RequestMapping(value = DELETE_ADVERTISEMENTTIME_WEB_API,method = RequestMethod.GET)
    public WebApiResponse delete(@PathVariable String queryJson){
        LOG.dStart(this, "删除用户广告时间设置");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null || req.getId() == null || req.getId() == 0){
            throw new WebBizException("请求参数不能为空");
        }

        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        if(userId == null){
            throw new WebBizException("请先登录");
        }
        boolean result = otc.advertisementTimeWebFacade.deleteById(req.getId());
        LOG.dEnd(this, "删除用户广告时间设置");
        if(result){
            return buildWebApiResponse(SUCCESS, "操作成功");
        }else{
            return buildWebApiResponse(FAILURE, "操作失败");
        }
    }

}
