package com.ruizton.main.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fsystemoperatorlog")
public class Fsystemoperatorlog {


    public Fsystemoperatorlog(){

    }

    private Long id;

    /**
     * User Agent,所属表字段为tbl_system_operator_log.user_agent
     */
    private String userAgent;

    /**
     * 操作模块,所属表字段为tbl_system_operator_log.module
     */
    private String module;

    /**
     * 操作时间,所属表字段为tbl_system_operator_log.operator_date
     */
    private Date operatorDate;

    /**
     * 操作人,所属表字段为tbl_system_operator_log.user_id
     */
    private int userId;

    /**
     * 操作人登录名,所属表字段为tbl_system_operator_log.login_name
     */
    private String loginName;

    /**
     * IP,所属表字段为tbl_system_operator_log.ip
     */
    private String ip;

    /**
     * 操作代码,所属表字段为tbl_system_operator_log.operator_code
     */
    private String operatorCode;

    /**
     * 操作名称,所属表字段为tbl_system_operator_log.operator_name
     */
    private String operatorName;

    /**
     * 执行类名,所属表字段为tbl_system_operator_log.class_name
     */
    private String className;

    /**
     * 执行方法名,所属表字段为tbl_system_operator_log.method_name
     */
    private String methodName;

    /**
     * 操作是否成功,所属表字段为tbl_system_operator_log.issuccess
     */
    private Boolean issuccess;

    /**
     * 请求参数,所属表字段为tbl_system_operator_log.request_parameters
     */
    private String requestParameters;



    private String responseResult;

    /**
     * 操作内容,所属表字段为tbl_system_operator_log.operator_content
     */
    private String operatorContent;

    /**
     * 备注,所属表字段为tbl_system_operator_log.remart
     */
    private String remart;

    @Column(name = "operatorDate")
    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }

    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }




    @Column(name = "loginName")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    @Column(name = "operatorCode")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode == null ? null : operatorCode.trim();
    }

    @Column(name = "operatorName")
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    @Column(name = "className")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    @Column(name = "methodName")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    @Column(name = "issuccess")
    public Boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Boolean issuccess) {
        this.issuccess = issuccess;
    }

    @Column(name = "requestParameters")
    public String getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(String requestParameters) {
        this.requestParameters = requestParameters == null ? null : requestParameters.trim();
    }

    @Column(name = "responseResult")
    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult == null ? null : responseResult.trim();
    }

    @Column(name = "operatorContent")
    public String getOperatorContent() {
        return operatorContent;
    }

    public void setOperatorContent(String operatorContent) {
        this.operatorContent = operatorContent == null ? null : operatorContent.trim();
    }

    @Column(name = "remart")
    public String getRemart() {
        return remart;
    }

    public void setRemart(String remart) {
        this.remart = remart == null ? null : remart.trim();
    }

    @Column(name = "userAgent")
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Column(name = "module")
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
