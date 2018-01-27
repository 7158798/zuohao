package com.jucaifu.common.interactive;

/**
 * ModuleManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/20.
 */
public class ModuleManager {


    public static final String CUSTOMER_MODULE_ID = "customer";

    private static ModuleManager sInstance = null;

    private ModuleManager() {
    }

    public static ModuleManager getInstance() {

        if (sInstance == null) {
            synchronized (ModuleManager.class) {
                if (sInstance == null) {
                    sInstance = new ModuleManager();
                }
            }
        }

        return sInstance;
    }

    public ModulePo getModulePo(String moduleId) {

        ModulePo modulePo = null;

        if (CUSTOMER_MODULE_ID.equals(moduleId)) {
            //暂时没有模块管理，只是简单的做个测试数据
            modulePo = new ModulePo();
            modulePo.setDeploySchema("http://");
            modulePo.setDeployIP("127.0.0.1");
            modulePo.setDeployPort("9090");
            modulePo.setInteractiveUrl("/jucaifu-customermgr/customer/invoke");
        }

        return modulePo;
    }


}
