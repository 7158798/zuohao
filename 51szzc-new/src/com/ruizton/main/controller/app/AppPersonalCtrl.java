package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.FileUploadTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.PersonalReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvalidatekyc;
import com.ruizton.main.model.vo.AppUserVo;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 个人信息操作
 * Created by zygong on 17-3-28.
 */
@Controller
public class AppPersonalCtrl extends BaseController implements ApiConstants {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyyMMddHHmmsss");

    /**
     * 头像上传
     * @param req
     * @return
     */
    @RequestMapping(value = APP_PERSONAL_UPLOADAVATAR, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String uploadAvatar(@RequestBody PersonalReq req) {

        String resultUrl = "";  //绝对路径
        String loginToken = req.getLoginToken();
        String imgStr = req.getImgStr();
        if(!checkParam(loginToken, imgStr)){
            LOG.w(this, "参数错误");
            return ApiConstant.getRequestError();
        }
        try {
            ByteArrayInputStream stream = null;
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(imgStr);
            stream = new ByteArrayInputStream(bytes);

            //*/WebRoot/WEB-INF/classes/upload/avatar
            String realPath = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("/WEB-INF/classes", "") + FileUploadTypeEnum.getObjectByCode("5").getFolderName();
            // 对 Windows 下获取 物理路径 做 特殊处理
            if("\\".equals(File.separator)){
                realPath = realPath.substring(1).replaceAll("/", "\\\\");
            }
            String time = simpleDateFormat.format(new Date());
            String fileName = Utils.getRandomImageName() + "." + "png";
            LOG.i(this, "common upload file name：" + fileName);
            boolean flag = Utils.saveFile(realPath, fileName, stream);
            LOG.i(this, "common upload file result：" + flag);
            if (flag) {
                resultUrl = OSSPostObject.URL + "/" + fileName;
                user.setFavatarUrl(resultUrl);
                this.userService.updateObj(user);
            }else{
                return ApiConstant.getRequestError("上传图片失败");
            }

        } catch (Exception e) {
            LOG.e(this, "上传图片失败", e);
            return ApiConstant.getRequestError("上传图片失败");
        }
        return ApiConstant.getSuccessResp();
    }


    /**
     * 获取用户信息
     * @param loginToken
     * @return
     */
    @RequestMapping(value = APP_PERSONAL_GETUSERINFO, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getUserInfo(String loginToken) {
        AppUserVo userVo = null;
        JSONObject jsonObject = null;
        if (!checkParam(loginToken)) {
            LOG.w(this, "参数错误");
            return ApiConstant.getRequestError();
        }
        try {
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

//            Fuser user = this.realTimeData.getAppFuser(loginToken);
            Fuser user = this.realTimeData.getAppSimpleUserScore(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            jsonObject = new JSONObject();
            userVo = new AppUserVo();
            userVo.setFid(user.getFid());
            userVo.setFisMailValidate(user.getFisMailValidate());
            userVo.setFavatarUrl(user.getFavatarUrl());
            userVo.setFisTelValidate(user.getFisTelValidate());
            userVo.setPhone(user.getFtelephone());
            userVo.setMail(user.getFemail());
            userVo.setVipLevel(user.getFscore().getFlevel());
            userVo.setRealName(user.getFrealName());
            userVo.setLoginName(user.getFloginName());
            userVo.setFisGoogleBind(user.getFgoogleBind());
            // 判断是否有交易密码
            if(checkParam(user.getFtradePassword())){
                userVo.setTradePassword(true);
            }else {
                userVo.setTradePassword(false);
            }
            userVo.setStatus(user.getFstatus());
            userVo.setKycLevel(user.getKyclevel());

            Fvalidatekyc kycinfo = this.fvalidateKycService.findByUserId(user.getFid());
            if(kycinfo != null){
                userVo.setRejection(kycinfo.getCancelReason());
            }
            jsonObject.accumulate("userInfo", userVo);
        } catch (Exception e) {
            LOG.e(this, "获取用户信息失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }
}
