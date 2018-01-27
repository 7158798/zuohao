package com.szzc.fentrustLog.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.szzc.facade.fentrustLog.pojo.dto.FentrustDto;
import com.szzc.facade.fentrustLog.pojo.vo.FentrustVo;
import com.szzc.facade.fentrustLog.service.FentrustFacade;
import com.szzc.pool.ThreeBizPool;

import java.util.List;

/**
 * Created by zygong on 17-7-21.
 */
@Service
public class FentrustFacadeImpl implements FentrustFacade {

    @Override
    public FentrustVo getFentrustByConditionPage(FentrustVo vo) {
        return ThreeBizPool.getInstance().fentrustBiz.getFentrustByConditionPage(vo);
    }
}
