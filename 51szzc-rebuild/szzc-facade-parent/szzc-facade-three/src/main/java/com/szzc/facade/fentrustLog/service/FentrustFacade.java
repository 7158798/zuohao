package com.szzc.facade.fentrustLog.service;

import com.szzc.facade.fentrustLog.pojo.dto.FentrustDto;
import com.szzc.facade.fentrustLog.pojo.vo.FentrustVo;

import java.util.List;

/**
 * Created by zygong on 17-7-21.
 */
public interface FentrustFacade {
    /**
     * 获取委托列表
     * @param vo
     * @return
     */
    FentrustVo getFentrustByConditionPage(FentrustVo vo);
}
