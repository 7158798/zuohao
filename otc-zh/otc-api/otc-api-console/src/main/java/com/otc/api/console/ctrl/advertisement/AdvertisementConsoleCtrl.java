package com.otc.api.console.ctrl.advertisement;

import com.jucaifu.common.log.LOG;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiAdvertisement;
import com.otc.api.console.ctrl.advertisement.request.AdvertisementCloseReq;
import com.otc.api.console.ctrl.advertisement.request.AdvertisementListReq;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.facade.advertisement.pojo.enums.AdvertisementTypeEnum;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 广告
 * Created by zygong on 17-4-27.
 */
@RestController
public class AdvertisementConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiAdvertisement {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取广告列表
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = GETLIST_ADVERTISEMENT_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse getList(@RequestBody AdvertisementListReq req){
        LOG.dStart(this, "获取广告列表");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        AdvertisementVo vo = new AdvertisementVo();
        vo.setStatus(req.getStatus());
        vo.setFilter(req.getFilter());
        vo.setSymbol(req.getSymbol());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());
        if(req.getTransactionType() != null && req.getTransactionType() != 0 ){
            vo.setTransactionType(AdvertisementTypeEnum.getByKey(req.getTransactionType()).getValue());
        }
        if (req.fetchDateFilterStart() != null) {
            vo.setBigenTime(req.fetchDateFilterStart());
        }
        if (req.fetchDateFilterEnd() != null) {
            vo.setEndTime(req.fetchDateFilterEnd());
        }

        vo = otc.advertisementConsoleFacade.queryCountByConditionPage(vo);
        List<Advertisement> respList = vo.fatchTransferList();
        LOG.dEnd(this, "获取广告列表");

        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 更新状态
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UPDATESTATUS_ADVERTISEMENT_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse updateStatus(@RequestBody AdvertisementCloseReq req){
        LOG.dStart(this, "更新状态");
        // 默认进行关闭
        Integer status = 2;
        Long id = req.getId();
        if(req == null || id == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if(req.getStatus() != null){
            status = req.getStatus();
        }

        boolean result = otc.advertisementConsoleFacade.updateStatus(status, id);

        LOG.dEnd(this, "更新状态");
        if(result){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }
}
