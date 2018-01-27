package com.otc.api.web.ctrl.user;

import com.base.common.email.client.AzureEmailUtil;
import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.configs.TimeOutConfigConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiUser;
import com.otc.api.web.ctrl.user.request.ChangeEmailReq;
import com.otc.api.web.ctrl.user.request.EmailVeriCodeReq;
import com.otc.api.web.ctrl.user.request.RegistImageReq;
import com.otc.api.web.ctrl.user.request.SendMailCodeReq;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.api.web.utils.WebTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.core.validator.SmsCodeBean;
import com.otc.core.validator.SmsCodeGet;
import com.otc.core.validator.SmsType;
import com.otc.core.validator.SmsVerifyUtils;
import com.otc.facade.user.pojo.po.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-4-24.
 */
@Controller
public class SendValidateWebCtrl extends BaseWebCtrl implements WebApiUser {
    /**
     * 获取图形验证码
     *
     * @param queryJson
     * @param response
     * @return
     * @throws Exception
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_VALIDATE_API, method = RequestMethod.GET)
    public WebApiResponse getKaptchaImage(@PathVariable String queryJson, HttpServletResponse response) throws Exception {
        LOG.dStart(this, "开始获取图形验证码");
        response.setContentType("image/jpeg");
        RegistImageReq req = JsonHelper.jsonStr2Obj(queryJson, RegistImageReq.class);
        if (StringUtils.isBlank(req.getAuthCodeCache())) {
            throw new WebBizException("请求参数异常");
        }

        String capText = otc.captchaProducer.createText();
        LOG.d(this, "图形验证码: " + capText);
        BufferedImage bi = otc.captchaProducer.createImage(capText);

        //保存cookie 前端验证
        CacheHelper.saveObj(req.getAuthCodeCache(), capText, TimeOutConfigConstant.STEP_DEPEND_TOKEN_TIMEOUT);
        LOG.d(this, "缓存authCodeCache的key：" + req.getAuthCodeCache());

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }
        LOG.dEnd(this, "获取图形验证码");
        return buildWebApiResponse(SUCCESS, bi);
    }


    /**
     * 发送注册验证码
     *
     * @return app api response
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = WEB_REGISTER_EMAILCODE_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sendSmsForRegist(@RequestBody EmailVeriCodeReq req) throws Exception {
        LOG.dStart(this, "发送注册验证码");
        String email = req.getEmailAddress();
        if (req == null || StringUtils.isBlank(email)) {
            throw new WebBizException("邮箱地址不能为空");
        }
        String veryCode = req.getVeryCode();
        String authCodeCache = req.getAuthCodeCache();

        if(StringUtils.isBlank(veryCode)){
            throw new WebBizException("请输入图形验证码");
        }
        if(StringUtils.isBlank(authCodeCache)){
            throw new WebBizException("验证码无效");
        }
        //校验短信验证结果缓存是否过期
        String code = CacheHelper.getObj(authCodeCache);
        if(code == null){
             throw new WebBizException("图形验证码不存在或已失效");
        }
        if(!StringUtils.equals(veryCode,code)){
             throw new WebBizException("图形验证码不正确");
        }

        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new WebBizException("邮箱格式不正确");
        }
        User user = otc.userWebFacade.selectByLoginName(email);
        if (user != null) {
            throw new WebBizException("此邮箱已被注册");
        }
        SmsCodeGet smsCodeGet = this.sendMessage(email, SmsType.REGISTER);
        return buildWebApiResponse(SUCCESS, smsCodeGet);

    }


    /**
     * 发送重置密码验证码
     * @return app api response
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = RESET_PASSWORD_SMS_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sendSmsForResetPWD(@RequestBody EmailVeriCodeReq req) throws Exception {
        LOG.dStart(this, "发送重置密码验证码");

        String email = req.getEmailAddress();
        if (req == null || StringUtils.isBlank(email)) {
            throw new WebBizException("邮箱地址不能为空");
        }
        String veryCode = req.getVeryCode();

        if(StringUtils.isBlank(veryCode)){
            throw new WebBizException("请输入图形验证码");
        }
        String code = CacheHelper.getObj(req.getAuthCodeCache());
        if(code == null){
            throw new WebBizException("图形验证码不存在或已失效");
        }
        if(!StringUtils.equals(veryCode, code)){
            throw new WebBizException("图形验证码不正确");
        }
        User user = otc.userWebFacade.selectByLoginName(email);
        if(user == null){
            throw new WebBizException("用户不存在");
        }
        if(StringUtils.isBlank(user.getLoginPassword())){
            throw new WebBizException("尚未设置密码");
        }
        SmsCodeGet smsCodeGet = this.sendMessage(email, SmsType.LOGIN_PWD_RESET);
        return buildWebApiResponse(SUCCESS, smsCodeGet);
    }

    /**
     * 发送修改密码验证码
     * @return app api response
     */
    @RequestMapping(value = UPDATE_PASSWORD_SMS_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sendPWDCode(@RequestBody WebApiBaseReq req) throws Exception {
        LOG.dStart(this, "发送修改密码验证码");
        Long userId = WebTokenValidate.validateToken(req);
        User user = otc.userWebFacade.selectByIdForException(userId);
        String email = user.getEmailAddress();
        if (StringUtils.isBlank(email)) {
            throw new WebBizException("邮箱地址不能为空");
        }
        if(StringUtils.isBlank(user.getLoginPassword())){
            throw new WebBizException("尚未设置密码");
        }
        SmsCodeGet smsCodeGet = this.sendMessage(email, SmsType.CHANGE_PWD);
        return buildWebApiResponse(SUCCESS, smsCodeGet);
    }


    /**
     * 发送修改邮箱验证码
     * @return app api response
     */
    @RequestMapping(value = CHANGE_EMAIL_SMS_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sendChangeEmailCode(@RequestBody ChangeEmailReq req) throws Exception {
        LOG.dStart(this, "发送修改密码验证码");
        Long userId = WebTokenValidate.validateToken(req);
        User user = otc.userWebFacade.selectByIdForException(userId);
        String email;
        if(StringUtils.equals("1",req.getStepType())){
            email = user.getEmailAddress();
        }else{
            email = req.getEmail();
        }
        if (StringUtils.isBlank(email)) {
            throw new WebBizException("邮箱地址不能为空");
        }
        if(StringUtils.isBlank(user.getEmailAddress())){
            throw new WebBizException("尚未设置邮箱");
        }
        SmsCodeGet smsCodeGet = this.sendMessage(email, SmsType.CHANGE_EMAIL);

        return buildWebApiResponse(SUCCESS, smsCodeGet);
    }


    /**
     * 发送验证码
     * @return app api response
     */
    @RequestMapping(value = WEB_SEND_EMAILCODE_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sendEamilCode(@RequestBody SendMailCodeReq req) throws Exception {
        LOG.dStart(this, "发送邮箱验证码");
        Long userId = WebTokenValidate.validateToken(req);
        User user = otc.userWebFacade.selectByIdForException(userId);
        String email = user.getEmailAddress();
        String type = req.getMailType();
        if (StringUtils.isBlank(email)) {
            throw new WebBizException("邮箱地址不能为空");
        }
        if(!SmsType.validateType(type)){
            throw new WebBizException("验证码类型不正确");
        }
        SmsType smsType = SmsType.getSmsTypeByType(type);
        if(smsType == null){
            throw new WebBizException("验证码类型不存在");
        }
        SmsCodeGet smsCodeGet = this.sendMessage(email, smsType);
        return buildWebApiResponse(SUCCESS, smsCodeGet);
    }


    /**
     * 发送验证短信公共部分
     *
     * @param email
     * @param smsType
     * @return
     * @throws Exception
     */
    private SmsCodeGet sendMessage(String email, SmsType smsType) throws Exception {
        if (StringUtils.isBlank(email)) {
            throw new WebBizException("邮箱地址为空");
        }
        if (smsType == null) {
            throw new WebBizException("验证类型为空");
        }
        Long allSendLimit = this.getSendMaxForAll();
        if (allSendLimit > 0) {
            int allSendTimes = this.getAllSendTimes(email);
            if (allSendLimit < allSendTimes) {
                throw new WebBizException("验证码总数已超出限制");
            }
        }
        SmsCodeGet smsCodeGet = SmsVerifyUtils.getVerifyCode(email, smsType, 50L);
        LOG.d(this, smsCodeGet);
        if (smsCodeGet.isStatus()) {
            String ver_code =  smsCodeGet.getContent();
            String content = smsType.getVerTemplateCode().replace("{code}",ver_code);
            boolean sendResult = AzureEmailUtil.sendEmail(email,smsType.getDesc(),content);
            if (sendResult) {
                return smsCodeGet;
            }else{
                throw new WebBizException("网络异常，请稍后重试");
            }
        } else {
            throw new WebBizException(smsCodeGet.getContent());
        }
    }


    /**
     * 获取验证码发送总次数
     *
     * @param email
     * @return
     */
    private int getAllSendTimes(String email) throws Exception {
        List<SmsCodeBean> list = SmsVerifyUtils.getUserSmsRecordList(email, null);
        int account = 0;
        Date now = new Date();
        for (SmsCodeBean smsCodeBean : list) {
            if (DateUtils.isSameDay(now, smsCodeBean.getCreateDate())) {
                account = account + smsCodeBean.getCount();
            }
        }
        return account;
    }

    /**
     * 获取验证码单总数发送限制数
     */
    private Long getSendMaxForAll() throws Exception {
        return 10L;
    }



}
