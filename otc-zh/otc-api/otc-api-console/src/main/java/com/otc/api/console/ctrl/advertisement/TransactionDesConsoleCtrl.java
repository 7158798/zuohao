package com.otc.api.console.ctrl.advertisement;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiAdvertisement;
import com.otc.api.console.ctrl.advertisement.request.TransactionDesDetailReq;
import com.otc.api.console.ctrl.advertisement.request.TransactionDesSaveReq;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.pojo.vo.TransactionDesVo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 获取交易说明列表
 * Created by zygong on 17-4-27.
 */
@RestController
public class TransactionDesConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiAdvertisement {

    /**
     * 获取交易说明列表
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = GETLIST_TRANSACTIONDES_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse getTransactionList(@RequestBody TransactionDesDetailReq req){
        LOG.dStart(this, "获取交易说明列表");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        TransactionDesVo vo = new TransactionDesVo();
        vo.setType(req.getType());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.transactionDesConsoleFacade.queryCountByConditionPage(vo);
        List<TransactionDes> respList = vo.fatchTransferList();
        LOG.dEnd(this, "获取交易说明列表");

        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 保存交易说明
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = SAVE_TRANSACTIONDES_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse save(@RequestBody TransactionDesSaveReq req){
        LOG.dStart(this, "保存交易说明");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Integer type = req.getType();
        String content = req.getContent();
        String title = req.getTitle();
        if(!checkParam(type, content, title)){
            throw new ConsoleBizException("请求参数不能为空");
        }
        TransactionDes transactionDes = new TransactionDes();
        transactionDes.setContent(content);
        transactionDes.setTitle(title);
        transactionDes.setType(type);
        transactionDes.setLastUpdateTime(new Date());
        transactionDes.setDelete(Boolean.FALSE);

        boolean result = otc.transactionDesConsoleFacade.save(transactionDes);

        LOG.dEnd(this, "保存交易说明");
        if(result){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 更新交易说明
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UPDATE_TRANSACTIONDES_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse update(@RequestBody TransactionDesSaveReq req){
        LOG.dStart(this, "更新交易说明");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Integer type = req.getType();
        String content = req.getContent();
        String title = req.getTitle();
        Long id = req.getId();
        if(!checkParam(type, content, title, id)){
            throw new ConsoleBizException("请求参数不能为空");
        }
        TransactionDes transactionDes = new TransactionDes();
        transactionDes.setContent(content);
        transactionDes.setTitle(title);
        transactionDes.setType(type);
        transactionDes.setLastUpdateTime(new Date());
        transactionDes.setId(id);

        int update = otc.transactionDesConsoleFacade.update(transactionDes);

        LOG.dEnd(this, "更新交易说明");
        if(update != 0){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 获取详情
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = DETAIL_TRANSACTIONDES_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson){
        LOG.dStart(this, "获取详情");
        TransactionDesDetailReq req = JsonHelper.jsonStr2Obj(queryJson, TransactionDesDetailReq.class);
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long id = req.getId();
        if(!checkParam(id)){
            throw new ConsoleBizException("请求参数不能为空");
        }

        TransactionDes byTypeAndId = otc.transactionDesConsoleFacade.getByTypeAndId(id, null);

        LOG.dEnd(this, "获取详情");
        if(byTypeAndId != null){
            return buildWebApiResponse(SUCCESS, byTypeAndId);
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
    @RequestMapping(value = DELETE_TRANSACTIONDES_CONSOLE_API,method = RequestMethod.GET)
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

        int delete = otc.transactionDesConsoleFacade.delete(id);

        LOG.dEnd(this, "删除详情");
        if(delete != 0){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }
}
