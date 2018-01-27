package com.otc.common.api.controller;

import com.jucaifu.common.constants.ApiBasePathConstant;
import com.jucaifu.common.interactive.ModuleInteractiveVo;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.UrlHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AbsModuleInteractiveController
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/20.
 */
@Deprecated
public abstract class AbsModuleInteractiveController extends AbsBaseController {

    private static final String PATH_MODULE_INVOKE = "/invoke" + ApiBasePathConstant.PATH_QUERY_JSON;

    /**
     * Module call.
     *
     * @param queryJson the query json
     * @return the object
     */
    @RequestMapping(value = PATH_MODULE_INVOKE, method = RequestMethod.GET)
    @ResponseBody
    public Object moduleCall(@PathVariable String queryJson) {

        //1.解析queryJson -> model
        queryJson = UrlHelper.dencodeHexStr(queryJson);
        ModuleInteractiveVo model = encodeJsonStr2Obj(queryJson, ModuleInteractiveVo.class);

        LOG.d(this, model);

        //2.执行真正的业务调用
        Object ret = executeModuleCall(model.getCmd(), model.getQueryJsonStr());

        return ret;
    }

    /**
     * Execute module call.
     *
     * @param cmd the cmd
     * @param queryJsonStr the query json str
     * @return the object
     */
    protected abstract Object executeModuleCall(String cmd, String queryJsonStr);
}
