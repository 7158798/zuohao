package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.MessageTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.SecuritySettingReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fuser;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 安全设置
 * Created by zygong on 17-4-1.
 */
@Controller
public class AppSecuritySettingCtrl extends BaseController implements ApiConstants {

    /**
     * 重置交易密码(MessageTypeEnum 20)
     * @param req
     * @return
     */
    @RequestMapping(value = APP_SECURITYSETTING_RESETTRADEPWD, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String resetTradePwd(@RequestBody SecuritySettingReq req) {
        String loginToken = req.getLoginToken();
        String phone = req.getPhone();
        String idCard = req.getIdCard();
        String password = req.getPassword();
        String verificationCode = req.getVerificationCode();

        if (!checkParam(loginToken, verificationCode, idCard, password, phone)) {
            LOG.w(this, "参数错误");
            return ApiConstant.getRequestError("操作失败");
        }
        try {
            //验证是否登录
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser user = this.realTimeData.getAppFuser(loginToken);

            if (validateLoginPassword(password)) {
                return ApiConstant.getRequestError("密码格式不正确");
            }
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            if (!phone.equals(user.getFtelephone())) {
                return ApiConstant.getRequestError("手机号错误");
            }
            if (!idCard.equals(user.getFidentityNo())) {
                return ApiConstant.getRequestError("身份证号错误");
            }
            if (super.validateMessageCode(user, "86", user.getFtelephone(), MessageTypeEnum.RESET_TRADEPWD, verificationCode) == false) {
                return ApiConstant.getRequestError("短信验证码错误");
            }

            //交易
            user.setFtradePassword(Utils.MD5(password, user.getSalt()));
            this.frontUserService.updateFuser(user);
            this.validateMap.removeMessageMap(user.getFid() + "_" + MessageTypeEnum.RESET_TRADEPWD);
        } catch (Exception e) {
            LOG.e(this, "重置交易密码", e);
            return ApiConstant.getRequestError("重置交易密码");
        }
        return ApiConstant.getSuccessResp();
    }

    /**
     * 修改手机号(MessageTypeEnum 2：绑定手机   3：解绑手机)
     * @param req
     * @return
     */
    @RequestMapping(value = APP_SECURITYSETTING_CHANGEPHONE, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String changePhone(@RequestBody SecuritySettingReq req) {
        String loginToken = req.getLoginToken();
        String verificationCodeOrigin = req.getVerificationCodeOrigin();
        String verificationCodeNew = req.getVerificationCodeNew();
        String phoneOrigin = req.getPhoneOrigin();
        String phoneNew = req.getPhoneNew();
        if (!checkParam(loginToken, verificationCodeOrigin, phoneOrigin, verificationCodeNew, phoneNew)) {
            LOG.w(this, "参数错误");
            return ApiConstant.getRequestError("操作失败");
        }
        try {
            //验证是否登录
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            if (!phoneOrigin.equals(user.getFtelephone())) {
                return ApiConstant.getRequestError("原手机号错误");
            }
            // 验证原手机发送的验证码
            if (super.validateMessageCode(user, "86", user.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, verificationCodeOrigin) == false) {
                return ApiConstant.getRequestError("原手机短信验证码错误");
            }
            // 验证新手机发送的验证码
            if (super.validateMessageCode(user, "86", phoneNew, MessageTypeEnum.BANGDING_MOBILE, verificationCodeNew) == false) {
                return ApiConstant.getRequestError("新手机短信验证码错误");
            }

            // 如果登录名为手机号，同时修改登录名
            if(phoneOrigin.equals(user.getFloginName())){
                user.setFloginName(phoneNew);
            }
            user.setFtelephone(phoneNew);
            user.setFisMailValidate(true);
            this.frontUserService.updateFuser(user);
            this.validateMap.removeMessageMap(user.getFid() + "_" + MessageTypeEnum.JIEBANG_MOBILE);
            this.validateMap.removeMessageMap(user.getFid() + "_" + MessageTypeEnum.BANGDING_MOBILE);
        } catch (Exception e) {
            LOG.e(this, "修改手机号", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp();
    }


}
