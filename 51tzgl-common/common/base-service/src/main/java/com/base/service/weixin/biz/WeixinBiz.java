package com.base.service.weixin.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.weixin.enums.WeixinUrlType;
import com.base.facade.weixin.pojo.bean.WeixinRedPacket;
import com.base.facade.weixin.pojo.po.WeixinLog;
import com.base.facade.weixin.pojo.vo.WeixinLogVo;
import com.base.facade.weixin.response.WeixinResponse;
import com.base.facade.weixin.utils.WeixinXMLUtil;
import com.base.service.weixin.dao.WeixinLogMapper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.*;

/**
 * Created by lx on 16-12-22.
 */
@Service
public class WeixinBiz extends AbsBaseBiz<WeixinLog,WeixinLogVo,WeixinLogMapper> {

    @Autowired
    private WeixinLogMapper weixinLogMapper;
    @Override
    public WeixinLogMapper getDefaultMapper() {
        return null;
    }

    /**
     * 发送红包前的交易
     * @param redPack
     */
    private void redPackFilter(WeixinRedPacket redPack){
        if (StringUtils.isEmpty(redPack.getMch_id())){
            throw new BaseCommonBizException("商户号不能为空");
        }
        if (StringUtils.isEmpty(redPack.getWxappid())){
            throw new BaseCommonBizException("公众账号appid不能为空");
        }
        if (StringUtils.isEmpty(redPack.getSend_name())){
            throw new BaseCommonBizException("商户名称不能为空");
        }
        if (StringUtils.isEmpty(redPack.getRe_openid())){
            throw new BaseCommonBizException("用户openid不能为空");
        }
        if (redPack.getTotal_amount() == 0){
            throw new BaseCommonBizException("付款金额不能为零");
        }
        // 发放人数
        redPack.setTotal_num(1);
        if (StringUtils.isEmpty(redPack.getWishing())){
            throw new BaseCommonBizException("红包祝福语不能为空");
        }
        if (StringUtils.isEmpty(redPack.getClient_ip())){
            throw new BaseCommonBizException("ip地址不能为空");
        }
        if (StringUtils.isEmpty(redPack.getAct_name())){
            throw new BaseCommonBizException("活动名称不能为空");
        }
        if (StringUtils.isEmpty(redPack.getRemark())){
            throw new BaseCommonBizException("备注不能为空");
        }

    }

    /**
     * 加载证书
     * @return
     */
    private SSLConnectionSocketFactory loadSslsf(){
        SSLConnectionSocketFactory sslsf = null;
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("D:/10016225.p12"));
            try {
                keyStore.load(instream, "10016225".toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "10016225".toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        }catch (Exception e){
            LOG.e(this,e.getMessage(),e);
        }

        return sslsf;
    }

    /**
     * 获取随机串
     * @return
     */
    private String getUUID() {
        //获取UUID并转化为String对象
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");
        return uuid;
    }

    /**
     * 获取订单号
     * @param mch_id　商号号
     * @return
     */
    private String getOrderId(String mch_id){
        StringBuffer orderId = new StringBuffer();
        orderId.append(mch_id);
        Date date = new Date();
        orderId.append(DateHelper.date2String(date, DateHelper.DateFormatType.YearMonthDay_Log));
        String str = DateHelper.date2String(date, DateHelper.DateFormatType.YearMonthDayHourMinuteSecond);
        str = str.substring(4);
        orderId.append(str);
        return orderId.toString();
    }

    /**
     * 获取签名
     * @param params    请求参数
     * @param key       商户的key
     * @return
     */
    private String getSignValue(Map<String,String> params,String key){
        StringBuffer buffer = new StringBuffer();

        for(Map.Entry<String,String> entry: params.entrySet()){
            buffer.append(entry.getKey() + "=" + entry.getValue());
        }
        buffer.append("&key=");
        buffer.append(key);
        // 得到签名
        return DigestUtils.md5Hex(buffer.toString()).toUpperCase();

    }

    /**
     * 组装请求的按数
     * @param redPack   发送红包对象
     * @return
     */
    private Map<String, String> getRequestParams(WeixinRedPacket redPack){
        Map<String, String> map = new TreeMap<>();
        map.put("nonce_str",getUUID());
        map.put("mch_billno",getOrderId(redPack.getMch_id()));
        map.put("mch_id",redPack.getMch_id());
        map.put("wxappid",redPack.getWxappid());
        map.put("send_name",redPack.getSend_name());
        map.put("re_openid",redPack.getRe_openid());
        map.put("total_amount",String.valueOf(redPack.getTotal_amount()));
        map.put("total_num",String.valueOf(redPack.getTotal_num()));
        map.put("wishing",redPack.getWishing());
        map.put("client_ip",redPack.getClient_ip());
        map.put("act_name",redPack.getAct_name());
        map.put("remark",redPack.getRemark());
        map.put("sign",getSignValue(map,redPack.getKey()));
        return map;
    }

    /**
     * 发送商户红包
     *
     * @param redPack the red pack
     * @return the weixin response
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WeixinResponse sendRedPacket(WeixinRedPacket redPack){
        WeixinResponse resp = new WeixinResponse();
        try {
            redPackFilter(redPack);
            // 加载证书
            SSLConnectionSocketFactory sslsf = loadSslsf();
            /*if (sslsf == null){
                throw new BaseCommonBizException("加载证书失败");
            }*/
            Map<String, String> requestParams = getRequestParams(redPack);
            String response = null;
            WeixinLog weixinLog = new WeixinLog();
            String errorMsg = null;
            weixinLog.setStatus(Boolean.FALSE);
            weixinLog.setRequestUrl(WeixinUrlType.send_redpack.getUrl());
            weixinLog.setDescription(WeixinUrlType.send_redpack.getDesc());
            try {
                response = HttpClientHelper.post(weixinLog.getRequestUrl(), WeixinXMLUtil.getWeixinRequestXml(requestParams), sslsf);
                LOG.d(this,"接口响应内容" + response);
            } catch (IOException e) {
                LOG.e(this,e.getMessage(),e);
                weixinLog.setStatus(Boolean.FALSE);
                errorMsg = "请求发送红包接口失败,红包发送失败";
            }
            weixinLog.setCreateDate(new Date());
            weixinLog.setRequest(JsonHelper.obj2JsonStr(requestParams));
            Map<String,String> resultMap = null;
            if (StringUtils.isNotEmpty(response)){
                try {
                    resultMap = WeixinXMLUtil.doXMLParse(response);
                } catch (Exception e) {
                    LOG.e(this,e.getMessage(),e);
                    errorMsg = "解析响应结果失败";
                }
            }
            if (resultMap != null){
                weixinLog.setResponse(JsonHelper.obj2JsonStr(resultMap));
                // 返回状态码
                String return_code = resultMap.get("return_code");
                if ("SUCCESS".equals(return_code)){
                    // 业务结果
                    String result_code = resultMap.get("result_code");
                    if ("SUCCESS".equals(result_code)){
                        weixinLog.setStatus(Boolean.TRUE);
                    } else {
                        errorMsg = resultMap.get("err_code_des");
                    }
                } else {
                    errorMsg = resultMap.get("return_msg");
                }
            }
            weixinLogMapper.insert(weixinLog);
            resp.setIsSuccess(weixinLog.getStatus());
            resp.setErrorMsg(errorMsg);
        } catch (Exception ex){
            LOG.e(this,ex.getMessage(),ex);
            // 发送失败
            resp.setIsSuccess(Boolean.FALSE);
            String errorMsg = "发送商户红包失败";
            if (ex instanceof BaseCommonBizException){
                errorMsg = ex.getMessage();
            }
            resp.setErrorMsg(errorMsg);
        }
        return resp;
    }

}
