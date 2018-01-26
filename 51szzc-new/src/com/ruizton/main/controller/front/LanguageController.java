package com.ruizton.main.controller.front;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by luwei on 17-2-24.
 */
@Controller
public class LanguageController extends BaseController{


    /**
     * 语言切换
     */
    @RequestMapping("language")
    public ModelAndView language(HttpServletRequest request, HttpServletResponse response, String language){

        language=language.toLowerCase();
        if(language==null||language.equals("")){
            return new ModelAndView("redirect:/");
        }else{
            if(language.equals("zh_cn")){
                resolver.setLocale(request, response, Locale.CHINA );
            }else if(language.equals("en")){
                resolver.setLocale(request, response, Locale.ENGLISH );
            }else if(language.equals("zh_tw")) {
                resolver.setLocale(request, response, Locale.TAIWAN);
            }else{
                resolver.setLocale(request, response, Locale.CHINA );
            }
        }

        return new ModelAndView("redirect:/");
    }


    @RequestMapping(value = "getmessage", produces = {JsonEncode})
    @ResponseBody
    public String getMessage(String msgkey) {
        JSONObject jsonObject = new JSONObject();

        String message = this.i18nMsg(msgkey);
        jsonObject.accumulate("message", message);
        return jsonObject.toString();
    }


    @RequestMapping(value = "i18npro", produces = {JsonEncode})
    @ResponseBody
    public String i18nPro() {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> map = this.loadi18nPro();
        jsonObject.accumulate("i18n_map", map);
        return jsonObject.toString();
    }


}
