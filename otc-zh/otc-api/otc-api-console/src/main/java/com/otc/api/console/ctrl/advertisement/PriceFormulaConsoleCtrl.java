package com.otc.api.console.ctrl.advertisement;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiAdvertisement;
import com.otc.api.console.ctrl.advertisement.request.OtherPlatformPriceReq;
import com.otc.api.console.ctrl.advertisement.request.PriceFormulaListReq;
import com.otc.api.console.ctrl.advertisement.request.PriceFormulaSaveReq;
import com.otc.api.console.ctrl.advertisement.response.VirtualCoinResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;
import com.otc.facade.advertisement.pojo.vo.PriceFormulaVo;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-4-28.
 */
@RestController
public class PriceFormulaConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiAdvertisement {

    /**
     * 获取价格公式列表
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = GETLIST_PRICEFORMULA_CONSOLE_API,method = RequestMethod.POST)
   public WebApiResponse getList(@RequestBody PriceFormulaListReq req){
        LOG.dStart(this, "获取价格公式列表");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        PriceFormulaVo vo = new PriceFormulaVo();
        vo.setCoinId(req.getCoinId());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.priceFormulaConsoleFacade.queryCountByConditionPage(vo);
        List<PriceFormula> respList = vo.fatchTransferList();
        LOG.dEnd(this, "获取价格公式列表");

        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 保存价格公式
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = SAVE_PRICEFORMULA_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse save(@RequestBody PriceFormulaSaveReq req){
        LOG.dStart(this, "保存价格公式");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long coinId = req.getCoinId();
        String priceFormulaStr = req.getPriceFormula();
        String platformName = req.getPlatformName();
        if(!checkParam(coinId, priceFormulaStr)){
            throw new ConsoleBizException("请求参数不能为空");
        }
        PriceFormula priceFormula = new PriceFormula();
        priceFormula.setCoinId(coinId);
        priceFormula.setPriceFormula(priceFormulaStr);
        priceFormula.setCreatetime(new Date());
        priceFormula.setUpdatetime(new Date());
        priceFormula.setIsDelete(Boolean.FALSE);
        priceFormula.setPlatformName(platformName);

        boolean result = otc.priceFormulaConsoleFacade.savePriceFormual(priceFormula);

        LOG.dEnd(this, "保存价格公式");
        if(result){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 更新价格公式
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UPDATE_PRICEFORMULA_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse update(@RequestBody PriceFormulaSaveReq req){
        LOG.dStart(this, "更新价格公式");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long id = req.getId();
        Long coinId = req.getCoinId();
        String priceFormulaStr = req.getPriceFormula();
        String platformName = req.getPlatformName();
        if(!checkParam(id, coinId, priceFormulaStr)){
            throw new ConsoleBizException("请求参数不能为空");
        }
        PriceFormula priceFormula = new PriceFormula();
        priceFormula.setId(id);
        priceFormula.setCoinId(coinId);
        priceFormula.setPriceFormula(priceFormulaStr);
        priceFormula.setCreatetime(new Date());
        priceFormula.setUpdatetime(new Date());
        priceFormula.setIsDelete(Boolean.FALSE);
        priceFormula.setPlatformName(platformName);

        boolean result = otc.priceFormulaConsoleFacade.updatePriceFormula(priceFormula);

        LOG.dEnd(this, "更新价格公式");
        if(result){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 价格公式详情
     * @param queryJson
     * @return
     */
    @ResponseBody
    @RequestMapping(value = DETAIL_PRICEFORMULA_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson){
        LOG.dStart(this, "价格公式详情");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long id = req.getId();
        if(!checkParam(id)){
            throw new ConsoleBizException("请求参数不能为空");
        }

        PriceFormula detail = otc.priceFormulaConsoleFacade.detail(id);

        LOG.dEnd(this, "价格公式详情");
        if(detail != null){
            return buildWebApiResponse(SUCCESS, detail);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 删除详情
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = DELETE_PRICEFORMULA_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse delete(@PathVariable String queryJson){
        LOG.dStart(this, "删除详情");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long id = req.getId();
        if(!checkParam(id)){
            throw new ConsoleBizException("请求参数不能为空");
        }

        int result = otc.priceFormulaConsoleFacade.delete(id);

        LOG.dEnd(this, "删除详情");
        if(result != 0){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = COINLIST_PRICEFORMULA_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getCoinList(){
        LOG.dStart(this,"获取可用的币种");
        Map<Long,VirtualCoin> coinMap = otc.virtualCoinConsoleFacade.getVirtualCoin();
        List<VirtualCoinResp> respList = new ArrayList<>();
        VirtualCoinResp resp;
        if (coinMap != null){
            for (Map.Entry<Long,VirtualCoin> entry : coinMap.entrySet()){
                VirtualCoin temp = entry.getValue();
                resp = new VirtualCoinResp();
                resp.setShortName(temp.getShortName());
                resp.setName(temp.getName());
                resp.setCoinId(temp.getId());
                respList.add(resp);
            }
        }
        LOG.d(this,respList);
        LOG.dEnd(this,"获取可用的币种");
        return buildWebApiResponse(SUCCESS,respList);
    }

    /**
     * 获取其他平台接口
     * @param queryJson
     * @return
     */
    @RequestMapping(value = OTHERPLATFORMPRICE_ADVERTISEMENT_CONSOLE_API, method = RequestMethod.GET)
    public WebApiResponse otherPlatformPrice(@PathVariable String queryJson) {
        LOG.dStart(this, "获取其他平台");
        OtherPlatformPriceReq req = JsonHelper.jsonStr2Obj(queryJson, OtherPlatformPriceReq.class);
        if (req == null || req.getSymbol() == null) {
            throw new ConsoleBizException("请求参数不能为空");
        }

        List<OtherPlatformPriceVo> otherPlatformPriceVos = otc.advertisementConsoleFacade.otherPlatformPrice(req.getSymbol());
        List<String> otherPlatformName = new ArrayList<>();
        if(otherPlatformPriceVos != null && !otherPlatformPriceVos.isEmpty()){
            for(OtherPlatformPriceVo otherPlatformPriceVo : otherPlatformPriceVos){
                otherPlatformName.add(otherPlatformPriceVo.getName());
            }
        }
        LOG.dEnd(this, "获取其他平台");
        return buildWebApiResponse(SUCCESS, otherPlatformName);
    }
}

