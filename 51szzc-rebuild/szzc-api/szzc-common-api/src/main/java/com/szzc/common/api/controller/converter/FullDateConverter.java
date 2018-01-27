package com.szzc.common.api.controller.converter;

import com.jucaifu.common.util.DateHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * FullDateConverter
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/6.
 */
@Component
public class FullDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        return DateHelper.string2Date(source, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
    }
}
