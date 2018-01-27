package com.base.service.user.impl.app;

import com.base.facade.user.service.app.UserLoginAppFacade;
import com.base.service.user.impl.UserLoginFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * @author luwei
 * @Date 12/27/16 5:58 PM
 */
@Service("userLoginAppFacade")
public class UserLoginAppFacadeImpl extends UserLoginFacadeImpl implements UserLoginAppFacade{
}
