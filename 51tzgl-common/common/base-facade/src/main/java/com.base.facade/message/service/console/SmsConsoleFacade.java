package com.base.facade.message.service.console;

import com.base.facade.message.pojo.po.Sms;
import com.base.facade.message.pojo.po.SmsSendObj;
import com.base.facade.message.pojo.vo.SmsVo;
import com.base.facade.message.service.SmsFacade;
import java.util.List;

/**
 * @author luwei
 * @Date 16-10-26 下午4:17
 */
public interface SmsConsoleFacade extends SmsFacade{

    Long addSms(Sms sms);

    Long updateSms(Sms sms, Boolean isReloadFile);

    void updateSendStatus(Sms sms);

    void deleteById(Long id);

    Sms queryById(Long id);

    /**
     * 根据短信id查询短信发送对象
     * @param smsId
     * @return
     */
    List<SmsSendObj> querySendObjListBySmsId(Long smsId);

    String queryFilePath(Long id);

    SmsVo querySmsPageListByConditionPage(SmsVo smsVo);

    /**
     * 查询待发送的短信
     * @param status 待发送状态
     * @return
     */
    List<Sms> queryWaitSendSms(int status);

}
