package com.otc.api.console.ctrl.user;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.ReflectHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiUser;
import com.otc.api.console.ctrl.user.reponse.UserAssetListResp;
import com.otc.api.console.ctrl.user.reponse.UserListResp;
import com.otc.api.console.ctrl.user.reponse.UserOperationListResp;
import com.otc.api.console.ctrl.user.req.UserListReq;
import com.otc.api.console.ctrl.user.req.UserOperationListReq;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.user.enums.*;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.poex.UserAssetRecord;
import com.otc.facade.user.pojo.poex.UserReportEx;
import com.otc.facade.user.pojo.vo.UserVo;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggq on 17-4-26.
 */
@Controller
public class UserConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiUser {
    /**
     * 条件查询用户列表
     */

    @RequestMapping(value = USER_LIST_QUERY_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getUserListByConditionPage(@PathVariable String queryJson)throws Exception {

        LOG.dStart(this, "查询console用户列表开始");
        UserListReq req = JsonHelper.jsonStr2Obj(queryJson, UserListReq.class);
        if (req == null) {
            throw new UserBizException("请求参数为空");
        }
        String status = req.getStatus();
        String userInfo = req.getUserInfo();

        UserVo vo = new UserVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());

        if(StringUtils.isNotBlank(status) && !StringUtils.equals("00",status)){
            //用户状态
            vo.setUserStatus(getUserStatus(status));
            //KYC状态
          //  vo(getKycStatus(status));
        }

        //会员信息
        vo.setUserInfo(userInfo);
        if (req.fetchDateFilterStart() != null) {
            vo.setStart(req.fetchDateFilterStart());
        }
        if (req.fetchDateFilterEnd() != null) {
            vo.setEnd(req.fetchDateFilterEnd());
        }

        vo.setOrderType("1");
        vo = otc.userConsoleFacade.selectUserListByConditionPage(vo);
        List<UserReportEx> list = vo.fatchTransferList();

        List<UserListResp> respList = new ArrayList<>();
        for(UserReportEx ex:list){
            UserListResp resp = new UserListResp();
            ReflectHelper.injectAttrFromSrcObj(ex,resp);
            resp.setUserStatus(UserStatusEnum.getNameByCode(ex.getUserStatus()));
            resp.setKycStatus(UserAutStatusEnum.isKyc(ex.getKycStatus())?"是":"否");
            resp.setPassportType(IdentityTypeEnum.getNameByCode(ex.getPassportType()));
            resp.setHasRealName(UserAutStatusEnum.isRealName(ex.getKycStatus())?"是":"否");
            respList.add(resp);
        }
        LOG.d(this, JsonHelper.obj2JsonStr(respList));

        LOG.dEnd(this, "查询console用户列表结束");
        return buildWebApiPageResponse(vo, respList);
    }


    /**
     * 禁用用户
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = USER_FORBIDDEN_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse forbiddenUser(@RequestBody WebApiBaseReq req)throws Exception {
        LOG.dStart(this,"禁用用户");
        if(req == null || req.getId() == null){
            throw new ConsoleBizException("请求参数不正确");
        }
        otc.userConsoleFacade.forbiddenUser(req.getId());
        //清理token
        UserCacheManager.deleteAllCache(req.getId());
        //发送通知
        otc.messageConsoleFacade.sendMessage(req.getId(), UserMessageConstantEnum.FORRIN_USER,null,null);
        LOG.dEnd(this,"禁用用户");
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     *  解禁用户
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = USER_RELIEVE_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse relieveUser(@RequestBody WebApiBaseReq req)throws Exception {
        LOG.dStart(this,"解禁用户");
        if(req == null || req.getId() == null){
            throw new ConsoleBizException("请求参数不正确");
        }
        otc.userConsoleFacade.LiftForbiddenUser(req.getId());

        LOG.dEnd(this,"解禁用户");
        //清理token
        UserCacheManager.deleteAllCache(req.getId());

        //发送通知
        otc.messageConsoleFacade.sendMessage(req.getId(), UserMessageConstantEnum.NO_FORRIN_USER,null,null);
        return buildWebApiResponse(SUCCESS,null);
    }


    /**
     *  重置登录密码
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = USER_RESET_PWD_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse resetUserPwd(@RequestBody WebApiBaseReq req)throws Exception {
        LOG.dStart(this,"重置用户密码");
        if(req == null || req.getId() == null){
            throw new ConsoleBizException("请求参数不正确");
        }
        otc.userConsoleFacade.resetUserPwd(req.getId(),req.getToken());

        LOG.dEnd(this,"重置用户密码");
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * 根据查询条件获取用户状态
     * @param status
     * @return
     */
    private String getUserStatus(String status){
        if(StringUtils.equals(status, UserStatusEnum.COMMON.getCode()) ||
                StringUtils.equals(status, UserStatusEnum.FORBIDDEN.getCode()) ){
            return status;
        }
        return null;
    }

    /**
     * 根据查询条件获取用户状态
     * @param status
     * @return
     */
    private String getKycStatus(String status){
        if(StringUtils.equals(status, UserAutStatusEnum.KYC_NO_PASS.getCode()) ||
                StringUtils.equals(status, UserAutStatusEnum.KYC_PASS.getCode()) ){
            return status;
        }
        return null;
    }


    /**
     * 条件查询用户操作列表
     */

    @RequestMapping(value = USER_OPERATION_LIST_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getUserOperationListByConditionPage(@PathVariable String queryJson)throws Exception {

        LOG.dStart(this, "查询console用户操作列表开始");
        UserOperationListReq req = JsonHelper.jsonStr2Obj(queryJson, UserOperationListReq.class);
        if (req == null) {
            throw new UserBizException("请求参数为空");
        }
        String opertionType = req.getOperationType();
        String userInfo = req.getUserInfo();

        UserVo vo = new UserVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());

        if(StringUtils.isNotBlank(opertionType) && !StringUtils.equals(opertionType,"00")){
            vo.setOperationType(opertionType);
        }

        //会员信息
        vo.setUserInfo(userInfo);
        if (req.fetchDateFilterStart() != null) {
            vo.setStart(req.fetchDateFilterStart());
        }
        if (req.fetchDateFilterEnd() != null) {
            vo.setEnd(req.fetchDateFilterEnd());
        }
        vo = otc.userConsoleFacade.selectUserOperationList(vo);
        List<UserReportEx> list = vo.fatchTransferList();

        List<UserOperationListResp> respList = new ArrayList<>();



        for(UserReportEx ex:list){
            UserOperationListResp resp = new UserOperationListResp();
            ReflectHelper.injectAttrFromSrcObj(ex,resp);
            resp.setUserStatus(UserStatusEnum.getNameByCode(ex.getUserStatus()));
            resp.setKycStatus(UserAutStatusEnum.isKyc(ex.getKycStatus())?"是":"否");
            resp.setHasRealName(UserAutStatusEnum.isRealName(ex.getKycStatus())?"是":"否");
            resp.setRegistType("邮箱注册");
            resp.setOperationType(UserOperationEnum.getMap().get(ex.getOperationType()));
            respList.add(resp);
        }
        LOG.d(this, JsonHelper.obj2JsonStr(respList));

        LOG.dEnd(this, "查询console用户操作列表结束");
        return buildWebApiPageResponse(vo, respList);
    }


    /**
     * 条件查询资产记录
     */
    @RequestMapping(value = USER_ASSET_LIST_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getUserAssetListByConditionPage(@PathVariable String queryJson)throws Exception {

        LOG.dStart(this, "查询console用户操作列表开始");
        UserListReq req = JsonHelper.jsonStr2Obj(queryJson, UserListReq.class);
        if (req == null) {
            throw new UserBizException("请求参数为空");
        }
        String userInfo = req.getUserInfo();

        UserVo vo = new UserVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());

        //会员信息
        vo.setUserInfo(userInfo);
        if (req.fetchDateFilterStart() != null) {
            vo.setStart(req.fetchDateFilterStart());
        }
        if (req.fetchDateFilterEnd() != null) {
            vo.setEnd(req.fetchDateFilterEnd());
        }
        vo = otc.userConsoleFacade.selectUserAssetList(vo);
        List<UserReportEx> list = vo.fatchTransferList();

        List<UserAssetListResp> respList = new ArrayList<>();

        List<UserAssetRecord> records;

        Map<Long,VirtualCoin>  coinMap= otc.virtualCoinConsoleFacade.getAllVirtualCoin();

        for(UserReportEx ex:list){
            UserAssetListResp resp = new UserAssetListResp();
            ReflectHelper.injectAttrFromSrcObj(ex,resp);
            records = JsonHelper.jsonArrayStr2List(ex.getRecordDetail(),UserAssetRecord.class);

            if(coinMap != null){
                for(UserAssetRecord record:records){
                    VirtualCoin virtualCoin = coinMap.get(record.getCoinId());
                    record.setCoinName(virtualCoin== null?"":virtualCoin.getName());
                }
            }
            resp.setRecords(records);
            respList.add(resp);
        }
        LOG.d(this, JsonHelper.obj2JsonStr(respList));

        LOG.dEnd(this, "查询console用户操作列表结束");
        return buildWebApiPageResponse(vo, respList);
    }

}
