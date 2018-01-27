package com.base.service.version.impl.app;

import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.pojo.vo.AppVersionAppApiVo;
import com.base.facade.version.service.app.MobileVersionAppFacade;
import com.base.service.pool.BaseServiceBizPool;
import com.base.service.version.impl.MobileVersionFacadeImpl;
import com.base.service.version.utils.MobilVersionUtil;
import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by yong on 15-12-4.
 */
@Service("mobileVersionAppFacade")
public class MobileVersionAppFacadeImpl extends MobileVersionFacadeImpl implements MobileVersionAppFacade {

    @Override
    public MobileVersion getAppLatestMobileVersion(String appName, String deviceType) {

        LOG.dStart(this, "查询app版本开始");
        AppVersionAppApiVo req = new AppVersionAppApiVo(appName, deviceType, true);
        LOG.dEnd(this, "查询app版本结束");

        return BaseServiceBizPool.getInstance().mobileVersionBiz.selectOpenVersion(req);
    }

    @Override
    public boolean isForcedUpdate(String version, String appName, String deviceType) {

        LOG.dStart(this, "判断是否是强制跟新开始");
        boolean flag = false;
        if (StringUtils.isBlank(version) || StringUtils.isBlank(appName) || StringUtils.isBlank(deviceType)) {
            flag = true;
            return flag;
        }
        //todo 去掉
        deviceType = MobilVersionUtil.GetDeviceType(deviceType);
        AppVersionAppApiVo req = new AppVersionAppApiVo(appName, deviceType, true);
        MobileVersion mobileVersion = BaseServiceBizPool.getInstance().mobileVersionBiz.selectOpenVersion(req);
        if (mobileVersion == null) {
            flag = true;
        } else {
            int type = MobilVersionUtil.checkAppVersion(version, mobileVersion.getVersion());
            if (type == 2) {
                flag = true;
            }
            LOG.d(this, flag);
            LOG.dEnd(this, "判断是否是强制更新结束");
        }
        return flag;
    }
}
