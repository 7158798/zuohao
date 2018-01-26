package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.SendMailTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.AppEmailReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fuser;
import com.ruizton.util.Constant;
import com.ruizton.util.ConstantKeys;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 邮箱
 * Created by zygong on 17-3-30.
 */
@Controller
public class AppEmailCtrl extends BaseController implements ApiConstants{

    /**
     * 绑定邮箱
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_EMAIL_ADDEMAIL, method = RequestMethod.POST, produces = JsonEncode)
    public String saveFeedback(@RequestBody AppEmailReq req) {
        JSONObject jsonObject = new JSONObject();
        try {
            String loginToken = req.getLoginToken();
            if(StringUtils.isBlank(loginToken) || StringUtils.isBlank(req.getEmail()) || StringUtils.isBlank(req.getVerificationCode()) || null == req.getType()){
                LOG.w(this, "参数为空");
                return ApiConstant.getRequestError();
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            if (StringUtils.isNotBlank(user.getFemail())) {
                return ApiConstant.getRequestError("用户已绑定邮箱");
            }

            // 验证验证码是否正确
            boolean flag = this.validateMailCode(getIpAddr(request), req.getEmail(), req.getType(), req.getVerificationCode());
            if(!flag){
                return ApiConstant.getRequestError("验证码错误");
            }
            user.setFemail(req.getEmail());
            user.setFisMailValidate(true);
            this.userService.updateObj(user);

        } catch (Exception e) {
            LOG.e(this, "绑定邮箱", e);
            return ApiConstant.getRequestError("操作失败");
        }
        return ApiConstant.getSuccessResp();
    }

    /**
     * 发送邮箱
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_EMAIL_SENDEMAIL, method = RequestMethod.POST, produces = JsonEncode)
    public String sendEmail(@RequestBody AppEmailReq req) {
        try {
            String loginToken = req.getLoginToken();
            Integer type = req.getType();
            String email = req.getEmail();
            if (StringUtils.isBlank(loginToken) || StringUtils.isBlank(email) || null == type) {
                LOG.w(this, "参数为空");
                return ApiConstant.getRequestError();
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            String ip = getIpAddr(request);

            if (type <= SendMailTypeEnum.BEGIN || type >= SendMailTypeEnum.END) {
                return ApiConstant.getRequestError("非法提交");
            }
            if (email.matches(Constant.EmailReg) == false) {
                return ApiConstant.getRequestError("邮箱格式错误");
            }
            boolean flag = this.frontUserService.isEmailExists(email);
            if (type == SendMailTypeEnum.FindPassword) {
                if (flag == false) {
                    return ApiConstant.getRequestError("邮箱不存在");
                }
            }
            if (type == SendMailTypeEnum.RegMail || type == SendMailTypeEnum.ValidateMail) {
                if (flag == true) {
                    return ApiConstant.getRequestError("邮箱已经存在");
                }
            }
            boolean isSuccess = SendMail(ip, email, type, ConstantKeys.regmailContent, "绑定邮箱");
            if(!isSuccess){
                return ApiConstant.getRequestError("发送失败");
            }
        } catch (Exception e) {
            LOG.e(this, "发送邮箱", e);
            return ApiConstant.getRequestError("操作失败");
        }
        return ApiConstant.getSuccessResp();
    }
}