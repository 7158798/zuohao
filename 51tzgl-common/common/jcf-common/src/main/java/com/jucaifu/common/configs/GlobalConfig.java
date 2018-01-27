package com.jucaifu.common.configs;

/**
 * GlobalConfigConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/10.
 */
public interface GlobalConfig {

    /**
     * Spring XML Scan Expression
     */
    String SPRING_XML_SCAN_EXPRESSION = "classpath*:spring/spring*.xml";

//    /**
//     * 是否为在线状态
//     */
//    Boolean JCF_IS_ONLINE = Boolean.valueOf(SpringPropReaderHelper.getProperty("jucaifu.is.online"));

}
