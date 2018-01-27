package com.otc.api.console.base;

import com.otc.api.console.service.ConsoleService;
import com.otc.common.api.controller.AbsBaseController;
import com.jucaifu.common.constants.CommonConstant;
import com.jucaifu.common.constants.WebConstant;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * BaseConsoleCtrl
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/22.
 */
public class BaseConsoleCtrl extends AbsBaseController implements WebConstant, CommonConstant {

    @Resource(name = "consoleService")
    protected ConsoleService otc;

    /**
     * 判断部分数据类型是否为空
     * @param paramArray
     * @return
     */
    public boolean checkParam(Object... paramArray){
        boolean flag = false;
        for(Object param : paramArray){
            if(null == param){
                return flag;
            }
            if(param instanceof String){
                if(((String) param).trim().length() == 0){
                    return flag;
                }
            }
            if(param instanceof ArrayList){
                if(((ArrayList) param).size() < 1){
                    return flag;
                }
            }
        }
        return true;
    }
}
