package com.base.facade.version.service;

import java.util.List;
import java.util.Map;

import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.pojo.vo.AppVersionAppApiVo;


/**
 * AppHomeBanneMgrFacade
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/15.
 */
public interface MobileVersionFacade {

    /**
     * 获取所有App产品最新版本
     *
     * @return
     */
    List<MobileVersion> getAppLatestVersionList();

    /**
     * 历史版本列表
     *
     * @return
     */
    AppVersionAppApiVo getTagAppVersionList(AppVersionAppApiVo vo);

    /**
     * 编辑app版本
     *
     * @param id：uuid
     * @param deviceType 设备类型
     * @param version：版本
     * @param url：地址
     * @param updateMessage：描述信息
     */
    void editAppVersion(String id, String deviceType, String version, String url, String updateMessage);

    /**
     * app名称列表
     *
     * @return
     */
    Map<String, String> appProductList();

    /**
     * 添加app版本
     *
     * @param appCode
     * @param deviceType
     * @param version
     * @param url
     * @param updateMessage
     */
    void addAppVersion(String appCode, String deviceType, String version, String url, String updateMessage);

    /**
     * 修改是否强制更新
     *
     * @param vo
     * @return
     */
    int updateGoWebStatus(AppVersionAppApiVo vo);

    /**
     * 根据uuid查询版本信息
     * @param uuid
     * @return
     */
    MobileVersion queryByUuid(String uuid);
}
