package com.otc.api.console.ctrl.system.process;

import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiProcess;
import com.otc.api.console.ctrl.system.process.request.ProcessDetailReq;
import com.otc.api.console.ctrl.system.process.request.ProcessReq;
import com.otc.api.console.ctrl.system.process.request.StatusListReq;
import com.otc.api.console.ctrl.system.process.response.ProcessResp;
import com.otc.api.console.ctrl.system.process.response.StatusResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.sys.enums.AuditProcessEnum;
import com.otc.facade.sys.pojo.po.AuditProcess;
import com.otc.facade.sys.pojo.poex.AuditProcessEx;
import com.otc.facade.sys.pojo.vo.AuditProcessVo;
import com.otc.facade.trade.enums.TradeStatusEnum;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.enums.UserOperationEnum;
import com.otc.facade.user.enums.UserStatusEnum;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-5-8.
 */
@RestController
public class ProcessCtrl extends BaseConsoleCtrl implements ConsoleApiProcess {

    @RequestMapping(value = LIST_PROCESS_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getProcessList(@PathVariable String queryJson){
        LOG.dStart(this, "获取角色列表");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        AuditProcessVo vo = new AuditProcessVo();
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.auditProcessConsoleFacade.queryAuditProcessByConditionPage(vo);
        List<AuditProcessEx> list = vo.fatchTransferList();
        List<ProcessResp> respList = new ArrayList<>();
        ProcessResp resp;
        for (AuditProcessEx process : list){
            resp = new ProcessResp();
            resp.setRoleName1(process.getRoleName1());
            resp.setRoleName2(process.getRoleName2());
            resp.setRoleName3(process.getRoleName3());
            resp.setId(process.getId());
            resp.setIsNeedPwd(process.getProcess().getIsNeedPwd());
            resp.setModifiedUser(process.getModifiedName());
            resp.setType(AuditProcessEnum.getMap().get(process.getProcess().getType()));
            if (process.getProcess().getModifiedDate() != null){
                resp.setModifiedDate(DateHelper.date2String(process.getProcess().getModifiedDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
            }
            respList.add(resp);
        }
        LOG.d(this, respList);
        LOG.dEnd(this, "获取角色列表");
        return buildWebApiPageResponse(vo,respList);
    }

    /*@RequestMapping(value = DETAIL_PROCESS_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getProcessDetail(@PathVariable String queryJson){
        LOG.dStart(this, "获取角色详情");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);

        AuditProcess process = otc.auditProcessConsoleFacade.queryById(req.getId());

        LOG.d(this, respList);
        LOG.dEnd(this, "获取角色详情");
        return buildWebApiPageResponse(vo,respList);
    }*/

    @RequestMapping(value = UPDATE_PROCESS_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse updateProcess(@RequestBody ProcessReq req){
        LOG.dStart(this, "更新流程数据开始");
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if (req.getId() == null){
            throw new ConsoleBizException("流程ID不能为空");
        }
        AuditProcess process = new AuditProcess();
        process.setRoleId1(req.getRoleId1());
        process.setRoleId2(req.getRoleId2());
        process.setRoleId3(req.getRoleId3());
        process.setIsNeedPwd(req.getIsNeedPwd());
        process.setId(req.getId());
        otc.auditProcessConsoleFacade.updateAuditProcess(process);
        LOG.dEnd(this, "更新流程数据结束");
        return buildWebApiResponse(SUCCESS,null);
    }


    /**
     * 是否需要密码
     * @param queryJson
     * @return
     */
    @RequestMapping(value = LIST_PROCESS_DETAIL_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getProcessDetail(@PathVariable String queryJson){
        LOG.dStart(this, "获取审核流程详情");
        ProcessDetailReq req = encodeJsonStr2Obj(queryJson, ProcessDetailReq.class);
        String type = req.getType();
        if(StringUtils.isBlank(type)){
            throw new ConsoleBizException("审核类型不能为空");
        }
        AuditProcess auditProcess = otc.auditProcessConsoleFacade.getAuditProcessByType(type);
        if(auditProcess == null){
            throw new ConsoleBizException("审核流程不存在");
        }
        ProcessResp resp = new ProcessResp();
        resp.setIsNeedPwd(auditProcess.getIsNeedPwd());
        resp.setRoleId1(auditProcess.getRoleId1());
        resp.setRoleId2(auditProcess.getRoleId2());
        resp.setRoleId3(auditProcess.getRoleId3());
     //   resp.setRoleName1(auditProcess.getRoleId1());
        LOG.dEnd(this, "获取审核流程详情");
        return buildWebApiResponse(SUCCESS,resp);
    }


    /**
     * 条件查询状态列表
     */
    @RequestMapping(value = TRADE_STATUS_LIST_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getTradeStatusList(@PathVariable String queryJson)throws Exception {
        LOG.dStart(this, "查询console交易状态列表开始");
        StatusListReq req = JsonHelper.jsonStr2Obj(queryJson, StatusListReq.class);
        if (req == null) {
            throw new ConsoleBizException("请求参数为空");
        }
        int type = req.getType();
        Map<String,String> status = new HashMap<>();
        switch (type){
            case 1:  //进行中交易
                status.put("-1","全部");
                status.put(TradeStatusEnum.INIT.getCode().toString(),TradeStatusEnum.INIT.getDesc());
                status.put(TradeStatusEnum.PAYED.getCode().toString(),TradeStatusEnum.PAYED.getDesc());
                break;
            case 2: //申诉中交易
                status.put(TradeStatusEnum.APPEAL.getCode().toString(),TradeStatusEnum.APPEAL.getDesc());
                status.put(TradeStatusEnum.COMPLETE.getCode().toString(),TradeStatusEnum.COMPLETE.getDesc());
                break;
            case 3://用户列表
                status.put("00","全部");
                status.put(UserStatusEnum.COMMON.getCode(),UserStatusEnum.COMMON.getDesc());
                status.put(UserStatusEnum.FORBIDDEN.getCode(),UserStatusEnum.FORBIDDEN.getDesc());
                break;
            case 4://KYC认证列表
                status.put("00","全部");
                status.put(UserAutStatusEnum.KYC_PASS.getCode(),UserAutStatusEnum.KYC_PASS.getDesc());
                status.put(UserAutStatusEnum.KYC_NO_PASS.getCode(),UserAutStatusEnum.KYC_NO_PASS.getDesc());
                status.put(UserAutStatusEnum.KYC_POST.getCode(),UserAutStatusEnum.KYC_POST.getDesc());
                break;
            case 5://用户操作记录
                status = UserOperationEnum.getMap();
                status.put("00","全部");
                break;
            case 6:// 待审核提币
                status.put(VirtualRecordOutStatus.APPLY.getCode(),VirtualRecordOutStatus.APPLY.getDesc());
                status.put(VirtualRecordOutStatus.REVOKE.getCode(),VirtualRecordOutStatus.REVOKE.getDesc());
                status.put(VirtualRecordOutStatus.PROCESSING.getCode(),VirtualRecordOutStatus.PROCESSING.getDesc());
                status.put(VirtualRecordOutStatus.TWO_TIME_AUDIT.getCode(),VirtualRecordOutStatus.TWO_TIME_AUDIT.getDesc());
                status.put(VirtualRecordOutStatus.THREE_TIME_AUDIT.getCode(),VirtualRecordOutStatus.THREE_TIME_AUDIT.getDesc());
                break;
            default:
                throw new BizException("请求参数不正确");
        }

        StatusResp resp = new StatusResp();
        resp.setStatusMap(status);

        LOG.dEnd(this, "查询console交易列表结束");
        return buildWebApiResponse(SUCCESS, resp);
    }


}
