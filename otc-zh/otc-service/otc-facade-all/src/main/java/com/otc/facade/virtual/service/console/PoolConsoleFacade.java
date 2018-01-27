package com.otc.facade.virtual.service.console;

import com.otc.facade.virtual.pojo.vo.PoolVo;

/**
 * Created by lx on 17-4-19.
 */
public interface PoolConsoleFacade {

    PoolVo queryCountByConditionPage(PoolVo vo);
}
