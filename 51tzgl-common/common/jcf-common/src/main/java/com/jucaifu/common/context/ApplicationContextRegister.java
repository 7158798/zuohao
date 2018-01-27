package com.jucaifu.common.context;

import com.jucaifu.common.log.LOG;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextRegister
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/31.
 */
public class ApplicationContextRegister implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        LOG.d(this, "ApplicationContext registed");

        ApplicationContextHelper.getInstance().setApplicationContext(applicationContext);

        SpringPropReaderHelper.updateApplicationContext(applicationContext);
    }

}
