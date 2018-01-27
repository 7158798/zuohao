package com.base.service.message.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.message.pojo.po.Sms;
import com.base.facade.message.pojo.vo.SmsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsMapper extends BaseMapper<Sms, SmsVo> {

    List<Sms> querySmsPageListByConditionPage(SmsVo smsVo);

    /**
     * 物理删除短信通知
     * @param id
     */
    void physicalDelete(Long id);


    /**
     * 查询待发送的短信
     * @param status
     * @return
     */
    List<Sms> queryWaitSendSms(@Param("status") int status);
}

