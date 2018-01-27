package com.base.service.version.biz;

import java.util.List;

import com.base.facade.version.pojo.po.MobileVersion;
import com.base.facade.version.pojo.vo.AppVersionAppApiVo;
import com.base.facade.version.pojo.vo.MobileVersionVo;
import com.base.service.version.dao.MobileVersionMapper;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现类
 */
@Service
@Transactional
public class MobileVersionBiz extends AbsBaseBiz<MobileVersion, MobileVersionVo,MobileVersionMapper> {

    @Autowired
    private MobileVersionMapper mobileVersionMapper;

    /**
     * 根据appName，deviceType，version，active字段
     * 查询数据库中App最新版本
     *
     * @return
     */
    public MobileVersion selectNewVersion(AppVersionAppApiVo vo) {
        return mobileVersionMapper.selectNewVersion(vo);
    }

    // 根据appName，deviceType，active字段　查询数据库中App最新版本
    public MobileVersion selectOpenVersion(AppVersionAppApiVo vo) {
        return mobileVersionMapper.selectOpenVersion(vo);
    }

    // 根据appName查询数据库中所有版本列表
    public List<MobileVersion> selectAppListByConditionPage(AppVersionAppApiVo vo) {
        return mobileVersionMapper.selectAppListByConditionPage(vo);
    }

    // 查询数据库中所有启用app版本列表
    public AppVersionAppApiVo selectOpenVersionList(AppVersionAppApiVo vo) {
        List<MobileVersion> mobileVersions = mobileVersionMapper.selectOpenVersionList(vo);
        vo.setResultList(mobileVersions);
        return vo;
    }

    //查询指定产品的有效的app最新版本。
    public MobileVersion checkVersionByAppCodeAndDeviceType(AppVersionAppApiVo vo) {
        return mobileVersionMapper.checkVersionByAppCodeAndDeviceType(vo);
    }

    //更新是否强制跳转web的状态
    public int updateForceGoWebStatus(AppVersionAppApiVo vo) {
        return mobileVersionMapper.updateForceGoWebStatus(vo);
    }

    /**
     * 根据uuid查询版本信息
     * @param uuid
     * @return
     */
    public MobileVersion queryByUuid(String uuid) {
        return mobileVersionMapper.select(uuid);
    }

    @Override
    public MobileVersionMapper getDefaultMapper() {
        return mobileVersionMapper;
    }
}
