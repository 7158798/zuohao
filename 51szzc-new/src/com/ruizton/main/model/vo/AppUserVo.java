package com.ruizton.main.model.vo;

/**
 * app 个人中心 用户信息显示
 * Created by zygong on 17-4-5.
 */
public class AppUserVo {
    private String favatarUrl; // 头像url
    private int fid;
    private String realName;// 用户名称
    private String loginName; // 登录名
    private boolean fisTelValidate;//手机认证
    private boolean fisMailValidate;//邮件认证
    private String phone; // 手机号
    private String mail; // 邮箱
    private int vipLevel; // 会员等级
    private int status; // 用户认证状态
    private boolean isTradePassword; // 是否绑定交易密码
    private int kycLevel; // kyc认证级别 0 未认证 1身份认证完成  2 银行卡认证完成   3审核中  4已完成  5审核未通过
    private String rejection;  //拒绝原因
    private boolean fisGoogleBind;

    public boolean isFisGoogleBind() {
        return fisGoogleBind;
    }

    public void setFisGoogleBind(boolean fisGoogleBind) {
        this.fisGoogleBind = fisGoogleBind;
    }

    public String getRejection() {
        return rejection;
    }

    public void setRejection(String rejection) {
        this.rejection = rejection;
    }

    public String getFavatarUrl() {
        return favatarUrl;
    }

    public void setFavatarUrl(String favatarUrl) {
        this.favatarUrl = favatarUrl;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public boolean isFisTelValidate() {
        return fisTelValidate;
    }

    public void setFisTelValidate(boolean fisTelValidate) {
        this.fisTelValidate = fisTelValidate;
    }

    public boolean isFisMailValidate() {
        return fisMailValidate;
    }

    public void setFisMailValidate(boolean fisMailValidate) {
        this.fisMailValidate = fisMailValidate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public boolean isTradePassword() {
        return isTradePassword;
    }

    public void setTradePassword(boolean tradePassword) {
        isTradePassword = tradePassword;
    }

    public int getKycLevel() {
        return kycLevel;
    }

    public void setKycLevel(int kycLevel) {
        this.kycLevel = kycLevel;
    }
}
