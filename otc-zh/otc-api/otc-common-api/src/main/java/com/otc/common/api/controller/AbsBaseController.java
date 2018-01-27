package com.otc.common.api.controller;

import com.jucaifu.common.constants.WebConstant;
import com.jucaifu.common.pojo.vo.BasePageVo;
import com.jucaifu.common.pojo.vo.ISeqNo;
import com.jucaifu.common.util.HttpServletHelper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.UrlHelper;
import com.jucaifu.common.util.page.Page;
import com.otc.common.api.packet.web.WebApiResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * AbsBaseController
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/27.
 */
public abstract class AbsBaseController {

    /**
     * 可以使用 Serlvet 原生的 API 作为目标方法的参数 具体支持以下类型
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale InputStream
     * OutputStream
     * Reader
     * Writer
     */
//////////////////////////////
////封装Controller的公共部分
//////////////////////////////


    /**
     * Build web api response.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @param body    the body
     * @return the web api response
     */
    public static <T> WebApiResponse buildWebApiResponse(String message, T body) {

        return new WebApiResponse<T>(message, body);
    }

    /**
     * Build web api page response.
     *
     * @param <Result> the type parameter
     * @param <Vo>     the type parameter
     * @param baseVo   the base vo
     * @param list     the list
     * @return the web api response
     */
    public <Result, Vo extends BasePageVo> WebApiResponse buildWebApiPageResponse(Vo baseVo, List<Result> list) {

        Page<Result> body_page = baseVo.getPage();

        //////////////////////////////
        //// 自动构建序号
        //////////////////////////////
        int start = body_page.getStart()+1;
        for (Result result : list) {
            if (result instanceof ISeqNo) {
                ((ISeqNo) result).setSeqNo(start++);
            }
        }
        body_page.setResult(list);


        return buildWebApiResponse(WebConstant.SUCCESS, body_page);
    }


    /**
     * Add response header.
     *
     * @param response the response
     * @param key      the key
     * @param value    the value
     */
    public static void addResponseHeader(HttpServletResponse response, String key, String value) {
        if (response != null && key != null && value != null) {
            response.addHeader(key, value);
        }
    }

    /**
     * Add base response header.
     *
     * @param response the response
     */
    public static void addBaseResponseHeader(HttpServletResponse response) {

        HttpServletHelper.addBaseResponseHeader(response);

//        if (response!=null){
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods","POST");
////            response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type");
//            response.setHeader("Access-Control-Allow-Headers", "*");
//        }

//        addResponseHeader(response, "Access-Control-Allow-Origin", "*");
//        addResponseHeader(response, "Access-Control-Allow-Headers", "X-Requested-With");
//        addResponseHeader(response, "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//        addResponseHeader(response, "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    }


    /**
     * Encode json str 2 obj.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param clz     the clz
     * @return the t
     */
    public static final <T> T encodeJsonStr2Obj(String jsonStr, Class<T> clz) {
        try {
            jsonStr = UrlHelper.decode(jsonStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JsonHelper.jsonStr2Obj(jsonStr, clz);
    }
}
