package com.jucaifu.core.utils;

import com.jucaifu.common.configs.GlobalConfig;
import com.jucaifu.common.log.LOG;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

/**
 * DubboProviderHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/10.
 */
public final class DubboProviderHelper {

    /**
     * Start void.
     *
     * @param serviceName the service name
     */
    public static void start(String serviceName) {

        start(serviceName, true);
    }

    /**
     * Start provider.
     *
     * @param serviceName       the service name
     * @param needShowDetailLog the need show detail log
     */
    public static void start(String serviceName, boolean needShowDetailLog) {

        if (StringUtils.isEmpty(serviceName)) {
            serviceName = " ";
        }

        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(GlobalConfig.SPRING_XML_SCAN_EXPRESSION);

            if (needShowDetailLog) {
                LOG.i(DubboProviderHelper.class, "Dubbo Provider [" + serviceName + "] start...");
            }

            context.start();

        } catch (Exception e) {
            LOG.e(DubboProviderHelper.class, "== DubboProvider context [" + serviceName + "]start error:", e);
        }

        if (needShowDetailLog) {
            LOG.i(DubboProviderHelper.class, "Dubbo Provider [" + serviceName + "] started ");
        }

        synchronized (DubboProviderHelper.class) {

            while (true) {

                try {
                    DubboProviderHelper.class.wait();
                } catch (InterruptedException e) {
                    LOG.e(DubboProviderHelper.class, "==[" + serviceName + "] synchronized error:", e);
                }
            }
        }
    }
}
