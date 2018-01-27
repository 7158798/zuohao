package com.otc.api.web.ctrl.advertisement;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiAdvertisement;
import com.otc.api.web.ctrl.advertisement.request.*;
import com.otc.api.web.ctrl.advertisement.response.CalculationFormulaResp;
import com.otc.api.web.ctrl.advertisement.response.DetailResp;
import com.otc.api.web.ctrl.advertisement.response.PayTypeResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.advertisement.pojo.dto.AdvertisementDto;
import com.otc.facade.advertisement.pojo.dto.CalculatePricedto;
import com.otc.facade.advertisement.pojo.enums.AdvertisementStatusEnum;
import com.otc.facade.advertisement.pojo.enums.PayTypeEnum;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.AdvertisementWebVo;
import com.otc.facade.advertisement.pojo.vo.CalculatePriceVo;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.pojo.poex.UserWebLoginInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTMLDocument;
import java.math.BigDecimal;
import java.util.*;

/**
 * 广告
 * Created by zygong on 17-4-26.
 */
@RestController
public class AdvertisementWebCtrl extends BaseWebCtrl implements WebApiAdvertisement {

    /**
     * 获取首页广告列表
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = GETINDEXLIST_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getIndexList(@PathVariable String queryJson){
        LOG.dStart(this, "获取首页广告列表");
        AdvertisementIndexReq req = JsonHelper.jsonStr2Obj(queryJson, AdvertisementIndexReq.class);
        if(req == null || req.getCoinType() == null || req.getTransactionType() == null){
            throw new WebBizException("请求参数不能为空");
        }

        AdvertisementWebVo vo = new AdvertisementWebVo();
        vo.setCoinType(req.getCoinType());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setPayType(req.getPayType());
        vo.setOrderField(req.getOrderField());
        vo.setOrderDirection(req.getOrderDirection());
        vo.setTransactionType(req.getTransactionType());
        vo = otc.advertisementWebFacade.indexQueryCountByConditionPage(vo);
        List<AdvertisementDto> respList = vo.fatchTransferList();
        LOG.dEnd(this, "获取首页广告列表");

        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 获取用户广告
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = GETUSERLIST_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getUserList(@PathVariable String queryJson){
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }

        LOG.dStart(this, "获取用户广告");
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        if(userId == null){
            throw new WebBizException("请先登录");
        }
        AdvertisementVo vo = new AdvertisementVo();
        vo.setFilter(String.valueOf(userId));
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());
        vo = otc.advertisementWebFacade.queryCountByConditionPage(vo);
        List<Advertisement> respList = vo.fatchTransferList();

        LOG.dEnd(this, "获取用户广告");

        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 获取用户其他交易广告
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = GETUSEROTHERLIST_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getUserOtherList(@PathVariable String queryJson){

        LOG.dStart(this, "获取用户其他交易广告");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }

        LOG.dStart(this, "获取用户广告");

        List<Advertisement> userOtherList = otc.advertisementWebFacade.getUserOtherList(req.getId(), null, 4);

        LOG.dEnd(this, "获取用户其他交易广告");

        return buildWebApiResponse(SUCCESS, userOtherList);
    }

    /**
     * 获取广告详情
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = DETAIL_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson){
        LOG.dStart(this, "获取广告详情");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }

        Long id = req.getId();
        DetailResp resp = new DetailResp();
        Advertisement detail = otc.advertisementWebFacade.getDetailAndMostDealCount(id, req.getToken());
        //从缓存读取用户信息
        CacheUserInfo cacheUserInfo = otc.userWebFacade.getCacheUserInfo(detail.getUserId());
        UserWebLoginInfo userWebLoginInfo = otc.userWebFacade.getUserWebLoginInfo(cacheUserInfo);
        resp.setAdvertisement(detail);
        resp.setUserInfo(userWebLoginInfo);

        LOG.dEnd(this, "获取广告详情");
        return buildWebApiResponse(SUCCESS, resp);
    }

    /**
     * 更新状态
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = UPDATESTATUS_ADVERTISEMENT_WEB_API,method = RequestMethod.POST)
    public WebApiResponse updateStatus(@RequestBody AdvertisementCloseReq req){
        LOG.dStart(this, "更新状态");
        // 默认进行关闭
        Integer status = 2;
        Long id = req.getId();
        if(req == null || id == null){
            throw new WebBizException("请求参数不能为空");
        }

        if(req.getStatus() != null){
            status = req.getStatus();
        }

        boolean result = otc.advertisementWebFacade.updateStatus(status, id);

        LOG.dEnd(this, "更新状态");
        if(result){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 保存用户广告
     * @param req
     * @return
     */
    @RequestMapping(value = SAVE_ADVERTISEMENT_WEB_API,method = RequestMethod.POST)
    public WebApiResponse saveAdvertisement(@RequestBody AdvertisementPostReq req){
        LOG.dStart(this, "保存用户广告");
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        if(userId == null){
            throw new WebBizException("请先登录");
        }

        String city = req.getCity();
        Long coinType = req.getCoinType();
        BigDecimal maxTransCount = req.getMaxTransCount();
        BigDecimal minTransCount = req.getMinTransCount();
        Boolean openSafetyVeri = req.getIsOpenSafetyVeri();
        String payType = req.getPayType();
        String payTypeRemark = req.getPayTypeRemark();
        String transactionPrice = req.getTransactionPrice();
        Float premiumRate = req.getPremiumRate();
        String tradePlatform = req.getTradePlatform();
        Integer transactionType = req.getTransactionType();
        Integer priceType = req.getPriceType();
        String payTypeName = req.getPayTypeName();

        Advertisement advertisement = new Advertisement();
        advertisement.setCity(city);
        advertisement.setCoinType(coinType);
        advertisement.setCreatetime(new Date());
        advertisement.setIsOpenSafetyVeri(openSafetyVeri);
        advertisement.setMaxTransCount(maxTransCount);
        advertisement.setMinTransCount(minTransCount);
        advertisement.setPayType(payType);
        advertisement.setPayTypeRemark(payTypeRemark);
        advertisement.setPremiumRate(premiumRate);
        advertisement.setPriceType(priceType);
        advertisement.setTradePlatform(tradePlatform);
        if(StringUtils.isNotBlank(transactionPrice)){
            advertisement.setTransactionPrice(new BigDecimal(transactionPrice));
        }
        advertisement.setTransactionType(transactionType);
        advertisement.setStatus(AdvertisementStatusEnum.PUBLISHED.getValue());
        advertisement.setUserId(userId);
        advertisement.setPayTypeName(payTypeName);

        boolean saveResult = otc.advertisementWebFacade.saveAdvertisement(advertisement);
        LOG.dEnd(this, "保存用户广告");
        if(saveResult){
            return buildWebApiResponse(SUCCESS, "保存成功");
        }else{
            return buildWebApiResponse(FAILURE, "保存失败");
        }
    }

    /**
     * 获取付款方式
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = PAYTYPELIST_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse payTypeList(){
        LOG.dStart(this, "获取付款方式");
        List<PayTypeResp> respList = new ArrayList<PayTypeResp>();
        PayTypeResp resp = null;
        for(PayTypeEnum p : PayTypeEnum.values()){
            resp = new PayTypeResp();
            resp.setName(p.getName());
            resp.setValue(p.getValue());
            respList.add(resp);
        }
        LOG.dEnd(this, "获取付款方式");
        return buildWebApiResponse(SUCCESS, respList);
    }

    /**
     * 获取其他平台接口
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = OTHERPLATFORMPRICE_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse otherPlatformPrice(@PathVariable String queryJson) {
        LOG.dStart(this, "获取其他平台");
        OtherPlatformPriceReq req = JsonHelper.jsonStr2Obj(queryJson, OtherPlatformPriceReq.class);
        if (req == null || req.getSymbol() == null) {
            throw new WebBizException("请求参数不能为空");
        }

        PriceFormula detail = otc.priceFormulaWebFacade.detail(0l, req.getSymbol());
        String platformName = detail.getPlatformName();
        if(StringUtils.isBlank(platformName)){
            throw new WebBizException("暂无平台可以选择");
        }
        String[] split = platformName.split(",");
        List<String> list = Arrays.asList(split);

        List<OtherPlatformPriceVo> otherPlatformPriceVos = otc.advertisementWebFacade.otherPlatformPrice(req.getSymbol());
        Iterator it = otherPlatformPriceVos.iterator();
        while(it.hasNext()){
            OtherPlatformPriceVo vo = (OtherPlatformPriceVo) it.next();
            if(!list.contains(vo.getName())){
                it.remove();
            }
        }

        LOG.dEnd(this, "获取其他平台");
        return buildWebApiResponse(SUCCESS, otherPlatformPriceVos);
    }

    /**
     * 计算公式
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = CALCULATIONFORMULA_ADVERTISEMENT_WEB_API,method = RequestMethod.POST)
    public WebApiResponse calculationFormula(@RequestBody CalculationFormulaReq req) {
        LOG.dStart(this, "获取计算公式开始");
        String calculationResult = "";
        CalculationFormulaResp resp = new CalculationFormulaResp();
        if (req == null) {
            throw new WebBizException("请求参数不能为空");
        }
        CalculatePriceVo vo = new CalculatePriceVo();
        vo.setSymbol(req.getSymbol());
        vo.setPlatform(req.getPlatform());
        vo.setPremiumRate(req.getPremiumRate());

        CalculatePricedto dto = otc.advertisementWebFacade.calculatePriceDto(vo);
        resp.setFormula(dto.getFormula());
        resp.setPrice(dto.getPrice());
        LOG.dEnd(this, "获取计算公式结束");
        return buildWebApiResponse(SUCCESS, resp);
    }

    /**
     * 人民币和币种价格转换
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = TRANSFORM_ADVERTISEMENT_WEB_API,method = RequestMethod.POST)
    public WebApiResponse transform(@RequestBody TransformReq req){
        LOG.dStart(this, "人民币和币种价格转换");
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }
        String transform = otc.advertisementWebFacade.transform(req.getType(), req.getNumber(), req.getAdId());
        LOG.dEnd(this, "人民币和币种价格转换");
        return buildWebApiResponse(SUCCESS, transform);
    }

    /**
     * 判断用户币的数量
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = USERCOINNUMBER_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse userCoinNumber(@PathVariable String queryJson){
        LOG.dStart(this, "判断用户币的数量");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        BigDecimal number = otc.advertisementWebFacade.userCoinNumber(userId, req.getId());
        LOG.dEnd(this, "判断用户币的数量");
        return buildWebApiResponse(SUCCESS, number);
    }

    /**
     * 溢价动态获取价格
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = NEWPRICE_ADVERTISEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse newPrice(@PathVariable String queryJson){
        LOG.dStart(this, "溢价动态获取价格");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }
        String price = otc.advertisementWebFacade.newPrice(req.getId());
        LOG.dEnd(this, "溢价动态获取价格");
        return buildWebApiResponse(SUCCESS, price);
    }

}
