/**
 * Creation Date:2015年9月9日下午2:32:13
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved.
 */
package com.base.service.version.utils;

import java.util.Date;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.pojo.vo.AppVersionAppApiVo;
import com.base.facade.version.pojo.vo.AppVersionVo;
import com.base.facade.version.pojo.vo.RemarkVo;
import com.base.service.version.biz.MobileVersionBiz;
import com.base.service.version.constants.ConfigConstant;
import com.jucaifu.common.enums.EnumDeviceType;
import com.jucaifu.common.exceptions.general.GeneralException;
import com.jucaifu.common.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * MobilVersion工具类
 *
 * @author zhijun
 */
public class MobilVersionUtil implements ConfigConstant {

    private MobilVersionUtil() {
    }

    /**
     * 是否可添加为新版本
     *
     * @param oldVersion
     * @param newVersion
     * @return
     */
    public static boolean addNewVersionBoolean(String oldVersion, String newVersion) {
        if (StringUtils.equals(oldVersion, newVersion)) {
            return false;
        }

        boolean hasNew = false;
        String[] old = oldVersion.split("\\.");
        String[] addNew = newVersion.split("\\.");

        if (addNew.length != VERSION_LENGTH || old.length != VERSION_LENGTH) {
            return false;
        }
        for (int i = 0; i < VERSION_LENGTH; i++) {
            int oldNum = Integer.parseInt(old[i].trim());
            int newNum = Integer.parseInt(addNew[i].trim());
            if (oldNum < newNum) {
                hasNew = true;
                break;
            } else if (oldNum > newNum) {
                break;
            }
        }
        return hasNew;
    }

    /**
     * 是否存在强制更新
     *
     * @param userVersion
     * @param systemVersion
     * @return
     */
    public static boolean hasNewVersion(String userVersion, String systemVersion) {
        if (StringUtils.equals(userVersion, systemVersion)) {
            return false;
        }

        boolean hasNew = false;
        String[] users = userVersion.split("\\.");
        String[] systems = systemVersion.split("\\.");

        for (int i = 0; i < systems.length - 1; i++) {
            int userNum;
            if (i < users.length) {
                userNum = Integer.parseInt(users[i].trim());
            } else {
                userNum = 0;
            }
            int sysNum = Integer.parseInt(systems[i].trim());
            if (userNum < sysNum) {
                hasNew = true;
                break;
            } else if (userNum > sysNum) {
                break;
            }
        }
        return hasNew;
    }

    /**
     * 添加版本验证
     *
     * @param version
     * @return
     */
    public static boolean addVersion(String version) {

        String[] versionArg = version.split("\\.");

        if (versionArg.length != VERSION_LENGTH) {
            return false;
        }
        return true;
    }

    /**
     * 添加或更新app新版本
     *
     * @param vo
     * @param mobileVersionBiz
     * @param opera
     * @throws GeneralException
     */

    public static void addOperationVersion(AppVersionVo vo, MobileVersionBiz mobileVersionBiz, String opera) {
        if (vo == null) {
            throw new BaseCommonBizException(10050004, "参数中存在为空情况或参数异常!");
        }
        String appCode;
        String deviceType;
        String version = vo.getVersion();
        String url = vo.getUrl();
        String updateMessage = vo.getUpdateMessage();
        boolean active = true;
        Date nowDate = new Date();
        String uuid = null;
        RemarkVo _remarkVo = new RemarkVo();
        String remark;

        MobileVersion mobileVersion;
        MobileVersion appversion = new MobileVersion();
        MobileVersion openVersion = null;

        if (StringUtils.equals(opera, ADD)) {
            appCode = vo.getAppCode();
            deviceType = vo.getDeviceType();
            openVersion = mobileVersionBiz.selectOpenVersion(new AppVersionAppApiVo(appCode, deviceType, active));
            AppVersionAppApiVo appVersionAppApiVoBeanVo = new AppVersionAppApiVo(appCode, deviceType, version, active);
            mobileVersion = mobileVersionBiz.selectNewVersion(appVersionAppApiVoBeanVo);
            if (mobileVersion != null) {
                throw new BaseCommonBizException(10050002, "数据库中存在此版本，不能重复添加!");
            }
            if (openVersion != null) {
                if (!MobilVersionUtil.addNewVersionBoolean(openVersion.getVersion(), version)) {
                    throw new BaseCommonBizException(10050002, "修改的版本号必须大于当前版本!");
                }
                _remarkVo.setOldVersion(openVersion.getVersion());

                if (MobilVersionUtil.hasNewVersion(openVersion.getVersion(), version)) {
                    _remarkVo.setUpdateRemark(EXISTS_CAST_UPDATE);
                } else {
                    _remarkVo.setUpdateRemark(EXISTS_UPDATE);
                }

            } else {
                if (!MobilVersionUtil.addVersion(version)) {
                    throw new BaseCommonBizException(10050003, "版本号规则错误！");

                }
                _remarkVo.setOldVersion("");
                _remarkVo.setUpdateRemark(EXISTS_CAST_UPDATE);
            }
        } else if (StringUtils.equals(opera, UPDATE)) {
            uuid = vo.getUuid();
            if (uuid == null) {
                throw new BaseCommonBizException(10050004, "参数中存在为空情况或参数异常!");
            }
            mobileVersion = mobileVersionBiz.select(uuid);
            if (mobileVersion == null) {
                throw new BaseCommonBizException(10050009, "数据库中未查到此记录！");
            }
            appCode = mobileVersion.getAppName();
            //deviceType = mobileVersion.getDeviceType();
            deviceType = vo.getDeviceType();

        } else {
            throw new BaseCommonBizException(10050004, "参数中存在为空情况或参数异常！");
        }

        appversion.setAppName(appCode);
        appversion.setDeviceType(deviceType);
        appversion.setDownloadUrl(url);
        appversion.setModifiedDate(nowDate);
        appversion.setUpdateDate(nowDate);
        appversion.setUpdateInfo(updateMessage);
        appversion.setUuid(uuid);


        int i;
        if (StringUtils.equals(opera, ADD)) {
            remark = JsonHelper.obj2JsonStr(_remarkVo);
            appversion.setRemark(remark);
            appversion.setVersion(version);
            appversion.setCreateDate(nowDate);
            appversion.setActive(active);
            appversion.setForceGoweb(Boolean.FALSE);
            i = mobileVersionBiz.insert(appversion);
            if (i == 0) {
                throw new BaseCommonBizException(10050007, "插入数据失败！");
            }

            if (openVersion != null) {
                openVersion.setActive(false);
                openVersion.setUpdateDate(new Date());
                int j = mobileVersionBiz.update(openVersion);
                if (j == 0) {
                    throw new BaseCommonBizException(10050008, "更新数据失败！");
                }
            }
        } else if (StringUtils.equals(opera, UPDATE)) {
            appversion.setVersion(version);
            appversion.setRemark(mobileVersion.getRemark());
            Boolean active1 = mobileVersion.getActive();
            appversion.setActive(active1);
            appversion.setForceGoweb(mobileVersion.getForceGoweb());
            appversion.setCreateDate(mobileVersion.getCreateDate());
            i = mobileVersionBiz.update(appversion);
            if (i == 0) {
                throw new BaseCommonBizException(10050007, "插入数据失败！");
            }
        }

    }

    public static int checkAppVersion(String versionMobile, String versionActive) {
        int updateType = 0;
        if (StringUtils.equals(versionActive, versionMobile)) {
            return updateType;
        }
        String[] server = versionActive.split("\\.");
        String[] mobile = versionMobile.split("\\.");
        if (server.length != VERSION_LENGTH || mobile.length != VERSION_LENGTH) {
            return updateType;
        }
        for (int i = 0; i < VERSION_LENGTH; i++) {
            int oldNum = Integer.parseInt(mobile[i].trim());
            int newNum = Integer.parseInt(server[i].trim());
            if (oldNum < newNum && i < VERSION_LENGTH - 1) {
                updateType = 2;
                break;
            }
            if (oldNum < newNum && i == VERSION_LENGTH - 1) {
                updateType = 1;
                break;
            } else if (oldNum > newNum) {
                break;
            }
        }
        return updateType;
    }

    public static String GetDeviceType(String deviceType) {

        String result = null;
        if (StringUtils.equals(deviceType, EnumDeviceType.ANDORID_MOBILD.getValue())) {
            result = "android";
        }
        if (StringUtils.equals(deviceType, EnumDeviceType.IOS_MOBILE.getValue())) {
            result = "ios";
        }
        if (StringUtils.equals(deviceType, EnumDeviceType.ANDROID_PAD.getValue())) {
            result = "androidPad";
        }
        if (StringUtils.equals(deviceType, EnumDeviceType.IPAD.getValue())) {
            result = "ipad";
        }
        return result;
    }
}
