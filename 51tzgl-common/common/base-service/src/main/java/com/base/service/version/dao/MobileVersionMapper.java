package com.base.service.version.dao;


import java.util.List;

import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.pojo.vo.AppVersionAppApiVo;
import com.base.facade.version.pojo.vo.MobileVersionVo;
import com.jucaifu.core.dao.BaseMapper;

public interface MobileVersionMapper extends BaseMapper<MobileVersion, MobileVersionVo> {

    MobileVersion selectNewVersion(AppVersionAppApiVo vo);

    MobileVersion selectOpenVersion(AppVersionAppApiVo vo);

    List<MobileVersion> selectAppListByConditionPage(AppVersionAppApiVo vo);

    List<MobileVersion> selectOpenVersionList(AppVersionAppApiVo vo);

    MobileVersion checkVersionByAppCodeAndDeviceType(AppVersionAppApiVo vo);

    int updateForceGoWebStatus(AppVersionAppApiVo vo);
}
