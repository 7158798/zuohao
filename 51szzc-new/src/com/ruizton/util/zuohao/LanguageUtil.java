package com.ruizton.util.zuohao;

import com.ruizton.main.log.LOG;
import com.ruizton.util.MyResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 国际化处理
 * Created by luwei on 17-3-29.
 * 新建工具类原因：枚举中无法获取国际化信息，不能在controller、service中处理
 * 此工具类放在controller>front下，放util包中，messageSource无法注入
 */
public class LanguageUtil {

    //国际化资源文件
    private  static  MyResourceBundleMessageSource messageSource;

    public  MyResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }

    public  void setMessageSource(MyResourceBundleMessageSource messageSource) {
        LanguageUtil.messageSource = messageSource;
    }

    /**
     * 根据用户选定的语言，获取国际化文件，指定key的value
     * @param msgKey
     * @return
     * 不带格式化参数
     */
    public static String i18nMsg(String msgKey) {
        String message = "";
        try {
            Locale locale = LocaleContextHolder.getLocale();
            message = messageSource.getMessage(msgKey, null, "Default", locale);
            return  message;
        }catch (Exception e) {
            LOG.i(LanguageUtil.class, "国际化信息读取失败，"+e.getMessage());
            LOG.e(LanguageUtil.class , "国际化信息读取失败", e);
        }
        return message;
    }

    /**
     * 根据用户选定的语言，获取国际化文件，指定key的value
     * @param msgKey
     * @return
     * 带格式化参数
     */
    public static String i18nMsg(String msgKey, Object[] args) {
        String message = "";
        try {
            Locale locale = LocaleContextHolder.getLocale();
            message = messageSource.getMessage(msgKey, args, "Default", locale);
            return  message;
        }catch (Exception e) {
            LOG.i(LanguageUtil.class, "国际化信息读取失败，"+e.getMessage());
            LOG.e(LanguageUtil.class , "国际化信息读取失败", e);
        }
        return message;
    }
}
