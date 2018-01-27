package com.szzc.api.app.ctrl;

import com.szzc.api.app.constants.ApiConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.html.HTML;

/**
 * Created by fenggq on 17-5-24.
 */
public class BaseCtrl implements ApiConstants{


    /**
     * 获取Get请求URL
     * @return
     */
    protected String buildGetUrl(String url,String token){
        if(StringUtils.isNotBlank(token)){
            if(url.contains("?")){
                url= url+"&loginToken="+token;
            }else{
                url = url+"?loginToken="+token;
            }
        }
        return BASE_URL+url;


    }

    /**
     * 获取Post url
     * @param url
     * @return
     */
    protected String buildPostUrl(String url){
        String s = BASE_URL+url;
        return BASE_URL+url;
    }
}
