package com.jucaifu.common.pojo;

import com.jucaifu.common.validate.DataValidateRuleAnno;
import com.jucaifu.common.validate.ValidateType;

/**
 * TestUserPo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/18.
 */
public class TestUserPo {

    @DataValidateRuleAnno(emptyMSG = "用户名不能为空")
    private String name;

    @DataValidateRuleAnno(validateType = ValidateType.PHONE_NUMBER,needValidateWhenEmpty = false)
    private String mobileNo;

    @DataValidateRuleAnno(validateType = ValidateType.PAY_PWD)
    private String pwd;

    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
