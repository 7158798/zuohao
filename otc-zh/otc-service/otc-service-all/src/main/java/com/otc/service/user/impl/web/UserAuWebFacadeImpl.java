package com.otc.service.user.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.user.service.UserAuFacade;
import com.otc.facade.user.service.web.UserAuWebFacade;
import com.otc.service.user.impl.UserAuFacadeImpl;

/**
 * Created by fenggq on 17-4-25.
 */
@Service
public class UserAuWebFacadeImpl extends UserAuFacadeImpl implements UserAuWebFacade{
}
