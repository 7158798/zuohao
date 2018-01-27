package com.base.service.message.impl.console;

import com.base.service.message.impl.SmsFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import com.base.facade.message.pojo.po.Sms;
import com.base.facade.message.pojo.po.SmsSendObj;
import com.base.facade.message.pojo.vo.SmsVo;
import com.base.facade.message.service.console.SmsConsoleFacade;
import com.base.service.message.impl.SmsFacadeImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luwei
 * @Date 16-10-26 下午4:21
 */
@Service("smsConsoleFacade")
public class SmsConsoleFacadeImpl extends SmsFacadeImpl implements SmsConsoleFacade  {

    @Override
    public Long addSms(Sms sms) {
        return BaseServiceBizPool.getInstance().smsBiz.addSms(sms);
    }

    @Override
    public Long updateSms(Sms sms, Boolean isReloadFile) {
        return BaseServiceBizPool.getInstance().smsBiz.updateSms(sms, isReloadFile);
    }


    @Override
    public void updateSendStatus(Sms sms) {
        BaseServiceBizPool.getInstance().smsBiz.updateSendStatus(sms);
    }

    @Override
    public void deleteById(Long id) {
        BaseServiceBizPool.getInstance().smsBiz.deleteById(id);
    }

    @Override
    public Sms queryById(Long id) {
        return BaseServiceBizPool.getInstance().smsBiz.queryById(id);
    }

    /**
     * 根据短信id查询短信发送对象
     * @param smsId
     * @return
     */
    public List<SmsSendObj> querySendObjListBySmsId(Long smsId) {
        return BaseServiceBizPool.getInstance().smsBiz.querySendObjListBySmsId(smsId);
    }

    @Override
    public SmsVo querySmsPageListByConditionPage(SmsVo smsVo) {
        return BaseServiceBizPool.getInstance().smsBiz.querySmsPageListByConditionPage(smsVo);
    }

    @Override
    public String queryFilePath(Long id) {
        return BaseServiceBizPool.getInstance().smsBiz.queryFilePath(id);
    }


    @Override
    public List<Sms> queryWaitSendSms(int status) {
        return BaseServiceBizPool.getInstance().smsBiz.queryWaitSendSms(status);
    }
}
