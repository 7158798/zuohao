package com.otc.service.virtual.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.service.console.UserAddressConsoleFacade;
import com.otc.service.virtual.impl.UserAddressFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-19.
 */
@Component
@Service
public class UserAddressConsoleFacadeImpl extends UserAddressFacadeImpl implements UserAddressConsoleFacade {
}
