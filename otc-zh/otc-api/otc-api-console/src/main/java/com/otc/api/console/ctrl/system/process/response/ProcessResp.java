package com.otc.api.console.ctrl.system.process.response;

/**
 * Created by lx on 17-5-8.
 */
public class ProcessResp {

    private Long id;

    private String type;

    private String roleName1;

    private String roleName2;

    private String roleName3;

    private Boolean isNeedPwd;

    private String modifiedDate;

    private String modifiedUser;



    private Long roleId1;
    private Long roleId2;
    private Long roleId3;



    public Long getRoleId1() {
        return roleId1;
    }

    public void setRoleId1(Long roleId1) {
        this.roleId1 = roleId1;
    }

    public Long getRoleId2() {
        return roleId2;
    }

    public void setRoleId2(Long roleId2) {
        this.roleId2 = roleId2;
    }

    public Long getRoleId3() {
        return roleId3;
    }

    public void setRoleId3(Long roleId3) {
        this.roleId3 = roleId3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(Boolean isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getRoleName1() {
        return roleName1;
    }

    public void setRoleName1(String roleName1) {
        this.roleName1 = roleName1;
    }

    public String getRoleName2() {
        return roleName2;
    }

    public void setRoleName2(String roleName2) {
        this.roleName2 = roleName2;
    }

    public String getRoleName3() {
        return roleName3;
    }

    public void setRoleName3(String roleName3) {
        this.roleName3 = roleName3;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }
}
