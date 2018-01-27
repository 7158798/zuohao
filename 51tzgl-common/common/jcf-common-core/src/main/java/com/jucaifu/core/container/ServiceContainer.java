package com.jucaifu.core.container;

import com.alibaba.dubbo.container.Container;
import com.jucaifu.common.log.LOG;

/**
 * ServiceContainer
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/15.
 */
public class ServiceContainer implements Container {

    @Override
    public void start() {
        LOG.dTag(this, "ServiceContainer start");
    }

    @Override
    public void stop() {
        LOG.dTag(this, "ServiceContainer stop");
    }
}
