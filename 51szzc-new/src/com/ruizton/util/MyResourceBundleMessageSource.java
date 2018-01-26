package com.ruizton.util;

import com.ruizton.main.log.LOG;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

/**
 * Created by luwei on 17-3-20.
 */
public class MyResourceBundleMessageSource extends ResourceBundleMessageSource {

    public static Map<Locale, Map<String, String>>  map = new HashMap<>();

    private static String baseName_temp = Configuration.getValue("i18n.basename");



    /**
     * 获取所有的key
     * @param locale
     */
    public Set<String> getAllKey(Locale locale){
        ResourceBundle temp = this.getResourceBundle(baseName_temp, locale);
        Set<String> set = temp.keySet();
        return set;
    }

    /**
     * 给全局的map赋值
     */
    public Map<String, String> loadi18nPro(Locale locale) {
        String message = "";
        if(map.get(locale) == null) {
            Set<String> set = getAllKey(locale);
            if(set == null || set.size() == 0) {
                LOG.i(this, "国际化读取资源文件失败");
            }
            Map<String, String> map1 = new HashMap<>();
            for(String str : set) {
                message = this.getMessage(str, null, "Default", locale);
                map1.put(str, message);
            }
            map.put(locale, map1);
        }

        return map.get(locale);
    }

}
