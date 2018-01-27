package com.base.common.sms.impl.request;

import com.base.common.sms.facade.request.SmsBeanReq;

/**
 * @author luwei
 * @Date 16-9-29 上午10:55
 */
public class DkBeanReq extends SmsBeanReq{

    /**发送内容**/
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
