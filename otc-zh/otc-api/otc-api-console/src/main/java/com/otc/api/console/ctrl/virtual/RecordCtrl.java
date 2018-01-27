package com.otc.api.console.ctrl.virtual;

import com.jucaifu.common.log.LOG;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiRecord;
import com.otc.api.console.ctrl.virtual.request.CancelReq;
import com.otc.api.console.ctrl.virtual.request.RecordAuditReq;
import com.otc.api.console.ctrl.virtual.request.RecordReq;
import com.otc.api.console.ctrl.virtual.response.RecordDetailResp;
import com.otc.api.console.ctrl.virtual.response.RecordListResp;
import com.otc.api.console.ctrl.virtual.response.RecordStatusResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.api.console.utils.ConsoleTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.sys.enums.AuditProcessEnum;
import com.otc.facade.sys.pojo.po.AuditProcess;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.virtual.enums.VirtualRecordInStatus;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.poex.VirtualRecordEx;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-5-2.
 */
@RestController
public class RecordCtrl extends BaseConsoleCtrl implements ConsoleApiRecord {

    /**
     * Get status list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_STATUS_RECORD_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getStatusList(@PathVariable String queryJson){
        LOG.dStart(this,"虚拟币充值列表");
        RecordReq req = encodeJsonStr2Obj(queryJson,RecordReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if (StringUtils.isEmpty(req.getType())){
            throw new ConsoleBizException("类型不能为空");
        }
        List<RecordStatusResp> respList = new ArrayList<>();
        RecordStatusResp resp;
        if (VirtualRecordType.RECHARGE.getCode().equals(req.getType())){
            // 充值
            for (VirtualRecordInStatus inStatus : VirtualRecordInStatus.values()){
                resp = new RecordStatusResp();
                resp.setCode(inStatus.getCode());
                resp.setName(inStatus.getDesc());
                respList.add(resp);
            }
        } else {
            // 提现
            for (VirtualRecordOutStatus outStatus : VirtualRecordOutStatus.values()){
                resp = new RecordStatusResp();
                resp.setCode(outStatus.getCode());
                resp.setName(outStatus.getDesc());
                respList.add(resp);
            }
        }
        LOG.d(this,respList);
        LOG.dEnd(this, "虚拟币充值列表");
        return buildWebApiResponse(SUCCESS,respList);
    }

    /**
     * Get record detail web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = DETAIL_RECORD_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getRecordDetail(@PathVariable String queryJson){
        LOG.dStart(this,"虚拟币操作记录详情");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson,WebApiBaseReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        VirtualRecord record = otc.virtualRecordConsoleFacade.queryById(req.getId());
        RecordDetailResp resp = new RecordDetailResp();
        resp.setId(record.getId());
        resp.setAddress(record.getAddress());
        resp.setAmount(record.getAmount().toString());
        if (record.getFees() != null){
            resp.setFees(record.getFees().toString());
        }
        User user = otc.userConsoleFacade.selectById(record.getUserId());
        if (user != null){
            resp.setUserName(user.getLoginName());
        }
        VirtualCoin coin = otc.virtualCoinConsoleFacade.isExist(record.getCoinId());
        if (coin != null){
            resp.setCoinName(coin.getName());
        }
        if (VirtualRecordOutStatus.PROCESSING.getCode().equals(record.getStatus())
                || VirtualRecordOutStatus.TWO_TIME_AUDIT.getCode().equals(record.getStatus())
                || VirtualRecordOutStatus.THREE_TIME_AUDIT.getCode().equals(record.getStatus())){
            VirtualRecordOutStatus node = VirtualRecordOutStatus.statusMap.get(record.getStatus());
            Boolean isNeedPwd = otc.auditProcessConsoleFacade.isNeedPwd(node,AuditProcessEnum.VIRTUAL_OUT.getCode());
            resp.setIsConfirmPwd(isNeedPwd);
            Boolean isLastNode = otc.auditProcessConsoleFacade.isLastNode(node, AuditProcessEnum.VIRTUAL_OUT.getCode());
            resp.setIsWalletPwd(isLastNode);
        }
        LOG.d(this,resp);
        LOG.dEnd(this,"虚拟币操作记录详情");
        return buildWebApiResponse(SUCCESS,resp);
    }

    /**
     * Get recharge list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_RECHARGE_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getRechargeList(@PathVariable String queryJson){
        LOG.dStart(this,"虚拟币充值列表");
        RecordReq req = encodeJsonStr2Obj(queryJson,RecordReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        VirtualRecordVo vo = new VirtualRecordVo();
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setType(VirtualRecordType.RECHARGE.getCode());
        vo.setStatus(req.getStatus());
        vo.setCondition(req.getCondition());
        vo.setCoinId(req.getCoinId());
        if (req.fetchDateFilterStart() != null)
            vo.setStart(req.fetchDateFilterStart());
        if (req.fetchDateFilterEnd() != null)
            vo.setEnd(req.fetchDateFilterEnd());

        vo = otc.virtualRecordConsoleFacade.queryConsoleByConditionPage(vo);
        List<VirtualRecordEx> list = vo.fatchTransferList();
        List<RecordListResp> respList = new ArrayList<>();
        RecordListResp resp;
        for (VirtualRecordEx recordEx : list){
            resp = new RecordListResp();
            resp.copy(recordEx);
            respList.add(resp);
        }
        LOG.d(this,respList);

        LOG.dEnd(this, "虚拟币充值列表");
        return buildWebApiPageResponse(vo,respList);
    }


    /**
     * Get examine withdraw list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_EXAMINE_WITHDRAW_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getExamineWithdrawList(@PathVariable String queryJson){
        LOG.dStart(this,"审核提现列表");
        RecordReq req = encodeJsonStr2Obj(queryJson,RecordReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        VirtualRecordVo vo = new VirtualRecordVo();
        vo.setStatus(req.getStatus());
        if (StringUtils.isEmpty(req.getStatus())){
            List<String> statusList = new ArrayList<>();
            statusList.add(VirtualRecordOutStatus.APPLY.getCode());
            statusList.add(VirtualRecordOutStatus.PROCESSING.getCode());
            statusList.add(VirtualRecordOutStatus.TWO_TIME_AUDIT.getCode());
            statusList.add(VirtualRecordOutStatus.THREE_TIME_AUDIT.getCode());
            vo.setStatusList(statusList);

        }
        return getWithdrawList(vo,req);
    }

    /**
     * Get success withdraw list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_SUCCESS_WITHDRAW_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getSuccessWithdrawList(@PathVariable String queryJson){
        LOG.dStart(this,"审核提现列表");
        RecordReq req = encodeJsonStr2Obj(queryJson,RecordReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        VirtualRecordVo vo = new VirtualRecordVo();
        List<String> statusList = new ArrayList<>();
        statusList.add(VirtualRecordOutStatus.SUCCESS.getCode());
//        statusList.add(VirtualRecordOutStatus.REVOKE.getCode());
        vo.setStatusList(statusList);
        return getWithdrawList(vo,req);
    }


    /**
     * Lock withdraw web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = LOCK_WITHDRAW_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse lockWithdraw(@RequestBody WebApiBaseReq req){
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if (req.getId() == null){
            throw new ConsoleBizException("提现id不能为空");
        }
        otc.virtualRecordConsoleFacade.lockWithdraw(req.getId());
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * Cancel web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = CANCEL_RECORD_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse cancel(@RequestBody CancelReq req){
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if (req.getId() == null){
            throw new ConsoleBizException("提现id不能为空");
        }
        if (StringUtils.isEmpty(req.getReason())){
            throw new ConsoleBizException("拒绝理由不能为空");
        }
        otc.virtualRecordConsoleFacade.cancelWithdraw(req.getId(),req.getReason());
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * Unlock withdraw web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = UNLOCK_WITHDRAW_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse unlockWithdraw(@RequestBody WebApiBaseReq req){
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if (req.getId() == null){
            throw new ConsoleBizException("提现id不能为空");
        }
        otc.virtualRecordConsoleFacade.unlockWithdraw(req.getId());
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * Audit withdraw web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = AUDIT_WITHDRAW_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse auditWithdraw(@RequestBody RecordAuditReq req){
        LOG.dStart(this,"审核提现开始");
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long uid = ConsoleTokenValidate.validateToken(req);
        otc.virtualRecordConsoleFacade.auditWithdraw(req.getId(),uid,req.getConfirmPwd(),req.getWalletPwd());
        LOG.dEnd(this,"审核提现结束");
        return buildWebApiResponse(SUCCESS,null);
    }

    private WebApiResponse getWithdrawList(VirtualRecordVo vo,RecordReq req){
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setType(VirtualRecordType.WITHDRAW.getCode());
        vo.setCondition(req.getCondition());
        vo.setCoinId(req.getCoinId());
        if (req.fetchDateFilterStart() != null)
            vo.setStart(req.fetchDateFilterStart());
        if (req.fetchDateFilterEnd() != null)
            vo.setEnd(req.fetchDateFilterEnd());
        vo = otc.virtualRecordConsoleFacade.queryConsoleByConditionPage(vo);
        List<VirtualRecordEx> list = vo.fatchTransferList();
        List<RecordListResp> respList = new ArrayList<>();
        RecordListResp resp;
        for (VirtualRecordEx recordEx : list){
            resp = new RecordListResp();
            resp.copy(recordEx);
            respList.add(resp);
        }
        LOG.d(this,respList);

        LOG.dEnd(this, "查询提现记录结束");
        return buildWebApiPageResponse(vo,respList);
    }

}
