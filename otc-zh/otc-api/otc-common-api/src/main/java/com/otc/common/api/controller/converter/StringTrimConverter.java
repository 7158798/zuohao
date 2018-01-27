package com.otc.common.api.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * StringTrimConverter
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/6.
 */
@Component
public class StringTrimConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {

        //去掉字符串两边空格，如果去除后为空设置为null
        if (source != null) {
            source = source.trim();
            if (source.equals("")) {
                source = null;
            }
        }
        return source;
    }
}
