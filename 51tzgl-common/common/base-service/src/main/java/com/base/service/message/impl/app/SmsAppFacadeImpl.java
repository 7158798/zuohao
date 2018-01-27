package com.base.service.message.impl.app;

import com.base.facade.message.service.app.SmsAppFacade;
import com.base.service.message.impl.SmsFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * @author luwei
 * @Date 16-10-26 下午4:23
 */
@Service("smsAppFacade")
public class SmsAppFacadeImpl extends SmsFacadeImpl implements SmsAppFacade {
}
