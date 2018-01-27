package com.otc.api.console.ctrl.system.employee.request;

/**
 * Created by yangyy on 15-12-8.
 */
public class EmployeeEditReq extends EmployeeAddReq {

    /**
     * 雇员id
     */
    private Long employeeId;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
