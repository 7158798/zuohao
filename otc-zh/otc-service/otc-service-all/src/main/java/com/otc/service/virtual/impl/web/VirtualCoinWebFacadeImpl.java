package com.otc.service.virtual.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.service.web.VirtualCoinWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.VirtualCoinFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-5-16.
 */
@Component
@Service
public class VirtualCoinWebFacadeImpl extends VirtualCoinFacadeImpl implements VirtualCoinWebFacade {

}
