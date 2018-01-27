package com.szzc.api.three.base;

import com.jucaifu.common.constants.CommonConstant;
import com.jucaifu.common.constants.WebConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.ReflectHelper;
import com.szzc.api.three.annotation.Required;
import com.szzc.api.three.exceptions.ThreeBizException;
import com.szzc.api.three.pojo.BaseApiReq;
import com.szzc.api.three.service.ThreeService;
import com.szzc.api.three.utils.api.SignHelper;
import com.szzc.common.api.controller.AbsBaseController;
import com.szzc.facade.api.pojo.po.UserApi;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * BaseCtrl
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/22.
 */
public class BaseCtrl extends AbsBaseController implements WebConstant, CommonConstant {
    public static final String JsonEncode = "application/json;charset=UTF-8" ;

    String APP_ASSETS_PRE = "/app/assets";

    String APP_HTML = ".html";

    String OSSURL = "https://51szzc.oss-cn-shanghai.aliyuncs.com/static/app/images/logo_rmb.png";

    @Resource(name = "threeService")
    protected ThreeService three;

    public static final String SIGN = "sign";

    /**
     * 校验参数
     * @param req
     */
    protected UserApi validation(BaseApiReq req){
        Set<Field> fields =  ReflectHelper.getFieldSet(req);
        TreeMap<String,String> treeMap = new TreeMap<>();
        String sign = null;
        for (Field field : fields){
            String name = field.getName();
            Object obj = validation(req,field);
            if (obj != null){
                if (SIGN.equals(name)){
                    sign = obj.toString();
                    continue;
                }
                treeMap.put(name,obj.toString());
            }
        }
        UserApi userApi = getUserApi(req.getApi_key());
        String temp = SignHelper.getSign(treeMap, userApi.getSecretKey());
        if (!StringUtils.equals(temp,sign)){
            throw new ThreeBizException("签名验证失败");
        }
        return userApi;
    }

    private Object validation(Object obj,Field field){
        Object value = null;
        try {
             value = ReflectHelper.getValueByFieldName(obj, field.getName());
        } catch (Exception ex) {
            LOG.e(this,ex.getMessage(),ex);
        }
        Required required = field.getAnnotation(Required.class);
        if (required != null && required.value()){
            if (value == null){
                throw new ThreeBizException(field.getName() + "不能为空");
            }
        }
        return value;
    }
    /**
     * 校验用户apikey
     * @param apiKey
     * @return
     */
    protected UserApi getUserApi(String apiKey){
        UserApi api = three.userApiFacade.getUserApiByKey(apiKey);
        if (api == null){
            throw new ThreeBizException("api_key输入有误");
        }
        return api;
    }
}
