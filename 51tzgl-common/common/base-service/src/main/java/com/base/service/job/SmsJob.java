package com.base.service.job;


import com.base.common.sms.client.SmsDayuUtils;
import com.base.common.sms.facade.response.SmsResultResp;
import com.jucaifu.common.util.DateHelper;
import java.util.List;

import com.jucaifu.common.util.JsonHelper;
import com.base.service.pool.BaseServiceBizPool;
import com.base.facade.message.enums.SmsStatus;
import com.base.facade.message.pojo.po.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * 短信定时发送
 * @author luwei
 * @Date 11/16/16 6:07 PM
 */
@Component
public class SmsJob implements JobConstant {

    private static final Logger logger = LoggerFactory.getLogger(SmsJob.class);


    /**
     * 短信定时发送
     */
    @Scheduled(cron = "0 2/10 * * * ? ")
    private void sendSms(){

        if (isJobClose()){
            return;
        }

        logger.info("短信定时发送开始： " + DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        //获取待发送的短信
        List<Sms> list = BaseServiceBizPool.getInstance().smsBiz.queryWaitSendSms(SmsStatus.WAIT.getStatus());
        if(list == null || list.size() == 0){
            return;
        }

        for(Sms sms : list){
            if(sms.getSendObjList() == null || sms.getSendObjList().size() == 0){
                continue;
            }
            boolean flag = true;
            String sendResultJson = "";
            List<String> phoneList = sms.convertList(sms.getSendObjList());
            if(phoneList != null && phoneList.size() > 0){
                for(String phoneStr : phoneList){
                    logger.info("本次发送短信的手机号,phoneNumber:{}", phoneStr);
                    SmsResultResp resp = SmsDayuUtils.sendSms(phoneStr, sms.getContent());
                    sendResultJson = JsonHelper.obj2JsonStr(resp);
                    if(!resp.isResult()){
                        flag = false;
                        logger.info("短信发送失败,本次发送的手机号为:{},内容为:{}", phoneStr, sms.getContent());
                    }
                }
            }

            sms.setActualSendingTime(new Date());
            sms.setSendResultJson(sendResultJson);
            sms.setId(sms.getId());

            if(flag){
                sms.setStatus(SmsStatus.SUCCESS.getStatus());
            }else{
                sms.setStatus(SmsStatus.FAILED.getStatus());
            }
            //修改数据库中的字段值信息
            BaseServiceBizPool.getInstance().smsBiz.updateSendStatus(sms);
        }

        logger.info("短信定时发送结束： " + DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }


}
