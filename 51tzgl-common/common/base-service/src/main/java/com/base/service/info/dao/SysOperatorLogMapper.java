package com.base.service.info.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.info.pojo.po.SysLogPo;
import com.base.facade.info.pojo.vo.SysLogVo;

import java.util.List;

public interface SysOperatorLogMapper extends BaseMapper<SysLogPo, SysLogVo> {

    /**
     * 按条件查询历史日志列表
     *
     * @param vo
     * @return
     */
    List<SysLogPo> getSysLogByConditionPage(SysLogVo vo);

    /**
     * 通过uuid或方法名查日志
     *
     * @param uuid
     * @param methordName
     * @return
     */
    SysLogPo selectByUidOrMethordName(String uuid, String methordName);

    /**
     * 根据remart查询日志
     *
     * @param remart
     * @return
     */
    SysLogPo queryByRemart(String remart);
}
