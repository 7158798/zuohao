package com.otc.api.console.ctrl.user;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.ReflectHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiUserKyc;
import com.otc.api.console.ctrl.user.req.UserKycAuditingReq;
import com.otc.api.console.ctrl.user.reponse.UserKycListResp;
import com.otc.api.console.ctrl.user.req.UserListReq;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.api.console.utils.ConsoleTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.facade.user.enums.IdentityTypeEnum;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.enums.UserStatusEnum;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.poex.UserReportEx;
import com.otc.facade.user.pojo.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggq on 17-4-27.
 */
@Controller
public class UserKycConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiUserKyc {


    @RequestMapping(value = USERKYC_LIST_QUERY_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getUserKycListByConditionPage(@PathVariable String queryJson)throws Exception {

        LOG.dStart(this, "查询console用户kyc列表开始");
        UserListReq req = JsonHelper.jsonStr2Obj(queryJson, UserListReq.class);
        if (req == null) {
            throw new UserBizException("请求参数为空");
        }
        String status = req.getStatus();
        String userInfo = req.getUserInfo();

        if(UserAutStatusEnum.getMap().get(status) == null){
            throw new UserBizException("认证状态不正确");
        }

        UserVo vo = new UserVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());

        //用户状态
        vo.setKycStatus(status);
        //会员信息
        vo.setUserInfo(userInfo);
        if (req.fetchDateFilterStart() != null) {
            vo.setStart(req.fetchDateFilterStart());
        }
        if (req.fetchDateFilterEnd() != null) {
            vo.setEnd(req.fetchDateFilterEnd());
        }
        vo.setOrderType("2");
        vo = otc.userConsoleFacade.selectUserListByConditionPage(vo);
        List<UserReportEx> list = vo.fatchTransferList();

        List<UserKycListResp> respList = new ArrayList<>();

        for(UserReportEx ex:list){
            UserKycListResp resp = new UserKycListResp();
            ReflectHelper.injectAttrFromSrcObj(ex,resp);
            resp.setUserStatus(UserStatusEnum.getNameByCode(ex.getUserStatus()));
            resp.setKycStatus(UserAutStatusEnum.isKyc(ex.getKycStatus())?"是":"否");
            resp.setHasRealName(UserAutStatusEnum.isRealName(ex.getKycStatus())?"是":"否");
            resp.setPassportType(IdentityTypeEnum.getNameByCode(ex.getPassportType()));
            respList.add(resp);
        }
        LOG.d(this, JsonHelper.obj2JsonStr(respList));

        LOG.dEnd(this, "查询console用户kyc列表结束");
        return buildWebApiPageResponse(vo, respList);
    }


    @RequestMapping(value = USERKYC_PASS_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse passUserKyc(@RequestBody UserKycAuditingReq req)throws Exception {
        LOG.dStart(this,"审核通过KYC认证");
        if(req == null || req.getId() == null){
            throw new ConsoleBizException("请求参数不正确!");
        }
        Long adminId = ConsoleTokenValidate.validateToken(req);
        otc.userAuConsoleFacade.passKycAudtiing(req.getId(),adminId);

        LOG.dEnd(this,"审核通过KYC认证");
        return buildWebApiResponse(SUCCESS,null);
    }


    @RequestMapping(value = USERKYC_NOPASS_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse noPassUserKyc(@RequestBody UserKycAuditingReq req)throws Exception {
        LOG.dStart(this,"拒绝KYC认证");
        if(req == null || req.getId() == null){
            throw new ConsoleBizException("请求参数不正确!");
        }
        if(StringUtils.isBlank(req.getRejectReason())){
            throw new ConsoleBizException("拒绝原因不能为空!");
        }
        Long adminId = ConsoleTokenValidate.validateToken(req);
        otc.userAuConsoleFacade.noPassKycAuditing(req.getId(),adminId,req.getRejectReason());
        LOG.dEnd(this,"拒绝KYC认证");
        return buildWebApiResponse(SUCCESS,null);
    }




    }
