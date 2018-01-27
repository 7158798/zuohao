package com.otc.facade.sys.pojo.poex;

import com.otc.facade.sys.pojo.po.AuditProcess;

import java.io.Serializable;

/**
 * Created by lx on 17-5-8.
 */
public class AuditProcessEx implements Serializable {

    private AuditProcess process;
    private Long id;
    private String roleName1;
    private String roleName2;
    private String roleName3;
    private String modifiedName;

    public AuditProcess getProcess() {
        return process;
    }

    public void setProcess(AuditProcess process) {
        this.process = process;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }
}
