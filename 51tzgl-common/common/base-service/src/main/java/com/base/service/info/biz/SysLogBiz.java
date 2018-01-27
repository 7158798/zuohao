package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.service.info.dao.SysOperatorLogMapper;
import com.base.facade.info.pojo.po.LogModulePo;
import com.base.facade.info.pojo.po.SysLogPo;
import com.base.facade.info.pojo.vo.SysLogVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SysLogBiz extends AbsBaseBiz<SysLogPo, SysLogVo, SysOperatorLogMapper> {

    @Autowired
    private SysOperatorLogMapper sysOperatorLogMapper;

    /**
     * 后台操作日志-按条件查询历史日志列表
     *
     * @param vo the vo
     * @return sys log list
     */
    public SysLogVo getSysLogList(SysLogVo vo) {

        List<SysLogPo> list = sysOperatorLogMapper.getSysLogByConditionPage(vo);
        vo.setResultList(list);

        return vo;
    }

    public void editSysLog(SysLogPo sysLogPo) {
        if(sysLogPo == null || StringUtils.isBlank(sysLogPo.getUuid())) {
            throw new BaseCommonBizException("参数异常");
        }

        //根据uuid查询日志
        SysLogPo vo = sysOperatorLogMapper.select(sysLogPo.getUuid());
        if(vo == null) {
            throw new BaseCommonBizException("产生脏读，请核对后再操作");
        }


        vo.setUserAgent(sysLogPo.getUserAgent());
        sysOperatorLogMapper.update(vo);

    }



    /**
     * 通过uuid或者方法名查询日志
     *
     * @param uuid
     * @param methordName
     * @return
     */
    public SysLogPo selectByUidOrMethordName(String uuid, String methordName) {

        return sysOperatorLogMapper.selectByUidOrMethordName(uuid, methordName);
    }


    /**
     * 根据remart查询日志
     *
     * @param remart
     * @return
     */
    public SysLogPo queryByRemart(String remart) {
        return sysOperatorLogMapper.queryByRemart(remart);
    }

    @Override
    public SysOperatorLogMapper getDefaultMapper() {
        return sysOperatorLogMapper;
    }
}
