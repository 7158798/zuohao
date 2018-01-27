package com.otc.api.console.ctrl.common;

import com.base.common.fileupload.client.AzureFileUploadUtils;
import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.base.BaseConsoleCtrl;

import com.otc.api.console.constants.WebApiCommon;
import com.otc.api.console.ctrl.common.request.TestTokenReq;
import com.otc.api.console.ctrl.common.response.UploadFileResp;
import com.otc.api.console.utils.ConsoleTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.utils.enums.FileUploadType;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fenggq on 17-5-4.
 */
@Controller
public class commonCtrl extends BaseConsoleCtrl implements WebApiCommon {

    /**
     * 上传文件
     * @param request
     * @param file
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value=UPLOAD_FILE_API,method = RequestMethod.POST)
    public WebApiResponse uploadFile(HttpServletRequest request
            , @RequestParam CommonsMultipartFile file, String code, String fileToken) throws Exception {
        LOG.dStart(this,"开始上传文件，类型+"+code);
        if(file == null || file.isEmpty()) {
            throw new BizException("上传文件为空");
        }

        if(StringUtils.isBlank(code)) {
            throw new BizException("上传类型不能为空");
        }

        if(StringUtils.isBlank(fileToken)) {
            throw new BizException("上传fileToken不能为空");
        }

        String resultUrl = AzureFileUploadUtils.getFileUploadObject().fileUpload(file, FileUploadType.getObjectByCode(code).getConfigReq());
        if(StringUtils.isBlank(resultUrl)){
            throw new BizException("文件上传失败");
        }
        UploadFileResp resp = new UploadFileResp();
        resp.setUrl(resultUrl);
        LOG.dEnd(this,"上传文件完成，返回url:"+resultUrl);
        CacheHelper.saveObj(fileToken, resultUrl, TimeConstant.ONE_HOUR_UNIT_MILLISECONDS);
        return buildWebApiResponse(SUCCESS, resp);
    }

    @ResponseBody
    @RequestMapping(value = GET_UPLOAD_IMG_URL, method = RequestMethod.GET)
    public WebApiResponse uploadFileNew(@PathVariable String queryJson) throws Exception {
        UploadFileResp resp = new UploadFileResp();
        TestTokenReq req = JsonHelper.jsonStr2Obj(queryJson, TestTokenReq.class);
        if(req == null || StringUtils.isBlank(req.getUid())){
            LOG.d(this, "参数为空");
            resp.setUrl("-2");
            return buildWebApiResponse(SUCCESS, resp);
        }
        String fileToken = req.getUid();

        String resultUrl = "" ;

        try {
            resultUrl = CacheHelper.getObj(fileToken);
        }catch (Exception e) {
            LOG.e(this,"fileUpload读取redis数据异常", e);
            resp.setUrl("-1");
        }


        if(StringUtils.isNotBlank(resultUrl)) {
            resp.setUrl(resultUrl);
        }else{
            resp.setUrl("-1");
        }
        return buildWebApiResponse(SUCCESS, resp);
    }



}
