package com.jucaifu.common.context;

import com.jucaifu.common.log.LOG;
import org.springframework.context.ApplicationContext;

/**
 * ApplicationContextHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/31.
 */
public class ApplicationContextHelper {

    private static ApplicationContextHelper sInstance = null;

    private ApplicationContext applicationContext = null;

    private ApplicationContextHelper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ApplicationContextHelper getInstance() {

        if (sInstance == null) {
            synchronized (ApplicationContextHelper.class) {
                if (sInstance == null) {
                    sInstance = new ApplicationContextHelper();
                }
            }
        }

        return sInstance;
    }


    /**
     * Sets application context.
     *
     * @param applicationContext the application context
     */
    public void setApplicationContext(ApplicationContext applicationContext) {

        synchronized (sInstance) {
            LOG.d(this, "setApplicationContext, notifyAll");
            sInstance.applicationContext = applicationContext;
            sInstance.notifyAll();
        }
    }

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {

        synchronized (sInstance) {

            while (applicationContext == null) {
                try {
                    LOG.d(this, "getApplicationContext, wait...");

                    sInstance.wait(20000);

                    if (applicationContext == null) {
                        LOG.e(this, "Have been waiting for ApplicationContext to be set for 20s", new Exception());
                    }

                } catch (InterruptedException ex) {
                    LOG.e(this, "getApplicationContext, wait interrupted", ex);
                }
            }

            return applicationContext;
        }
    }


    /**
     * Gets bean.
     *
     * @param name the name
     * @return the bean
     */
    public <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * Get bean.
     *
     * @param <T>          the type parameter
     * @param requiredType the required type
     * @return the t
     */
    public <T> T getBean(Class<T> requiredType) {
        return (T) getApplicationContext().getBean(requiredType);
    }

    /**
     * Print all beans.
     */
    public void printAllBeans() {

        String[] allBeans = applicationContext.getBeanDefinitionNames();

        LOG.d(this, "allBeans count=" + allBeans.length);

        for (String beanName : allBeans) {
            LOG.d(this, beanName);
        }
    }

}
