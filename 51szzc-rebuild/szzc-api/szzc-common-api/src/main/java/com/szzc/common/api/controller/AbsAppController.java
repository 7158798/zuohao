package com.szzc.common.api.controller;

import com.alibaba.fastjson.JSONException;
import com.szzc.common.api.exceptions.AppForceUpdateException;
import com.szzc.common.api.exceptions.BuildAppResponseException;
import com.szzc.common.api.exceptions.ParseAppRequestException;
import com.szzc.common.api.exceptions.SignException;
import com.szzc.common.api.exceptions.global.ErrorResponse;
import com.szzc.common.api.key.SecureKeyManager;
import com.szzc.common.api.packet.app.request.AppApiRequest;
import com.szzc.common.api.packet.app.request.AppRequestHeader;
import com.szzc.common.api.packet.app.response.AppApiResponse;
import com.szzc.common.api.packet.app.response.AppResponseHeader;
import com.jucaifu.common.constants.WebConstant;
import com.jucaifu.common.enums.EnumHttpStatusCode;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.pojo.vo.BasePageVo;
import com.jucaifu.common.pojo.vo.ISeqNo;
import com.jucaifu.common.security.DESedeHelper;
import com.jucaifu.common.security.MD5Helper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.UrlHelper;
import com.jucaifu.common.util.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * AbsAppController
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/22.
 */
public class AbsAppController {

    //@Resource(name = "mobileVersionAppFacade")
    //private MobileVersionAppFacade mobileVersionAppFacade;

    /**
     * Is open security mode.
     */

    private static ThreadLocal<Boolean> isOpenSecurityMode = new ThreadLocal<>();

    /**
     * 微站请求设备类型
     */
    private static final String WM_DEVICE_TYPE = "5";

    /**
     * Validate app version.
     *
     * @param header the header
     * @throws AppForceUpdateException the app force update exception
     */
    protected void validateAppVersion(AppRequestHeader header) throws AppForceUpdateException {

        //TODO:从redis中直接获取当前的版本号
        boolean isExistFocusUpdate = true;
        if (header != null && header.getDeviceInfo() != null) {
           /* isExistFocusUpdate = mobileVersionAppFacade.isForcedUpdate(header.getAppVersion(), header.getAppName(),
                    header.getDeviceInfo().getDeviceType());*/
            isExistFocusUpdate = false;
        }

        if (isExistFocusUpdate) {
            LOG.d(this, "------->存在强制更新");
            throw new AppForceUpdateException(header);
        } else {
            LOG.d(this, "------->不存在更新");
        }
    }

    /**
     * Parse app api request.
     *
     * @param key        the key
     * @param apiRequest the api request
     * @param reqBodyClz the req body clz
     * @return the app api request
     * @throws ParseAppRequestException the parse app request exception
     * @throws ParseAppRequestException the parse app request exception
     */
    protected static AppApiRequest parseAppApiRequest(String key, AppApiRequest apiRequest, Class<?> reqBodyClz)
            throws ParseAppRequestException, SignException {

//        AppRequestHeader header = apiRequest.getHeader();

        //客户端请求的报文密文
        String ciphertextBody = apiRequest.getBody();
        LOG.d(key, "App请求密文body : " + ciphertextBody);

        //明文body的签名
        String sign = apiRequest.getSign();
        LOG.d(key, "App请求明文sign : " + sign);

        //1.解密body密文
        String bodyJsonStr = null;
        //2.验证报文完整性
        boolean validateSignResult = false;

        if (ciphertextBody != null && sign != null) {

            try {
                if (getIsOpenSecurityMode()) {
                    bodyJsonStr = DESedeHelper.decrypt(key, ciphertextBody);
                } else {
                    bodyJsonStr = ciphertextBody;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ParseAppRequestException();
            }
            LOG.d(key, "服务器解析请求得到的明文: " + bodyJsonStr);


            validateSignResult = MD5Helper.validateSign(bodyJsonStr, sign);

        } else if (ciphertextBody == null && sign == null) {

            validateSignResult = true;
        }

        if (validateSignResult) {

            //解析得到请求对象
            Object body = JsonHelper.jsonStr2Obj(bodyJsonStr, reqBodyClz);
            apiRequest.setBodyObj(body);

            apiRequest.setBody(null);
            apiRequest.setSign(null);
            apiRequest.setValidateSignResult(true);

            LOG.d(key, "App请求的明文数据:");
            LOG.d(key, body);

        } else {
            throw new SignException();
        }

        return apiRequest;
    }


    /**
     * Build app api response.
     *
     * @param <T>      the type parameter
     * @param key      the key
     * @param respHead the resp head
     * @param message  the message
     * @param respBody the resp body
     * @return the app api response
     * @throws BuildAppResponseException the build app response exception
     */
    protected static <T> AppApiResponse buildAppApiResponse(String key, AppResponseHeader respHead, String message, T
            respBody) throws BuildAppResponseException {

        String respBodyJsonStr = null;
        String respSign = null;

        if (respBody != null) {
            respBodyJsonStr = JsonHelper.obj2JsonStrWithDateFormat(respBody);
            respSign = MD5Helper.encodeMD5Hex(respBodyJsonStr);
        }

        AppApiResponse apiResponse = new AppApiResponse();

        if (respBodyJsonStr != null) {
            try {
                if (getIsOpenSecurityMode()) {
                    apiResponse.setBody(DESedeHelper.encrypt(key, respBodyJsonStr));
                } else {
                    apiResponse.setBody(respBodyJsonStr);
                }
            } catch (Exception e) {
                throw new BuildAppResponseException(e);
            }
        }
        apiResponse.setSign(respSign);

        apiResponse.setHeader(respHead);

        apiResponse.setCode(HttpStatus.OK.toString());
        apiResponse.setMessage(message);
        isOpenSecurityMode.remove();
        return apiResponse;
    }

    /**
     * Build app file upload api response.
     *
     * @param filePath the file path
     * @return the app api response
     */
    protected static AppApiResponse buildAppFileUploadApiResponse(String filePath) {
        return AppApiResponse.buildForAppFileUpload(EnumHttpStatusCode.CODE_200.getValue().toString(), WebConstant
                .SUCCESS, filePath);
    }


    /**
     * Build app response header.
     *
     * @param reqHeader the req header
     * @return the app response header
     */
    protected static AppResponseHeader buildAppResponseHeader(AppRequestHeader reqHeader) {

        AppResponseHeader respHead = new AppResponseHeader();
        respHead.setSeqNo(reqHeader.getSeqNo());
        respHead.setReqTimeStamp(reqHeader.getReqTimeStamp());

        respHead.setRespTimeStamp(System.currentTimeMillis());

        return respHead;
    }


    /**
     * Parse app api request. <p> 说明: 包含了报文的解密和验签流程
     *
     * @param apiRequest the api request
     * @param reqBodyClz the req body clz
     * @return the app api request
     * @throws ParseAppRequestException the parse app request exception
     * @throws ParseAppRequestException the parse app request exception
     */
    public AppApiRequest parseAppApiRequest(AppApiRequest apiRequest, Class<?> reqBodyClz) throws
            ParseAppRequestException, AppForceUpdateException {

        // 微站请求不进行版本校验，报文解密
        if (StringUtils.equals(apiRequest.getHeader().getDeviceInfo().getDeviceType(), WM_DEVICE_TYPE)) {
            Object body = JsonHelper.jsonStr2Obj(apiRequest.getBody(), reqBodyClz);
            apiRequest.setBodyObj(body);
            isOpenSecurityMode.set(Boolean.FALSE);
            return apiRequest;
        } else {
            isOpenSecurityMode.set(Boolean.TRUE);
        }

        validateAppVersion(apiRequest.getHeader());

        try {
            String key = SecureKeyManager.getCommonKey();

            LOG.d(this, "App Request解析前：");
            LOG.d(this, apiRequest);

            if (reqBodyClz != null) {
                parseAppApiRequest(key, apiRequest, reqBodyClz);
            } else {
                apiRequest.setValidateSignResult(true);
            }

            LOG.d(this, "App Request解析后：");
            LOG.d(this, apiRequest);

        } catch (SignException ex) {

            apiRequest.setValidateSignResult(false);
            LOG.d(this, "App Request报文不完整验签失败.");
            ex.printStackTrace();

        } catch (ParseAppRequestException e) {

            apiRequest.setValidateSignResult(false);
            LOG.d(this, "App Request报文解析失败.");
            e.printStackTrace();

        }

        if (!apiRequest.getValidateSignResult()) {
            throw new ParseAppRequestException();
        }

        return apiRequest;
    }

    /**
     * Build app api response.
     *
     * @param <T>       the type parameter
     * @param reqHeader the req header
     * @param message   the message
     * @param respBody  the resp body
     * @return the app api response
     * @throws BuildAppResponseException the build app response exception
     */
    public static <T> AppApiResponse buildAppApiResponse(AppRequestHeader reqHeader, String message, T respBody)
            throws BuildAppResponseException {

        String key = SecureKeyManager.getCommonKey();

        AppResponseHeader respHeader = buildAppResponseHeader(reqHeader);

        return buildAppApiResponse(key, respHeader, message, respBody);
    }

    /**
     * Build app api response.
     *
     * @param <T>       the type parameter
     * @param reqHeader the req header
     * @param respBody  the resp body
     * @return the app api response
     * @throws BuildAppResponseException the build app response exception
     */
    public static <T> AppApiResponse buildAppApiResponse(AppRequestHeader reqHeader, T respBody) throws
            BuildAppResponseException {
        return buildAppApiResponse(reqHeader, WebConstant.SUCCESS, respBody);
    }

    /**
     * Build app api page response.
     *
     * @param <Result>  the type parameter
     * @param <Vo>      the type parameter
     * @param reqHeader the req header
     * @param baseVo    the base vo
     * @param list      the list
     * @return the app api response
     * @throws BuildAppResponseException the build app response exception
     */
    public static <Result, Vo extends BasePageVo> AppApiResponse buildAppApiPageResponse(AppRequestHeader reqHeader,
                                                                                         Vo baseVo, List<Result>
                                                                                                 list) throws
            BuildAppResponseException {

        String key = SecureKeyManager.getCommonKey();
        AppResponseHeader respHeader = buildAppResponseHeader(reqHeader);

        Page<Result> body_page = baseVo.getPage();

        //////////////////////////////
        //// 自动构建序号
        //////////////////////////////
        int start = body_page.getStart() + 1;
        for (Result result : list) {
            if (result instanceof ISeqNo) {
                ((ISeqNo) result).setSeqNo(start++);
            }
        }
        body_page.setResult(list);

        LOG.d(AbsAppController.class, body_page);

        return buildAppApiResponse(key, respHeader, WebConstant.SUCCESS, body_page);
    }

    /**
     * Error response.
     *
     * @param request  the request
     * @param response the response
     * @param ex       the ex
     * @return the error response
     */
    @ExceptionHandler(ParseAppRequestException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response,
                                       ParseAppRequestException ex) {

        LOG.dTag(this, "Parse App Request Exception");

        return new ErrorResponse(GeneralExceptionType.PARSE_APP_REQUEST_EXCEPTION, ex);
    }

    /**
     * Error response.
     *
     * @param request  the request
     * @param response the response
     * @param ex       the ex
     * @return the error response
     */
    @ExceptionHandler(JSONException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, JSONException ex) {

        LOG.dTag(this, "Parse App Request Json Exception");

        return new ErrorResponse(GeneralExceptionType.BUILD_APP_JSON_EXCEPTION, ex);
    }

    /**
     * Error response.
     *
     * @param request  the request
     * @param response the response
     * @param ex       the ex
     * @return the error response
     */
    @ExceptionHandler(BuildAppResponseException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response,
                                       BuildAppResponseException ex) {

        LOG.dTag(this, "Build App Response Exception");

        return new ErrorResponse(GeneralExceptionType.PARSE_APP_REQUEST_EXCEPTION, ex);
    }

    /**
     * Encode json str 2 obj.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param clz     the clz
     * @return the t
     */
    public static <T> T encodeJsonStr2Obj(String jsonStr, Class<T> clz) {
        try {
            jsonStr = UrlHelper.decode(jsonStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JsonHelper.jsonStr2Obj(jsonStr, clz);
    }

    /**
     * Encode hex str 2 obj.
     *
     * @param <T>    the type parameter
     * @param hexStr the hex str
     * @param clz    the clz
     * @return the t
     * @throws ParseAppRequestException the parse app request exception
     */
    public static <T> T encodeHexStr2Obj(String hexStr, Class<T> clz) throws ParseAppRequestException {

        try {
            String jsonStr = UrlHelper.dencodeHexStr(hexStr);
            return JsonHelper.jsonStr2Obj(jsonStr, clz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseAppRequestException();
        }

    }

    private static boolean getIsOpenSecurityMode() {
        return isOpenSecurityMode.get() == null || isOpenSecurityMode.get();
    }

}
