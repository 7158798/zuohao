package com.base.service.message.dao;


import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.message.pojo.po.SmsSendObj;
import com.base.facade.message.pojo.vo.SmsSendObjVo;
import java.util.List;

public interface SmsSendObjMapper extends BaseMapper<SmsSendObj,SmsSendObjVo> {


    /**
     * 根据短信id逻辑删除发送对象
     * @param id
     */
    void deleteBySmsId(Long id);

    /**
     * 根据短信id物理删除发送对象
     * @param smsId
     */
    void physicalDeleteBySmsId(Long smsId);

    List<SmsSendObj> queryList(Long smsId);
}