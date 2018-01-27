package com.base.common.push.facade.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxun on 16-9-26.
 */
public class PushMessageResp {

    // 推送结果
    private boolean result;
    // 消息
    private String msg;
    // 接口返回json内容
    private List<PushMessageDetail> body = new ArrayList<>();

    public PushMessageResp(){
        result = Boolean.TRUE;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PushMessageDetail> getBody() {
        return body;
    }

    public void setBody(List<PushMessageDetail> body) {
        this.body = body;
    }
}
