package com.jucaifu.common.constants;

import com.jucaifu.common.configs.ApiVersionManager;

/**
 * ApiBasePathConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/16.
 */
public interface ApiBasePathConstant extends ApiType {


    String V = ApiVersionManager.V;
//////////////////////////////
//// Api模块分类
//////////////////////////////
    /**
     * 注意点：C/S 架构必须控制版本号 The APP _ API.
     */
    String APP_API = "/" + APP + V;
    /**
     * The WECHAT _ API.
     */
    String WECHAT_API = "/" + WECHAT + V;
    /**
     * The CONSOLE _ API.
     */
    String CONSOLE_API = "/" + CONSOLE + V;
    /**
     * The WEB _ API.
     */
    String WEB_API = "/" + WEB + V;


//////////////////////////////
//// Api基本CRUD
//////////////////////////////

    /**
     * The PATH _ add.
     */
    String PATH_ADD = "/add";

    /**
     * The PATH _ update.
     */
    String PATH_UPDATE = "/update";

    /**
     * The PATH _ delete.
     */
    String PATH_DELETE = "/delete";

    /**
     * The PATH _ list.
     */
    String PATH_LIST = "/list";

    /**
     * The PATH _ upload.
     */
    String PATH_UPLOAD = "/upload";

    /**
     * The PATH _ queryJson.
     */
    String PATH_QUERY_JSON = "/{queryJson}";

//////////////////////////////
//// Api 模块名称
//////////////////////////////

    /**
     * The MODULE _ mobile _ version.
     */
    String MODULE_MOBILE_VERSION = "/mobileVersion";
    /**
     * The MODULE _ active.
     */
    String MODULE_ACTIVE = "/mobileActive";
    /**
     * The MODULE _ organization.
     */
    String MODULE_ORGANIZATION = "/finorg";
    /**
     * The MODULE _ permission.
     */
    String MODULE_PERMISSION = "/permission";
    /**
     * 权限子模块:公司
     */
    String MODULE_PERMISSION_COMPANY = MODULE_PERMISSION + "/company";
    /**
     * 权限子模块:部门
     */
    String MODULE_PERMISSION_DEPT = MODULE_PERMISSION + "/dept";
    /**
     * 权限子模块:用户角色
     */
    String MODULE_PERMISSION_ROLE = MODULE_PERMISSION + "/role";
    /**
     * 权限子模块:系统用户
     */
    String MODULE_PERMISSION_SYSUSER = MODULE_PERMISSION + "/sysuser";
    /**
     * 权限子模块:资源
     */
    String MODULE_PERMISSION_resource = MODULE_PERMISSION + "/resource";
    /**
     * 权限子模块:操作
     */
    String MODULE_PERMISSION_action = MODULE_PERMISSION + "/action";

    /**
     * 邮件短信模块
     */
    String MODULE_MAIL_SMS = "/mailsms";
    /**
     * 邮件短信子模块:邮件
     */
    String MODULE_MAIL = MODULE_MAIL_SMS + "/mail";
    /**
     * 邮件短信子模块:短信
     */
    String MODULE_SMS = MODULE_MAIL_SMS + "/sms";

    /**
     * 系统配置模块
     */
    String MODULE_SYS_CONFIG = "/sysconfig";

    /**
     * 产品模块
     */
    String MODULE_PRODUCT = "/product";

    /**
     * 订单模块
     */
    String MODULE_ORDER = "/order";

    /**
     * 用户模块
     */
    String MODULE_USER = "/user";

    /**
     * 日志模块
     */
    String MODULE_LOG = "/log";

    /**
     * 报表模块
     */
    String MODULE_REPORT = "/report";

}
