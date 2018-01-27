package com.otc.api.web.ctrl.user;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.IpHelper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiUser;
import com.otc.api.web.ctrl.user.request.*;
import com.otc.api.web.ctrl.user.response.NoReadMessageResp;
import com.otc.api.web.ctrl.user.response.ResetForgetPwdResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.api.web.utils.WebTokenValidate;
import com.otc.common.api.exceptions.BuildAppResponseException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;
import com.otc.core.validator.SmsType;
import com.otc.facade.message.pojo.cond.MessageCond;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.pojo.poex.UserLoginInfo;
import com.otc.facade.user.pojo.poex.UserWebLoginInfo;
import com.otc.facade.user.pojo.poex.VerifyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 16-10-27.
 */
@RestController
public class UserWebCtrl extends BaseWebCtrl implements WebApiUser {

    private static final String CHANNEL_SOURCE = "web";



    /**
     * 用户手机注册（是否需要登录：否，请求方法：POST）
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_REGIST_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse registUser(@RequestBody UserRegisterReq req) throws BizException {
        LOG.dStart(this, "用户注册开始");
        String email = req.getEmail();
        String code = req.getCode();
        String loginName = req.getLoginName();
        String pwd = req.getPwd();

        String deviceIpAdress = IpHelper.getRequestIp();

        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new WebBizException("邮箱格式不正确");
        }
        if (!ValidateManager.validateValue(pwd, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new WebBizException("用户密码格式不正确，必须为6~16位数字、字母或符号的组合");
        }
        LOG.dEnd(this, "- 注册邮箱格式");

        if (!ValidateManager.validateValue(code, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            throw new WebBizException("验证码格式不正确");
        }
        User user = otc.userWebFacade.registerUserByEmail(loginName, email, code, pwd,req.getIpAdress());


        //用户注册成功后直接登录，生成token，并把token存入redis
        UserLoginInfo userLoginInfo = otc.userWebFacade.getUserLoginInfo(user, deviceIpAdress);
        UserWebLoginInfo userWebLoginInfo = otc.userWebFacade.getUserWebLoginInfo(userLoginInfo.getToken(), userLoginInfo.getCacheUserInfo());

        //发送通知
        otc.messageWebFacade.sendMessage(user.getId(), UserMessageConstantEnum.REGIST_SUCESS,null,null);

        LOG.d(this, userWebLoginInfo);
        LOG.dEnd(this, "处理业务逻辑－End");
        return buildWebApiResponse(SUCCESS, userWebLoginInfo);
    }

    /**
     * 用户登录
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_LOGIN_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse userLogin(@RequestBody UserLoginReq req) {
        UserWebLoginInfo userWebLoginInfo = userLoginLogic(req);
        WebApiResponse response = buildWebApiResponse(SUCCESS, userWebLoginInfo);
        LOG.d(this, response);
        return response;
    }

    public UserWebLoginInfo userLoginLogic(UserLoginReq req) {
        //1、校验数据,(手机号、密码)
        if (req == null) {
            throw new WebBizException("请求参数不正确");
        }
        String loginName = req.getLoginName();
        String password = req.getPassword();
        String deviceIpAdress = IpHelper.getRequestIp();
        if(StringUtils.isBlank(loginName)){
            throw new WebBizException("帐号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new WebBizException("密码不能为空");
        }
        UserLoginInfo userLoginInfo = otc.userWebFacade.userLoginByPassword(loginName, password, deviceIpAdress, "web");
        if(userLoginInfo == null){
            throw new WebBizException("用户名或密码输入不正确");
        }

        UserWebLoginInfo userWebLoginInfo = otc.userWebFacade.getUserWebLoginInfo(userLoginInfo.getToken(), userLoginInfo.getCacheUserInfo());
        LOG.d(this, userWebLoginInfo);
        LOG.dEnd(this, "处理业务逻辑－End");
        return userWebLoginInfo;
    }


    /**
     * 用户信息刷新 (是否需要登录：是；提交方式：GET)
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = USER_REFRESH_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse refreshUser(@PathVariable String queryJson) throws WebBizException {
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        //2.处理业务逻辑
        Long userId = WebTokenValidate.validateToken(req);
        User user = otc.userWebFacade.selectById(userId);
        String token = req.getToken();
        String ip = IpHelper.getRequestIp();
        //从缓存读取用户信息
        CacheUserInfo cacheUserInfo = otc.userWebFacade.getCacheUserInfo(token, userId,ip);
        UserWebLoginInfo userWebLoginInfo = otc.userWebFacade.getUserWebLoginInfo(token, cacheUserInfo);
        LOG.d(this, userWebLoginInfo);
        LOG.dEnd(this, "处理业务逻辑－End");

        //设置用户最后登录时间
        user.setLastLoginTime(user.getLoginTime());
        user.setLoginTime(new Date());
        otc.userWebFacade.updateUser(user);
        LOG.d(this, userWebLoginInfo);
        LOG.dEnd(this, "处理业务逻辑－End");
        return buildWebApiResponse(SUCCESS, userWebLoginInfo);
    }


    /**
     * 验证刷新token
     */
    @RequestMapping(value = WEB_VALIDATE_REFRESH_TOKEN_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse validateAndRefreshToken(@RequestBody WebApiBaseReq req) throws
            BuildAppResponseException {
        WebTokenValidate.validateToken(req);
        String token = req.getToken();
        otc.userWebFacade.validateAndRefreshToken(token);
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 注销
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_LOGOUT_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse logout(@RequestBody WebApiBaseReq req) throws BuildAppResponseException {

        LOG.dStart(this, "注销");
        WebTokenValidate.validateToken(req);

        String token = req.getToken();

        otc.userWebFacade.logout(token);

        LOG.dEnd(this, "注销");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * app忘记登录密码，重置密码(是否需要登录：否；提交方式：POST)
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_RESET_PASSWORD_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse resetPWD(@RequestBody ResetForgetPwdReq req) {

        /** 手机号码 */
        String email = req.getEmailAddress();
        /** 短信验证码 */
        String smsAuthCode = req.getAuthCode();
        /** 新密码 */
        String newPassword = req.getNewPassword();
        /** 操作步骤 */
        String stepType = req.getStepType();
        /** 验证码验证结果缓存 */
        String authCodeCache = req.getAuthCodeCache();

        ResetForgetPwdResp resp = new ResetForgetPwdResp();
        LOG.dTag(this, "<1> 验证提交参数格式是否正确");

        if (StringUtils.isBlank(stepType)) {
            throw new UserBizException("步骤参数参数不能为空");
        }
        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new UserBizException("邮箱格式不正确");
        }
        if (StringUtils.equals(stepType, "1")) {
            //验证短信信息
            if (!ValidateManager.validateValue(smsAuthCode, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
                throw new UserBizException("验证码格式不正确");
            }
            User user = otc.userWebFacade.selectByLoginName(email);
            if (user == null) {
                throw new UserBizException("用户不存在");
            }
            VerifyResult result = otc.userWebFacade.verifySmsAuthCode(email, smsAuthCode, SmsType
                    .LOGIN_PWD_RESET);
            if (result.isResult()) {
                resp.setStepType(result.getStepType());
                resp.setEmail(email);
                resp.setAuthCodeCache(result.getAuthCodeCache());
            } else {
                throw new UserBizException("验证码验证错误");
            }
        } else if (StringUtils.equals(stepType, "2")) {
            //重置密码
            if (!ValidateManager.validateValue(newPassword, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
                throw new UserBizException("用户密码格式不正确");
            }
            if (StringUtils.isBlank(authCodeCache)) {
                throw new UserBizException("用户验证码校验结果为空");
            }
            VerifyResult result = otc.userWebFacade.resetPassword(email, newPassword, authCodeCache);
            if (result.isResult()) {
                resp.setStepType(result.getStepType());
                String email1 = result.getPhoneNumber();
                resp.setEmail(email1);
                resp.setAuthCodeCache(result.getAuthCodeCache());

            } else {
                throw new UserBizException("重置密码失败");
            }
        } else {
            LOG.dEnd(this, "重置登录密码失败, 参数stepType错误");
            throw new UserBizException("重置登录密码失败");
        }

        LOG.d(this, resp);

        return buildWebApiResponse(SUCCESS, resp);
    }




    /**
     * 修改登录密码(是否需要登录：是；提交方式：PUT)
     */
    @RequestMapping(value = WEB_UPDATE_PASSWORD_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse updatePWD(@RequestBody UpdatePwdReq req) {
        LOG.dStart(this,"修改用户密码开始");
        Long userId = WebTokenValidate.validateToken(req);

        String newPassword = req.getNewPassword();
        String oldPassword = req.getOldPassword();
        if (!ValidateManager.validateValue(newPassword, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException("用户新密码格式不正确");
        }
        if (!ValidateManager.validateValue(oldPassword, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException("用户旧密码格式不正确");
        }
        if(StringUtils.equals(newPassword,oldPassword)){
            throw new UserBizException("两次密码一致");
        }
        if(StringUtils.isBlank(req.getCode())){
            throw new UserBizException("验证码不能为空");
        }
        otc.userWebFacade.updateUserPassword(newPassword, oldPassword, req.getToken(),req.getCode());
        UserCacheManager.deleteCacheObj(userId);
        LOG.dEnd(this, "用户登录密码修改成功--并清理所有的用户登录token信息");

        //发送通知
        otc.messageWebFacade.sendMessage(userId, UserMessageConstantEnum.MODIFY_PWD_SUCESS,null,null);
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 设置、修改用户防钓鱼码(是否需要登录：是；提交方式：PUT)
     */
    @RequestMapping(value = WEB_UPDATE_FISHCODE_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse updateUserFish(@RequestBody UserFishCodeReq req) throws Exception {
        LOG.dStart(this,"设置防钓鱼码开始");
        Long userId = WebTokenValidate.validateToken(req);

        String fishCode = req.getFishCode();
        if (StringUtils.isBlank(fishCode) || fishCode.length() > 16 || fishCode.length() < 10) {
            throw new UserBizException("防钓鱼码格式不正确");
        }

        User user = otc.userWebFacade.selectByIdForException(userId);

        otc.userWebFacade.updateUserFishCode(userId,fishCode);
        //发送通知
        if(StringUtils.isBlank(user.getPreventFish())){
            otc.messageWebFacade.sendMessage(userId, UserMessageConstantEnum.MODIFY_FISHCODE_SUCESS,"设置",null);
        }else{
            otc.messageWebFacade.sendMessage(userId, UserMessageConstantEnum.MODIFY_FISHCODE_SUCESS,"修改",null);
        }




        UserCacheManager.deleteCacheObj(userId);
        LOG.dEnd(this, "设置防钓鱼码成功--并清理所有的用户登录token信息");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 校验登录名是否重复
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_CHECKLOGINNAME_API,method = RequestMethod.POST)
    public WebApiResponse checkLoginName(@RequestBody UserRegisterReq req){

        String loginName = req.getLoginName();

        if(StringUtils.isBlank(loginName)){
            throw new WebBizException("请求参数不能为空");
        }

        if(req == null){
            throw new WebBizException("请求参数错误");
        }

        User user = otc.userWebFacade.selectByLoginName(loginName);
        if(user == null){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            throw new WebBizException("用户名已被注册");
        }
    }



    /**
     * 修改邮箱 (是否需要登录：是；提交方式：POST)
     */
    @RequestMapping(value = WEB_UPDATE_EMAIL_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse changeEmail(@RequestBody ChangeEmailReq req) throws Exception {
        LOG.dStart(this,"修改邮箱");
        //获取id
        Long userId = WebTokenValidate.validateToken(req);

        User user = otc.userWebFacade.selectByIdForException(userId);
        /** 新邮箱 */
        String newMail = req.getEmail();
        /** 验证码 */
        String authCode = req.getAuthCode();
        /** 操作步骤 */
        String stepType = req.getStepType();
        /** 验证码验证结果缓存 */
        String authCodeCache = req.getAuthCodeCache();

        String oldMail = user.getEmailAddress();

        ResetForgetPwdResp resp = new ResetForgetPwdResp();
        LOG.dTag(this, "<1> 验证提交参数格式是否正确");

        if(StringUtils.isBlank(stepType)){
            throw new UserBizException("步骤参数参数不能为空");
        }

        if (StringUtils.equals(stepType, "1")) {
            //验证短信信息
            if (!ValidateManager.validateValue(authCode, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
                throw new UserBizException("验证码格式不正确");
            }
            VerifyResult result = otc.userWebFacade.verifySmsAuthCode(oldMail, authCode, SmsType.CHANGE_EMAIL);
            if (result.isResult()) {
                resp.setStepType(result.getStepType());
                String email1 = result.getPhoneNumber();
                resp.setEmail(email1);
                resp.setAuthCodeCache(result.getAuthCodeCache());
            } else {
                throw new UserBizException("验证码验证错误");
            }
        } else if (StringUtils.equals(stepType,"2")) {
            //修改手机号码
            if (!ValidateManager.validateValue(newMail, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
                throw new UserBizException("邮箱号码格式不正确");
            }
            if (StringUtils.isBlank(authCodeCache)) {
                throw new UserBizException("原验证结果为空");
            }
            VerifyResult result = otc.userWebFacade.changeEmail(userId,newMail,authCodeCache,authCode);
            if (result.isResult()) {
                resp.setStepType(result.getStepType());
                String email = result.getPhoneNumber();
                resp.setEmail(email);
            } else {
                throw new UserBizException("重置密码失败");
            }
        } else {
            LOG.dEnd(this, "修改邮箱失败, 参数stepType错误");
            throw new UserBizException("修改邮箱失败");
        }

        LOG.d(this, resp);
        LOG.dEnd(this,"修改邮箱");
        return buildWebApiResponse( SUCCESS, resp);
    }


    /**
     * 获取交易数、消息未读数
     */
    @RequestMapping(value = WEB_GET_MESSAGE_NOREAD, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse noReadMessage(@PathVariable String queryJson) throws
            BuildAppResponseException {
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        LOG.dStart(this,"获取未读消息");
        //获取id
        Long userId = WebTokenValidate.validateToken(req);

        //未读消息
        MessageCond messageCond = new MessageCond();
        messageCond.setReceive(userId);
        messageCond.setIsRead(false);
        List<Message>  messages = otc.messageWebFacade.queryListByCondition(messageCond);

        //未处理交易
        List<Trade> trades = otc.tradeWebFacade.queryRunningTrade(userId);

        NoReadMessageResp noReadMessage = new NoReadMessageResp();
        noReadMessage.setMessageNum(messages.size());
        noReadMessage.setTradeNum(trades.size());

        LOG.dEnd(this,"获取未读消息");
        return buildWebApiResponse(SUCCESS, noReadMessage);
    }



}
