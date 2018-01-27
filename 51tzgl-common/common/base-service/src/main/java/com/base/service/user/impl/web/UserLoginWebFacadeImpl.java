package com.base.service.user.impl.web;

import com.base.facade.user.service.web.UserLoginWebFacade;
import com.base.service.user.impl.UserLoginFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * @author luwei
 * @Date 12/27/16 5:58 PM
 */
@Service("userLoginWebFacade")
public class UserLoginWebFacadeImpl extends UserLoginFacadeImpl implements UserLoginWebFacade{
}
