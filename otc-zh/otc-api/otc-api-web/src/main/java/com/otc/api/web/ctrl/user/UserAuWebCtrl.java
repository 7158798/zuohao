package com.otc.api.web.ctrl.user;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiUser;
import com.otc.api.web.ctrl.user.request.UserKycPostReq;
import com.otc.api.web.ctrl.user.request.UserRealNameReq;
import com.otc.api.web.ctrl.user.response.UserAuthResp;
import com.otc.api.web.utils.WebTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.user.enums.IdentityTypeEnum;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserAuthentication;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by a123 on 17-4-25.
 */
@Controller
public class UserAuWebCtrl extends BaseWebCtrl implements WebApiUser {

    /**
     * 实名认证
     * @param req
     * @return
     */
    @RequestMapping(value = WEB_REALNAME_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse realName(@RequestBody UserRealNameReq req){
        LOG.d(this,"实名认证开始");
        Long userId = WebTokenValidate.validateToken(req);
        String idCardType = req.getIdCardType();
        String idCardNumber = req.getIdCardNumber();
        String realName = req.getRealName();

        if(StringUtils.isBlank(idCardType) ||
                StringUtils.isBlank(idCardNumber) ||
                StringUtils.isBlank(realName)){
            throw new BizException("请求参数不正确");
        }
        otc.userAuWebFacade.realNameAuth(userId,idCardNumber,idCardType,realName);
        //发送通知
        otc.messageWebFacade.sendMessage(userId, UserMessageConstantEnum.REALNAME_PASS,null,null);

        LOG.d(this,"实名认证结束");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * KYC认证
     * @param req
     * @return
     */
    @RequestMapping(value = WEB_KYC_POST_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse kycPost(@RequestBody UserKycPostReq req){
        LOG.d(this,"KYC认证开始");
        Long userId = WebTokenValidate.validateToken(req);

        String identityurlOn = req.getIdentityurlOn();
        String identityurlOff = req.getIdentityurlOff();
        String identityurlHold = req.getIdentityurlHold();

        if(StringUtils.isBlank(identityurlOn) ||
                StringUtils.isBlank(identityurlOff) ||
                StringUtils.isBlank(identityurlHold)){
            throw new BizException("请求参数不正确");
        }
        otc.userAuWebFacade.kycAuth(userId,identityurlOn,identityurlOff,identityurlHold);

        //发送通知
        otc.messageWebFacade.sendMessage(userId, UserMessageConstantEnum.KYC_POST,null,null);
        LOG.d(this,"KYC认证结束");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 获取实名认证信息
     * @return
     */
    @RequestMapping(value = WEB_GET_REALNAME_INFO, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getRealNameInfo(@PathVariable String queryJson){
        LOG.dStart(this,"获取实名认证信息");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);

        Long userId = WebTokenValidate.validateToken(req);
        UserAuthentication userAuth = otc.userAuWebFacade.getUserAuthentication(userId);
        if(userAuth == null){
            throw new BizException("用户未实名认证");
        }
        UserAuthResp resp = new UserAuthResp();
        resp.setPassportType(IdentityTypeEnum.getMap().get(userAuth.getPassportType()));
        resp.setPassportNo(userAuth.getPassportNo());
        resp.setRealName(userAuth.getRealName());

        LOG.dEnd(this,"获取实名认证信息");
        return buildWebApiResponse(SUCCESS, resp);
    }
}
