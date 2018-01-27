package com.base.service.version.impl;

import java.util.List;
import java.util.Map;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.version.enums.VersionAppCodeType;
import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.pojo.vo.AppVersionAppApiVo;
import com.base.facade.version.pojo.vo.AppVersionVo;
import com.base.facade.version.service.MobileVersionFacade;
import com.base.service.pool.BaseServiceBizPool;
import com.base.service.version.constants.ConfigConstant;
import com.base.service.version.utils.MobilVersionUtil;
import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;

/**
 * MobileVersionFacadeImpl
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/15.
 */
public class MobileVersionFacadeImpl implements MobileVersionFacade, ConfigConstant {

    /**
     * 获取所有App产品最新版本
     *
     * @return
     */
    @Override
    public List<MobileVersion> getAppLatestVersionList() {

        AppVersionAppApiVo vo = new AppVersionAppApiVo(true);
        vo = BaseServiceBizPool.getInstance().mobileVersionBiz.selectOpenVersionList(vo);
        return vo.fatchTransferList();
    }

    /**
     * 历史版本列表
     *
     * @return
     */
    @Override
    public AppVersionAppApiVo getTagAppVersionList(AppVersionAppApiVo vo) {

        LOG.dStart(this, "查询指定app历史版本列表开始");
//        if (StringUtils.isBlank(appName) || StringUtils.isBlank(deviceType) || page <= 0 || size <= 0) {
//            throw new MobileBizException(10050001, "appCode、deviceType、page、size请求参数不能为空");
//        }
        LOG.dEnd(this, "<1>校验数据");
        LOG.dTag(this, "<2>主业务流程");

        List<MobileVersion> mobileVersions = BaseServiceBizPool.getInstance().mobileVersionBiz.selectAppListByConditionPage(vo);
        vo.setResultList(mobileVersions);

        LOG.dEnd(this, "查询指定app历史版本列表结束");
        return vo;
    }

    /**
     * 编辑app版本
     *
     * @param uuid：uuid
     * @param deviceType 设备类型
     * @param version：版本
     * @param url：地址
     * @param updateMessage：描述信息
     */
    @Override
    public void editAppVersion(String uuid, String deviceType, String version, String url, String updateMessage) {

        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(version) || StringUtils.isBlank(url) || StringUtils.isBlank(updateMessage)) {
            throw new BaseCommonBizException(10050001, "请求参数不能为空");
        }
        MobileVersion mobileVersion = BaseServiceBizPool.getInstance().mobileVersionBiz.select(uuid);
        if (mobileVersion == null) {
            throw new BaseCommonBizException(10050002, "未查询到id对应的版本信息");
        }
        AppVersionVo vo = new AppVersionVo();
        vo.setUuid(uuid);
        vo.setVersion(version);
        vo.setUpdateMessage(updateMessage);
        vo.setUrl(url);
        vo.setDeviceType(deviceType);
        MobilVersionUtil.addOperationVersion(vo, BaseServiceBizPool.getInstance().mobileVersionBiz, UPDATE);
    }

    /**
     * @return
     */
    @Override
    public Map<String, String> appProductList() {

        Map<String, String> map = VersionAppCodeType.getMap();
        return map;
    }

    /**
     * @param appCode
     * @param deviceType
     * @param version
     * @param url
     * @param updateMessage
     */
    @Override
    public void addAppVersion(String appCode, String deviceType, String version, String url, String updateMessage) {
        AppVersionVo vo = new AppVersionVo();
        vo.setUrl(url);
        vo.setUpdateMessage(updateMessage);
        vo.setDeviceType(deviceType);
        vo.setVersion(version);
        vo.setAppCode(appCode);
        MobilVersionUtil.addOperationVersion(vo, BaseServiceBizPool.getInstance().mobileVersionBiz, ADD);
    }

    @Override
    public int updateGoWebStatus(AppVersionAppApiVo vo) {
        return BaseServiceBizPool.getInstance().mobileVersionBiz.updateForceGoWebStatus(vo);
    }


    @Override
    public MobileVersion queryByUuid(String uuid) {
        return BaseServiceBizPool.getInstance().mobileVersionBiz.queryByUuid(uuid);
    }
}
