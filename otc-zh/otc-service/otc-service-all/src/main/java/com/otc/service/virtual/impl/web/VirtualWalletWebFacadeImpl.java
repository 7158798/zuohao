package com.otc.service.virtual.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.service.web.VirtualWalletWebFacade;
import com.otc.service.virtual.impl.VirtualWalletFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-19.
 */
@Component
@Service
public class VirtualWalletWebFacadeImpl extends VirtualWalletFacadeImpl implements VirtualWalletWebFacade {
}
