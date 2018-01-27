package com.otc.facade.sys.service.console;

import com.otc.facade.sys.pojo.vo.RoleVo;
import com.otc.facade.sys.service.RoleFacade;

/**
 * Created by lx on 17-4-26.
 */
public interface RoleConsoleFacade extends RoleFacade {

    RoleVo queryRoleByConditionPage(RoleVo vo);
}
