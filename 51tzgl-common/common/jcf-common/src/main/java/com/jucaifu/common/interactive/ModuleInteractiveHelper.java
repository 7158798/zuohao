package com.jucaifu.common.interactive;

import java.io.UnsupportedEncodingException;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.UrlHelper;
import org.springframework.stereotype.Service;

/**
 * ModuleInteractiveHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/17.
 */
@Service
public class ModuleInteractiveHelper {

    /**
     * 实现模块间的远程调用
     *
     * @param moduleInteractiveVo the module interactive vo
     * @return string string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public String invoke(ModuleInteractiveVo moduleInteractiveVo) throws UnsupportedEncodingException {

        //1：根据moduleId去获取到该模块部署的信息
        String moduleId = moduleInteractiveVo.getModuleId();
        ModulePo modulePo = ModuleManager.getInstance().getModulePo(moduleId);

        if (modulePo == null) {
            LOG.w(this, "当前模块不存在---->");
            return null;
        }

        String queryJsonStr = JsonHelper.obj2JsonStr(moduleInteractiveVo);
        //2：构建queryJsonStr
        LOG.d(this, queryJsonStr);
        String queryJsonStrEncode = UrlHelper.encode(queryJsonStr);

        //3：拼接一个远程调用的URL
        String fullUrlStr = modulePo.getFullInvokeUrl(queryJsonStrEncode);

        LOG.d(this, "fullUrlStr=" + fullUrlStr);

        //4：使用URL进行远程调用，流式操作
        return HttpClientHelper.sendGetRequest(fullUrlStr,false);

    }


    /**
     * 实现模块间的远程调用
     *
     * @param <T>           the type parameter
     * @param moduleInteractiveVo the module interactive vo
     * @param retObjCls 要转换成的业务对象的class
     * @return 业务对象模型 t
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public <T> T invoke(ModuleInteractiveVo moduleInteractiveVo, Class<T> retObjCls) throws UnsupportedEncodingException {

        String jsonStr = invoke(moduleInteractiveVo);

        T retObj = JsonHelper.jsonStr2Obj(jsonStr, retObjCls);

        return retObj;
    }


}
