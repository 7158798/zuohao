package com.otc.facade.user.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * Created by fenggq on 17-4-19.
 */
public class UserVo extends BasePageVo {

    private String email;

    private String loginName;

    private Date start;

    private Date end;

    private String realName;


    private String userStatus;

    private String kycStatus;

    private Long coinId;  //币种id

    private String operationType; //操作类型

    private String  orderType; //1 用户列表  2、kyc列表


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public  void setUserInfo(String userInfo){
        //会员信息
        if(StringUtils.isNotBlank(userInfo)){
            if(StringUtils.isNumeric(userInfo)){
                setId(Long.parseLong(userInfo));
            }else if(userInfo.contains("@")){
                setEmail(userInfo);
            }else{
                setRealName(userInfo);
            }
        }
    }


    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}

