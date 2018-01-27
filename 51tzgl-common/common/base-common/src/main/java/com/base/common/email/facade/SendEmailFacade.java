package com.base.common.email.facade;

import com.base.common.email.facade.request.EmailParamBean;
import com.base.common.email.facade.request.SendEmailReq;
import com.base.common.email.facade.response.SendEmailResultResp;

/**
 * Created by luwei on 17-6-16.
 */
public interface SendEmailFacade {

     SendEmailFacade initParam(EmailParamBean req);

     /**邮件发送**/
     SendEmailResultResp sendEmail(SendEmailReq req);
}
