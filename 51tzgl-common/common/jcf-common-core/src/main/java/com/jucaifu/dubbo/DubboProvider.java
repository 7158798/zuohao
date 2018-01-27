package com.jucaifu.dubbo;

import com.jucaifu.core.utils.DubboProviderHelper;

/**
 * DubboProvider 启动Dubbo服务
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/29.
 */
public class DubboProvider {

    public static void main(String[] args) {

        String serviceName = null;

        if (args != null && args.length == 1) {
            serviceName = args[0];
        }

        DubboProviderHelper.start(serviceName);
    }
}
