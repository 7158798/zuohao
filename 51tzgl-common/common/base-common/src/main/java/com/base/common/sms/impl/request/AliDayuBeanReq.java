package com.base.common.sms.impl.request;

import com.base.common.sms.facade.request.SmsBeanReq;

/**
 * @author luwei
 * @Date 16-9-29 上午11:34
 */
public class AliDayuBeanReq extends SmsBeanReq{

    /**短信模板code**/
    private String templateCode;

    /**模板中变量的参数值,json格式**/
    private String paramJsonStr;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getParamJsonStr() {
        return paramJsonStr;
    }

    public void setParamJsonStr(String paramJsonStr) {
        this.paramJsonStr = paramJsonStr;
    }
}
