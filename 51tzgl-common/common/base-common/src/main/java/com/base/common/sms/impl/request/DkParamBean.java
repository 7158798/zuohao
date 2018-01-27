package com.base.common.sms.impl.request;

import com.base.common.sms.facade.request.SmsParamBean;

/**
 * @author luwei
 * @Date 16-9-30 上午10:20
 */
public class DkParamBean extends SmsParamBean {

    /**账户**/
    private String account;

    /**密码**/
    private String pwd;

    /**启用状态**/
    private Boolean disabled;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
