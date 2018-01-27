package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * Created by fenggq on 17-5-24.
 */
@Controller
public class AppApiCtrl extends BaseCtrl {

    @ResponseBody
    @RequestMapping(value = "/appApi",produces=JsonEncode_Text)
    public String appApi(HttpServletRequest request) throws Exception {
        LOG.d(this,"进入"+request.getRequestURI());
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParam = getParamStr(parameterMap);
        String s = HttpClientHelper.sendGetRequest(BASE_URL+"/appApi.html?"+requestParam,Boolean.FALSE);
        LOG.d(this,"完成");
        return s;
    }

    public String getParamStr(Map<String, String[]> map){
        String str = "";
        if(map == null || map.isEmpty()){
            return str;
        }
        for(Map.Entry<String, String[]> m : map.entrySet()){
            str += m.getKey() + "=" + m.getValue()[0] + "&";
        }
        if(StringUtils.isNotBlank(str)){
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
