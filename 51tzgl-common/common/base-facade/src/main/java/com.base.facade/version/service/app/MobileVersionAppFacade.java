package com.base.facade.version.service.app;

import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.service.MobileVersionFacade;

/**
 * Created by yong on 15-12-4.
 */
public interface MobileVersionAppFacade extends MobileVersionFacade {

    /**
     * 检测手机app版本
     *
     * @param appName：appn名称
     * @param deviceType：手机类型，ios还是案桌
     * @return
     */
    MobileVersion getAppLatestMobileVersion(String appName, String deviceType);

    /**
     * 检测是否是强制跟新
     *
     * @param version
     * @param appName
     * @param deviceType
     * @return
     */
    boolean isForcedUpdate(String version, String appName, String deviceType);
}
