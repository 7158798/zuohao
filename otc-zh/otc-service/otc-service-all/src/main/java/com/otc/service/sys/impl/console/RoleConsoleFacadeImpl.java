package com.otc.service.sys.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.sys.pojo.vo.RoleVo;
import com.otc.facade.sys.service.console.RoleConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.sys.impl.RoleFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-26.
 */
@Component
@Service
public class RoleConsoleFacadeImpl extends RoleFacadeImpl implements RoleConsoleFacade {

    @Override
    public RoleVo queryRoleByConditionPage(RoleVo vo) {
        return OTCBizPool.getInstance().roleBiz.queryRoleByConditionPage(vo);
    }
}
