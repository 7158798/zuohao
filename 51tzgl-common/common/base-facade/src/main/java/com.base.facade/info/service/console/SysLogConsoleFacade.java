package com.base.facade.info.service.console;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.LogModulePo;
import com.base.facade.info.pojo.po.SysLogPo;
import com.base.facade.info.pojo.vo.SysLogVo;

import java.util.List;

/**
 * SysLogFacade
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/08.
 */
public interface SysLogConsoleFacade {

    /**
     * 获取系统操作日志列表
     *
     * @param vo the vo
     * @return the sys log list
     * @throws BaseCommonBizException the log biz exception
     */
    SysLogVo getSysLogList(SysLogVo vo) throws BaseCommonBizException;


    /**
     * 添加系统日志
     *
     * @param po the po
     * @throws BaseCommonBizException the log biz exception
     */
    void addSysLog(SysLogPo po) throws BaseCommonBizException;

    /**
     * 修改系统日志
     *
     * @param po
     * @throws BaseCommonBizException
     */
    void editSysLog(SysLogPo po) throws BaseCommonBizException;

    /**
     * 查看操作日志明细
     *
     * @param uuid
     * @param methordName
     * @return
     * @throws BaseCommonBizException
     */
    SysLogPo viewSysLogDetail(String uuid, String methordName) throws BaseCommonBizException;

    /**
     * 根据remart查询日志
     *
     * @param remart
     * @return
     */
    SysLogPo queryByRemart(String remart);

}
