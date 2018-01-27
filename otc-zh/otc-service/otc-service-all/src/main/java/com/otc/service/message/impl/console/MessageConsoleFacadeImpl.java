package com.otc.service.message.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.message.service.console.MessageConsoleFacade;
import com.otc.service.message.impl.MessageFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by a123 on 17-6-15.
 */
@Component
@Service
public class MessageConsoleFacadeImpl extends MessageFacadeImpl implements MessageConsoleFacade {
}
